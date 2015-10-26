package br.com.concretesolutions.cantina.ui.fragment;

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
import com.parse.SaveCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.application.Preferences_;
import br.com.concretesolutions.cantina.data.type.parse.Credentials;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.ui.adapter.ListProductAdapter;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.ui.presenter.ListProductPresenter;
import br.com.concretesolutions.cantina.ui.view.ItemProductView;

@EFragment(R.layout.fragment_list_product)
public class ListProductFragment extends Fragment implements ItemProductView.OnClickItemButtonListener,
        RecyclerViewAdapterBase.OnItemViewClickListener, RecyclerViewFill<Product> {

    public static final String NAME = ListProductFragment.class.getSimpleName();

    @ViewById(R.id.product_list)
    RecyclerView productList;
    @ViewById(R.id.progress_of_list_product)
    ProgressBar progressOfListProduct;
    @Pref
    Preferences_ mPrefs;

    ListProductPresenter presenter;

    List<Product> mProducts;

    @AfterViews
    public void afterView() {
        productList.setVisibility(View.GONE);
        progressOfListProduct.setVisibility(View.VISIBLE);
        presenter = new ListProductPresenter();
        presenter.initialize(this);
    }


    @UiThread
    @Override
    public void prepareRecyclerViewWithData(List<Product> list) {
        progressOfListProduct.setVisibility(View.GONE);
        productList.setVisibility(View.VISIBLE);
        ListProductAdapter adapter = new ListProductAdapter(getActivity());
        adapter.setList(list);
        adapter.setOnClickItemButtonListener(this);
        adapter.setItemViewClickListener(this);
        productList.setAdapter(adapter);
        productList.setLayoutManager(new LinearLayoutManager(getActivity()));
        productList.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickedItemButton(Product product) {
        final Sale sale = new Sale();
        sale.setProduct(product);
        ParseQuery.getQuery(Credentials.class).whereEqualTo(Credentials.EMAIL, mPrefs.email().getOr(""))
                .findInBackground(new FindCallback<Credentials>() {
                    @Override
                    public void done(List<Credentials> list, ParseException e) {
                        sale.setBuyer(list.get(0));
                        sale.setPaid(false);
                        sale.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                Toast.makeText(getActivity(),
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
