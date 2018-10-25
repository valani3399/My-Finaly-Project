package com.example.softices.pankajtrainee.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.softices.pankajtrainee.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    public double latitued;
    public double longitued;
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private ImageView ivtedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        ivtedirect=(ImageView) findViewById(R.id.iv_redirect);
        ivtedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //encode use = pass uri change web language means sam symbol
                Uri gmmIntentUri = Uri.parse("geo:latitued,longitued?q=" + Uri.encode(""));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if(mapIntent.resolveActivity(getPackageManager())!=null) {
                    startActivity(mapIntent);
                }else {
                    Toast.makeText(MapsActivity.this, "empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
        init();
        permissions.add(ACCESS_FINE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        startLocationUpdates();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        startLocationUpdates();
        updateLocationUI();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //map is redirect
//        mMap.setMyLocationEnabled(true); necessry this sentens
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public void init() {
        //use location sarvies get
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //location services in settings use...
        mSettingsClient = LocationServices.getSettingsClient(this);
        //location result use
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                Toast.makeText(MapsActivity.this, mCurrentLocation + "", Toast.LENGTH_SHORT).show();
                updateLocationUI();
            }

        };
        //this method intilization
        mLocationRequest = new LocationRequest();
        //new method creat
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        //uper method use....addlocationreques
        builder.addLocationRequest(mLocationRequest);
        //Locationsettingrequest in do build
        mLocationSettingsRequest = builder.build();
    }

    private void startLocationUpdates() {
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                                (getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        //update location on set text use
                        updateLocationUI();

                    }
                });
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            latitued = mCurrentLocation.getLatitude();
            longitued = mCurrentLocation.getLongitude();
            LatLng sydney = new LatLng(latitued, longitued);
            //map is update before current markar is remove...
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(sydney).title("My Current location"));
            //use for direct location position set
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            //zoom use this
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f));
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {
                        permissionsRejected.add(perms);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                            builder.setMessage("Hello, you can hide this message by just tapping outside the dialog box!");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent f = new Intent(MapsActivity.this, Dashboard.class);
                                    startActivity(f);
                                    finish();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            return;
                        }
                    }
                }
                break;
        }

    }
}
