package br.com.concretesolutions.cantina;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import br.com.concretesolutions.cantina.data.type.parse.Product;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class MainActivity extends ActionBarActivity implements ZXingScannerView.ResultHandler {
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
                    RegisterProductActivity_.intent(MainActivity.this)
                            .productId(product.getObjectId()).start();
                } else {
                    RegisterProductActivity_.intent(MainActivity.this)
                            .productBarCode(barCode).start();
                }
            }
        });
    }
}
