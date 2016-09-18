
package western.alarm_app;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

	public void setDestination(View view)
	{
		GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
				.addApi(LocationServices.API)
				.build();

		//Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

	}
}