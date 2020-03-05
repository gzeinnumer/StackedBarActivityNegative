package com.gzeinnumer.stackedbaractivitynegative;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private HorizontalBarChart newChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("StackedBarActivityNegative");
        newChart = findViewById(R.id.chart1);
        newChart.setOnChartValueSelectedListener(this);
        newChart.setDrawGridBackground(false);
        newChart.getDescription().setEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        newChart.setPinchZoom(false);

        newChart.setDrawBarShadow(false);
        newChart.setDrawValueAboveBar(true);
        newChart.setHighlightFullBarEnabled(false);

        newChart.getAxisLeft().setEnabled(false);
        newChart.getAxisRight().setAxisMaximum(25f);
        newChart.getAxisRight().setAxisMinimum(-25f);
        newChart.getAxisRight().setDrawGridLines(false);
        newChart.getAxisRight().setDrawZeroLine(true);
        newChart.getAxisRight().setLabelCount(7, false);
        newChart.getAxisRight().setValueFormatter(new CustomFormatter());
        newChart.getAxisRight().setTextSize(9f);

        XAxis xAxisNew = newChart.getXAxis();
        xAxisNew.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxisNew.setDrawGridLines(false);
        xAxisNew.setDrawAxisLine(false);
        xAxisNew.setTextSize(9f);
        xAxisNew.setAxisMinimum(0f);
        xAxisNew.setAxisMaximum(110f);
        xAxisNew.setCenterAxisLabels(true);
        xAxisNew.setLabelCount(12);
        xAxisNew.setGranularity(10f);
        xAxisNew.setValueFormatter(new ValueFormatter() {

            private final DecimalFormat format = new DecimalFormat("###");

            @Override
            public String getFormattedValue(float value) {
                return format.format(value) + "-" + format.format(value + 10);
            }
        });

        Legend lNew = newChart.getLegend();
        lNew.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lNew.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        lNew.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lNew.setDrawInside(false);
        lNew.setFormSize(8f);
        lNew.setFormToTextSpace(4f);
        lNew.setXEntrySpace(6f);

        // IMPORTANT: When using negative values in stacked bars, always make sure the negative values are in the array first
        ArrayList<BarEntry> valuesNew = new ArrayList<>();
        valuesNew.add(new BarEntry(5, new float[]{ -10, 10 }));
        valuesNew.add(new BarEntry(15, new float[]{ -12, 13 }));
        valuesNew.add(new BarEntry(25, new float[]{ -15, 15 }));
        valuesNew.add(new BarEntry(35, new float[]{ -17, 17 }));
        valuesNew.add(new BarEntry(45, new float[]{ -19, 20 }));
        valuesNew.add(new BarEntry(45, new float[]{ -19, 20 }));
        valuesNew.add(new BarEntry(55, new float[]{ -19, 19 }));
        valuesNew.add(new BarEntry(65, new float[]{ -16, 16 }));
        valuesNew.add(new BarEntry(75, new float[]{ -13, 14 }));
        valuesNew.add(new BarEntry(85, new float[]{ -10, 11 }));
        valuesNew.add(new BarEntry(95, new float[]{ -5, 6 }));
        valuesNew.add(new BarEntry(105, new float[]{ -1, 2 }));

        BarDataSet setNew = new BarDataSet(valuesNew, "Age Distribution");
        setNew.setDrawIcons(false);
        setNew.setValueFormatter(new CustomFormatter());
        setNew.setValueTextSize(7f);
        setNew.setAxisDependency(YAxis.AxisDependency.RIGHT);
        setNew.setColors(Color.rgb(67,67,72), Color.rgb(124,181,236));
        setNew.setStackLabels(new String[]{
                "Men", "Women"
        });

        BarData dataNew = new BarData(setNew);
        dataNew.setBarWidth(8.5f);
        newChart.setData(dataNew);
        newChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        BarEntry entry = (BarEntry) e;
        Log.i("VAL SELECTED",
                "Value: " + Math.abs(entry.getYVals()[h.getStackIndex()]));
    }

    @Override
    public void onNothingSelected() {
        Log.i("NOTING SELECTED", "");
    }

    private class CustomFormatter extends ValueFormatter {

        private final DecimalFormat mFormat;

        CustomFormatter() {
            mFormat = new DecimalFormat("###");
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(Math.abs(value)) + "m";
        }
    }
}
