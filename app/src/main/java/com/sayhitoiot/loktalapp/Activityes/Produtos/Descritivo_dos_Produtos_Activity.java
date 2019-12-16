package com.sayhitoiot.loktalapp.Activityes.Produtos;
import android.os.Bundle;
import android.content.Intent;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sayhitoiot.loktalapp.R;

/**
 * Criado por Evandro Costa
 */

public class Descritivo_dos_Produtos_Activity extends AppCompatActivity {

    private TextView tv_Titulo, tv_Descritivo;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        tv_Titulo = findViewById(R.id.tv_Titulo);
        tv_Descritivo = findViewById(R.id.tv_Descritivo);

        img = findViewById(R.id.id_imagem);

        // Recebo Dados
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Titulo");
        String Description = intent.getExtras().getString("Descritivo");
        int image = intent.getExtras().getInt("Imagem") ;

        // Carrego dados nas Views
        tv_Titulo.setText(Title);
        tv_Descritivo.setText(Description);
        img.setImageResource(image);

    }
}