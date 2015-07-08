package br.com.concretesolutions.cantina.application;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface Preferences {

    String GooglePlusId();

    String username();

    String email();
}
