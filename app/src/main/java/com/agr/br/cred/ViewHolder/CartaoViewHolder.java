package com.agr.br.cred.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.agr.br.cred.R;

/**
 * Created by Familia on 27/02/2017.
 */
public class CartaoViewHolder extends RecyclerView.ViewHolder {
    public final TextView nomeCartao;
    public final TextView txtCartaoDatas;
    public final TextView limite;
    public final TextView limiteDisponivel;
    public final TextView limiteTotal;

    public CartaoViewHolder(View view) {
        super(view);
        nomeCartao = (TextView) view.findViewById(R.id.txtCartaoNome);
        txtCartaoDatas = (TextView) view.findViewById(R.id.txtCartaoDatas );
        limite = (TextView) view.findViewById(R.id.txtCartaoLimite);
        limiteDisponivel = (TextView) view.findViewById(R.id.txtCartaoLimiteDisponivel);
        limiteTotal = (TextView) view.findViewById(R.id.txtCartaoLimiteTotal);
    }
}
