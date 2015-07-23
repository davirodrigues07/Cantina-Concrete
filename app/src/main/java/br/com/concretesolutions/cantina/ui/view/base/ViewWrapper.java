package br.com.concretesolutions.cantina.ui.view.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    V mView;

    public ViewWrapper(V itemView) {
        super(itemView);
        mView = itemView;
    }

    public V getView() {
        return mView;
    }
}
