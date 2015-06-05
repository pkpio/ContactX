package xyz.praveen.contactx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import eu.livotov.zxscan.ScannerView;


public class MainActivity extends AppCompatActivity implements ScannerView.ScannerViewEventListener {
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView test = (ImageView) findViewById(R.id.helloIm);
        test.setImageBitmap(encodeToQrCode("Hello", 150, 150));
        ScannerView scanner = (ScannerView) findViewById(R.id.scanner);
        scanner.setScannerViewEventListener(this);
        scanner.startScanner();
    }

    public static Bitmap encodeToQrCode(String text, int width, int height) {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = null;
        try {
            matrix = writer.encode(text, BarcodeFormat.QR_CODE, 150, 150);
        } catch (WriterException ex) {
            ex.printStackTrace();
        }
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    @Override
    public void onScannerReady() {

    }

    @Override
    public void onScannerFailure(int i) {

    }

    @Override
    public boolean onCodeScanned(String s) {
        //scanner.stopScanner();
        Toast.makeText(this, "Data scanned: " + s, Toast.LENGTH_SHORT).show();
        return true;
    }
}
