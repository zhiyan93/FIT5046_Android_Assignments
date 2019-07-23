package com.example.navdrawertest3;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment  {
    MapView mMapView;
    private GoogleMap googleMap;
    private Button showParkBtn;
    private double latitude ;
    private  double longitude;
    ArrayList<LatLng> markerPoints;
    private ArrayList<Park> parkList;
    private SharedPreferences sp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        showParkBtn = rootView.findViewById(R.id.showParkBtn);
        parkList = new ArrayList<>();
        sp = ((MainActivity)getActivity()).getSP();
        String homeAddress= sp .getString("userAdd","Melbourne");
        System.out.println(homeAddress);
        LatLng latLng = getLocationFromAddress(rootView.getContext(),homeAddress);
        latitude = latLng.latitude;
        longitude = latLng.longitude;

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        showParkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parkList = new ArrayList<>();
                SearchParkTask task = new SearchParkTask();
                task.execute(latitude,longitude);


            }
        });

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;


                if (checkLocationPermission()) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        googleMap.setMyLocationEnabled(true);
                    }
                }


                markerPoints = new ArrayList<LatLng>();

                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);
                // For dropping a marker at a point on the Map
               // LatLng sydney = new LatLng(Float.parseFloat("-34"), Float.parseFloat("151"));
                LatLng sydney = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Home").snippet("Home location"));
                //

                System.out.println("parksize2 "+parkList.size());
                /*for(int i=0;i<parkList.size();i++)
                {
                    Park parkItem = parkList.get(i);
                    LatLng parkLatLng = new LatLng(parkItem.getLantitude(),parkItem.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(parkLatLng).title("park").snippet("TitleName"));
                }*/
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            }
        });
     //   Intent searchAddress = new  Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+"malvern East Caulfield"));
     //   startActivity(searchAddress);

        return rootView;
    }

    private class SearchParkTask extends AsyncTask<Double,Void,String>
    {


        @Override
        protected String doInBackground(Double... location) {
          String out =  FoursquareAPI.searchParks(location[0],location[1]);
            return out;
        }

        @Override
        protected void onPostExecute(String out)
        {
            try {
                JSONObject outObj = new JSONObject(out);
               JSONArray venueArr =  outObj.getJSONObject("response").getJSONArray("venues");
               for(int i=0;i<venueArr.length();i++) {
                  JSONObject locObj = venueArr.getJSONObject(i).getJSONObject("location");
                   double parkLati = locObj.getDouble("lat");
                   double parkLongi = locObj.getDouble("lng");
                   String parkName = venueArr.getJSONObject(i).getString("name");
                   Park park = new Park(parkName,parkLati,parkLongi);
                   parkList.add(park);
               }
               System.out.println("size "+parkList.size());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;


                    if (checkLocationPermission()) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {

                            //Request location updates:
                            googleMap.setMyLocationEnabled(true);
                        }
                    }


                    markerPoints = new ArrayList<LatLng>();

                    googleMap.getUiSettings().setCompassEnabled(true);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                    googleMap.getUiSettings().setRotateGesturesEnabled(true);
                    // For dropping a marker at a point on the Map
                    // LatLng sydney = new LatLng(Float.parseFloat("-34"), Float.parseFloat("151"));
                    LatLng sydney = new LatLng(latitude, longitude);
                   // googleMap.addMarker(new MarkerOptions().position(sydney).title("Home").snippet("Home Location"));
                    //

                    System.out.println("parksize2 "+parkList.size());
                    for(int i=0;i<parkList.size();i++)
                    {
                        Park parkItem = parkList.get(i);
                        LatLng parkLatLng = new LatLng(parkItem.getLantitude(),parkItem.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(parkLatLng).title("park").snippet(parkItem.getName()+"").icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                    }

                    /*float   HUE_AZURE
                    float   HUE_BLUE
                    float   HUE_CYAN
                    float   HUE_GREEN
                    float   HUE_MAGENTA
                    float   HUE_ORANGE
                    float   HUE_RED
                    float   HUE_ROSE
                    float   HUE_VIOLET
                    float   HUE_YELLOW*/
                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                }
            });
        }
    }

    public class Park
    {
        private String name;
        private double lantitude;
        private double longitude;

        public Park(String name, double lantitude, double longitude) {
            this.name = name;
            this.lantitude = lantitude;
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public double getLantitude() {
            return lantitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }


   public LatLng getLocationFromAddress(Context context, String strAddress) {

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
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage("")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(), new String[]
                                        {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                        googleMap.setMyLocationEnabled(true);
                    }

                } else {


                }
                return;
            }

        }
    }
}
