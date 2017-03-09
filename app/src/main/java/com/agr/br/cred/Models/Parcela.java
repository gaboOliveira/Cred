package com.agr.br.cred.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by Familia on 27/02/2017.
 */
@Table(name = "parcela")
public class Parcela extends Model {
    @Column(name = "divida")
    public Divida divida;

    @Column(name = "data")
    public Date data;

    @Column(name = "status")
    public Boolean status;

    @Column(name = "valor")
    public Double valor;
}
