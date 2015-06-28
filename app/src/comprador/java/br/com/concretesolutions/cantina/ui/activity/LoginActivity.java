package br.com.concretesolutions.cantina.ui.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Credentials;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;

@EActivity(R.layout.activity_main)
public class LoginActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    ConnectionResult mConectionResult;
    Person person = null;

    @ViewById
    SignInButton signInButton;

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

    @AfterViews
    public void afterVews() {

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
        signInButton.setEnabled(false);
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

            final Credentials credentials = new Credentials();
            credentials.setGooglePlusId(person.getId());
            // verifica se o item já existe no parse
            ParseQuery.getQuery(Credentials.class).findInBackground(new FindCallback<Credentials>() {
                @Override
                public void done(List<Credentials> list, ParseException e) {
                    for (Credentials item : list) {
                        if(item.getGooglePlusId().equals(credentials.getGooglePlusId())) {
                            // pega o objectId para sobrescrever o objeto
                            credentials.setObjectId(item.getObjectId());
                        }
                        credentials.setName(person.getDisplayName());
                        credentials.setImage(person.getImage().getUrl().replace("?sz=50",""));
                        credentials.setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
                        credentials.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                String msg;
                                if (e == null) {
                                    msg = "Nome: " + credentials.getName() + " email: " + credentials.getEmail();
                                } else {
                                    msg = "Erro: "+e.getMessage();
                                }
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    }
                }
            });
        }
    }

    @Click(R.id.sign_in_button)
    public void click(View v) {
        if (!mGoogleApiClient.isConnecting() && mConectionResult.hasResolution()) {
            try {
                mConectionResult.startResolutionForResult(this, mConectionResult.getErrorCode());
            } catch (IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        getProfileInformation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
            return;
        }
        mConectionResult = connectionResult;
    }
}
