package com.sayhitoiot.loktalapp.Activityes.Visualizador_PDF;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sayhitoiot.loktalapp.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Criado por Evandro Costa
 */

public class WebViewActivity extends AppCompatActivity {

    WebView wbV;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        String url_intent = dados.getString("url");
        String title = dados.getString("title");


        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setTitle(title);
        pDialog.setMessage("Carregando...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        wbV = findViewById(R.id.view_manuais);
        wbV.getSettings().setJavaScriptEnabled(true);
        wbV.getSettings().setBuiltInZoomControls(true);


        //Se intenção de url contem sympla usuario clicou em treinamentos
        if(url_intent.contains("sympla")){
            wbV.loadUrl(url_intent);
        }
        //Se não contem, usuario clicou em Manuais, transformar webview em
        //visualizador de pdf
        else{
            String url = "";
            try {
                url= URLEncoder.encode(url_intent,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            wbV.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    pDialog.show();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    pDialog.dismiss();
                }
            });
            wbV.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+url);
        }

    }
}
