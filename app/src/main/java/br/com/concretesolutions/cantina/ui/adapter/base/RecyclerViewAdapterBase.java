package br.com.concretesolutions.cantina.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.concretesolutions.cantina.ui.view.base.ViewWrapper;

public abstract class RecyclerViewAdapterBase<T, V extends View> extends
        RecyclerView.Adapter<ViewWrapper<V>> {
    private List<T> mList = new ArrayList<>();
    private OnItemViewClickListener mItemViewClickListener;

    /**
     * Checks empty list
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return mList == null || mList.isEmpty();
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        if (mList == null) return 0;
        return mList.size();
    }

    @Override
    public ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewWrapper<V> viewWrapper = new ViewWrapper<>(onCreateItemView(parent, viewType));
        if (mItemViewClickListener != null) {
            viewWrapper.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemViewClickListener.onItemViewClicked(v);
                }
            });
        }
        return viewWrapper;
    }

    /**
     * Listener item click
     *
     * @param listener
     */
    public void setItemViewClickListener(OnItemViewClickListener listener) {
        mItemViewClickListener = listener;
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);


    public T getItem(int position) {
        return mList.get(position);
    }

    // Listener click interface
    public interface OnItemViewClickListener {
        public void onItemViewClicked(View v);
    }
}
