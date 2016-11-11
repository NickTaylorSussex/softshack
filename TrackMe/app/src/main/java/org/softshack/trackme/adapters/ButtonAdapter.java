package org.softshack.trackme.adapters;

import android.view.View;
import android.widget.Button;

import org.softshack.trackme.interfaces.IButton;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public class ButtonAdapter implements IButton {
    private final DefaultEvent<EventArgs> onClicked = new DefaultEvent<EventArgs>();
    private Button button;

    public ButtonAdapter(Button button){
        this.button = button;

        this.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getOnClicked().fire(this, EventArgs.Empty);
            }
        });
    }

    @Override
    public DefaultEvent<EventArgs> getOnClicked() {
        return onClicked;
    }

    @Override
    public void setText(String text) {
        this.button.setText(text);
    }

}
