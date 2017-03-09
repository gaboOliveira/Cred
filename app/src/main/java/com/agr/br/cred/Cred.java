package com.agr.br.cred;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;
import com.agr.br.cred.Models.Cartao;
import com.agr.br.cred.Models.Divida;
import com.agr.br.cred.Models.Parcela;

/**
 * Created by Familia on 28/02/2017.
 */
public class Cred extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Notice this initialization code here
        Configuration.Builder config = new Configuration.Builder(getApplicationContext());
        config.addModelClasses(Cartao.class, Divida.class, Parcela.class);
        ActiveAndroid.initialize(config.create());
    }
}
