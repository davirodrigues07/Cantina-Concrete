package br.com.concretesolutions.cantina.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.ui.view.ItemProductView;
import br.com.concretesolutions.cantina.ui.view.base.ViewWrapper;

public class ListProductAdapter extends RecyclerViewAdapterBase<Product, ItemProductView> {

    Context mContext;
    ItemProductView.OnClickItemButtonListener mOnClickItemButtonListener;

    public ListProductAdapter(Context context) {
        mContext = context;
    }

    public void setOnClickItemButtonListener(ItemProductView.OnClickItemButtonListener onClickItemButtonListener) {
        mOnClickItemButtonListener = onClickItemButtonListener;
    }

    @Override
    protected ItemProductView onCreateItemView(ViewGroup parent, int viewType) {
        return new ItemProductView(mContext);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ItemProductView> holder, int position) {
        ItemProductView view = holder.getView();
        view.setClickItemButtonListener(mOnClickItemButtonListener);
        view.bind(getList().get(position));
    }
}