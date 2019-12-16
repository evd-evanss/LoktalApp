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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sayhitoiot.loktalapp.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Criado por Evandro Costa
 */

public class RegistroActivity extends AppCompatActivity {

    private EditText edt_Email, edt_Senha;
    private Button btn_Retornar, btn_Registrar;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
         //Recupero instancia do Firebase auth
        auth = FirebaseAuth.getInstance();
        initializeViews();

        // Listener dos Botões
        btn_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_Email.getText().toString().trim();
                String senha = edt_Senha.getText().toString().trim();
                //Validação dos EditTexts
                if (TextUtils.isEmpty(email)) {
                    //Se EditText estiver vazio informo o usuario
                    Toast.makeText(getApplicationContext(), "Insira o endereço de e-mail!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(senha)) {
                    //Se EditText estiver vazio informo o usuario
                    Toast.makeText(getApplicationContext(), "Digite a senha!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (senha.length() < 6) {
                    Toast.makeText(getApplicationContext(), getString(R.string.minimum_password), Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Crio Usuario
                auth.createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = auth.getCurrentUser();
                                    if (auth.getCurrentUser() != null) {
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(RegistroActivity.this,
                                                                    "Usuário Criado com Sucesso," +
                                                                            " verifique seu email..." ,
                                                                    Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                    progressBar.setVisibility(View.GONE);
                                    new Timer().schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 5000);

                                }else{
                                    String error = task.getException().toString();
                                    if(error.contains("The email address is already in use")){
                                        Toast.makeText(RegistroActivity.this, "Este endereço de email já está sendo utilizado por outro usuário...",
                                                Toast.LENGTH_LONG).show();
                                    }

                                }
                            }
                        });

            }
        });

        btn_Retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeViews() {
        btn_Registrar = findViewById(R.id.id_btn_registrar);
        btn_Retornar = findViewById(R.id.id_btn_retornar);
        edt_Email = findViewById(R.id.id_email);
        edt_Senha = findViewById(R.id.id_senha);
        progressBar = findViewById(R.id.id_progressbar);
    }

}

