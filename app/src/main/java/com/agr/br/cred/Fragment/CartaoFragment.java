package com.agr.br.cred.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.agr.br.cred.Activity.Cartao.CartaoAddActivity;
import com.agr.br.cred.Adapter.CartaoAdapter;
import com.agr.br.cred.Classes.DividerItemDecoration;
import com.agr.br.cred.Classes.HelperClass;
import com.agr.br.cred.Interfaces.RecyclerTouchListener;
import com.agr.br.cred.Models.Cartao;
import com.agr.br.cred.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Familia on 27/02/2017.
 */
public class CartaoFragment extends Fragment {
    private MenuItem add;
    private MenuItem save;
    private MenuItem delete;
    private CartaoAdapter cartaoAdapter;
    private List<Cartao> cartaoList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle("Cartões");
        View view = inflater.inflate(R.layout.cartao_list, container, false);

        preencheCartoes();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.listCartoes);

        cartaoAdapter = new CartaoAdapter(cartaoList, getActivity());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(cartaoAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Cartao cartao = cartaoList.get(position);
                        Intent intent = new Intent(getActivity(), CartaoAddActivity.class);
                        intent.putExtra("CartaoId", cartao.getId());
                        startActivityForResult(intent, HelperClass.AUXCODECARTAO);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        onItemClick(view, position);
                    }
                })
        );
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_cartao, menu);
        add = menu.findItem(R.id.cartaoadd);
        save = menu.findItem(R.id.cartaosave);
        save.setVisible(false);
        delete = menu.findItem(R.id.cartaodelete);
        delete.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == add.getItemId()) {
            Intent addCartao = new Intent(getActivity(), CartaoAddActivity.class);
            startActivityForResult(addCartao, HelperClass.AUXCODECARTAO);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == HelperClass.AUXCODECARTAO && resultCode == HelperClass.AUXCODECARTAO) {
            preencheCartoes();
            cartaoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void preencheCartoes() {
        cartaoList.clear();
        cartaoList = new Select().from(Cartao.class).execute();
    }
}
