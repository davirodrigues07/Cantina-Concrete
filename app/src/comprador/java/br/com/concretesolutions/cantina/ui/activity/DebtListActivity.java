package br.com.concretesolutions.cantina.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;
import br.com.concretesolutions.cantina.ui.adapter.DebitListAdapter;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.ui.fragment.ListProductFragment;
import br.com.concretesolutions.cantina.ui.fragment.RecyclerViewFill;
import br.com.concretesolutions.cantina.ui.presenter.DebitListPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DebtListActivity extends BaseActivity implements RecyclerViewFill<Sale>, RecyclerViewAdapterBase.OnItemViewClickListener {

    @Bind(R.id.fab_buy)
    FloatingActionButton fabBuy;
    /*@Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;*/
    @Bind(R.id.debit_list)
    RecyclerView debitList;

    Fragment fragment;
    DebitListPresenter presenter;

    @Override
    protected void onStart() {
        super.onStart();
        if (mPreferences.username().equals("") &&
                mPreferences.GooglePlusId().equals("")) {
            Intent loginActivityIntent = new Intent(this, LoginActivity.class);
            startActivity(loginActivityIntent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_list);
        presenter = new DebitListPresenter();
        presenter.initialize(this, mPreferences);
        ButterKnife.bind(this);
    }

    @Override
    public void prepareRecyclerViewWithData(final List<Sale> list) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                debitList.setVisibility(View.VISIBLE);
                DebitListAdapter adapter = new DebitListAdapter(DebtListActivity.this);
                adapter.setList(list);
                adapter.setItemViewClickListener(DebtListActivity.this);
                debitList.setLayoutManager(new LinearLayoutManager(DebtListActivity.this));
                debitList.setAdapter(adapter);
                debitList.setItemAnimator(new DefaultItemAnimator());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemViewClicked(View v) {
        Toast.makeText(this, "Teste", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.fab_buy)
    public void fabBuyClick() {
        fragment = new ListProductFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_animation_open, R.anim.fragment_animation_close);
        transaction.add(android.R.id.content, fragment);
        transaction.addToBackStack(ListProductFragment.NAME);
        transaction.commit();
    }

}
