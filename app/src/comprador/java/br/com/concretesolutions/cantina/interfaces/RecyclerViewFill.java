package br.com.concretesolutions.cantina.interfaces;

import java.util.List;

public interface RecyclerViewFill<T> {

    void prepareRecyclerViewWithData(final List<T> list);

    void loadingData();

    void finishLoadDAta();

}
