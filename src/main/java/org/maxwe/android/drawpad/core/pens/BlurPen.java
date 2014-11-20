package org.maxwe.android.drawpad.core.pens;

import android.graphics.BlurMaskFilter;
import android.graphics.MaskFilter;
import org.maxwe.android.drawpad.core.APen;

/**
 * Created by dingpengwei on 11/3/14.
 */
public class BlurPen extends APen {

    private MaskFilter maskFilter;

    public BlurPen() {
        this.maskFilter = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
        this.setMaskFilter(maskFilter);
    }

    @Override
    public String getName() {
        return ValuesOfPenName.BLUR.name();
    }
}
