package br.com.concretesolutions.cantina.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.application.ApplicationPreference;
import br.com.concretesolutions.cantina.application.Preferences;
import br.com.concretesolutions.cantina.data.type.parse.Credentials;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.data.type.parse.SALE_STATE;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import br.com.concretesolutions.cantina.interfaces.RecyclerViewFill;
import br.com.concretesolutions.cantina.presenter.ListProductPresenter;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;
import br.com.concretesolutions.cantina.ui.adapter.ListProductAdapter;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.ui.view.ItemProductView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListProductActivity extends BaseActivity implements ItemProductView.OnClickItemButtonListener,
        RecyclerViewAdapterBase.OnItemViewClickListener, RecyclerViewFill<Product> {

    public static final String NAME = ListProductActivity.class.getSimpleName();

    @Bind(R.id.product_list)
    RecyclerView productList;
    @Bind(R.id.progress_of_list_product)
    ProgressBar progressOfListProduct;

    @Bind(R.id.container_root)
    FrameLayout containerRoot;
    Preferences mPreferences;

    View root;

    ListProductPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = LayoutInflater.from(this).inflate(R.layout.activity_list_product, null);
        setContentView(root);
        ButterKnife.bind(this, root);
        mPreferences = ApplicationPreference.getPreferece(getApplicationContext());
        presenter = new ListProductPresenter();
        presenter.initialize(this);
    }


    @Override
    public void prepareRecyclerViewWithData(final List<Product> list) {
        ListProductAdapter adapter = new ListProductAdapter(this);
        adapter.setList(list);
        adapter.setOnClickItemButtonListener(ListProductActivity.this);
        adapter.setItemViewClickListener(ListProductActivity.this);
        productList.setAdapter(adapter);
        productList.setLayoutManager(new LinearLayoutManager(this));
        productList.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();

    }


    @OnClick(R.id.buttom_buy)
    public void buy() {
        final Transition transition = new Fade(Fade.IN);
        final TransitionManager mTransitionManager = new TransitionManager();
        final Scene mScene1 = Scene.getSceneForLayout(containerRoot, R.layout.button_buy_scene, this);
        final Scene mScene2 = Scene.getSceneForLayout(containerRoot, R.layout.popup_buy_scene, this);
        mTransitionManager.setTransition(mScene1, mScene2, transition);
        mTransitionManager.setTransition(mScene2, mScene1, transition);

        mTransitionManager.go(mScene2);
        Button yes = ButterKnife.findById(containerRoot, R.id.buy_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO finalize sale process
                onBackPressed();
            }
        });
        Button no = ButterKnife.findById(containerRoot, R.id.buy_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTransitionManager.go(mScene1);
                ButterKnife.bind(ListProductActivity.this, root);
            }
        });

    }

    @Override
    public void loadingData() {
        productList.setVisibility(View.GONE);
        progressOfListProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishLoadDAta() {
        progressOfListProduct.setVisibility(View.GONE);
        productList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickedItemButton(List<Product> products) {
        final Sale sale = new Sale();
        sale.setProduct(products.get(0));
        ParseQuery.getQuery(Credentials.class)
                .whereEqualTo(Credentials.EMAIL, mPreferences.email())
                .findInBackground(new FindCallback<Credentials>() {
                    @Override
                    public void done(List<Credentials> list, ParseException e) {
                        sale.setBuyer(list.get(0));
                        sale.setState(SALE_STATE.CREATED);
                        sale.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                Toast.makeText(ListProductActivity.this,
                                        "Compra realizada com sucesso",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                    }
                });
    }

    @Override
    public void onItemViewClicked(View v) {
        // add action
    }

}
