package org.rgbstudios.binomialdistributioncalculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import android.widget.TextView

class LearnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)
        title = "Learn"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val learnText = findViewById<View>(R.id.learnText) as TextView
        learnText.text = Html.fromHtml("<b>FORMULAS</b>" +
                "<br>A binomial experiment with <b>n</b> trials, probability of success <b>p</b>, and <b>x</b> successes, has binomial probability:" +
                "<br>P(X = x) = <sub>n</sub>C<sub>x</sub> * p<sup>x</sup> * (1 - p)<sup>n - x</sup>" +
                "<br><br><sub>n</sub>C<sub>x</sub>, or \"n choose x\" is the number of combinations of <b>x</b> elements from <b>n</b> elements:" +
                "<br><sub>n</sub>C<sub>x</sub> = n! / [ x! (n - x)! ]" +
                "<br><br>Mean (&mu;) = n * p" +
                "<br>Variance (&sigma;) = n * p * (1-p)" +
                "<br>Standard Deviation (&sigma;<sup>2</sup>) = &radic; [ n * p * (1-p) ]" +
                "<br><br><b>BINOMIAL EXPERIMENT REQUIREMENTS</b>" +
                "<br><br>Binomial Experiments must:" +
                "<br> - Involve repeated trials, with only 2 outcomes (success or failure)" +
                "<br> - The probability of a particular outcome is constant and trials are independent" +
                "<br><br><b>NOTE</b>" +
                "<br><br><i>You might also see q which is 1-p, as well as p instead written as &pi;</i>" +
                "<br><br><b>p</b> must be between 0 and 1, <b>n</b> and <b>x</b> must be positive integers where <b>n</b> &ge; <b>x</b>")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}