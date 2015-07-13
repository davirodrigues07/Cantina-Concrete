package br.com.concretesolutions.cantina.ui.view.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    View mView;

    public ViewWrapper(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public View getView() {
        return mView;
    }
}
