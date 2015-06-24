package br.com.concretesolutions.cantina.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;

@EActivity(R.layout.activity_product_info)
public class ProductInfoActivity extends BaseActivity {

    @Extra
    String productId;
    Product mProduct;
    @ViewById
    ImageView productImage;
    @ViewById
    TextView productPrice;
    @ViewById
    TextView productAmount;
    @ViewById
    ProgressBar progressOfProductInfo;
    @ViewById
    LinearLayout containerProductInfo;

    @ViewById
    CollapsingToolbarLayout collapsingToolbar;
    @ViewById
    Toolbar toolbar;

    @AfterViews
    public void afterViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        containerProductInfo.setVisibility(View.INVISIBLE);
        progressOfProductInfo.setVisibility(View.VISIBLE);

        ParseQuery<Product> parseQuery = ParseQuery.getQuery(Product.class);
        parseQuery.whereEqualTo("objectId", this.productId);
        parseQuery.getFirstInBackground(new GetCallback<Product>() {
            @Override
            public void done(Product product, ParseException e) {
                mProduct = product;
                loadProductInfo();

                progressOfProductInfo.setVisibility(View.GONE);
                containerProductInfo.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        afterViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * load the product info
     */
    public void loadProductInfo() {
        collapsingToolbar.setTitle(mProduct.getName());
        Glide.with(this)
                .load(mProduct.getImage().getUrl())
                .centerCrop()
                .into(productImage);
        productPrice.setText(mProduct.getPrice());
        productAmount.setText(mProduct.getAmount());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * opens intent product register for edit
     */
    @Click(R.id.editProduct)
    public void click(View v) {
        RegisterProductActivity_.intent(this)
                .productId(productId)
                .start();
    }
}
