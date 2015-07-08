package br.com.concretesolutions.cantina.data.type.parse;


import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.GregorianCalendar;

@ParseClassName("Sale")
public class Sale extends ParseObject implements Serializable {

    public static String BUYER = "Buyer";
    public static String PRODRUCT = "Product";
    public static String PAID = "Paid";
    public static String DATE_OF_CREATE = "createAt";

    public Credentials getBuyer() {
        return (Credentials)this.getParseObject(BUYER);
    }

    public void setBuyer(Credentials buyer) {
        this.put(BUYER, buyer);
    }

    public Product getProduct() {
        return (Product)this.getParseObject(PRODRUCT);
    }

    public void setProduct(Product product) {
        this.put(PRODRUCT, product);
    }

    public boolean getPaid() {
        return this.getBoolean(PAID);
    }

    public void setPaid(boolean paid) {
        this.put(PAID, paid);
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
                .get(GregorianCalendar.HOUR_OF_DAY))+":"+
                String.valueOf(getDataCreate()
                .get(GregorianCalendar.SECOND));
    }
}
