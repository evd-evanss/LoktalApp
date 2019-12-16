package com.sayhitoiot.loktalapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.sayhitoiot.loktalapp.Classes.EditModel;
import com.sayhitoiot.loktalapp.R;
import java.util.ArrayList;



public class FormularioAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<String> itens;
    String[]items_form;
    public static ArrayList<EditModel> editModelArrayList;


    //Construtor
    public FormularioAdapter(Context ctx, ArrayList<String> itens, String[] items_form) {
        this.ctx = ctx;
        this.itens = itens;
        this.items_form = items_form;
        editModelArrayList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setForm() {
        this.itens.clear();
        notifyDataSetChanged();
    }

    //Relaciona o layout com o view holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_formulario, parent, false);
        return new MeuViewHolder(v);
    }

    //
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final MeuViewHolder holder = ((MeuViewHolder) viewHolder);

        //Se posição for impar escrever hint se par escrever no textview
            holder.tvItem.setText(itens.get(position));

            if(position==1){
                holder.edtConteudo.setHint("Ex: EWBB-TS");
            }else if(position==3){
                holder.edtConteudo.setHint("Inserir nome");
            }else if(position==5){
                holder.edtConteudo.setHint("Inserir Nome ou Razão Social");
            }else if(position==16){
                holder.edtConteudo.setHint("Inserir a descrição do defeito, por exemplo: O equipamento dispara o alarme quando é ligado");
            }else{
                holder.edtConteudo.setHint("Inserir "+itens.get(position));
            }
            if(position==17){
                holder.ll.setVisibility(View.VISIBLE);
                holder.btnSave.setVisibility(View.VISIBLE);
            }

            holder.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String show = editModelArrayList.get(8).getEditTextValue();
                    Toast.makeText(ctx, show, Toast.LENGTH_SHORT).show();
                }
            });

        if(!items_form[0].isEmpty()){
            holder.edtConteudo.setText(items_form[position]);
        }

        if(!items_form[1].isEmpty()){
            holder.edtConteudo.setText(items_form[position]);
        }

        if(!items_form[2].isEmpty()){
            holder.edtConteudo.setText(items_form[position]);
        }

        String s = holder.edtConteudo.getText().toString();
        holder.edtConteudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                editModelArrayList.get(position).setEditTextValue(holder.edtConteudo.getText().toString());
                //Toast.makeText(ctx, holder.edtConteudo.getText().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            String s = editable.toString();
            EditModel e = new EditModel();
            e.setEditTextValue(s);
            editModelArrayList.add(position, e);
            }
        });
    }



    @Override
    public int getItemCount() {
        return itens.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    //instancia os componentes do layout
    public class MeuViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;
        TextView tvLaudo;
        Button btnSave;
        EditText edtConteudo;
        LinearLayout ll;

        @SuppressLint("WrongViewCast")
        public MeuViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_itm);
            tvLaudo = itemView.findViewById(R.id.tv_laudo);
            edtConteudo = itemView.findViewById(R.id.edt_ctd);
            ll = itemView.findViewById(R.id.ll_final);
            btnSave = itemView.findViewById(R.id.btn_solicita);


        }
    }


}