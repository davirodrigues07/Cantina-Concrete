package br.com.concretesolutions.cantina.ui.presenter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import br.com.concretesolutions.cantina.application.Preferences;
import br.com.concretesolutions.cantina.data.type.parse.Credentials;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.ui.fragment.RecyclerViewFill;

public class DebitListPresenter {

    public void initialize(final RecyclerViewFill<Sale> recyclerViewFillCallback, Preferences preferences) {

        recyclerViewFillCallback.loadingData();

        ParseQuery<Credentials> queryUser = ParseQuery.getQuery(Credentials.class);
        queryUser.whereEqualTo(Credentials.EMAIL, preferences.email());
        queryUser.whereEqualTo(Credentials.GOOGLE_PLUS_ID, preferences.GooglePlusId());

        ParseQuery<Sale> querySale = ParseQuery.getQuery(Sale.class)
                .include(Sale.BUYER)
                .include(Sale.PRODRUCT);
        querySale.whereMatchesQuery(Sale.BUYER, queryUser);

        querySale.findInBackground(new FindCallback<Sale>() {
            @Override
            public void done(List<Sale> list, ParseException e) {
                recyclerViewFillCallback.finishLoadDAta();
                recyclerViewFillCallback.prepareRecyclerViewWithData(list);
            }
        });
    }
}
