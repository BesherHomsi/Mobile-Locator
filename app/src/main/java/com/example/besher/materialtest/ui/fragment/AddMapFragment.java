package com.example.besher.materialtest.ui.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.R;
import com.example.besher.materialtest.helpers.MyLocationManager;
import com.example.besher.materialtest.helpers.Utility;
import com.example.besher.materialtest.models.MyCLocation;
import com.example.besher.materialtest.ui.activity.MainActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMapFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private Button addLocation;
    private MapView mapView;


    private ArrayList<MyCLocation> myCLocations = new ArrayList<>();


    private GoogleMap mMap;
    private Marker chosenPosition;
    private MyCLocation currentLocation;


    private EditText searchBar;
    private double home_long;
    private double home_lat;
    private LatLng latLng;
    private MarkerOptions markerOptions;
    private CollationElementIterator locationTv;
    private boolean cameraMoved = false;
    private int GPS_REQUEST = 100;

    public AddMapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isGPSEnabled()) {
            buildAlertMessageNoGps();
        } else {
            processLocation();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        setUpView(root);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();


        addLocation.setOnClickListener(this);
        mapView.getMapAsync(this);

        //myCLocations.add(MyLocationManager.getLocationViaTower(ControllerApplication.getInstance()));
        searchBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (searchBar.getRight() - searchBar.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        getAddress();

                        return true;
                    }
                }
                return false;
            }
        });
        return root;

    }

    private void setUpView(View view) {

        addLocation = (Button) view.findViewById(R.id.addLocation);
        mapView = (MapView) view.findViewById(R.id.mapView);
        searchBar = (EditText) view.findViewById(R.id.search_bar);

    }


    void processLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.
                        ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            setCurrentLocaiton();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_REQUEST);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    boolean isGPSEnabled() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void setCurrentLocaiton() {
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                currentLocation = new MyCLocation(MyCLocation.GPS_LOCATION, location.getLatitude(), location.getLongitude());
                LocationManager locationManager = (LocationManager) ControllerApplication.getInstance().getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(ControllerApplication.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ControllerApplication.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.removeUpdates(this);

                if (mMap != null && !cameraMoved) {
                    moveCamera(mMap, currentLocation);
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (!MyLocationManager.getLocationViaGps(getActivity(), locationListener))
            currentLocation = MyLocationManager.getLocationViaTower(getActivity());

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onClick(View v) {

        dialogAddLocation();

    }

    public void addLocation() {

        if (chosenPosition != null) {
            MyCLocation myCLocation = new MyCLocation(0, chosenPosition.getPosition().latitude,
                    chosenPosition.getPosition().longitude);

            ((MainActivity) getActivity()).returnToAddEvent(myCLocation);

        } else {
            ((MainActivity) getActivity()).onBackPressed();
        }


    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        mMap = map;
        if (currentLocation != null && !cameraMoved) {
            moveCamera(mMap, currentLocation);
        }
        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
    }

    private void moveCamera(GoogleMap mMap, MyCLocation currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLat(), currentLocation.getLng());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.animateCamera(cameraUpdate);
        cameraMoved = true;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (findMarker(marker)) {
            marker.remove();
        }

        return false;
    }

    private boolean findMarker(Marker marker) {

        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {

        if (chosenPosition == null) {
            chosenPosition = mMap.addMarker(new MarkerOptions().position(latLng));
        } else {
            chosenPosition.remove();
            chosenPosition = mMap.addMarker(new MarkerOptions().position(latLng));
        }


        //do you wnat to add evient with tis location
    }

    private void dialogAddLocation() {

        AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity) getActivity());

        builder.setMessage("This location will be added");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addLocation();

            }
        });

        Dialog dialog = builder.create();
        dialog.show();


    }


    public void getAddress() {

        String g = searchBar.getText().toString();

        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> addresses = null;

        try {
            // Getting a maximum of 3 Address that matches the input
            // text
            addresses = geocoder.getFromLocationName(g, 3);
            if (addresses != null && !addresses.equals(""))
                search(addresses);

        } catch (Exception e) {

        }
    }

    public void search(List<Address> addresses) {

        Address address = (Address) addresses.get(0);
        home_long = address.getLongitude();
        home_lat = address.getLatitude();
        latLng = new LatLng(address.getLatitude(), address.getLongitude());

        String addressText = String.format(
                "%s, %s",
                address.getMaxAddressLineIndex() > 0 ? address
                        .getAddressLine(0) : "", address.getCountryName());

        markerOptions = new MarkerOptions();

        markerOptions.position(latLng);
        markerOptions.title(addressText);

        mMap.clear();
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        locationTv.setText("Latitude:" + address.getLatitude() + ", Longitude:"
                + address.getLongitude());


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS:
                if (Utility.permissionsGranted(grantResults)) {
                    setCurrentLocaiton();
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST) {
            if(isGPSEnabled()){
                processLocation();
            }
        }
    }
}
