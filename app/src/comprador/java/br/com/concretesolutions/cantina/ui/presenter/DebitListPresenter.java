package br.com.concretesolutions.cantina.ui.presenter;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import br.com.concretesolutions.cantina.application.Preferences;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.ui.fragment.RecyclerViewFill;

public class DebitListPresenter {

    public void initialize(final RecyclerViewFill<Sale> recyclerViewFillCallback, Preferences preferences) {
        // TODO find by user!
        ParseQuery.getQuery(Sale.class)
                .include(Sale.BUYER)
                .include(Sale.PRODRUCT)
                .findInBackground(new FindCallback<Sale>() {
                    @Override
                    public void done(List<Sale> list, ParseException e) {
                        Log.d("App","list.size: "+ list.size());
                        for (Sale sale : list) {
                            Log.d("App", "Buyer name: "+ sale.getBuyer().getName() +" - product name: "+sale.getProduct().getName());
                        }
                        recyclerViewFillCallback.prepareRecyclerViewWithData(list);
                    }
                });
    }
}
