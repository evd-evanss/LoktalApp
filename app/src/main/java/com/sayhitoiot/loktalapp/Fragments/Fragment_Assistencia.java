package com.sayhitoiot.loktalapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sayhitoiot.loktalapp.R;

/**
 * Criado por Evandro Costa
 */

public class Fragment_Assistencia extends Fragment implements OnMapReadyCallback {
    private static Fragment_Assistencia instance;
    Context ctx;
    private MapView loktal_map;
    private GoogleMap map;

    public static Fragment_Assistencia getInstance() {
        if (instance == null ) {
            instance = new Fragment_Assistencia();
        }
        return instance;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = activity;
    }

    @Override
    public void onResume() {
        loktal_map.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        loktal_map.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        loktal_map.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        loktal_map.onLowMemory();
        super.onLowMemory();
    }

    // Retorna a view qdo o fragment for chamado
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_assistencia, container, false);
        loktal_map = v.findViewById(R.id.google_maps);
        loktal_map.onCreate(savedInstanceState);
        loktal_map.getMapAsync(this);
        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(new LatLng(-23.580581, -46.719123)).title("Loktal Medical"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.580581, -46.719123), 14));
    }
}
