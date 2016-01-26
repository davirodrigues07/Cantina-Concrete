package br.com.concretesolutions.cantina.data.type.parse;


import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.GregorianCalendar;

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

    public GregorianCalendar getDataCreate() {
        GregorianCalendar dataCreate = new GregorianCalendar();
        dataCreate.setGregorianChange(this.getDate(DATE_OF_CREATE));
        return dataCreate;
    }

    public String getDay() {
        return String.valueOf(getDataCreate()
                .get(GregorianCalendar.DAY_OF_MONTH));
    }

    public String getMonth() {
        return String.valueOf(getDataCreate()
                .get(GregorianCalendar.MONTH));
    }

    public String getHour() {
        return String.valueOf(getDataCreate()
                .get(GregorianCalendar.HOUR_OF_DAY)) + ":" +
                String.valueOf(getDataCreate()
                        .get(GregorianCalendar.SECOND));
    }
}
