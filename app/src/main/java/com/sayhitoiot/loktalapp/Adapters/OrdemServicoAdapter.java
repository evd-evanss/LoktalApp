package com.sayhitoiot.loktalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sayhitoiot.loktalapp.Classes.EditModel;
import com.sayhitoiot.loktalapp.R;

import java.util.ArrayList;

/**
 * Criado por Evandro Costa
 */

public class OrdemServicoAdapter extends RecyclerView.Adapter<OrdemServicoAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    public static ArrayList<EditModel> editModelArrayList;
    ArrayList<String> itens;
    String[]items_form;
    Context ctx;

    public OrdemServicoAdapter(Context ctx, ArrayList<EditModel> editModelArrayList, ArrayList<String> itens, String[] items_form){

        inflater = LayoutInflater.from(ctx);
        this.editModelArrayList = editModelArrayList;
        this.itens = itens;
        this.items_form = items_form;
        this.ctx = ctx;
    }

    @Override
    public OrdemServicoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_formulario, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final OrdemServicoAdapter.MyViewHolder holder, final int position) {


//        holder.editText.setText(editModelArrayList.get(position).getEditTextValue());
//        Log.d("print","yes");
        //Se posição for impar escrever hint se par escrever no textview
        holder.tvItem.setText(itens.get(position));

        if(position==1){
            holder.editText.setHint("Ex: EWBB-TS");
        }else if(position==3){
            holder.editText.setHint("Inserir nome");
        }else if(position==5){
            holder.editText.setHint("Inserir Nome ou Razão Social");
        }else if(position==16){
            holder.editText.setHint("Inserir a descrição do defeito, por exemplo: O equipamento dispara o alarme quando é ligado");
        }else{
            holder.editText.setHint("Inserir "+itens.get(position));
        }
        if(position==17){
            holder.ll.setVisibility(View.VISIBLE);
            holder.btnSave.setVisibility(View.VISIBLE);
        }

        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailForAT(editModelArrayList);
            }
        });

        if(!items_form[0].isEmpty()){
            holder.editText.setText(items_form[position]);
        }

        if(!items_form[1].isEmpty()){
            holder.editText.setText(items_form[position]);
        }

        if(!items_form[2].isEmpty()){
            holder.editText.setText(items_form[position]);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked())
                {
                    editModelArrayList.get(17).setEditTextValue("Sim");
                }
                else
                {
                    editModelArrayList.get(17).setEditTextValue("Não");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return editModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        protected EditText editText;
        TextView tvItem;
        TextView tvLaudo;
        Button btnSave;
        LinearLayout ll;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.edt_ctd);
            checkBox = itemView.findViewById(R.id.id_rdb);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    editModelArrayList.get(getAdapterPosition()).setEditTextValue(editText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            tvItem = itemView.findViewById(R.id.tv_itm);
            tvLaudo = itemView.findViewById(R.id.tv_laudo);
            ll = itemView.findViewById(R.id.ll_final);
            btnSave = itemView.findViewById(R.id.btn_solicita);
        }

    }

    public void sendEmailForAT(ArrayList<EditModel> editModelArrayList){
        String body = "";
        for(int i = 0; i < editModelArrayList.size(); i++){
            body += itens.get(i)+": ";
            body += editModelArrayList.get(i).getEditTextValue() + "\n \n";
            body += "--------------------------------------------------------------------------------\n";
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "assistencia@loktal.com" });
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Equipamento para Manutenção");
        ctx.startActivity(Intent.createChooser(intent, "Enviar Email via..."));
    }
}