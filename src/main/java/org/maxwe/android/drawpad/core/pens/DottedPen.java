package org.maxwe.android.drawpad.core.pens;

import android.graphics.DashPathEffect;
import android.graphics.PathEffect;
import org.maxwe.android.drawpad.core.APen;

/**
 * Created by dingpengwei on 11/3/14.
 */
public class DottedPen extends APen {

    private PathEffect effects;

    public DottedPen() {
        super();
        this.effects = new DashPathEffect(new float[]{4, 4}, 0);
        this.setPathEffect(effects);
    }

    @Override
    public String getName() {
        return ValuesOfPenName.DOTTED.name();
    }
}
