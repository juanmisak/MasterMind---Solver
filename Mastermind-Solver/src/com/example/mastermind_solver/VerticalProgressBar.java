package com.example.mastermind_solver;

import java.math.BigDecimal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class VerticalProgressBar extends ImageView {

    private static final BigDecimal MAX = BigDecimal.valueOf(10000);

    public VerticalProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setImageResource(R.drawable.progress_bar);
    }

    public void setCurrentValue(Percent percent){
        int cliDrawableImageLevel = percent.asBigDecimal().multiply(MAX).intValue();
        setImageLevel(cliDrawableImageLevel);
    }
}
