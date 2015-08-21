package br.com.concretesolutions.cantina.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.ui.view.ItemDebitView;
import br.com.concretesolutions.cantina.ui.view.ItemDebitView_;
import br.com.concretesolutions.cantina.ui.view.base.ViewWrapper;

public class DebitListAdapter extends RecyclerViewAdapterBase<Sale, ItemDebitView> {

    Context mContext;

    public DebitListAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected ItemDebitView onCreateItemView(ViewGroup parent, int viewType) {
        return ItemDebitView_.build(mContext);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ItemDebitView> holder, int position) {
        ItemDebitView view = holder.getView();
        view.bind(getList().get(position));
    }
}
