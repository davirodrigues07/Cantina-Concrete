package br.com.concretesolutions.cantina.ui.activity.base;

import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        if (!isOnline()) {
            Toast.makeText(this, "Offline", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if connectivity is available
     *
     * @return boolean
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
