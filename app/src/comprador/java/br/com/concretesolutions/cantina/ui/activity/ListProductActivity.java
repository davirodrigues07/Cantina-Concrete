package br.com.concretesolutions.cantina.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.application.ApplicationPreference;
import br.com.concretesolutions.cantina.application.Preferences;
import br.com.concretesolutions.cantina.data.type.parse.Credentials;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.data.type.parse.SALE_STATE;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.interfaces.RecyclerViewFill;
import br.com.concretesolutions.cantina.presenter.ListProductPresenter;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;
import br.com.concretesolutions.cantina.ui.adapter.ListProductAdapter;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.ui.view.ItemProductView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ListProductActivity extends BaseActivity implements ItemProductView.OnClickItemButtonListener,
        RecyclerViewAdapterBase.OnItemViewClickListener, RecyclerViewFill<Product> {

    public static final String NAME = ListProductActivity.class.getSimpleName();

    @Bind(R.id.product_list)
    RecyclerView productList;
    @Bind(R.id.progress_of_list_product)
    ProgressBar progressOfListProduct;

    Preferences mPreferences;

    View root;

    ListProductPresenter presenter;

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initialize(this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = LayoutInflater.from(this).inflate(R.layout.activity_list_product, null);
        setContentView(root);
        ButterKnife.bind(this, root);
        mPreferences = ApplicationPreference.getPreference(getApplicationContext());
        presenter = new ListProductPresenter();
    }


    @Override
    public void prepareRecyclerViewWithData(List<Product> list, String totalInvoiced) {
        ListProductAdapter adapter = new ListProductAdapter(this);
        adapter.setList(list);
        adapter.setOnClickItemButtonListener(ListProductActivity.this);
        adapter.setItemViewClickListener(ListProductActivity.this);
        productList.setAdapter(adapter);
        productList.setLayoutManager(new LinearLayoutManager(this));
        productList.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadingData() {
        productList.setVisibility(View.GONE);
        progressOfListProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishLoadDAta() {
        progressOfListProduct.setVisibility(View.GONE);
        productList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickedItemButton(Product products) {
        final Sale sale = new Sale();
        sale.setProduct(products);
        ParseQuery.getQuery(Credentials.class)
                .whereEqualTo(Credentials.EMAIL, mPreferences.email())
                .findInBackground(new FindCallback<Credentials>() {
                    @Override
                    public void done(List<Credentials> list, ParseException e) {
                        sale.setBuyer(list.get(0));
                        sale.setState(SALE_STATE.CREATED);
                        sale.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                Toast.makeText(ListProductActivity.this,
                                        "Compra realizada com sucesso",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                    }
                });
    }

    @Override
    public void onItemViewClicked(View v) {
        // add action
    }
}
