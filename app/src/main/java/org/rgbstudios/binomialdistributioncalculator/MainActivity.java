package org.rgbstudios.binomialdistributioncalculator;

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
*/

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText equalOutput, lessOutput, greaterOutput, lessEqualOutput, greaterEqualOutput, pInput, nInput, xInput;
    private TextView errorText, infoText;
    private BarChart barChart;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        equalOutput = (EditText) findViewById(R.id.equalOutput);
        lessOutput = (EditText) findViewById(R.id.lessOutput);
        greaterOutput = (EditText) findViewById(R.id.greaterOutput);
        lessEqualOutput = (EditText) findViewById(R.id.lessEqualOutput);
        greaterEqualOutput = (EditText) findViewById(R.id.greaterEqualOutput);

        pInput = (EditText) findViewById(R.id.pInput);
        nInput = (EditText) findViewById(R.id.nInput);
        xInput = (EditText) findViewById(R.id.xInput);

        errorText = (TextView) findViewById(R.id.errorText);
        infoText = (TextView) findViewById(R.id.infoText);

        barChart = (BarChart) findViewById(R.id.barChart);
        barChart.setNoDataTextColor(getResources().getColor(R.color.white));
        barChart.setNoDataText(" ");

        pieChart = (PieChart) findViewById(R.id.pieChart);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleRadius(0);
        pieChart.setNoDataTextColor(getResources().getColor(R.color.white));
        pieChart.setNoDataText("");


        //copy buttons
        final ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        ImageButton equalCopy = (ImageButton) findViewById(R.id.equalCopy);
        equalCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText equalOutput = (EditText) findViewById(R.id.equalOutput);
                String equalString = equalOutput.getText().toString();
                clipboard.setPrimaryClip(ClipData.newPlainText("P(X=x)", equalString));
                Toast.makeText(getApplicationContext(), "Copied P(X=x) to clipboard", Toast.LENGTH_SHORT).show();

            }
        });
        ImageButton lessCopy = (ImageButton) findViewById(R.id.lessCopy);
        lessCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText lessOutput = (EditText) findViewById(R.id.lessOutput);
                String lessString = lessOutput.getText().toString();
                clipboard.setPrimaryClip(ClipData.newPlainText("P(X<x)", lessString));
                Toast.makeText(getApplicationContext(), "Copied P(X<x) to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton greaterCopy = (ImageButton) findViewById(R.id.greaterCopy);
        greaterCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText greaterOutput = (EditText) findViewById(R.id.greaterOutput);
                String greaterString = greaterOutput.getText().toString();
                clipboard.setPrimaryClip(ClipData.newPlainText("P(X>x)", greaterString));
                Toast.makeText(getApplicationContext(), "Copied P(X>x) to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton lessEqualCopy = (ImageButton) findViewById(R.id.lessEqualCopy);
        lessEqualCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText lessEqualOutput = (EditText) findViewById(R.id.lessEqualOutput);
                String lessEqualString = lessEqualOutput.getText().toString();
                clipboard.setPrimaryClip(ClipData.newPlainText("P(X<=x)", lessEqualString));
                Toast.makeText(getApplicationContext(), "Copied P(X<=x) to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton greaterEqualCopy = (ImageButton) findViewById(R.id.greaterEqualCopy);
        greaterEqualCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText greaterEqualOutput = (EditText) findViewById(R.id.greaterEqualOutput);
                String greaterEqualString = greaterEqualOutput.getText().toString();
                clipboard.setPrimaryClip(ClipData.newPlainText("P(X>=x)", greaterEqualString));
                Toast.makeText(getApplicationContext(), "Copied P(X>=x) to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        //calc
        Button calcButton = (Button) findViewById(R.id.calcButton);

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double p;
                int n;
                int x;

                try {
                    p = Double.parseDouble(pInput.getText().toString().trim());
                    n = Integer.parseInt(nInput.getText().toString().trim());
                    x = Integer.parseInt(xInput.getText().toString().trim());
                } catch (Exception e) {
                    errorText.setText("Missing or invalid inputs");
                    return;
                }

                if (p < 0 || p > 1) {
                    errorText.setText("p must be between 0 and 1");
                    return;
                }
                if (x < 0 || n < 0) {
                    errorText.setText("x and n must be greater than 0");
                    return;
                }
                if (x > 100 || n > 100) { //TODO: make back to 999?
                    errorText.setText("x and n must be 100 or less");
                    return;
                }
                if (x > n) {
                    errorText.setText("x cannot be greater than n");
                    return;
                }
                errorText.setText("");

//                findViewById(R.id.mainLayout).invalidate();
                //showProgressDialog();


                BigDecimal equalResult = Util.equal(p, n, x);
                BigDecimal lessResult = Util.less(p, n, x);
                BigDecimal greaterResult = Util.greater(p, n, x);

                BigDecimal lessEqualResult = equalResult.add(lessResult);
                BigDecimal greaterEqualResult = equalResult.add(greaterResult);

//                updateProgressDialog(50);

//                long nchoosek = Util.nchoosek(n, x);
                BigInteger nchoosek = Util.nchoosek(n, x);

                double mean = Util.round(Util.mean(p, n, x), 5);
                double variance = Util.round(Util.variance(p, n, x), 5);
                double stddev = Util.round(Util.stddev(p, n, x), 5);

                equalOutput.setText(Util.getPrettyNum(equalResult));
                lessOutput.setText(Util.getPrettyNum(lessResult));
                greaterOutput.setText(Util.getPrettyNum(greaterResult));
                lessEqualOutput.setText(Util.getPrettyNum(lessEqualResult));
                greaterEqualOutput.setText(Util.getPrettyNum(greaterEqualResult));

                infoText.setText("\nn Choose x = " + Util.getPrettyNum(nchoosek) + "\nMean = " + Util.getPrettyNum(mean) + "\nVariance = " + Util.getPrettyNum(variance) + "\nStandard Deviation = " + Util.getPrettyNum(stddev));


                //barchart
                ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
                int[] barColors = new int[n + 1];

                for (int i = 0; i < n + 1; i++) {
                    barEntries.add(new BarEntry(i, (float) (Util.equal(p, n, i).doubleValue() * 100)));
                    barColors[i] = getResources().getColor(i < x ? R.color.red : i == x ? R.color.blue : R.color.green);
                }

                BarDataSet barDataSet = new BarDataSet(barEntries, "");
                barDataSet.setColors((barColors));
                BarData barData = new BarData(barDataSet);
                barChart.getDescription().setEnabled(false);
                barChart.getLegend().setEnabled(false);
                barChart.setData(barData);

                barChart.invalidate(); //refresh chart

                //piechart
                ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
                pieEntries.add(new PieEntry((int) (equalResult.doubleValue() * 100)));
                pieEntries.add(new PieEntry((int) (lessResult.doubleValue() * 100)));
                pieEntries.add(new PieEntry((int) (greaterResult.doubleValue() * 100)));

                PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                int[] pieColors = {getResources().getColor(R.color.blue), getResources().getColor(R.color.red), getResources().getColor(R.color.green)};
                pieDataSet.setColors(pieColors);
                pieDataSet.setValueTextColor(getResources().getColor(R.color.white));

                PieData pieData = new PieData(pieDataSet);
                pieData.setValueTextSize(12f);
                pieChart.setData(pieData);
                pieChart.invalidate(); //refresh chart


//                dismissProgressDialog();
            } //end calc
        });

    }

    private ProgressDialog progressDialog;

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme_DialogStyle);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.setMessage("Calculating...");
        progressDialog.show();
    }

    public void updateProgressDialog(int val) {
        if (progressDialog != null) {
            progressDialog.setProgress(val);
        }
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // adds items to action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_info) {
            Intent i = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_learn) {
            Intent i = new Intent(MainActivity.this, LearnActivity.class);
            startActivity(i);
            return true;
        }

//        if (id == R.id.action_share) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}


