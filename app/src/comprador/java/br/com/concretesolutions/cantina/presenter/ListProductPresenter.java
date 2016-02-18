package br.com.concretesolutions.cantina.presenter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.interfaces.RecyclerViewFill;

public class ListProductPresenter {

    public void initialize(final RecyclerViewFill<Product> recyclerViewFill) {
        recyclerViewFill.loadingData();
        ParseQuery.getQuery(Product.class).findInBackground(new FindCallback<Product>() {
            @Override
            public void done(List<Product> list, ParseException e) {
                recyclerViewFill.finishLoadDAta();
                recyclerViewFill.prepareRecyclerViewWithData(list, null);
            }
        });
    }
}
