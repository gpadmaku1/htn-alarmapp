package western.alarm_app;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service implements LocationListener
{
	private Alarm alarm;

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
}