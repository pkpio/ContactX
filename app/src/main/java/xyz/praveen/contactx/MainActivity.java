package xyz.praveen.contactx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Widgets setup
        findViewById(R.id.contact_decode).setOnClickListener(this);
        findViewById(R.id.contact_encode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact_decode:
                Intent i = new Intent(this, DecodeActivity.class);
                startActivity(i);
                break;

            case R.id.contact_encode:
                Intent j = new Intent(this, EncodeActivity.class);
                j.putExtra("pickContact", true);
                startActivity(j);
                break;
        }
    }


}
