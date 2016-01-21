package br.com.concretesolutions.cantina.presenter;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;

import br.com.concretesolutions.cantina.application.Preferences;
import br.com.concretesolutions.cantina.data.type.parse.Credentials;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.interfaces.DebitListView;
import br.com.concretesolutions.cantina.utils.PriceHelper;

public class DebitListPresenter {

    private DebitListView debitListView;
    private Preferences preferences;


    public void initialize(final DebitListView debitListView, Preferences preferences) {

        this.debitListView = debitListView;
        this.preferences = preferences;
        getTotalDebit();
        prepareCallback();
    }

    public void getTotalDebit() {
        HashMap<String, String> map = new HashMap<>();
        map.put("googlePlusId", preferences.GooglePlusId());
        ParseCloud.callFunctionInBackground("totalDebit", map, new FunctionCallback<Double>() {
            @Override
            public void done(Double value, ParseException e) {
                if (e == null) {
                    String debit = "R$"+ String.valueOf(PriceHelper.formatPrice(value.toString()));
                    debitListView.returnTotalDebit(debit);
                } else {
                    Log.d("app", "error: " + e.getMessage());
                }
            }
        });
    }

    public void prepareCallback() {
        debitListView.loadingData();

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
                debitListView.finishLoadDAta();
                debitListView.prepareRecyclerViewWithData(list);
            }
        });
    }
}
