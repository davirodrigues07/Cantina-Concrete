package br.com.concretesolutions.cantina.ui.activity;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;
import br.com.concretesolutions.cantina.ui.adapter.ListProductAdapter;

@EActivity(R.layout.activity_list_product)
public class ListProductActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    @ViewById
    ListView productList;
    @ViewById
    FloatingActionButton addProduct;
    List<Product> products;
    @ViewById
    FrameLayout containerListProduct;
    @ViewById
    ProgressBar progressOfListProduct;

    Product productSelected;

    @AfterViews
    public void afterViews() {
        containerListProduct.setVisibility(View.GONE);
        progressOfListProduct.setVisibility(View.VISIBLE);

        ParseQuery<Product> parseQuery = ParseQuery.getQuery(Product.class);
        parseQuery.findInBackground(new FindCallback<Product>() {
            @Override
            public void done(List<Product> list, ParseException e) {
                products = list;
                generateListView();

                progressOfListProduct.setVisibility(View.GONE);
                containerListProduct.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        afterViews();
    }

    public void generateListView() {
        ArrayAdapter<Product> adapter = new ListProductAdapter(this, products);

        productList.setAdapter(adapter);
        productList.setOnItemClickListener(this);
        productList.setOnItemLongClickListener(this);

        registerForContextMenu(productList);
    }

    @Click
    public void addProduct(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product product = ((Product) parent.getItemAtPosition(position));
        ProductInfoActivity_.intent(this)
                .productId(product.getObjectId())
                .start();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        productSelected = (Product) parent.getItemAtPosition(position);
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem delete = menu.add("Deletar " + productSelected.getName());
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                deleteItem();
                return false;
            }
        });
    }

    public void deleteItem() {
        Toast.makeText(this, "Deletando " + productSelected.getName(), Toast.LENGTH_SHORT).show();
        productSelected.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                afterViews();
            }
        });
    }
}
