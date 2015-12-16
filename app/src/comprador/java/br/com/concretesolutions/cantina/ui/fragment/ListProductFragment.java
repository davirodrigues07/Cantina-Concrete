package br.com.concretesolutions.cantina.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.ui.adapter.ListProductAdapter;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.ui.presenter.ListProductPresenter;
import br.com.concretesolutions.cantina.ui.view.ItemProductView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ListProductFragment extends Fragment implements ItemProductView.OnClickItemButtonListener,
        RecyclerViewAdapterBase.OnItemViewClickListener, RecyclerViewFill<Product> {

    public static final String NAME = ListProductFragment.class.getSimpleName();

    @Bind(R.id.product_list)
    RecyclerView productList;
    @Bind(R.id.progress_of_list_product)
    ProgressBar progressOfListProduct;
    Preferences mPreferences;

    ListProductPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_product, container);
        ButterKnife.bind(this, view);
        presenter = new ListProductPresenter();
        presenter.initialize(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mPreferences = ApplicationPreference.getPreferece(this.getActivity().getApplicationContext());
    }


    @Override
    public void prepareRecyclerViewWithData(final List<Product> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListProductAdapter adapter = new ListProductAdapter(getActivity());
                adapter.setList(list);
                adapter.setOnClickItemButtonListener(ListProductFragment.this);
                adapter.setItemViewClickListener(ListProductFragment.this);
                productList.setAdapter(adapter);
                productList.setLayoutManager(new LinearLayoutManager(getActivity()));
                productList.setItemAnimator(new DefaultItemAnimator());
                adapter.notifyDataSetChanged();
            }
        });
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
    public void onClickedItemButton(Product product) {
        final Sale sale = new Sale();
        sale.setProduct(product);
        ParseQuery.getQuery(Credentials.class).whereEqualTo(Credentials.EMAIL, mPreferences.email())
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemViewClicked(View v) {
        // add action
    }
}
