package eu.activelogic.android.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckableLayout extends LinearLayout implements Checkable {
    private boolean mChecked;



    public CheckableLayout(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
    }

    public CheckableLayout(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public CheckableLayout(Context context) {
	super(context);
    }

    public void setChecked(boolean checked) {
	mChecked = checked;
	setBackgroundColor(checked ? Color.CYAN : Color.TRANSPARENT);
    }

    public boolean isChecked() {
	return mChecked;
    }

    public void toggle() {
	setChecked(!mChecked);
    }

}
