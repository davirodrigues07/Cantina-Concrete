package br.com.concretesolutions.cantina.application;

public interface Preferences {

    String GooglePlusId();

    String username();

    String email();

    void GooglePlusId(String value);

    void username(String value);

    void email(String value);
}
