package western.alarm_app;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
	private Alarm alarm;

	public static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 1;

	private GoogleApiClient googleApiClient;
	private Location destination;

	public LocationService()
	{
		alarm = new Alarm(this);
		googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
		// TODO: get destination from file
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onLocationChanged(Location location)
	{

	}

	@Override
	public void onConnected(@Nullable Bundle bundle)
	{

	}

	@Override
	public void onConnectionSuspended(int i)
	{

	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
	{

	}
}
