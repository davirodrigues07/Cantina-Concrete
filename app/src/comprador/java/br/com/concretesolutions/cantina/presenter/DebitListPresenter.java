package br.com.concretesolutions.cantina.presenter;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.com.concretesolutions.cantina.application.Preferences;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.interfaces.DebitListView;
import br.com.concretesolutions.cantina.utils.PriceHelper;

public class DebitListPresenter {

    private DebitListView debitListView;
    private Preferences preferences;

    public DebitListPresenter() {
    }

    public void initialize(final DebitListView debitListView, Preferences preferences) {

        this.debitListView = debitListView;
        this.preferences = preferences;
        prepareCallback();
    }


    public void prepareCallback() {
        debitListView.loadingData();

        final HashMap<String, String> map = new HashMap<>();
        map.put("googlePlusId", preferences.GooglePlusId());
        ParseCloud.callFunctionInBackground("totalInvoice", map, new FunctionCallback<String>() {
            @Override
            public void done(final String value, ParseException e) {
                debitListView.returnTotalInvoice("R$ " + PriceHelper.formatPrice(value));
                ParseCloud.callFunctionInBackground("debitList", map, new FunctionCallback<HashMap>() {
                    @Override
                    public void done(HashMap object, ParseException e) {
                        if (e == null) {
                            List<Sale> sales = (List<Sale>) object.get("sales");
                            Collections.reverse(sales);
                            debitListView.prepareRecyclerViewWithData(sales, "R$ " + PriceHelper.formatPrice(value));
                            debitListView.finishLoadDAta();
                        } else {

                        }
                    }
                });
            }
        });


    }
}
