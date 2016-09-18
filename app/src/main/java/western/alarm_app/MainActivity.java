
package western.alarm_app;

import android.content.Context;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
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
    }

	public void setDestination(View view)
	{
		GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
				.addApi(LocationServices.API)
				.build();

		try
		{
			Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
			String filename = "destination.txt";
			StringBuilder builder = new StringBuilder();

			double lat = location.getLatitude();
			double lng = location.getLongitude();

			Toast.makeText(this, "Latitude is " + lat + "Longitude is " + lng, Toast.LENGTH_SHORT).show();

			builder.append(location.getLatitude());
			builder.append("\n");
			builder.append(location.getLongitude());

			String fileContents = builder.toString();

			FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(fileContents.getBytes());
			outputStream.close();
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

	}


    public void setAlarm(View view) {
        
    }
}