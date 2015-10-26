package br.com.concretesolutions.cantina.ui.fragment;

import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.application.Preferences_;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.ui.adapter.DebitListAdapter;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.ui.presenter.DebitListPresenter;

@EFragment(R.layout.fragment_debit_list)
public class DebitListFragment extends Fragment implements RecyclerViewAdapterBase.OnItemViewClickListener, RecyclerViewFill<Sale> {

    @ViewById(R.id.debit_list)
    RecyclerView debitList;
    @ViewById(R.id.progress_of_list_debit)
    ProgressBar progressBarOfListDebit;
    @Pref
    Preferences_ mPrefs;
    DebitListPresenter presenter;

    @AfterViews
    public void afterViews() {
        debitList.setVisibility(View.GONE);
        progressBarOfListDebit.setVisibility(View.VISIBLE);
        presenter = new DebitListPresenter();
        presenter.initialize(this);
    }

    @UiThread
    @Override
    public void prepareRecyclerViewWithData(List<Sale> list) {
        progressBarOfListDebit.setVisibility(View.GONE);
        debitList.setVisibility(View.VISIBLE);
        DebitListAdapter adapter = new DebitListAdapter(getActivity());
        adapter.setList(list);
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
