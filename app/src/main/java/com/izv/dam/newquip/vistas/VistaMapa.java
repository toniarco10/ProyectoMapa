package com.izv.dam.newquip.vistas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.izv.dam.newquip.R;
import com.izv.dam.newquip.basedatos.AyudanteLocalizador;
import com.izv.dam.newquip.pojo.Localizador;
import com.izv.dam.newquip.pojo.Nota;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import static com.izv.dam.newquip.R.string.nota;

public class VistaMapa extends FragmentActivity
        implements OnMapReadyCallback,
        com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private com.google.android.gms.common.api.GoogleApiClient GoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        init();
    }

    protected void onStart() {
        GoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        GoogleApiClient.disconnect();
        super.onStop();
    }


    private void init() {
        if (GoogleApiClient == null) {
            GoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    //OnMapReadyCallback
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Nota nota;

        Bundle b = getIntent().getExtras();
        nota = b.getParcelable("nota");

        AyudanteLocalizador a = new AyudanteLocalizador(this);
        RuntimeExceptionDao<Localizador, Integer> simpleDao = a.getDataDao();

        QueryBuilder<Localizador, Integer> queryBuilder = simpleDao.queryBuilder();
        try {
            queryBuilder.setWhere(queryBuilder.where().eq(Localizador.ID_NOTA, nota.getId()));
            List<Localizador> localizador = simpleDao.query(queryBuilder.prepare());

            for(Localizador loc: localizador){
                LatLng posicion = new LatLng(loc.getLatitud(), loc.getLongitud());
                mMap.addMarker(new MarkerOptions().position(posicion).title("Granada, España"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 18));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //GoogleApiClient.ConnectionCallbacks
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Conectado", Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }
        permisos();
    }

    //GoogleApiClient.ConnectionCallbacks
    @Override
    public void onConnectionSuspended(int i) {

    }

    //GoogleApiClient.OnConnectionFailedListener
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void permisos(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(GoogleApiClient);
            if (mLastLocation != null) {
            //    Toast.makeText(this, mLastLocation.getLatitude() + " - " + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sin datos", Toast.LENGTH_SHORT).show();
            }

            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(100000);
            mLocationRequest.setFastestInterval(50000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(GoogleApiClient, mLocationRequest, this);
        }
    }
}





















