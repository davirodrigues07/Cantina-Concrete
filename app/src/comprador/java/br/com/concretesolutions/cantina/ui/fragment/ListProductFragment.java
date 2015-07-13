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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.adapter.ListProductAdapter;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.ui.view.ItemProductView;

@EFragment(R.layout.fragment_list_product)
public class ListProductFragment extends Fragment implements ItemProductView.OnClickItemButtonListener,
        RecyclerViewAdapterBase.OnItemViewClickListener {

    public static final String NAME = ListProductFragment.class.getSimpleName();

    @ViewById(R.id.product_list)
    RecyclerView productList;
    @ViewById(R.id.progress_of_list_product)
    ProgressBar progressOfListProduct;

    List<Product> mProducts;

    @AfterViews
    public void afterView() {
        productList.setVisibility(View.GONE);
        progressOfListProduct.setVisibility(View.VISIBLE);
        ParseQuery.getQuery(Product.class).findInBackground(new FindCallback<Product>() {
            @Override
            public void done(List<Product> list, ParseException e) {
                mProducts = list;
                progressOfListProduct.setVisibility(View.GONE);
                productList.setVisibility(View.VISIBLE);
                prepareRecyclerView();
            }
        });
    }

    public void prepareRecyclerView() {
        ListProductAdapter adapter = new ListProductAdapter(getActivity());
        adapter.setList(mProducts);
        adapter.setOnClickItemButtonListener(this);
        adapter.setItemViewClickListener(this);
        productList.setLayoutManager(new LinearLayoutManager(getActivity()));
        productList.setAdapter(adapter);
        productList.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickedItemButton(Product product) {
        Toast.makeText(getActivity(), "button click name: " + product.getName(), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onItemViewClicked(View v) {
        int position = productList.getChildLayoutPosition(v);
        Toast.makeText(getActivity(), "item click name: " + mProducts.get(position).getName(), Toast.LENGTH_SHORT)
                .show();
    }
}
