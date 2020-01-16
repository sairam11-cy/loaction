package com.example.sapp25;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int Request_location=1;
    Button getlocation;
    TextView showlocation;
    LocationManager locationManager;
    String latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_location);
        showlocation=findViewById(R.id.show_location);
        getlocation=findViewById(R.id.get_location);
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    OnGPS();
                }
                else
                {
                    getLocation();
                }
            }

            private void getLocation() {

                if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_location);

                }
                else
                {
                    Location LocationGPS=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    Location LoacationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    if(LocationGPS!=null)
                    {
                        double lat= LocationGPS.getLatitude();
                        double lon= LocationGPS.getLongitude();
                        latitude=String.valueOf(lat);
                        longitude=String.valueOf(lon);
                        showlocation.setText("Your location:"+"\n"+"latitude="+latitude+"\n"+"longitude="+longitude);

                    }
                    else if(LocationNetwork!=null)
                    {
                        double lat= LocationNetwork.getLatitude();
                        double lon= LocationNetwork.getLongitude();
                        latitude=String.valueOf(lat);
                        longitude=String.valueOf(lon);
                        showlocation.setText("Your location:"+"\n"+"latitude="+latitude+"\n"+"longitude="+longitude);

                    }
                    else if(LocationGPS!=null)
                    {
                        double lat= LoacationPassive.getLatitude();
                        double lon= LoacationPassive.getLongitude();
                        latitude=String.valueOf(lat);
                        longitude=String.valueOf(lon);
                        showlocation.setText("Your location:"+"\n"+"latitude="+latitude+"\n"+"longitude="+longitude);

                    }
                    else{
                        Toast.makeText(MainActivity.this, "cannot get your location", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private void OnGPS() {
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                      builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                          }
                      }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.cancel();
                          }
                      });
                      final AlertDialog alertDialog=builder.create();
                      alertDialog.show();
            }
        });



    }
}
