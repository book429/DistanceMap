package com.nc.distancemap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

//created by nc ( calculates the distance between your location to the given Longitude and Latitude )// https://github.com/Codrata/LongitudeLatitude

public class MainActivity extends AppCompatActivity {

    private TextView LatitudeView, LongitudeView;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get user location
        client = LocationServices.getFusedLocationProviderClient(this);
        Button button = findViewById(R.id.button);
        LongitudeView = findViewById(R.id.longitude);
        requestPermissions();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null){
                            double longitude = location.getLongitude();
                            double latitude = location.getLatitude();
                            String latitudeString = String.valueOf(latitude);
                            String longitudeString = String.valueOf(longitude);
                            TextView longitudeView = findViewById(R.id.longitude);
                            TextView latitudeView = findViewById(R.id.latitude);
                            EditText latB = findViewById(R.id.latitudeBedit);
                            EditText longB = findViewById(R.id.longitudeBedit);

                            double latitudeB = Double.parseDouble(latB.getText().toString());
                            double longitudeB = Double.parseDouble(longB.getText().toString());

                            longitudeView.setText("Longitude: " + longitudeString);
                            latitudeView.setText("Latitude: " + latitudeString);

                            Location locationA = new Location("point A");
                            locationA.setLatitude(latitude);
                            locationA.setLongitude(longitude);
                            Location locationB = new Location("point B");
                            locationB.setLatitude(latitudeB);
                            locationB.setLongitude(longitudeB);

                            TextView finalDistance = findViewById(R.id.finaldistance);

                            float distance = locationA.distanceTo(locationB);

                            //convert distance to km
                            distance = (float) (distance * 0.001);
                            String finalDist = String.valueOf(distance);
                            finalDistance.setText(finalDist + " " + "km");
                        }

                    }
                });
            }
        });

    }
    private void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

}