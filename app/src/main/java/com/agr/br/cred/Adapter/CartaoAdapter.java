package com.agr.br.cred.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.agr.br.cred.Models.Cartao;
import com.agr.br.cred.Models.Divida;
import com.agr.br.cred.Models.Parcela;
import com.agr.br.cred.R;
import com.agr.br.cred.ViewHolder.CartaoViewHolder;

import java.util.List;

/**
 * Created by Familia on 27/02/2017.
 */
public class CartaoAdapter extends RecyclerView.Adapter {
    private List<Cartao> cartaoList;
    private Context context;

    public CartaoAdapter(List<Cartao> cartaoList, Context context) {
        this.cartaoList = cartaoList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_view_cartao, parent, false);
        CartaoViewHolder cartaoViewHolder = new CartaoViewHolder(view);
        return cartaoViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CartaoViewHolder holder = (CartaoViewHolder) viewHolder;

        Cartao cartao = cartaoList.get(position);
        holder.nomeCartao.setText(cartao.nome);
        holder.txtCartaoDatas.setText(String.valueOf(cartao.vencimento) + "\\" + String.valueOf(cartao.melhorDia));
        holder.limite.setText("R$ " + String.valueOf(cartao.limite == null ? 0 : cartao.limite));

        new Select("SUM(parcela.valor) AS total")
                .from(Divida.class)
                .where("cartao = ?", cartao.getId())
                .innerJoin(Parcela.class)
                    .on("divida.Id = parcela.divida")
                .executeSingle();
    }

    @Override
    public int getItemCount() {
        return cartaoList.size();
    }
}
