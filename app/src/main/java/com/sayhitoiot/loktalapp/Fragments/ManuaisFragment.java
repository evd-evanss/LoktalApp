package com.sayhitoiot.loktalapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sayhitoiot.loktalapp.Classes.Constants;
import com.sayhitoiot.loktalapp.R;
import com.sayhitoiot.loktalapp.Activityes.Visualizador_PDF.WebViewActivity;

/**
 * Criado por Evandro Costa
 */

public class ManuaisFragment extends Fragment {

    private CardView ivWavetronic;
    private CardView ivWavevac;
    private CardView ivFraxx;

    private Context ctx;
    StorageReference mStorageReference;

    public ManuaisFragment() {

    }

    public static ManuaisFragment newInstance() {
        ManuaisFragment fragment = new ManuaisFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manuais, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivWavetronic = view.findViewById(R.id.id_wavetronic);
        ivWavevac = view.findViewById(R.id.id_wavevac);
        ivFraxx = view.findViewById(R.id.id_fraxx);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference(Constants.STORAGE_PATH_UPLOADS);

        ivWavetronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    viewManual("wavetronic.pdf", "Wavetronic 6000 Touch");
                }else{
                    Toast.makeText(ctx, "Não há conexão com a internet, tente novamente mais tarde...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivWavevac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ctx, "Wavevac", Toast.LENGTH_SHORT).show();
                if(isOnline()) {
                    viewManual("wavevac.pdf", "Wavevac");
                }else{
                    Toast.makeText(ctx, "Não há conexão com a internet, tente novamente mais tarde...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivFraxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                viewManual("fraxx.pdf", "Megapulse HF Fraxx 6000");
                }else{
                    Toast.makeText(ctx, "Não há conexão com a internet, tente novamente mais tarde...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void viewManual(String manual, final String title){
        mStorageReference.child(manual).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Intent i = new Intent(ctx, WebViewActivity.class);
                i.putExtra("url", uri.toString());
                i.putExtra("title", title);
                startActivity(i);
            }}).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(ctx, exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
