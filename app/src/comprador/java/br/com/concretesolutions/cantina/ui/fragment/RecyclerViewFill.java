package br.com.concretesolutions.cantina.ui.fragment;

import java.util.List;

public interface RecyclerViewFill<T> {

    void prepareRecyclerViewWithData(final List<T> list);

}
