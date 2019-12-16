package com.sayhitoiot.loktalapp.Activityes.Dados_Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sayhitoiot.loktalapp.Classes.DadosCliente;
import com.sayhitoiot.loktalapp.R;

/**
 * Criado por Evandro Costa
 */

public class DadosUsuarioActivity extends AppCompatActivity {

    EditText edtNome;
    EditText edtRazaoSocial;
    EditText edtDoc;
    EditText edtEnd;
    EditText edtEst;
    EditText edtCidade;
    EditText edtCep;
    EditText edtProf;
    EditText edtEmail;
    EditText edtTelefone;
    EditText edtCelular;
    Button btnSave;
    public static FirebaseAuth auth;
    private DatabaseReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_usuario);
        edtNome = findViewById(R.id.id_nome);
        edtRazaoSocial = findViewById(R.id.id_rs);
        edtDoc = findViewById(R.id.id_doc);
        edtEnd = findViewById(R.id.id_end);
        edtEst = findViewById(R.id.id_estado);
        edtCep = findViewById(R.id.id_cep);
        edtProf = findViewById(R.id.id_prof);
        edtEmail = findViewById(R.id.id_email);

        edtCidade = findViewById(R.id.id_cidade);
        edtTelefone = findViewById(R.id.id_telefone);
        edtCelular = findViewById(R.id.id_celular);
        btnSave = findViewById(R.id.btn_save);

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("DadosCliente");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtDoc.getText().toString().isEmpty()){
                    DadosCliente dadosCliente = new DadosCliente();
                    dadosCliente.setNome(edtNome.getText().toString());
                    dadosCliente.setRazao_social(edtRazaoSocial.getText().toString());
                    dadosCliente.setCpf_cnpj(edtDoc.getText().toString());
                    dadosCliente.setEndereco(edtEnd.getText().toString());
                    dadosCliente.setCidade(edtCidade.getText().toString());
                    dadosCliente.setEstado(edtEst.getText().toString());
                    dadosCliente.setCep(edtCep.getText().toString());
                    dadosCliente.setProfissao(edtProf.getText().toString());
                    dadosCliente.setEmail(edtEmail.getText().toString());
                    dadosCliente.setTelefone(edtTelefone.getText().toString());
                    dadosCliente.setCelular(edtCelular.getText().toString());
                    mUserRef.setValue(dadosCliente);
                    finish();
                }else{
                    Toast.makeText(DadosUsuarioActivity.this, "Preencha no mínimo o campo CPF/CNPJ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    DadosCliente dadosCliente = dataSnapshot.getValue(DadosCliente.class);
                    fillTextViews(dadosCliente);
                }
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    DadosCliente dadosCliente = postSnapshot.getValue(DadosCliente.class);
//                    fillTextViews(dadosCliente);
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fillTextViews(DadosCliente dadosCliente) {
        edtNome.setText(dadosCliente.getNome());
        edtRazaoSocial.setText(dadosCliente.getRazao_social());
        edtDoc.setText(dadosCliente.getCpf_cnpj());
        edtEnd.setText(dadosCliente.getEndereco());
        edtCidade.setText(dadosCliente.getCidade());
        edtEst.setText(dadosCliente.getEstado());
        edtCep.setText(dadosCliente.getCep());
        edtProf.setText(dadosCliente.getProfissao());
        edtEmail.setText(dadosCliente.getEmail());
        edtTelefone.setText(dadosCliente.getTelefone());
        edtCelular.setText(dadosCliente.getCelular());
    }
}
