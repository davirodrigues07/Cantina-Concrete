package br.com.concretesolutions.cantina.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.application.Preferences_;
import br.com.concretesolutions.cantina.data.type.parse.Credentials;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;
import br.com.concretesolutions.cantina.ui.adapter.ListProductAdapter;

@EActivity(R.layout.activity_list)
public class ListProductActivity extends BaseActivity implements AdapterView.OnItemLongClickListener,
        AdapterView.OnItemClickListener {

    @ViewById
    ListView productList;
    @ViewById
    CoordinatorLayout containerListProduct;
    @ViewById
    ProgressBar progressOfListProduct;
    @ViewById
    Toolbar toolbar;
    @ViewById
    CollapsingToolbarLayout collapsingToolbar;
    @Pref
    Preferences_ mPref;

    List<Product> mProducts;

    @AfterViews
    public void afterViews() {
        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle(getResources().getString(R.string.app_name));

        containerListProduct.setVisibility(View.GONE);
        progressOfListProduct.setVisibility(View.VISIBLE);

        ParseQuery<Product> productParseQuery = ParseQuery.getQuery(Product.class);
        productParseQuery.findInBackground(new FindCallback<Product>() {
            @Override
            public void done(List<Product> products, ParseException e) {
                mProducts = products;
                generateListView();
                progressOfListProduct.setVisibility(View.GONE);
                containerListProduct.setVisibility(View.VISIBLE);
            }
        });

    }

    public void generateListView() {
        ArrayAdapter<Product> adapter = new ListProductAdapter(this, mProducts);
        productList.setAdapter(adapter);
        productList.setOnItemClickListener(this);
        productList.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ParseQuery.getQuery(Credentials.class).findInBackground(new FindCallback<Credentials>() {
            Credentials user;
            @Override
            public void done(List<Credentials> list, ParseException e) {
                for (Credentials credentials : list) {
                    if (credentials.getEmail().equals(mPref.email().get())) {
                        Sale sale = new Sale();
                        sale.setBuyer(user);
                    }
                }
            }
        });
    }
}
