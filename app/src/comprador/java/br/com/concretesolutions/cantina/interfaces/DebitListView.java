package br.com.concretesolutions.cantina.interfaces;

import br.com.concretesolutions.cantina.data.type.parse.Sale;

public interface DebitListView extends RecyclerViewFill<Sale> {
    void returnTotalInvoice(String value);
    void returnError(String message);
}
