
package western.alarm_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.view.Window;
import android.view.WindowManager;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements LocationListener
{
	private LocationRequest locationRequest;
	private GoogleApiClient googleApiClient;
    private Alarm alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

		if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

			ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
					LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION );
		}

		locationRequest = new LocationRequest();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		// TODO: get updates every 5 seconds; check if that's  a good number
		locationRequest.setInterval(5000);

		googleApiClient = new GoogleApiClient.Builder(this)
				.addApi(LocationServices.API)
				.build();
    }

	public void setDestination(View view)
	{
		try
		{
			LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
		}
		catch(SecurityException e)
		{
			Log.d("SecurityException", "setDestination is failing");
			e.printStackTrace();
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();
		googleApiClient.connect();
	}

	@Override
	public void onStop()
	{
		super.onStop();
		googleApiClient.disconnect();
	}

	@Override
	public void onLocationChanged(Location location)
	{
		String filename = "destination.txt";
		StringBuilder builder = new StringBuilder();

		double lat = location.getLatitude();
		double lng = location.getLongitude();

		builder.append(location.getLatitude());
		builder.append("\n");
		builder.append(location.getLongitude());

		String fileContents = builder.toString();

		FileOutputStream outputStream = null;
		try
		{
			outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			outputStream.write(fileContents.getBytes());
			outputStream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		Toast.makeText(this, "Destination recorded", Toast.LENGTH_LONG).show();

		// disable location updates because we only need to record it once
		LocationServices.FusedLocationApi.removeLocationUpdates(
				googleApiClient, this);

	}

    public void setAlarm(View view) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        calendar.set(Calendar.HOUR,timePicker.getHour());
        calendar.set(Calendar.MINUTE,timePicker.getMinute());
        Alarm.executionTime = calendar.getTimeInMillis();
        alarm = new Alarm(this);
        alarm.setAlarm(this);
        Log.d("time",String.valueOf(calendar.getTimeInMillis()));


    }
}