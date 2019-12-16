package com.sayhitoiot.loktalapp.Activityes.Login_Registro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sayhitoiot.loktalapp.Activityes.MainActivity;
import com.sayhitoiot.loktalapp.R;

/**
 * Criado por Evandro Costa
 */

public class LoginActivity extends AppCompatActivity {

    private EditText edt_Email, edtSenha;
    public static FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btn_Registrar, btn_Entrar, btnReset;
    private DatabaseReference mUserRef_;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        //Instancio a referencia Users da minha base de dados firebsae
        mUserRef_ = FirebaseDatabase.getInstance().getReference().child("Users");

        //Instancio usuario
        auth = FirebaseAuth.getInstance();
        //Se usuario já está logado
        user = auth.getCurrentUser();


        // Listener dos Botões
        btn_Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edt_Email.getText().toString();
                final String senha = edtSenha.getText().toString();
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
                hideKeyboard(getBaseContext(), edtSenha);
                btn_Entrar.setEnabled(false);


                //authenticate user
                auth.signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Se o loggin falhar, mostro uma mensagem. Se for bem sucedido
                                // o listener do estado de autenticação será notificado

                                if (!task.isSuccessful()) {
                                    // Se loggin falhar, informo usuario para verificar email e senha
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                }
                                if (task.isSuccessful()) {
                                    // Se loggin for realizado Instancio Usuario para verificar email
                                    user = auth.getCurrentUser();
                                    // Verifico se usuario registrado já verificou email
                                    if(user.isEmailVerified()){
                                        //Se email foi verificado, usuario pode ser logado e inicio a activity mãe finalizando a Activity Atual
                                        if (auth.getCurrentUser() != null) {
                                            setTokenIdInFirebase();
                                        }
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Verifique seu email para validar sua conta!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            }
                        });
            }
        });

        btn_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetActivity.class));
            }
        });


    }

    private void initializeViews() {
        edt_Email = findViewById(R.id.id_email);
        edtSenha = findViewById(R.id.id_senha);
        progressBar = findViewById(R.id.id_progressbar);
        btn_Registrar = findViewById(R.id.id_btn_registrar);
        btn_Entrar = findViewById(R.id.id_btn_entrar);
        btnReset = findViewById(R.id.id_btn_resetar);

    }


    private void setTokenIdInFirebase() {
        String deviceTokenId = FirebaseInstanceId.getInstance().getToken();
        String current_user_id = auth.getCurrentUser().getUid();
        //Crio referencia e atribuo token para mensagens
        mUserRef_.child(current_user_id).child("device_token").setValue(deviceTokenId).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }
                });
    }

    public static void hideKeyboard(Context ctx, View editText){
        InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(ctx.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}

