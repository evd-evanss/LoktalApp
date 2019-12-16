package com.sayhitoiot.loktalapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sayhitoiot.loktalapp.Classes.DadosCliente;
import com.sayhitoiot.loktalapp.Classes.Equipamento;
import com.sayhitoiot.loktalapp.Activityes.Ordem_de_Servico.FormularioActivity;
import com.sayhitoiot.loktalapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Criado por Evandro Costa
 */

public class MeusEquipamentosAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Equipamento> itens;
    boolean man_prevent;
    public static FirebaseAuth auth;
    private DatabaseReference mUserRef;
    private DadosCliente dadosCliente;
    private List<Boolean> list_buttons;

    //Construtor
    public MeusEquipamentosAdapter(Context ctx, ArrayList<Equipamento> itens) {
        this.ctx = ctx;
        this.itens = itens;
        notifyDataSetChanged();
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        //Instancio referencia de dados do cliente no firebase para recuperar os dados do cliente para usar ao solicitar
        //manutenções preventivas ou corretivas
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("DadosCliente");
        dadosCliente = new DadosCliente();
        //Verifico a qtd de itens e atribuo como qtd para os botões de "Solicitar Manutenção"
        list_buttons = new ArrayList<Boolean>(Arrays.asList(new Boolean[this.itens.size()]));
        //Seto minha lista de botões como falso para iniciarem invisíveis ao usuário
        for (int x = 0; x < list_buttons.size(); x++){
            list_buttons.set(x,false);
        }
    }

    public void setForm() {
        this.itens.clear();
        notifyDataSetChanged();
    }

    //Relaciona o layout com o view holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_equipamentos, parent, false);
        return new MeuViewHolder(v);
    }

    //
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final MeuViewHolder holder = ((MeuViewHolder) viewHolder);
        final Equipamento e = itens.get(position);

        holder.tvModelo.setText(e.getModelo());
        holder.tvData.setText("Data de Compra: "+e.getData());
        holder.tvNf.setText("Nota Fiscal: "+e.getNf());
        holder.tvSerial.setText("Serial: "+e.getSerial());

        //Crio instancia de data e de calendario para recuperar data atual
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        //Instancio data atual para comparar com a data de compra do equipamento
        Date data_atual = cal.getTime();
        //Formato data em dia , mês e ano
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dataGarantia= null;
        try {
            dataGarantia = dateFormat.parse(e.getData());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        //Verifico se o Equipamento está no período de garantia (1 ano)
        // 1 dia 24hs, 1 hora 60min, 1 min 60seg, 1seg 1000
        if(((data_atual.getTime() - dataGarantia.getTime()) / (1000 * 60 * 60 * 24)) > 365){
            holder.tvGar.setText("Garantia: Fora do período");
            holder.imgStatusGar.setImageDrawable(ctx.getDrawable(R.drawable.status_red));
        }else{
            holder.tvGar.setText("Garantia: No período");
            holder.imgStatusGar.setImageDrawable(ctx.getDrawable(R.drawable.status_green));
        }
        //Verifico se o Equipamento está no período de Manutenção preventiva (6 meses)
        if(((data_atual.getTime() - dataGarantia.getTime()) / (1000 * 60 * 60 * 24)) > 182){
            holder.tvMan.setText("Man Preventiva: Fora do período");
            man_prevent = false;
            holder.imgStatusMan.setImageDrawable(ctx.getDrawable(R.drawable.status_red));
        }else{
            holder.tvMan.setText("Man Preventiva: No período");
            man_prevent = true;
            holder.imgStatusMan.setImageDrawable(ctx.getDrawable(R.drawable.status_green));
        }
        //Verifico qual é o equipamento para atribuir imagem correspondente
        if(holder.tvModelo.getText().equals("Wavetronic 6000 touch")){
            holder.imgItem.setImageDrawable(ctx.getDrawable(R.drawable.ic_wavetronic));
        }
        if(holder.tvModelo.getText().equals("Wavevac")){
            holder.imgItem.setImageDrawable(ctx.getDrawable(R.drawable.ic_wavevac));
        }
        if(holder.tvModelo.getText().equals("MegaPulse HF Fraxx")){
            holder.imgItem.setImageDrawable(ctx.getDrawable(R.drawable.ic_fraxx));
        }
        //Se usuario pressionar card e segurar botão aparece/desaparece
        holder.cdv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                    if(!list_buttons.get(position)){
                        list_buttons.set(position, true);
                        holder.btnSolicita.setVisibility(View.VISIBLE);
                    }else{
                        list_buttons.set(position, false);
                        holder.btnSolicita.setVisibility(View.GONE);
                    }

                return false;
            }
        });
        //Recupero dados do cliente no firebase p/ abrir Ordem de Serviço ao clicar em Solicitar Manutenção
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    dadosCliente = dataSnapshot.getValue(DadosCliente.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Botão para solicitar manutenção
        holder.btnSolicita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, FormularioActivity.class);
                intent.putExtra("equipamento",e.getModelo());
                intent.putExtra("serial",e.getSerial());
                intent.putExtra("nf",e.getNf());
                intent.putExtra("nome",dadosCliente.getNome());
                intent.putExtra("razao_social",dadosCliente.getRazao_social());
                intent.putExtra("cpf_cnpj",dadosCliente.getCpf_cnpj());
                intent.putExtra("endereco",dadosCliente.getEndereco());
                intent.putExtra("estado",dadosCliente.getEstado());
                intent.putExtra("cidade",dadosCliente.getCidade());
                intent.putExtra("cep",dadosCliente.getCep());
                intent.putExtra("profissao",dadosCliente.getProfissao());
                intent.putExtra("email",dadosCliente.getEmail());
                intent.putExtra("telefone",dadosCliente.getTelefone());
                intent.putExtra("celular",dadosCliente.getCelular());
                //Verificar se man preventiva se esta no peridodo

                ctx.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return itens.size();
    }


    //instancia os componentes do layout
    public class MeuViewHolder extends RecyclerView.ViewHolder {

        ImageView imgItem;
        ImageView imgStatusGar;
        ImageView imgStatusMan;
        TextView tvSerial;
        TextView tvData;
        TextView tvGar;
        TextView tvMan;
        TextView tvNf;
        TextView tvModelo;
        CardView cdv;
        Button btnSolicita;
        @SuppressLint("WrongViewCast")
        public MeuViewHolder(View itemView) {
            super(itemView);
            tvSerial = itemView.findViewById(R.id.id_serial);
            tvData = itemView.findViewById(R.id.id_data);
            tvGar = itemView.findViewById(R.id.id_gar);
            tvMan = itemView.findViewById(R.id.id_man);
            tvModelo = itemView.findViewById(R.id.id_model);
            tvNf = itemView.findViewById(R.id.id_nf);
            imgItem = itemView.findViewById(R.id.id_img);
            imgStatusGar = itemView.findViewById(R.id.status_gar);
            imgStatusMan = itemView.findViewById(R.id.status_man);
            cdv = itemView.findViewById(R.id.id_cdv);
            btnSolicita = itemView.findViewById(R.id.btn_solicita);
        }
    }


}