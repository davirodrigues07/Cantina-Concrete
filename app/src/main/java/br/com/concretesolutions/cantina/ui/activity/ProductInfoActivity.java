package br.com.concretesolutions.cantina.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;

@EActivity(R.layout.activity_product_info)
public class ProductInfoActivity extends ActionBarActivity {

    @Extra
    String productId;
    Product mProduct;

    @ViewById
    TextView productName;
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

    @AfterViews
    public void afterViews() {
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

    public void loadProductInfo() {
        productName.setText(mProduct.getName());
        Picasso.with(this)
                .load(mProduct.getImage().getUrl())
                .resize(productImage.getWidth(), productImage.getHeight())
                .centerCrop()
                .into(productImage);
        productPrice.setText(mProduct.getPrice());
        productAmount.setText(mProduct.getAmount());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Click(R.id.editProduct)
    public void click(View v) {
        RegisterProductActivity_.intent(this)
                .productId(productId)
                .start();
    }
}
