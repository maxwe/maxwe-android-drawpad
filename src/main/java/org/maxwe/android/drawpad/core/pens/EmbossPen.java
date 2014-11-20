package org.maxwe.android.drawpad.core.pens;

import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import org.maxwe.android.drawpad.core.APen;

/**
 * Created by dingpengwei on 11/3/14.
 */
public class EmbossPen extends APen{

    private MaskFilter maskFilter;

    public EmbossPen() {
        this.maskFilter = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6, 3.5f);
        this.setMaskFilter(maskFilter);
    }

    @Override
    public String getName() {
        return ValuesOfPenName.EMBOSS.name();
    }
}
