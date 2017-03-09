package com.agr.br.cred.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * Created by Familia on 27/02/2017.
 */
@Table(name = "divida")
public class Divida extends Model {
    @Column(name = "cartao")
    public Cartao cartao;

    @Column(name = "data")
    public Date data;

    @Column(name = "status")
    public Boolean status;

    @Column(name = "valor")
    public Double valor;

    @Column(name = "parcelas")
    public Integer parcelas;

    public List<Parcela> getParcelas() {
        return getMany(Parcela.class, "divida");
    }
}
