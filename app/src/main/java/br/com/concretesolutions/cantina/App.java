package br.com.concretesolutions.cantina;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import br.com.concretesolutions.cantina.data.type.parse.Credentials;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.data.type.parse.Sale;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Product.class);
        ParseObject.registerSubclass(Credentials.class);
        ParseObject.registerSubclass(Sale.class);

        Parse.initialize(this, "z5kcJ98mrUkJ6V3ZGvNIYGiCizld9w9Se65I6zIO",
                "FeyojuuN6VhgR2hk1Qa7k5e3mZ9klyuOrXLWSWh5");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

}