package br.com.concretesolutions.cantina.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.application.Preferences_;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.ui.adapter.DebitListAdapter;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;

@EFragment(R.layout.fragment_debit_list)
public class DebitListFragment extends Fragment implements RecyclerViewAdapterBase.OnItemViewClickListener {

    @ViewById(R.id.debit_list)
    RecyclerView debitList;
    @Pref
    Preferences_ mPrefs;

    List<Sale> mSales;

    @AfterViews
    public void afterViews() {
        ParseQuery.getQuery(Sale.class).findInBackground(new FindCallback<Sale>() {
            @Override
            public void done(List<Sale> list, ParseException e) {
                mSales = list;
                debitList.setVisibility(View.VISIBLE);
                generateRecyclerView();
            }
        });
    }

    @UiThread
    public void generateRecyclerView() {
        DebitListAdapter adapter = new DebitListAdapter(getActivity());
        adapter.setList(mSales);
        adapter.setItemViewClickListener(this);
        debitList.setLayoutManager(new LinearLayoutManager(getActivity()));
        debitList.setAdapter(adapter);
        debitList.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemViewClicked(View v) {
        Toast.makeText(getActivity(), "Teste", Toast.LENGTH_SHORT).show();
    }
}
