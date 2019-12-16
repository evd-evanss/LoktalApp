package com.sayhitoiot.loktalapp.Activityes.Ordem_de_Servico;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sayhitoiot.loktalapp.Adapters.OrdemServicoAdapter;
import com.sayhitoiot.loktalapp.Classes.EditModel;
import com.sayhitoiot.loktalapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Criado por Evandro Costa
 */

public class FormularioActivity extends AppCompatActivity {

    public static RecyclerView minhaRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        String equipamento = dados.getString("equipamento", "");
        String serial = dados.getString("serial", "");
        String nf = dados.getString("nf", "");
        String nome = dados.getString("nome", "");
        String razao_social = dados.getString("razao_social","");
        String cpf_cnpj = dados.getString("cpf_cnpj","");
        String endereco = dados.getString("endereco","");
        String cidade = dados.getString("cidade","");
        String estado = dados.getString("estado","");
        String cep = dados.getString("cep","");
        String profissao = dados.getString("profissao","");
        String email = dados.getString("email","");
        String telefone = dados.getString("telefone","");
        String celular = dados.getString("celular","");


        String[] items_form = new String[18];
        items_form[0] = equipamento;
        items_form[1] = serial;
        items_form[2] = nf;
        items_form[3] = nome;
        items_form[4] = getCurrentDate();
        items_form[5] = razao_social;
        items_form[6] = cpf_cnpj;
        items_form[7] = endereco;
        items_form[8] = cidade;
        items_form[9] = estado;
        items_form[10] = cep;
        items_form[11] = nome;
        items_form[12] = profissao;
        items_form[13] = celular;
        items_form[14] = telefone;
        items_form[15] = email;

        OrdemServicoAdapter ordemServicoAdapter;
        ArrayList<EditModel> editModelArrayList;

        minhaRecyclerView = findViewById(R.id.my_recyclerview);
        minhaRecyclerView.setItemViewCacheSize(34);
        ArrayList itm = itensRelatorio();
        //FormularioAdapter formularioAdapter = new FormularioAdapter(this,itm,items_form);
        editModelArrayList = populateList();
        ordemServicoAdapter = new OrdemServicoAdapter(this,editModelArrayList,itm,items_form);
        @SuppressLint("WrongConstant") RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        minhaRecyclerView.setLayoutManager(layoutManager);
        minhaRecyclerView.setAdapter(ordemServicoAdapter);
        //minhaRecyclerView.setAdapter(formularioAdapter);
    }

    public static ArrayList<String> itensRelatorio() {
        ArrayList<String> itens =  new ArrayList<>();
        itens.add("Equipamento");
        itens.add("Número de Série");
        itens.add("Número Nota Fiscal");
        itens.add("Emitente");
        itens.add("Data");
        itens.add("Dados do Cliente \n(Conforme Emissão da Nota Fiscal) \nNome ou Razão Social");
        itens.add("CPF ou CNPJ");
        itens.add("Endereço");
        itens.add("Cidade");
        itens.add("Estado");
        itens.add("CEP");
        itens.add("Responsável pelas informações");
        itens.add("Cargo");
        itens.add("Telefone Celular");
        itens.add("Telefone");
        itens.add("E-mail");
        itens.add("Descrição do Defeito");
        itens.add("Incluir Laudo Técnico");
        return itens;
    }

    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();

        for(int i = 0; i < 18; i++){
            EditModel editModel = new EditModel();
            editModel.setEditTextValue(" ");
            list.add(editModel);
        }

        return list;
    }

    public String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dma = new SimpleDateFormat("dd/MM/yyyy");
        String date = dma.format(calendar.getTime());
        return date;
    }
}
