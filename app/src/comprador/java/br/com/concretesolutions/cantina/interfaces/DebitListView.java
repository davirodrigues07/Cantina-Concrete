package br.com.concretesolutions.cantina.interfaces;

import br.com.concretesolutions.cantina.data.type.parse.Sale;

/**
 * Created by davi.silva on 1/21/16.
 */
public interface DebitListView extends RecyclerViewFill<Sale>{
    void returnTotalDebit(String value);
}
