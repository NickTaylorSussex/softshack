package org.softshack.trackme.adapters;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import org.softshack.trackme.R;
import org.softshack.trackme.interfaces.IDialog;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public class YearPickerDialogAdapter implements IDialog {
    private final DefaultEvent<EventArgs> onYearChanged = new DefaultEvent<EventArgs>();
    private Dialog dialog;
    private String year;

    public YearPickerDialogAdapter(final Dialog dialog) {
        this.dialog = dialog;
        this.getDialog().setTitle("NumberPicker");
        this.getDialog().setContentView(R.layout.dialog_yearpicker);
        Button b1 = (Button) this.getDialog().findViewById(R.id.button1);
        Button b2 = (Button) this.getDialog().findViewById(R.id.button2);
        final NumberPicker numberPicker = (NumberPicker) this.getDialog().findViewById(R.id.numberPicker1);
        numberPicker.setMaxValue(2016);
        numberPicker.setMinValue(1995);
        numberPicker.setWrapSelectorWheel(true);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                setYear(String.valueOf(numberPicker.getValue()));
                getDialog().dismiss();
                getOnYearChanged().fire(this, EventArgs.Empty);
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private Dialog getDialog() {
        return dialog;
    }

    @Override
    public DefaultEvent<EventArgs> getOnYearChanged() {
        return onYearChanged;
    }

    @Override
    public String getYear() {
        return year;
    }

    private void setYear(String year) {
        this.year = year;
    }

    @Override
    public void show() {
        this.getDialog().show();
    }
}
