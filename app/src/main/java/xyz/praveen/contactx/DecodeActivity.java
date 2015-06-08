package xyz.praveen.contactx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import eu.livotov.zxscan.ScannerView;


public class DecodeActivity extends AppCompatActivity implements ScannerView.ScannerViewEventListener {

    // Widgets
    ScannerView mScanner;
    TextView mDecodedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode);

        // Init widgets
        mDecodedView = (TextView) findViewById(R.id.contact_decoded);
        mScanner = (ScannerView) findViewById(R.id.contact_scanner);
        mScanner.setScannerViewEventListener(this);
        mScanner.startScanner();
    }

    @Override
    public void onScannerReady() {

    }

    @Override
    public void onScannerFailure(int i) {

    }

    @Override
    public boolean onCodeScanned(String s) {
        mScanner.stopScanner();
        mScanner.setVisibility(View.GONE);
        mDecodedView.setText(JsonFactory.JsonToContact(s));
        return true;
    }

    @Override
    public void onBackPressed() {
        mScanner.stopScanner();
        finish();
    }
}
