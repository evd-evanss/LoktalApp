package com.sayhitoiot.loktalapp.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sayhitoiot.loktalapp.Adapters.MeusEquipamentosAdapter;
import com.sayhitoiot.loktalapp.Classes.Equipamento;
import com.sayhitoiot.loktalapp.R;

import java.util.ArrayList;

/**
 * Criado por Evandro Costa
 */

public class Fragment_Equipamentos extends Fragment {
    private static Fragment_Equipamentos instance;
    Context ctx;
    public static RecyclerView minhaRecyclerView;
    private ImageButton fabAdd;
    Dialog cadastroView;
    public static FirebaseAuth auth;
    private DatabaseReference mUserRef;
    private ArrayList<Equipamento> meusEquipamentos = new ArrayList<>();

    public static Fragment_Equipamentos getInstance() {
        if (instance == null ) {
            instance = new Fragment_Equipamentos();
        }
        return instance;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = activity;
    }
    // Retorna a view qdo o fragment for chamado
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_equipamentos, container, false);

        minhaRecyclerView = rootView.findViewById(R.id.cadastro_recyclerview);
        //minhaRecyclerView.setItemViewCacheSize(34);

        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Equipamento");
        meusEquipamentos.clear();



        fabAdd = view.findViewById(R.id.fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCadastroView();
            }
        });


        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                meusEquipamentos.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Equipamento e = postSnapshot.getValue(Equipamento.class);
                    meusEquipamentos.add(e);
                    //Toast.makeText(ctx, e.getModelo(), Toast.LENGTH_SHORT).show();
                }
                MeusEquipamentosAdapter meusEquipamentosAdapter = new MeusEquipamentosAdapter(ctx,meusEquipamentos);
                @SuppressLint("WrongConstant") RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
                //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                minhaRecyclerView.setLayoutManager(layoutManager);
                minhaRecyclerView.setAdapter(meusEquipamentosAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(meusEquipamentos.size()>0){


        }
    }

    //********************  SHOW DIALOG  *********************
    public void showCadastroView() {
        //Instacia de dialogo
        cadastroView = new Dialog(ctx);
        cadastroView.setCancelable(true);
        cadastroView.setContentView(R.layout.cadastro_view);

        //Crio Itens do Dialogo
        Button btnClear = cadastroView.findViewById(R.id.btn_clear);
        Button btn_Cancel =  cadastroView.findViewById(R.id.btn_cancell);
        Button btnSave =  cadastroView.findViewById(R.id.btn_save);
        final EditText edtSerial = cadastroView.findViewById(R.id.id_serial);
        final EditText edtNf = cadastroView.findViewById(R.id.id_nf);
        final EditText edtData = cadastroView.findViewById(R.id.id_data);
        final Spinner dropdown = cadastroView.findViewById(R.id.my_spinner);
        final String []items = new String[]{"Wavetronic 6000 touch", "Wavevac", "MegaPulse HF Fraxx"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        edtSerial.requestFocus();
        final Equipamento equipamento = new Equipamento();

        edtData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                edtData.setSelection(s.toString().length());
            }
            String oldString = "";
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (!str.isEmpty()) {

                    if (oldString.equals(str)) {
                        //significs que o user esta apagando
                        //do Nothing

                    } else if (str.length() == 2) {  //  xx
                        String element0  = s.subSequence(0,1).toString();
                        String element1 = s.subSequence(1,2).toString();
                        str = element0 + element1 + "/";
                        edtData.setText(str);
                        oldString = element0 + element1;
                        edtData.setSelection(str.length());

                    } else if (str.length() == 5) { //  xx/xx

                        String element0  = s.subSequence(0,1).toString();
                        String element1 = s.subSequence(1,2).toString();
                        String element2 = s.subSequence(2,3).toString();
                        String element3 = s.subSequence(3,4).toString();
                        String element4 = s.subSequence(4,5).toString();

                        str = element0 + element1 + element2 + element3 + element4 + "/";
                        edtData.setText(str);
                        oldString = element0 + element1 + element2 + element3 + element4;
                        edtData.setSelection(str.length());
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String textLenght = edtData.getText().toString();

            if((!edtSerial.getText().toString().isEmpty()) && (!edtNf.getText().toString().isEmpty())
                    && (!edtData.getText().toString().isEmpty())){
                if(textLenght.length()<10){
                    Toast.makeText(ctx, "coloque a data no formato XX/XX/XXXX", Toast.LENGTH_SHORT).show();
                }else{
                    equipamento.setSerial(edtSerial.getText().toString());
                    equipamento.setData(edtData.getText().toString());
                    equipamento.setNf(edtNf.getText().toString());
                    equipamento.setModelo(items[dropdown.getSelectedItemPosition()]);
                    mUserRef.child(equipamento.getSerial()).setValue(equipamento);
                    cadastroView.dismiss();
                }

            }else{
                Toast.makeText(ctx, "Todos os campos devem ser preenchidos...", Toast.LENGTH_SHORT).show();
            }
            }

        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSerial.setText("");
                edtNf.setText("");
                edtData.setText("");
            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastroView.dismiss();
            }
        });
        cadastroView.show();
    }




}
