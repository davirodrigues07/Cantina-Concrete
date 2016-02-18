package br.com.concretesolutions.cantina.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.ui.view.ItemDebitView;
import br.com.concretesolutions.cantina.ui.view.base.ViewWrapper;

public class DebitListAdapter extends RecyclerViewAdapterBase<Sale, ItemDebitView> {

    Context mContext;
    String totalInvoiced;

    public DebitListAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected ItemDebitView onCreateItemView(ViewGroup parent, int viewType) {
        return new ItemDebitView(mContext);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ItemDebitView> holder, int position) {
        ItemDebitView view = holder.getView();
        if (getList().get(position) == null) {
            view.bind(totalInvoiced);
        } else {
            view.bind(getList().get(position));
        }
    }

    public void setTotalInvoiced(String totalInvoiced) {
        this.totalInvoiced = totalInvoiced;
    }

}
