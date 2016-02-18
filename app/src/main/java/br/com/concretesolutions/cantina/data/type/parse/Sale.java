package br.com.concretesolutions.cantina.data.type.parse;


import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;

@ParseClassName("Sale")
public class Sale extends ParseObject implements Serializable {

    public static String BUYER = "buyer";
    public static String PRODRUCT = "product";
    public static String STATE = "state";
    public static String DATE_OF_CREATE = "createAt";

    public Credentials getBuyer() {
        return (Credentials) this.getParseObject(BUYER);
    }

    public void setBuyer(Credentials buyer) {
        this.put(BUYER, buyer);
    }

    public Product getProduct() {
        return (Product) this.getParseObject(PRODRUCT);
    }

    public void setProduct(Product product) {
        this.put(PRODRUCT, product);
    }

    public SALE_STATE getPaid() {
        return (SALE_STATE) this.get(STATE);
    }

    public void setState(SALE_STATE state) {
        this.put(STATE, state.getValue());
    }

    public String getDataCreate() {
        return new SimpleDateFormat("MMM dd")
                .format(this.getCreatedAt().getTime());
    }

    public String getTimeCreate(){

        return new SimpleDateFormat("hh:mm'h'")
                .format(this.getCreatedAt().getTime());
    }
}
