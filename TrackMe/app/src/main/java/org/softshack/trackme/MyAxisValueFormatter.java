package org.softshack.trackme;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Formatter for the X-Axis of the house price graph.
 */

public class MyAxisValueFormatter implements IAxisValueFormatter {

    private DecimalFormat mFormat;

    public MyAxisValueFormatter() {

        // format values to 1 decimal digit
        mFormat = new DecimalFormat("0000");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mFormat.format(value);
    }

    /** this is only needed if numbers are returned, else return 0 */

    public int getDecimalDigits() { return 1; }
}
