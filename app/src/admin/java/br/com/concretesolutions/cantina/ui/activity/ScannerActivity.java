package br.com.concretesolutions.cantina.ui.activity;

import android.os.Bundle;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.activity.base.BaseActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.EAN_13);
        mScannerView.setFormats(formats);
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        final String barCode = rawResult.getText();
        ParseQuery<Product> parseQuery = ParseQuery.getQuery(Product.class);
        parseQuery.whereEqualTo(Product.BARCODE, barCode);
        parseQuery.getFirstInBackground(new GetCallback<Product>() {
            @Override
            public void done(Product product, ParseException e) {
                if (e == null && product != null) {
                    ProductInfoActivity_.intent(ScannerActivity.this)
                            .productId(product.getObjectId()).start();
                } else {
                    RegisterProductActivity_.intent(ScannerActivity.this)
                            .productBarCode(barCode).start();
                }
            }
        });
    }
}
