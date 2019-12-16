package com.sayhitoiot.loktalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayhitoiot.loktalapp.Activityes.Produtos.Descritivo_dos_Produtos_Activity;
import com.sayhitoiot.loktalapp.Classes.Produto;
import com.sayhitoiot.loktalapp.R;

import java.util.List;

/**
 * Criado por Evandro Costa
 */

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Produto> mProdutos;


    public ProdutosAdapter(Context mContext, List<Produto> mProdutos) {
        this.mContext = mContext;
        this.mProdutos = mProdutos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_produtos,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_Titulo.setText(mProdutos.get(position).getTitulo());
        holder.tv_Imagem.setImageResource(mProdutos.get(position).getImagem());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Descritivo_dos_Produtos_Activity.class);
                // envia dados do produto clicado para BookActivity
                intent.putExtra("Titulo", mProdutos.get(position).getTitulo());
                intent.putExtra("Descritivo", mProdutos.get(position).getDescritivo());
                intent.putExtra("Imagem", mProdutos.get(position).getImagem());
                // inicia a activity
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mProdutos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Titulo;
        ImageView tv_Imagem;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_Titulo = itemView.findViewById(R.id.id_titulo) ;
            tv_Imagem = itemView.findViewById(R.id.id_imagem_produto);
            cardView = itemView.findViewById(R.id.id_card);

        }

    }


}