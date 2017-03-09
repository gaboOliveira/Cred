package com.agr.br.cred.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Familia on 27/02/2017.
 */
@Table(name = "cartao")
public class Cartao extends Model {
    @Column(name = "nome")
    public String nome;

    @Column(name = "vencimento")
    public Integer vencimento;

    @Column(name = "melhordia")
    public Integer melhorDia;

    @Column(name = "status")
    public Boolean status;

    @Column(name = "limite")
    public BigDecimal limite;

    public List<Divida> getDividas() {
        return getMany(Divida.class, "cartao");
    }
}
