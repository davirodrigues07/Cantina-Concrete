package br.com.concretesolutions.cantina;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import org.androidannotations.annotations.EApplication;

import br.com.concretesolutions.cantina.data.type.parse.Product;

@EApplication
public class App extends Application {

    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Product.class);
        Parse.initialize(this, "z5kcJ98mrUkJ6V3ZGvNIYGiCizld9w9Se65I6zIO",
                "FeyojuuN6VhgR2hk1Qa7k5e3mZ9klyuOrXLWSWh5");
    }

}