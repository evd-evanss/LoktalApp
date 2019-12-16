package com.sayhitoiot.loktalapp.Activityes.Login_Registro;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sayhitoiot.loktalapp.R;

/**
 * Criado por Evandro Costa
 */

public class ResetActivity extends AppCompatActivity {

    private EditText edt_Email;
    private Button btn_Reset, btn_Retornar;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initializeViews();

        auth = FirebaseAuth.getInstance();

        btn_Retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_Email.getText().toString().trim();

                //Se EditText estiver vazio informo o usuario

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Digite seu e-mail cadastrado", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetActivity.this, "Enviamos instruções no seu email para redefinir sua senha!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetActivity.this, "Falha ao enviar e-mail redefinido!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

    private void initializeViews() {
        edt_Email = findViewById(R.id.id_email);
        btn_Reset = findViewById(R.id.id_btn_resetar);
        btn_Retornar = findViewById(R.id.id_btn_voltar);
        progressBar =  findViewById(R.id.id_progressbar);
    }

}
