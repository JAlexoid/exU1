package eu.activelogic.android.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class AnimatedFrameLayout extends FrameLayout {
   
    
    public AnimatedFrameLayout(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	// TODO Auto-generated constructor stub
    }

    public AnimatedFrameLayout(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
    }

    public AnimatedFrameLayout(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
    }

    private int width = 0;
    
    public float getXFraction() {
	width = getWidth();
	return width != 0 ? getX() / width : 0; // TODO: guard divide-by-zero
    }

    public void setXFraction(float xFraction) {
	width = getWidth();
	setX((width > 0) ? (xFraction * width) : -9999);
    }
}
