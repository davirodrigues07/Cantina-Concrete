package br.com.concretesolutions.cantina.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Credentials;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String CONCRETE_DOMAIN = "@concretesolutions.com.br";
    ConnectionResult mConectionResult;
    Person person = null;
    @Bind(R.id.sign_in_button)
    Button signInButton;
    @Bind(R.id.progress_login)
    ProgressBar progressLogin;
    @Bind(R.id.icon_app)
    ImageView iconApp;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        mGoogleApiClient.connect();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mGoogleApiClient.isConnecting() && mConectionResult.hasResolution()) {
            mGoogleApiClient.connect();
        }
    }

    public void getProfileInformation() {
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

            final Credentials credentials = new Credentials();
            credentials.setGooglePlusId(person.getId());

            ParseQuery.getQuery(Credentials.class).findInBackground(new FindCallback<Credentials>() {
                @Override
                public void done(List<Credentials> list, ParseException e) {
                    for (Credentials item : list) {
                        if (item.getGooglePlusId().equals(credentials.getGooglePlusId())) {
                            // Get objectId if already exists on the parse
                            credentials.setObjectId(item.getObjectId());
                        }
                    }
                    credentials.setName(person.getDisplayName());
                    credentials.setImage(person.getImage().getUrl().replace("?sz=50", ""));
                    credentials.setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
                    credentials.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            String msg;
                            if (e == null) {
                                msg = "Nome: " + credentials.getName() + " email: " + credentials.getEmail();
                                // Save in sharedpreferences
                                mPreferences.GooglePlusId(person.getId());
                                mPreferences.username(Plus.AccountApi.getAccountName(mGoogleApiClient));
                                mPreferences.email(credentials.getEmail());
                                // Disable singInButton
                                signInButton.setEnabled(false);
                                finish();
                            } else {
                                msg = "Erro: " + e.getMessage();
                            }
                            //Fixme remover isso depois xD
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

                }
            });
        }
    }

    @OnClick(R.id.sign_in_button)
    public void click(View v) {
        if (!mGoogleApiClient.isConnecting() && mConectionResult.hasResolution()) {
            iconApp.setVisibility(View.GONE);
            progressLogin.setVisibility(View.VISIBLE);
            try {
                mConectionResult.startResolutionForResult(this, mConectionResult.getErrorCode());
            } catch (IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Mail domain is not @concretesolutions.com.br
        if (!Plus.AccountApi.getAccountName(mGoogleApiClient).contains(CONCRETE_DOMAIN)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Esse email não é Concrete Solutions. Tente novamente.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showLoad(false);
                            mGoogleApiClient.disconnect();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            showLoad(true);
            getProfileInformation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        showLoad(false);
        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
            return;
        }
        mConectionResult = connectionResult;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPreferences.username().equals("") &&
                mPreferences.GooglePlusId().equals("")) {
            // Sets this Activity how last on stack
            moveTaskToBack(true);
        }
        finish();
    }

    public void showLoad(boolean show) {
        if (show) {
            iconApp.setVisibility(View.GONE);
            progressLogin.setVisibility(View.VISIBLE);
            signInButton.setEnabled(false);
        } else {
            progressLogin.setVisibility(View.GONE);
            iconApp.setVisibility(View.VISIBLE);
            signInButton.setEnabled(true);
        }
    }
}
