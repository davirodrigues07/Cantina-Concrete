package br.com.concretesolutions.cantina.data.type.parse;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Product")
public class Product extends ParseObject {

    public final static String IMAGE = "image";
    public final static String PRICE = "price";
    public final static String NAME = "name";
    public final static String AMOUNT = "amount";
    public final static String BARCODE = "barCode";

    public void setImage(ParseFile parseFile) {
        this.put(IMAGE, parseFile);
    }

    public void setPrice(double price) {
        this.put(PRICE, price);
    }

    public void setName(String name) {
        this.put(NAME, name);
    }

    public void setAmount(int amount) {
        this.put(AMOUNT, amount);
    }

    public void setBarCode(String barCode) {
        this.put(BARCODE, barCode);
    }

}
