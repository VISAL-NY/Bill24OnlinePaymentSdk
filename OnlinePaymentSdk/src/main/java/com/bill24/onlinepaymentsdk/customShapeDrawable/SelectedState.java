package com.bill24.onlinepaymentsdk.customShapeDrawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class SelectedState {
    public static StateListDrawable selectedSate(Drawable normal,Drawable selected) {
        StateListDrawable selector=new StateListDrawable();
        selector.addState(new int[] {android.R.attr.state_pressed},selected);
        selector.addState(new int[]{},normal);

        return  selector;
    }
}
