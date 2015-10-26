package br.com.concretesolutions.cantina.ui.presenter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.ui.fragment.RecyclerViewFill;

public class DebitListPresenter {

    public void initialize(final RecyclerViewFill<Sale> recyclerViewFill) {
        ParseQuery.getQuery(Sale.class).findInBackground(new FindCallback<Sale>() {
            @Override
            public void done(List<Sale> list, ParseException e) {
                recyclerViewFill.prepareRecyclerViewWithData(list);
            }
        });
    }
}
