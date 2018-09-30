package org.rgbstudios.binomialdistributioncalculator;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        setTitle("Info");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        TextView aboutText = (TextView) findViewById(R.id.aboutText);
        aboutText.setText("Created by Justin Golden (with help from Peter Nguyen)\n\nVersion " + versionName + "\n\n\u00A9 RGB Studios 2018");

        Button contactButton = (Button) findViewById(R.id.contactButton);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int versionCode = BuildConfig.VERSION_CODE;

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "contact@rgbstudios.org", null)); //to email
                intent.putExtra(Intent.EXTRA_SUBJECT, "Binomial Calc App"); //subject
                intent.putExtra(Intent.EXTRA_TEXT, "VersionCode: " + versionCode + "\r\n\r\n"); //message
                startActivity(Intent.createChooser(intent, "Choose an Email client :")); //choose app text
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
