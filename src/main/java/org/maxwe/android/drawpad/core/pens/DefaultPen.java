package org.maxwe.android.drawpad.core.pens;

import org.maxwe.android.drawpad.core.APen;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class DefaultPen extends APen{

    @Override
    public String getName() {
        return ValuesOfPenName.DEFAULT.name();
    }

    public DefaultPen(){}

}
