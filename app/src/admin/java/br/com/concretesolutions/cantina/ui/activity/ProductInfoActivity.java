package br.com.concretesolutions.cantina.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductInfoActivity extends BaseActivity {
    public final static String PRODUCT_ID_EXTRA = "productId";

    String productId;
    Product mProduct;
    @Bind(R.id.productImage)
    ImageView productImage;
    @Bind(R.id.productPrice)
    TextView productPrice;
    @Bind(R.id.productAmount)
    TextView productAmount;
    @Bind(R.id.progressOfProductInfo)
    ProgressBar progressOfProductInfo;
    @Bind(R.id.containerProductInfo)
    LinearLayout containerProductInfo;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        ButterKnife.bind(this);
        productId = getIntent().getExtras().getString(PRODUCT_ID_EXTRA);
        afterViews();
    }

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

    public static Intent intentOpenInfo(Context context, String objectId) {
        return new Intent(context, ProductInfoActivity.class)
                .putExtra(ProductInfoActivity.PRODUCT_ID_EXTRA, objectId);

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
    @OnClick(R.id.editProduct)
    public void click(View v) {
        Intent intent = RegisterProductActivity.intentEdit(this, productId);
        startActivity(intent);
    }
}
