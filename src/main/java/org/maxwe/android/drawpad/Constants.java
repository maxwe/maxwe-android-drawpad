package org.maxwe.android.drawpad;

import android.graphics.Color;

/**
 * Created by dingpengwei on 10/5/14.
 */
public interface Constants {
    public static final String KEY_BACKGROUND_FLAG = "backgroundFlag";

    public static enum ValuesOfBackgroundFlag {
        DRAWABLE,// the corresponding value is drawable id
        COLOR, // the corresponding value is color
        FILE; // the corresponding value is file path
    }

    public static final String KEY_BACKGROUND = "background";
    public static final String KEY_GRAPHICS = "graphics";

    /**
     * graphic的属性
     */
    public static final String KEY_GRAPH = "graphic";
    public static final String KEY_GRAPH_NAME = "graphName";
    public static enum ValuesOfGraphName {
        ANY_LINE, STRAIGHT_LINE, PERFECT_CIRCLE, ELLIPSE, RIGHT_ANGLE_RECTANGLE, ROUNDED_RECTANGLE, DRAWABLE;

    }
    public static final String KEY_GRAPH_SCALE = "scale";
    public static final String KEY_GRAPH_DEGREE = "degree";
    public static final String KEY_GRAPH_PEN = "pen";
    public static final String KEY_GRAPH_STATUS = "status";
    public static enum ValuesOfGraphStatus {
        DRAWING, CONFIRMING, CONFIRMED;
    }
    public static final String KEY_GRAPH_DRAWABLE = "drawable";
    public static final String KEY_GRAPH_DRAWABLE_BITMAP = "bitmap";
    public static final int VALUE_GRAPH_DRAWABLE_BITMAP_OFFSET_X = 140;
    public static final int VALUE_GRAPH_DRAWABLE_BITMAP_OFFSET_Y = 70;

    /**
     * pen的属性
     */
    public static final String KEY_PEN = "pen";
    public static final String KEY_PEN_NAME = "penName";
    public static enum ValuesOfPenName {
        DEFAULT,DOTTED,BLUR,EMBOSS,ERASER;
    }
    public static final String KEY_PEN_START_X = "startX";
    public static final String KEY_PEN_START_Y = "startY";
    public static final String KEY_PEN_MOVE_LOCATIONS = "moveLocations";
    public static final String KEY_PEN_END_X = "endX";
    public static final String KEY_PEN_END_Y = "endY";
    public static final String KEY_PEN_COLOR = "color";
    public static final String KEY_PEN_STROKE_WIDTH = "strokeWidth";
    public static final String KEY_PEN_FILLED = "filled";


    public static final int DEFAULT_VALUE_PEN_COLOR = Color.BLACK;
    public static final int DEFAULT_VALUE_PEN_SIZE = 5;
    public static final boolean DEFAULT_VALUE_PEN_FILLED = false;






}
