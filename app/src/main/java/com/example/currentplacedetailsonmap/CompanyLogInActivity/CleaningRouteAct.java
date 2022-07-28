package com.example.currentplacedetailsonmap.CompanyLogInActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CleaningRouteAct extends AppCompatActivity implements OnMapReadyCallback , GoogleMap.OnMapLongClickListener {

    private static final String TAG = ComboBoxMapAct.class.getSimpleName();
    private GoogleMap map;

    ArrayList<LatLng> map_markers = new ArrayList<>();

    private FusedLocationProviderClient fusedLocationProviderClient;

    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    private Location lastKnownLocation;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    int city_id = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.routemap);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

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
        Spinner sItems = findViewById(R.id.spinneroute);
        sItems.setAdapter(adapter);
        sItems.setSelection(spinnerArray.size()-1);
        if (getIntent().hasExtra("city")){
            sItems.setSelection(getIntent().getExtras().getInt("city")+1);
        }
        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position !=spinner_size-1) {
                    LatLng not_aux = getLocationFromAddress(CleaningRouteAct.this, String.valueOf(sItems.getSelectedItem()));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(not_aux.latitude,
                                    not_aux.longitude), DEFAULT_ZOOM));
                    city_id = position+1;
                    try {
                        setMarkers();
                    } catch (SQLException | ExecutionException | InterruptedException throwables) {
                        throwables.printStackTrace();
                    }
                    draw_route();



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


        map.getUiSettings().setZoomControlsEnabled(true);

        setTitle("Cleaning Route");

        set_spinner();

        map.setOnMapLongClickListener(this);

    }

    public void draw_route(){                   /// Should set traffic mode to optimistic for better routes(untested)
        List<Markers> aux;
        DatabaseSQLITE dbhelper = new DatabaseSQLITE(CleaningRouteAct.this);
        aux = dbhelper.get_marker_from_city(city_id);
        List<LatLng> sorted_points = calc_dist_array(aux);
        for (int z=0;z<sorted_points.size()-1;z++){
            LatLng origin = sorted_points.get(z);
            LatLng dest = sorted_points.get(z+1);
            List<LatLng> path = new ArrayList<>();
            String _origin = origin.latitude + ","+ origin.longitude;
            String _dest = dest.latitude + "," + dest.longitude;
            System.out.println(_origin + " " + _dest);
            //Execute Directions API request
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyAc6R2eTQ91amTW0aksVTm72flhZWdm5q0")
                    .build();
            DirectionsApiRequest req = DirectionsApi.getDirections(context, _origin, _dest);
            try {
                DirectionsResult res = req.await();
                //Loop through legs and steps to get encoded polylines of each step
                if (res.routes != null && res.routes.length > 0) {
                    DirectionsRoute route = res.routes[0];
                    if (route.legs !=null) {
                        for(int i=0; i<route.legs.length; i++) {
                            DirectionsLeg leg = route.legs[i];
                            if (leg.steps != null) {
                                for (int j=0; j<leg.steps.length;j++){
                                    DirectionsStep step = leg.steps[j];
                                    if (step.steps != null && step.steps.length >0) {
                                        for (int k=0; k<step.steps.length;k++){
                                            DirectionsStep step1 = step.steps[k];
                                            EncodedPolyline points1 = step1.polyline;
                                            if (points1 != null) {
                                                //Decode polyline and add points to list of route coordinates
                                                List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                                for (com.google.maps.model.LatLng coord1 : coords1) {
                                                    path.add(new LatLng(coord1.lat, coord1.lng));
                                                }
                                            }
                                        }
                                    } else {
                                        EncodedPolyline points = step.polyline;
                                        if (points != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords = points.decodePath();
                                            for (com.google.maps.model.LatLng coord : coords) {
                                                path.add(new LatLng(coord.lat, coord.lng));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch(Exception ex) {
                Log.e(TAG, ex.getLocalizedMessage());
            }
            //Draw the polyline
            if (path.size() > 0) {
                PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
                map.addPolyline(opts);
            }
        }
    }

    public List<LatLng> calc_dist_array(List<Markers> aux) {
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()));
        for (int i=0;i<aux.size();i++)
            points.add(new LatLng(aux.get(i).getLat(),aux.get(i).getLon()));
        List<LatLng> sorted = new ArrayList<>();
        sorted.add(points.get(0));
        points.remove(points.get(0));
        while (!points.isEmpty()) {
            int j = 1;
            double distance = Double.MAX_VALUE;
            for (int i = 0; i < points.size(); i++) {
                double aux_dist = calc_distance(sorted.get(sorted.size() - 1), points.get(i));
                if (aux_dist < distance) {
                    j = i;
                    distance = aux_dist;
                }
            }
            sorted.add(points.get(j));
            points.remove(points.get(j));
        }
        return sorted;
    }

    private double calc_distance(LatLng pointA , LatLng pointB){
        double x1 = pointA.latitude;
        double y1 = pointA.longitude;
        double x2 = pointB.latitude;
        double y2 = pointB.longitude;
        return Math.sqrt((y2-y1)*(y2-y1) + (x2-x1) * (x2-x1));
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
        DatabaseSQLITE dbhelper = new DatabaseSQLITE(CleaningRouteAct.this);
        List<Markers> markerlist = dbhelper.get_marker_from_city(city_id);
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
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 72, 72, true);
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
                myIntent.putExtra("actid",4);
                myIntent.putExtra("city",city_id);
                startActivity(myIntent);
                finish();
            }
        }
    }



}