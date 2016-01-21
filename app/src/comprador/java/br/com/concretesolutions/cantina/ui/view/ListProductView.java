package br.com.concretesolutions.cantina.ui.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.adapter.ListProductAdapter;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.interfaces.RecyclerViewFill;

public class ListProductView extends LinearLayout implements RecyclerViewFill<Product>,
        ItemProductView.OnClickItemButtonListener, RecyclerViewAdapterBase.OnItemViewClickListener {

    private RecyclerView mListProduct;
    private ProgressBar mProgressBar;

    public ListProductView(Context context) {
        this(context, null);
    }

    public ListProductView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListProductView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.view_list_product, this);
        mProgressBar = (ProgressBar) layout.findViewById(R.id.progress_of_list_product);
        mListProduct = (RecyclerView) layout.findViewById(R.id.product_list);
    }

    @Override
    public void prepareRecyclerViewWithData(List<Product> list) {
        ListProductAdapter adapter = new ListProductAdapter(getContext());
        adapter.setList(list);
        adapter.setOnClickItemButtonListener(this);
        adapter.setItemViewClickListener(this);
        mListProduct.setAdapter(adapter);
        mListProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        mListProduct.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadingData() {
        mProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void finishLoadDAta() {
        mProgressBar.setVisibility(GONE);
    }

    @Override
    public void onClickedItemButton(Product product) {

    }

    @Override
    public void onItemViewClicked(View v) {

    }
}
