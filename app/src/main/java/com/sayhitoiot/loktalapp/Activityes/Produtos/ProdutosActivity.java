package com.sayhitoiot.loktalapp.Activityes.Produtos;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sayhitoiot.loktalapp.Adapters.ProdutosAdapter;
import com.sayhitoiot.loktalapp.Classes.Produto;
import com.sayhitoiot.loktalapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.sayhitoiot.loktalapp.R.drawable.*;

/**
 * Criado por Evandro Costa
 */

public class ProdutosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        RecyclerView recyclerViewProdutos = findViewById(R.id.recyclerview_produtos);
        // Instancio o Adapter dos Produtos
        ProdutosAdapter myAdapter = new ProdutosAdapter(this,fillList());
        // Determino a quantidade de produtos por grid
        recyclerViewProdutos.setLayoutManager(new GridLayoutManager(this,2));
        // Seto o Adapter
        recyclerViewProdutos.setAdapter(myAdapter);
    }

    public List<Produto> fillList(){
        List<Produto> mList;
        mList = new ArrayList<>();
        mList.add(new Produto("Wavetronic 6000 Touch","Bisturi",getResources().getString(R.string.wavetronic), ic_wavetronic));
        mList.add(new Produto("Megapulse HF Fraxx 6000 Touch","Bisturi",getResources().getString(R.string.fraxx), ic_fraxx));
        mList.add(new Produto("Wavevac Dual","Aspiradores",getResources().getString(R.string.wavevac), ic_wavevac));
        mList.add(new Produto("Eletrodos Fracionados","Eletrodos","Eletrodo cirúrgico de ponta fracionada", ic_eletfrac));
        mList.add(new Produto("Eletrodo Lynli","Eletrodos","Eletrodo cirúrgico de ponta fracionada", ic_eletlinly));
        mList.add(new Produto("Eletrodo de Faca","Eletrodos","Eletrodos Reutilizáveis", ic_eletfaca));
        mList.add(new Produto("Eletrodo de Alça","Eletrodos","Eletrodos Reutilizáveis", ic_eletalca));
        mList.add(new Produto("Eletrodo de Bola e Microbola","Eletrodos","Eletrodos Reutilizáveis", ic_eletbola));
        mList.add(new Produto("Eletrodo de Agulha","Eletrodos","Eletrodos Reutilizáveis", ic_eletagulha));
        mList.add(new Produto("Eletrodo de Sucção","Eletrodos","Coaguladores com Sucção", ic_eletsuc));
        mList.add(new Produto("Pinças Bipolares","Pinças","Pinças Reutilizáveis", ic_eletpinca));
        mList.add(new Produto("Eletrodos de Laparoscopia","Eletrodos","Eletrodos Laparoscopia Reutilizáveis", ic_eletlapa));
        mList.add(new Produto("Pedais, Canetas e Cabos","Acessórios","Uso Geral", ic_acessorios));
        return mList;
    }
}
