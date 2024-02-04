package com.soham.qrcodescan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvcontent,tvformat;
    Button btn,btnurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvcontent = findViewById(R.id.textViewContent);
        tvformat = findViewById(R.id.textViewFormat);
        btn = findViewById(R.id.button);
        btnurl =  findViewById(R.id.buttonURL);

        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan QR code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult != null)
        {
            if(intentResult.getContents()==null) {
                tvcontent.setText("NA");
                Toast.makeText(this, "No QRCode", Toast.LENGTH_SHORT).show();
            }
            else if(!Patterns.WEB_URL.matcher(intentResult.getContents()).matches())
            {
                Toast.makeText(MainActivity.this, "Invalid URL", Toast.LENGTH_SHORT).show();
                tvcontent.setText("NA");
            }
            else
            {
                {
                    btnurl.setVisibility(View.VISIBLE);
                    tvcontent.setText(intentResult.getContents());
                    tvformat.setText("Format: "+intentResult.getFormatName());
                    String url  = intentResult.getContents();

                    btnurl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent i =new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                        }
                    });


                }
                super.onActivityResult(requestCode,resultCode,data);
            }

        }


    }
}