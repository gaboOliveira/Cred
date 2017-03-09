package com.agr.br.cred.Activity.Cartao;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.agr.br.cred.Classes.HelperClass;
import com.agr.br.cred.Models.Cartao;
import com.agr.br.cred.R;

import org.w3c.dom.Text;

public class CartaoAddActivity extends AppCompatActivity {
    private MenuItem add;
    private MenuItem save;
    private MenuItem delete;
    private EditText edtNomeCartao;
    private EditText edtVencimentoCartao;
    private EditText edtMelhorDiaCartao;
    private long cartaoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        edtNomeCartao = (EditText) findViewById(R.id.edtNomeCartao);
        edtVencimentoCartao = (EditText) findViewById(R.id.edtVencimentoCartao);
        edtMelhorDiaCartao = (EditText) findViewById(R.id.edtMelhorDia);

        Intent intent = getIntent();
        cartaoId = intent.getLongExtra("CartaoId", 0);

        if (cartaoId > 0) {
            setTitle("Alterar cartão");
            Cartao cartao = Cartao.load(Cartao.class, cartaoId);
            edtNomeCartao.setText(cartao.nome);
            edtVencimentoCartao.setText(cartao.vencimento.toString());
            edtMelhorDiaCartao.setText(cartao.melhorDia.toString());
        }else {
            setTitle("Novo Cartão");
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cartao, menu);
        add = menu.findItem(R.id.cartaoadd);
        add.setVisible(false);
        save = menu.findItem(R.id.cartaosave);
        delete = menu.findItem(R.id.cartaodelete);
        if (cartaoId == 0)
            delete.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.cartaosave:
                saveCartao();
                break;
            case R.id.cartaodelete:
                deleteCartao();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveCartao() {
        boolean isErro = false;

        if (edtNomeCartao.getText().toString().isEmpty()) {
            edtNomeCartao.setError("Informe o nome do cartão");
            isErro = true;
        }

        int diaVencimento = 0;
        if (edtVencimentoCartao.getText().toString().isEmpty()) {
            edtVencimentoCartao.setError("Informe o dia de vencimento do cartão");
            isErro = true;
        }else {
            diaVencimento = Integer.parseInt(edtVencimentoCartao.getText().toString());
            if (diaVencimento < 1 || diaVencimento > 31) {
                edtVencimentoCartao.setError("Informe o dia de vencimento entre 1 e 31");
                isErro = true;
            }
        }

        if (!isErro) {
            ActiveAndroid.beginTransaction();

            try {
                Cartao cartao;

                if (cartaoId == 0)
                    cartao = new Cartao();
                else
                    cartao = Cartao.load(Cartao.class, cartaoId);

                cartao.nome = edtNomeCartao.getText().toString();
                cartao.vencimento = diaVencimento;

                if (!edtMelhorDiaCartao.getText().toString().isEmpty())
                    cartao.melhorDia = Integer.parseInt(edtMelhorDiaCartao.getText().toString());

                cartao.save();
                setResult(HelperClass.AUXCODECARTAO);
                ActiveAndroid.setTransactionSuccessful();
                finish();
            }catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartaoAddActivity.this);
                builder.setMessage("Não foi possível salvar o cartão! Verifique os dados e tente novamente")
                        .setTitle("Atenção!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }finally {
                ActiveAndroid.endTransaction();
            }
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(CartaoAddActivity.this);
            builder.setMessage("Verifique os campos com erro e tente novamente!")
                    .setTitle("Atenção!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void deleteCartao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartaoAddActivity.this);
        builder.setMessage("Deseja realmente remover o cartão?")
                .setTitle("Atenção!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Cartao.delete(Cartao.class, cartaoId);

                        dialogInterface.dismiss();
                        setResult(HelperClass.AUXCODECARTAO);
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
