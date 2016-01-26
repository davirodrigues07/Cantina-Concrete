package br.com.concretesolutions.cantina.data.type.parse;

/**
 * Created by davirodrigues on 25/01/16.
 */
public enum SALE_STATE {

    CREATED(0),
    INVOICED(1),
    PAID(2);

    private final int value;

    SALE_STATE(final int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
