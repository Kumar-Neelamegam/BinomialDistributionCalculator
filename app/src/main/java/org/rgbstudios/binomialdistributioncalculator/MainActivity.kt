package org.rgbstudios.binomialdistributioncalculator

import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import java.math.BigDecimal
import java.util.*

/* TODO
BUGS

FIX

link to binomial calc website

AFTER RELEASE
link to rate and share app

MAYBE
night mode setting (and storage to remember night mode)
optimize algorithm
show  >= and <=
----------------
DONE
values xml, icon, landscape, rounding, mean,std,var,nchoosek, added learn, link to rgb, version #, (c), contact/send feedback, copy buttons work, made buttons not ugly, added rgb logo, updated info and learn, optimized algorithm, fixed no data text, fixed crash onclicking button twice, fixed for large outputs
*/   class MainActivity : AppCompatActivity() {
    private var equalOutput: EditText? = null
    private var lessOutput: EditText? = null
    private var greaterOutput: EditText? = null
    private var lessEqualOutput: EditText? = null
    private var greaterEqualOutput: EditText? = null
    private var pInput: EditText? = null
    private var nInput: EditText? = null
    private var xInput: EditText? = null
    private var errorText: TextView? = null
    private var infoText: TextView? = null
    private var barChart: BarChart? = null
    private var pieChart: PieChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        equalOutput = findViewById<View>(R.id.equalOutput) as EditText
        lessOutput = findViewById<View>(R.id.lessOutput) as EditText
        greaterOutput = findViewById<View>(R.id.greaterOutput) as EditText
        lessEqualOutput = findViewById<View>(R.id.lessEqualOutput) as EditText
        greaterEqualOutput = findViewById<View>(R.id.greaterEqualOutput) as EditText
        pInput = findViewById<View>(R.id.pInput) as EditText
        nInput = findViewById<View>(R.id.nInput) as EditText
        xInput = findViewById<View>(R.id.xInput) as EditText
        errorText = findViewById<View>(R.id.errorText) as TextView
        infoText = findViewById<View>(R.id.infoText) as TextView
        barChart = findViewById<View>(R.id.barChart) as BarChart
        barChart!!.setNoDataTextColor(resources.getColor(R.color.white))
        barChart!!.setNoDataText(" ")
        pieChart = findViewById<View>(R.id.pieChart) as PieChart
        pieChart!!.description.isEnabled = false
        pieChart!!.legend.isEnabled = false
        pieChart!!.holeRadius = 0f
        pieChart!!.transparentCircleRadius = 0f
        pieChart!!.setNoDataTextColor(resources.getColor(R.color.white))
        pieChart!!.setNoDataText("")
        //copy buttons
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val equalCopy = findViewById<View>(R.id.equalCopy) as ImageButton
        equalCopy.setOnClickListener {
            val equalOutput = findViewById<View>(R.id.equalOutput) as EditText
            val equalString = equalOutput.text.toString()
            clipboard.primaryClip = ClipData.newPlainText("P(X=x)", equalString)
            Toast.makeText(applicationContext, "Copied P(X=x) to clipboard", Toast.LENGTH_SHORT).show()
        }
        val lessCopy = findViewById<View>(R.id.lessCopy) as ImageButton
        lessCopy.setOnClickListener {
            val lessOutput = findViewById<View>(R.id.lessOutput) as EditText
            val lessString = lessOutput.text.toString()
            clipboard.primaryClip = ClipData.newPlainText("P(X<x)", lessString)
            Toast.makeText(applicationContext, "Copied P(X<x) to clipboard", Toast.LENGTH_SHORT).show()
        }
        val greaterCopy = findViewById<View>(R.id.greaterCopy) as ImageButton
        greaterCopy.setOnClickListener {
            val greaterOutput = findViewById<View>(R.id.greaterOutput) as EditText
            val greaterString = greaterOutput.text.toString()
            clipboard.primaryClip = ClipData.newPlainText("P(X>x)", greaterString)
            Toast.makeText(applicationContext, "Copied P(X>x) to clipboard", Toast.LENGTH_SHORT).show()
        }
        val lessEqualCopy = findViewById<View>(R.id.lessEqualCopy) as ImageButton
        lessEqualCopy.setOnClickListener {
            val lessEqualOutput = findViewById<View>(R.id.lessEqualOutput) as EditText
            val lessEqualString = lessEqualOutput.text.toString()
            clipboard.primaryClip = ClipData.newPlainText("P(X<=x)", lessEqualString)
            Toast.makeText(applicationContext, "Copied P(X<=x) to clipboard", Toast.LENGTH_SHORT).show()
        }
        val greaterEqualCopy = findViewById<View>(R.id.greaterEqualCopy) as ImageButton
        greaterEqualCopy.setOnClickListener {
            val greaterEqualOutput = findViewById<View>(R.id.greaterEqualOutput) as EditText
            val greaterEqualString = greaterEqualOutput.text.toString()
            clipboard.primaryClip = ClipData.newPlainText("P(X>=x)", greaterEqualString)
            Toast.makeText(applicationContext, "Copied P(X>=x) to clipboard", Toast.LENGTH_SHORT).show()
        }
        //calc
        val calcButton = findViewById<View>(R.id.calcButton) as Button
        calcButton.setOnClickListener(View.OnClickListener {
            val p: Double
            val n: Int
            val x: Int
            try {
                p = pInput!!.text.toString().trim { it <= ' ' }.toDouble()
                n = nInput!!.text.toString().trim { it <= ' ' }.toInt()
                x = xInput!!.text.toString().trim { it <= ' ' }.toInt()
            } catch (e: Exception) {
                errorText!!.text = "Missing or invalid inputs"
                return@OnClickListener
            }
            if (p < 0 || p > 1) {
                errorText!!.text = "p must be between 0 and 1"
                return@OnClickListener
            }
            if (x < 0 || n < 0) {
                errorText!!.text = "x and n must be greater than 0"
                return@OnClickListener
            }
            if (x > 1000 || n > 1000) { //TODO: make back to 999?
                errorText!!.text = "x and n must be 1000 or less"
                return@OnClickListener
            }
            if (x > n) {
                errorText!!.text = "x cannot be greater than n"
                return@OnClickListener
            }
            errorText!!.text = ""
            //                findViewById(R.id.mainLayout).invalidate();

//            showProgressDialog()

            val equalResult = Util.equal(p, n, x)
            val lessResult = Util.less(p, n, x)
            val greaterResult = Util.greater(p, n, x)
            val lessEqualResult = equalResult.add(lessResult)
            val greaterEqualResult = equalResult.add(greaterResult)

//            updateProgressDialog(50)

//                long nchoosek = Util.nchoosek(n, x);
            val nchoosek = Util.nchoosek(n, x)
            val mean = Util.round(Util.mean(p, n, x), 5)
            val variance = Util.round(Util.variance(p, n, x), 5)
            val stddev = Util.round(Util.stddev(p, n, x), 5)
            equalOutput!!.setText(Util.getPrettyNum(equalResult))
            lessOutput!!.setText(Util.getPrettyNum(lessResult))
            greaterOutput!!.setText(Util.getPrettyNum(greaterResult))
            lessEqualOutput!!.setText(Util.getPrettyNum(lessEqualResult))
            greaterEqualOutput!!.setText(Util.getPrettyNum(greaterEqualResult))
            infoText!!.text = "\nn Choose x = " + Util.getPrettyNum(nchoosek) + "\nMean = " + Util.getPrettyNum(mean) + "\nVariance = " + Util.getPrettyNum(variance) + "\nStandard Deviation = " + Util.getPrettyNum(stddev)
            //barchart
            val barEntries = ArrayList<BarEntry>()
            val barColors = IntArray(n + 1)
            for (i in 0 until n+1) {
                barEntries.add(BarEntry(i.toFloat(), (Util.equal(p, n, i) * (100).toBigDecimal() ).toFloat()  ))
                barColors[i] = resources.getColor(if (i < x) R.color.red else if (i == x) R.color.blue else R.color.green)
            }
            val barDataSet = BarDataSet(barEntries, "")
            barDataSet.setColors(*barColors)
            val barData = BarData(barDataSet)
            barChart!!.description.isEnabled = false
            barChart!!.legend.isEnabled = false
            barChart!!.data = barData
            barChart!!.invalidate() //refresh chart
            //piechart
            val pieEntries = ArrayList<PieEntry>()
            pieEntries.add(PieEntry(((equalResult * (100).toBigDecimal() ).toInt() ).toFloat()))
            pieEntries.add(PieEntry(((lessResult * (100).toBigDecimal() ).toInt() ).toFloat()))
            pieEntries.add(PieEntry(((greaterResult * (100).toBigDecimal() ).toInt() ).toFloat()))
            val pieDataSet = PieDataSet(pieEntries, "")
            val pieColors = intArrayOf(resources.getColor(R.color.blue), resources.getColor(R.color.red), resources.getColor(R.color.green))
            pieDataSet.setColors(*pieColors)
            pieDataSet.valueTextColor = resources.getColor(R.color.white)
            val pieData = PieData(pieDataSet)
            pieData.setValueTextSize(12f)
            pieChart!!.data = pieData
            pieChart!!.invalidate() //refresh chart

//            dismissProgressDialog()
        } //end calc
        )
    }

    private var progressDialog: ProgressDialog? = null
    fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this@MainActivity, R.style.AppTheme_DialogStyle)
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setCancelable(false)
        }
        progressDialog!!.setMessage("Calculating...")
        progressDialog!!.show()
    }

    fun updateProgressDialog(`val`: Int) {
        if (progressDialog != null) {
            progressDialog!!.progress = `val`
        }
    }

    fun dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // adds items to action bar
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_info) {
            val i = Intent(this@MainActivity, InfoActivity::class.java)
            startActivity(i)
            return true
        }
        if (id == R.id.action_learn) {
            val i = Intent(this@MainActivity, LearnActivity::class.java)
            startActivity(i)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}