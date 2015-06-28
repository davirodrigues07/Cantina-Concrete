package br.com.concretesolutions.cantina.data.type.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Credentials")
public class Credentials extends ParseObject implements Serializable {

    public static final String GOOGLE_PLUS_ID = "googlePlusId";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String DEBIT = "debit";
    public static final String EMAIL = "email";

    public String getGooglePlusId () {
        return this.getString(GOOGLE_PLUS_ID);
    }

    public void setGooglePlusId (String googlePlusId) {
        this.put(GOOGLE_PLUS_ID, googlePlusId);
    }

    public String getName() {
        return this.getString(NAME);
    }

    public void setName(String name) {
        this.put(NAME, name);
    }

    public String getEmail() {
        return this.getString(EMAIL);
    }

    public void setEmail(String email) {
        this.put(EMAIL, email);
    }

    public String getImage() {
        return this.getString(IMAGE);
    }

    public void setImage(String imageUrl) {
        this.put(IMAGE, imageUrl);
    }

    public float getDebit() {
        return (float)this.getNumber(DEBIT);
    }

    public void setDebit(float debit) {
        this.put(DEBIT, debit);
    }

}
