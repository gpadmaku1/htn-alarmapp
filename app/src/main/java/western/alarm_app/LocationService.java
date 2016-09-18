package western.alarm_app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LocationService extends Service
{
	private Alarm alarm;

	public static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 1;
	private static final long LOCATION_INTERVAL = 1000;
	private static final float LOCATION_DISTANCE = 0.5f;

	private LocationManager locationManager;
	private MyLocationListener locationListener;

	private double destinationLatitude;
	private double destinationLongitude;
	private double minDistance = 5;

	@Override
	public void onCreate()
	{
		super.onCreate();
		alarm = new Alarm(this);
		locationListener = new MyLocationListener(LocationManager.GPS_PROVIDER);
		initializeLocationManager();

		try
		{
			FileInputStream destinationFileStream = openFileInput("destination.txt");
			InputStreamReader streamReader = new InputStreamReader(destinationFileStream);
			BufferedReader bufferedReader = new BufferedReader(streamReader);

			destinationLatitude = Double.parseDouble(bufferedReader.readLine());
			destinationLongitude = Double.parseDouble(bufferedReader.readLine());

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL,
					LOCATION_DISTANCE, (android.location.LocationListener) locationListener);
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();

		if(locationManager != null)
		{
			try
			{
				locationManager.removeUpdates((android.location.LocationListener) locationListener);
			}
			catch(SecurityException e)
			{
				e.printStackTrace();
			}
		}
	}

	private class MyLocationListener implements LocationListener
	{
		private Location lastLocation;

		public MyLocationListener(String provider)
		{
			lastLocation = new Location(provider);
		}

		@Override
		public void onLocationChanged(Location location)
		{
			lastLocation = location;
			double currentLatitude = lastLocation.getLatitude();
			double currentLongitude = lastLocation.getLongitude();

			double distance = distFrom(currentLatitude, currentLongitude, destinationLatitude, destinationLongitude);

			if(distance <= minDistance)
			{
				stopAlarm();
			}
		}
	}

	private void stopAlarm()
	{
		try
		{
			locationManager.removeUpdates((android.location.LocationListener) locationListener);
			alarm.disableAlarm();
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
		}
	}

	private void initializeLocationManager()
	{
		if(locationManager == null)
		{
			locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		super.onStartCommand(intent, flags, startId);
		alarm.setAlarm(this);

		return START_STICKY;
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	private double distFrom(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371000; //meters
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lng2-lng1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
						Math.sin(dLng/2) * Math.sin(dLng/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double dist = earthRadius * c;

		return dist;
	}
}
