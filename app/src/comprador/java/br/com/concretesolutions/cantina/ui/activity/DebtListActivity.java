package br.com.concretesolutions.cantina.ui.activity;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.application.Preferences_;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;

@EActivity(R.layout.activity_debt_list)
public class DebtListActivity extends BaseActivity {

    @Pref
    Preferences_ mPreferences;
    @ViewById
    FloatingActionButton fabBuy;
    @ViewById
    AppBarLayout appbar;
    @ViewById
    Toolbar toolbar;

    @Override
    protected void onStart() {
        super.onStart();
        if (mPreferences.username().getOr("").equals("") &&
                mPreferences.GooglePlusId().getOr("").equals("")) {
            LoginActivity_.intent(this).start();
        }
    }

    @AfterViews
    public void afterView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fabBuy.setElevation(getResources().getDimension(R.dimen.appbar_elevation));
        }

    }

    @Click(R.id.fab_buy)
    public void fabBuyClick() {
        ListProductActivity_.intent(this).start();
    }

}
