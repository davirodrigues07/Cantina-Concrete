package br.com.concretesolutions.cantina.ui.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;
import br.com.concretesolutions.cantina.utils.BitmapUtils;

@EActivity(R.layout.activity_register_product)
public class RegisterProductActivity extends BaseActivity {

    private final static int TAKE_PICTURE_REQUEST_CODE = 1;
    private Uri fileUri;
    private Bitmap mProductBitmap;

    @Extra
    String productBarCode;
    @Extra
    String productId;
    Product product;

    @ViewById
    ImageView productPicture;
    @ViewById
    TextView name;
    @ViewById
    TextView price;
    @ViewById
    TextView amount;
    @ViewById
    Button save;

    @AfterViews
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

    /**
     * Start android camera to capture order photo
     */
    @Click(R.id.takeProductPicture)
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

    @Click
    void save() {
        // name, amount and price is empty
        if (name.getText().length() < 1 && amount.getText().length() < 1
                && price.getText().length() < 1 ) {
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
