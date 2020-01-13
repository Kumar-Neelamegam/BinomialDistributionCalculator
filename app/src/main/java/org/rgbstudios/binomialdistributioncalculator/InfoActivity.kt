package org.rgbstudios.binomialdistributioncalculator

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        title = "Info"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val versionName = BuildConfig.VERSION_NAME
        val aboutText = findViewById<View>(R.id.aboutText) as TextView
        aboutText.text = "Numbers over 250 may take several seconds to calculate; please be patient\n\nCreated by Justin Golden (with help from Peter Nguyen)\n\nVersion $versionName\n\n\u00A9 RGB Studios 2018"

        val contactButton = findViewById<View>(R.id.contactButton) as Button
        contactButton.setOnClickListener {
            val versionCode = BuildConfig.VERSION_CODE
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "contact@rgbstudios.org", null)) //to email
            intent.putExtra(Intent.EXTRA_SUBJECT, "Binomial Calc App") //subject
            intent.putExtra(Intent.EXTRA_TEXT, "VersionCode: $versionCode\r\n\r\n") //message
            startActivity(Intent.createChooser(intent, "Choose an Email client :")) //choose app text
        }

        val rateButton = findViewById<View>(R.id.rateButton) as Button
        rateButton.setOnClickListener {
            try {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=" + this.packageName)
                        )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + this.packageName)
                        )
                )
            }
        }

        val shareButton = findViewById<View>(R.id.shareButton) as Button
        shareButton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out " + getString(R.string.app_name) + ": https://play.google.com/store/apps/details?id=org.rgbstudios.binomialdistributioncalculator"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}