package br.com.concretesolutions.cantina.ui.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;
import br.com.concretesolutions.cantina.utils.BitmapUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterProductActivity extends BaseActivity {

    private final static int TAKE_PICTURE_REQUEST_CODE = 1;
    public final static String PRODUCT_BAR_CODE_EXTRA = "productBarCode";
    public final static String PRODUCT_ID_EXTRA = "productId";

    private Uri fileUri;
    private Bitmap mProductBitmap;

    String productBarCode;
    String productId;
    Product product;

    @Bind(R.id.productPicture)
    ImageView productPicture;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.amount)
    TextView amount;
    @Bind(R.id.save)
    Button save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);
        ButterKnife.bind(this);
        productBarCode = getIntent().getExtras().getString(PRODUCT_BAR_CODE_EXTRA) != null ?
                getIntent().getExtras().getString(PRODUCT_BAR_CODE_EXTRA) : null;
        productId = getIntent().getExtras().getString(PRODUCT_ID_EXTRA) != null ?
                getIntent().getExtras().getString(PRODUCT_ID_EXTRA) : null;
        afterViews();
    }

    void afterViews() {
        if (productId != null) {
            ParseQuery<Product> parseQuery = ParseQuery.getQuery(Product.class);
            parseQuery.getInBackground(this.productId, new GetCallback<Product>() {
                @Override
                public void done(Product parseProduct, ParseException e) {
                    if (e == null && parseProduct != null) {
                        product = parseProduct;
                        loadProductInfo();
                    }
                }
            });
        }
    }

    void loadProductInfo() {
        this.name.setText(this.product.getName());
        this.price.setText(this.product.getPrice());
        this.amount.setText(this.product.getAmount());
        this.productBarCode = this.product.getBarCode();
        Picasso.with(this).load(this.product.getImage().getUrl()).into(this.productPicture);
    }

    public static Intent intentNew(Context context, String productBarCode) {
        return new Intent(context, RegisterProductActivity.class).putExtra(PRODUCT_BAR_CODE_EXTRA, productBarCode);
    }

    public static Intent intentEdit(Context context, String objectId) {
        return new Intent(context, RegisterProductActivity.class).putExtra(PRODUCT_ID_EXTRA, objectId);
    }

    /**
     * Start android camera to capture order photo
     */
    @OnClick(R.id.takeProductPicture)
    void startCameraIntent() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "IMG_" + timeStamp + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // start the image capture Intent
        startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE);
    }

    /**
     * Activity result to receive results from collected package and signature.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE_REQUEST_CODE:
                try {
                    if (resultCode == Activity.RESULT_OK) {
                        String filePath;
                        if (fileUri != null) {
                            Cursor cursor = getContentResolver().query(fileUri, new String[]
                                            {MediaStore.Images.ImageColumns.DATA},
                                    null, null, null);
                            cursor.moveToFirst();
                            filePath = cursor.getString(0);
                            cursor.close();
                        } else {
                            filePath = fileUri.getPath();
                        }
                        this.mProductBitmap = BitmapUtils.decodeBitmapFromPath(filePath);
                        this.productPicture.setImageBitmap(this.mProductBitmap);
                    }
                } catch (Exception e) {
                    Log.e("Camera", e.getMessage(), e);
                }
        }
    }

    @OnClick(R.id.save)
    void save() {
        // name, amount and price is empty
        if (name.getText().length() < 1 && amount.getText().length() < 1
                && price.getText().length() < 1) {
            Toast.makeText(this, "Não pode haver campos vazios :)", Toast.LENGTH_LONG).show();
            return;
        }

        // don't have drawable in productPicture
        if (productPicture.getDrawable() == null) {
            Toast.makeText(this, "Não esqueça a foto ;)", Toast.LENGTH_LONG).show();
            return;
        }

        // fidback save
        save.setText("Salvando...");
        save.setEnabled(false);

        final Product product = new Product();
        // set object id for update object
        if (productId != null) {
            product.setObjectId(productId);
        }
        // set product attributes
        product.setName(this.name.getText().toString());
        product.setPrice(Double.valueOf(this.price.getText().toString()));
        product.setAmount(Integer.valueOf(this.amount.getText().toString()));
        product.setBarCode(this.productBarCode);

        if (this.mProductBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            this.mProductBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byte[] byteArray = stream.toByteArray();
            ParseFile productImage = new ParseFile(this.productBarCode + ".jpg", byteArray);
            product.setImage(productImage);
        }

        product.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(RegisterProductActivity.this, "Salvo com sucesso",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterProductActivity.this, "Erro ao salvar o produto.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
