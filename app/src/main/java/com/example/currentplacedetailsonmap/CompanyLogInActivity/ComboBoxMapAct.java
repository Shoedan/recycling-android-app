package com.example.currentplacedetailsonmap.CompanyLogInActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.Markers;
import com.example.currentplacedetailsonmap.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ComboBoxMapAct extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private static final String TAG = ComboBoxMapAct.class.getSimpleName();
    private GoogleMap map;

    int city_id = -1;

    ArrayList<LatLng> map_markers = new ArrayList<>();

    private FusedLocationProviderClient fusedLocationProviderClient;

    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    private Location lastKnownLocation;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_box_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map3);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        set_spinner();
        setTitle("City Selection Map");
    }

    public void set_spinner(){
        List<String> spinnerArray;
        DatabaseSQLITE db = new DatabaseSQLITE(this);
        spinnerArray = db.get_city_names();
        spinnerArray.add("No Selection");
        int spinner_size = spinnerArray.size();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = findViewById(R.id.spinner2);
        sItems.setAdapter(adapter);
        sItems.setSelection(spinnerArray.size()-1);
        if (getIntent().hasExtra("city")){
            sItems.setSelection(getIntent().getExtras().getInt("city"));
        }
        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position !=spinner_size-1) {
                    LatLng aux = getLocationFromAddress(ComboBoxMapAct.this, String.valueOf(sItems.getSelectedItem()));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(aux.latitude,
                                    aux.longitude), DEFAULT_ZOOM));
                    city_id = position;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do Nothing
            }

        });
    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        this.map = map;

        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(@NonNull Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(@NonNull Marker marker) {
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
                        findViewById(R.id.map2), false);

                TextView title = infoWindow.findViewById(R.id.title);
                title.setText(marker.getTitle());

                TextView snippet = infoWindow.findViewById(R.id.snippet);
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });

        getLocationPermission();

        updateLocationUI();

        getDeviceLocation();

        try {
            setMarkers();
        } catch (SQLException | ExecutionException | InterruptedException throwables) {
            throwables.printStackTrace();
        }



        map.setOnMapLongClickListener(this);
    }

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                @SuppressLint("MissingPermission") Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        map.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }



    @SuppressLint("MissingPermission")
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void setMarkers() throws SQLException, ExecutionException, InterruptedException {
        DatabaseSQLITE dbhelper = new DatabaseSQLITE(ComboBoxMapAct.this);
        List<Markers> markerlist = dbhelper.getMarkers();

        for (int i=0;i<markerlist.size();i++){
            double lat = markerlist.get(i).getLat();
            double lon = markerlist.get(i).getLon();
            map_markers.add(new LatLng(lat,lon));}
        for (int i=0;i<map_markers.size();i++){
            int capacity = markerlist.get(i).getPc() + markerlist.get(i).getPlc() + markerlist.get(i).getPg();
            map.addMarker(new MarkerOptions()
                    .position(map_markers.get(i))
                    .title("Recycle Spot  " + markerlist.get(i).getPoint_name() + " for " + markerlist.get(i).available())
                    .snippet("Total Amount at this location :  " + markerlist.get(i).getTotal())
                    .icon(GetIconAndColor(getApplicationContext(),markerlist.get(i).getTotal(),capacity,markerlist.get(i).isPaper(),markerlist.get(i).isPlastic(),markerlist.get(i).isGlass()))); /// HUE_GREEN FOR UNDER <70 CAPACITY , HUE_YELLOW FOR CAPACITY >=70 <=95 AMD HUE_RED FOR >=95 - 100
        }
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        // below line is use to set bounds to our vector drawable.
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);
        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 72, 72, true);
        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(resized);
    }

    private BitmapDescriptor GetIconAndColor(Context context , int size , int capacity , boolean ispaper , boolean isplastic , boolean isglass){
        double green = 50f/100f * capacity;
        double yellow = 70f/100f * capacity;
        double red = 95f/100f * capacity;
        if (ispaper && size <green && !isplastic && !isglass)
            return BitmapFromVector(context , R.drawable.ic_paper_blue);
        if (ispaper && size >=red && !isplastic && !isglass)
            return BitmapFromVector(context , R.drawable.ic_paper_red);
        if (ispaper && size >=yellow && !isplastic && !isglass)
            return BitmapFromVector(context , R.drawable.ic_paper_yellow);
        if (ispaper && size >=green && !isplastic && !isglass)
            return BitmapFromVector(context , R.drawable.ic_paper_green);

        if (isplastic && size <green && !ispaper && !isglass)
            return BitmapFromVector(context , R.drawable.ic_plastic_blue);
        if (isplastic && size >=red && !ispaper && !isglass)
            return BitmapFromVector(context , R.drawable.ic_plastic_red);
        if (isplastic && size >=yellow && !ispaper && !isglass)
            return BitmapFromVector(context , R.drawable.ic_plastic_yellow);
        if (isplastic && size >=green && !ispaper && !isglass)
            return BitmapFromVector(context , R.drawable.ic_plastic_green);

        if (isglass && size <green && !ispaper && !isplastic)
            return BitmapFromVector(context , R.drawable.ic_glass_blue);
        if (isglass && size >=red && !ispaper && !isplastic)
            return BitmapFromVector(context , R.drawable.ic_glass_red);
        if (isglass && size >=yellow && !ispaper && !isplastic)
            return BitmapFromVector(context , R.drawable.ic_glass_yellow);
        if (isglass && size >=green && !ispaper && !isplastic)
            return BitmapFromVector(context , R.drawable.ic_glass_green);

        if (size < green)
            return BitmapFromVector(context , R.drawable.blue_recycle);
        if (size >= red)
            return BitmapFromVector(context , R.drawable.red_recycle);
        if (size >= yellow)
            return BitmapFromVector(context , R.drawable.yellow_recycle);

        return BitmapFromVector(context , R.drawable.green_recycle);

    }

    @Override
    public void onMapLongClick(LatLng point) {
        Intent myIntent = new Intent(this, StatsAndDelete.class);
        DatabaseSQLITE dbhelper = new DatabaseSQLITE(this.getBaseContext());
        List<Markers> markerlist;
        markerlist = dbhelper.getMarkers();
        for (int i=0;i<markerlist.size();i++){
            if (Math.abs(markerlist.get(i).getLat() - point.latitude) < 0.005 && Math.abs(markerlist.get(i).getLon() - point.longitude) < 0.005){
                myIntent.putExtra("id",markerlist.get(i).getId());
                myIntent.putExtra("company",getIntent().getStringExtra("company"));
                myIntent.putExtra("actid",3);
                myIntent.putExtra("city",city_id);
                startActivity(myIntent);
                finish();
            }
        }
    }

}