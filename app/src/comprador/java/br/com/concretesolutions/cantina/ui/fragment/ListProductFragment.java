package br.com.concretesolutions.cantina.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import br.com.concretesolutions.cantina.ui.adapter.ListProductAdapter;
import br.com.concretesolutions.cantina.ui.adapter.base.RecyclerViewAdapterBase;
import br.com.concretesolutions.cantina.ui.view.ItemProductView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListProductFragment extends Fragment implements ItemProductView.OnClickItemButtonListener,
        RecyclerViewAdapterBase.OnItemViewClickListener, RecyclerViewFill<Product>{

    public static final String NAME = ListProductFragment.class.getSimpleName();

    @Bind(R.id.product_list)
    RecyclerView productList;
    @Bind(R.id.progress_of_list_product)
    ProgressBar progressOfListProduct;

    @Bind(R.id.container_root)
    FrameLayout containerRoot;
    Preferences mPreferences;

    View root;

    ListProductPresenter presenter;

    public void popFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(this)
                .commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list_product, container);
        ButterKnife.bind(this, root);
        presenter = new ListProductPresenter();
        presenter.initialize(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPreferences = ApplicationPreference.getPreferece(this.getActivity().getApplicationContext());
    }

    @Override
    public void prepareRecyclerViewWithData(final List<Product> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListProductAdapter adapter = new ListProductAdapter(getActivity());
                adapter.setList(list);
                adapter.setOnClickItemButtonListener(ListProductFragment.this);
                adapter.setItemViewClickListener(ListProductFragment.this);
                productList.setAdapter(adapter);
                productList.setLayoutManager(new LinearLayoutManager(getActivity()));
                productList.setItemAnimator(new DefaultItemAnimator());
                adapter.notifyDataSetChanged();
            }
        });
    }


    @OnClick(R.id.buttom_buy)
    public  void buy(){
        final Transition transition = new Fade(Fade.IN);
        final TransitionManager mTransitionManager = new TransitionManager();
        final Scene mScene1 = Scene.getSceneForLayout(containerRoot, R.layout.button_buy_scene, getContext());
        final Scene mScene2 = Scene.getSceneForLayout(containerRoot, R.layout.popup_buy_scene, getContext());
        mTransitionManager.setTransition(mScene1, mScene2, transition);
        mTransitionManager.setTransition(mScene2, mScene1, transition);

        mTransitionManager.go(mScene2);
        Button yes = ButterKnife.findById(containerRoot, R.id.buy_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO goto to activity
            }
        });
        Button no = ButterKnife.findById(containerRoot, R.id.buy_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTransitionManager.go(mScene1);
                ButterKnife.bind(ListProductFragment.this, root);
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
                                Toast.makeText(getActivity(),
                                        "Compra realizada com sucesso",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                    }
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemViewClicked(View v) {
        // add action
    }

}
