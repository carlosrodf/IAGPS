package usac.tesis.com.tesis.menuFragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import usac.tesis.com.tesis.utils.GPSTracker;
import usac.tesis.com.tesis.Principal;
import usac.tesis.com.tesis.R;


public class mapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private customLocationListener locationListener;
    private TextView latitudeDisplay;
    private TextView longitudeDisplay;

    public mapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.fmap);
        mapFragment.getMapAsync(this);

        this.latitudeDisplay = (TextView)view.findViewById(R.id.latitudeDisplay);
        this.longitudeDisplay = (TextView)view.findViewById(R.id.longitudeDisplay);

        this.locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        this.locationListener = new customLocationListener(latitudeDisplay,longitudeDisplay,mMap);
        Context ctx = getActivity().getApplicationContext();
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        setListeners(view);
        return view;
    }

    private void setListeners(View view){
        Button createB = (Button)view.findViewById(R.id.new_profile_button);
        createB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newProfileFragment f = new newProfileFragment();
                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,f);
                ft.commit();
                Principal.lastId = R.id.nav_newProfile;
            }
        });

        Button applyB = (Button)view.findViewById(R.id.set_profile_button);
        applyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilesFragment f = new profilesFragment();
                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,f);
                ft.commit();
                Principal.lastId = R.id.nav_newProfile;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Context ctx = getActivity().getApplicationContext();
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        GPSTracker tracker = new GPSTracker(getActivity().getApplicationContext());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(tracker.getLatitude(), tracker.getLongitude()), 15.0f));

        this.latitudeDisplay.setText("Lat: " + tracker.getLatitude());
        this.longitudeDisplay.setText("Lon: " + tracker.getLongitude());

    }

    private final class customLocationListener implements LocationListener {

        private TextView latitude;
        private TextView longitude;
        private GoogleMap map;

        public customLocationListener(TextView latitude, TextView longitude, GoogleMap map){
            this.latitude = latitude;
            this.longitude = longitude;
            this.map = map;
        }

        @Override
        public void onLocationChanged(Location location) {
            this.latitude.setText("Lat: " + location.getLatitude());
            this.longitude.setText("Lon: " + location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {


        }
    }

}
