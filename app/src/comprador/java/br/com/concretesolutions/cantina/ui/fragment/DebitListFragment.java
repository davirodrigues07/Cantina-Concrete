package br.com.concretesolutions.cantina.ui.fragment;

import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

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

@EFragment(R.layout.fragment_debit_list)
public class DebitListFragment extends Fragment {

    @ViewById
    RecyclerView debitList;
    List<Sale> mSales = new ArrayList<>();
    @Pref
    Preferences_ mPrefs;

    @AfterViews
    public void afterViews() {
        ParseQuery.getQuery(Sale.class).findInBackground(new FindCallback<Sale>() {
            @Override
            public void done(List<Sale> list, ParseException e) {
                for (Sale sale : list) {
                    if (sale.getBuyer().getEmail().equals(mPrefs.email())) {
                        mSales = list;
                    }
                }
                generateRecyclerView();
            }
        });
    }

    public void generateRecyclerView() {
        debitList.setHasFixedSize(true);
        debitList.setAdapter(new DebitListAdapter(
                getActivity(),
                mSales,
                R.layout.debit_item_layout));
        debitList.setItemAnimator(new DefaultItemAnimator());

    }
}
