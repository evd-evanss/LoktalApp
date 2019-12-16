package com.sayhitoiot.loktalapp.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.sayhitoiot.loktalapp.Activityes.Dados_Usuario.DadosUsuarioActivity;
import com.sayhitoiot.loktalapp.Activityes.Login_Registro.LoginActivity;
import com.sayhitoiot.loktalapp.Activityes.MainActivity;
import com.sayhitoiot.loktalapp.Activityes.Produtos.ProdutosActivity;
import com.sayhitoiot.loktalapp.Activityes.Visualizador_PDF.WebViewActivity;
import com.sayhitoiot.loktalapp.R;

/**
 * Criado por Evandro Costa
 */

public class HomeFragment extends Fragment {


    private CardView ivProdutos;
    private CardView ivYouTube;
    private CardView ivTel;
    private CardView cdvTreinamentos;
    private CardView cdvProfile;
    private CardView cdvSair;
    private Context ctx;
    public static FirebaseAuth auth;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        ivProdutos = view.findViewById(R.id.produtos);
        ivYouTube = view.findViewById(R.id.youtube);
        ivTel = view.findViewById(R.id.contatos);
        cdvTreinamentos = view.findViewById(R.id.treinamentos);
        cdvProfile = view.findViewById(R.id.id_profile);
        cdvSair = view.findViewById(R.id.id_logout);


        ivProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ProdutosActivity.class);
                startActivity(intent);
            }
        });

        ivYouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/user/loktalbrasil"));
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }
        });
        ivTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefone = "1137220345";
                Uri uri = Uri.parse("tel:"+telefone);
                Intent intent = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(intent);
            }
        });

        cdvTreinamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, WebViewActivity.class);
                i.putExtra("url", "https://www.sympla.com.br/loktalmedical");
                i.putExtra("title", "Treinamentos");
                startActivity(i);
            }
        });
        cdvSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderr = new AlertDialog.Builder(getActivity());
                builderr.setTitle("Loktal App");

                builderr.setMessage("Deseja sair da sua conta?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sair();
                    }

                    private void sair() {
                        auth.signOut();
                        try {
                            Intent intent = new Intent(ctx, LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }catch (Exception e){

                        }
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    }
                });
                AlertDialog dialog = builderr.create();
                dialog.show();
            }
        });
        cdvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, DadosUsuarioActivity.class);
                startActivity(i);
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
