package com.example.besher.materialtest.ui.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.helpers.MyLocationManager;
import com.example.besher.materialtest.helpers.SilenceManager;
import com.example.besher.materialtest.helpers.Utility;
import com.example.besher.materialtest.models.MyCLocation;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.Dialog.EventDetailsDialog;
import com.example.besher.materialtest.ui.Interfaces.ItemEventTrigger;
import com.example.besher.materialtest.ui.activity.MainActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, ItemEventTrigger.ItemEvent {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    FloatingActionButton btnNewEventTrial;
    private SilenceItem silenceItem = null;
    private MapView mapView;

    private ArrayList<MyCLocation> myCLocations = new ArrayList<>();
    private ArrayList<SilenceItem> silenceItemList;

    private GoogleMap mMap;
    private MyCLocation currentLocation;

    private EditText searchBar;
    private Button addLocation;


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemEventTrigger.addListener(this);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.
                        ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            setCurrentLocaiton();
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

        mapView.getMapAsync(this);

        //myCLocations.add(MyLocationManager.getLocationViaTower(ControllerApplication.getInstance()));
        if (mMap != null)
            loadEventsOnMap(mMap);

        searchBar.setVisibility(View.GONE);
        addLocation.setVisibility(View.GONE);

        return root;
    }

    public void setUpView(View view) {
        addLocation = (Button) view.findViewById(R.id.addLocation);
        mapView = (MapView) view.findViewById(R.id.mapView);
        searchBar = (EditText) view.findViewById(R.id.search_bar);
    }

    public MyCLocation setCurrentLocaiton() {

//        LocationListener locationListener = new LocationListener() {
//            public void onLocationChanged(Location location) {
//                new MyCLocation(MyCLocation.TOWER_LOCATION, location.getLongitude(), location.getLatitude());
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//                Log.d("TAG", "onSatatus");
//            }
//
//            public void onProviderEnabled(String provider) {
//                Log.d("TAG", "onProviderEnable");
//            }
//
//            public void onProviderDisabled(String provider) {
//                Log.d("TAG", "onProviderDisable");
//            }
//        };

        //if (!MyLocationManager.getLocationViaGps(getActivity(), locationListener))
//            return MyLocationManager.getLocationViaTower(getActivity());
//        else return null;
        currentLocation = MyLocationManager.getLocationViaTower(getContext());


        return currentLocation;
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
        ItemEventTrigger.removeListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addLocation:
                ((MainActivity) getActivity()).addNewEvent(silenceItem);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        mMap = map;
        if (currentLocation != null) {
            LatLng latLng = new LatLng(currentLocation.getLat(), currentLocation.getLng());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
            mMap.animateCamera(cameraUpdate);
        }
        // Set a listener for marker click.
        loadEventsOnMap(mMap);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        displayDetails(marker);
        //marker.remove();
        return false;
    }

    public void displayDetails(Marker marker) {
        for (SilenceItem item :
                silenceItemList) {
            if (item.getLat() == marker.getPosition().latitude &&
                    item.getLng() == marker.getPosition().longitude) {
                EventDetailsDialog daysDialog = EventDetailsDialog.newInstance(item);
                daysDialog.show(getFragmentManager(), "any");
                silenceItemList.remove(item);
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    public void loadEventsOnMap(GoogleMap mMap) {

        silenceItemList = SilenceManager.getListFromPref(getActivity());
        for (SilenceItem item :
                silenceItemList) {
            LatLng point = new LatLng(item.getLat(), item.getLng());
            mMap.addMarker(new MarkerOptions().position(point));
        }
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
    public void onNewItem(SilenceItem silenceItem) {

    }

    @Override
    public void onUpdateItem(SilenceItem silenceItem) {

    }

    @Override
    public void onDeleteItem(SilenceItem silenceItem) {
        if (mMap != null) {
            mMap.clear();
            loadEventsOnMap(mMap);
        }
    }

}
