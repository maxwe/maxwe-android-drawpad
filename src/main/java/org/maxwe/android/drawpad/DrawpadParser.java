package org.maxwe.android.drawpad;

import android.content.Context;
import android.graphics.Canvas;
import org.maxwe.android.drawpad.core.IGraphic;
import org.maxwe.android.drawpad.core.IPen;
import org.maxwe.android.drawpad.core.graphics.*;
import org.maxwe.android.drawpad.core.pens.DefaultPen;
import org.maxwe.json.JsonArray;
import org.maxwe.json.JsonObject;

/**
 * Created by dingpengwei on 10/5/14.
 */
public class DrawpadParser implements Constants {

    public static void parserLocal(Context context,Canvas canvas, JsonObject drawpad){
        String backgroundFlag = drawpad.getString(KEY_BACKGROUND_FLAG);
        if (ValuesOfBackgroundFlag.DRAWABLE.name().equals(backgroundFlag)) {

        } else if (ValuesOfBackgroundFlag.COLOR.name().equals(backgroundFlag)) {

        } else if (ValuesOfBackgroundFlag.FILE.name().equals(backgroundFlag)) {

        }

        JsonArray graphics = drawpad.getJsonArray(KEY_GRAPHICS);
        int lenght = graphics.getLenght();
        for (int i = 0; i < lenght; i++) {
            IGraphic iGraphic = graphics.get(i);
            iGraphic.onDraw(canvas);
            System.out.println(iGraphic);
        }
    }

    public static void parser(Context context,Canvas canvas, JsonObject jsonObject) {
        String backgroundFlag = jsonObject.getString(KEY_BACKGROUND_FLAG);
        if (ValuesOfBackgroundFlag.DRAWABLE.name().equals(backgroundFlag)) {

        } else if (ValuesOfBackgroundFlag.COLOR.name().equals(backgroundFlag)) {

        } else if (ValuesOfBackgroundFlag.FILE.name().equals(backgroundFlag)) {

        }

        JsonArray graphics = jsonObject.getJsonArray(KEY_GRAPHICS);
        int lenght = graphics.getLenght();
        for (int i = 0; i < lenght; i++) {
            IGraphic iGraphic = null;
            IPen iPen;
            JsonObject graphic = graphics.getJsonObject(i);
            String graphName = graphic.getString(KEY_GRAPH_NAME);
            float startX = graphic.getNumber(KEY_PEN_START_X).floatValue();
            float startY = graphic.getNumber(KEY_PEN_START_Y).floatValue();
            float endX = graphic.getNumber(KEY_PEN_END_X).floatValue();
            float endY = graphic.getNumber(KEY_PEN_END_Y).floatValue();

            if (ValuesOfGraphName.ANY_LINE.name().equals(graphName)) {
                iGraphic = new AnyLine();
            } else if(ValuesOfGraphName.DRAWABLE.name().equals(graphName)){
                JsonObject drawable = graphic.getJsonObject(KEY_GRAPH_DRAWABLE);
                int bitmapId = drawable.getNumber(KEY_GRAPH_DRAWABLE_BITMAP).intValue();
                iGraphic = new Drawable(context,bitmapId);
            } else if (ValuesOfGraphName.ELLIPSE.name().equals(graphName)) {
                iGraphic = new Ellipse();
            } else if (ValuesOfGraphName.PERFECT_CIRCLE.name().equals(graphName)) {
                iGraphic = new PerfectCircle();
            } else if (ValuesOfGraphName.RIGHT_ANGLE_RECTANGLE.name().equals(graphName)) {
                iGraphic = new RightAngleRectangle();
            } else if (ValuesOfGraphName.ROUNDED_RECTANGLE.name().equals(graphName)) {
                iGraphic = new RoundedRectangle();
            } else if (ValuesOfGraphName.STRAIGHT_LINE.name().equals(graphName)){
                iGraphic = new StraightLIne();
            }

            iPen = new DefaultPen();

            iPen.onTouchDown(startX, startY);
            JsonArray moveLocation = graphic.getJsonArray(KEY_PEN_MOVE_LOCATIONS);
            if(moveLocation != null){
                int len = moveLocation.getLenght();
                for (int j = 0; j < len; j = j + 2) {
                    float moveX = moveLocation.getNumber(j).floatValue();
                    float moveY = moveLocation.getNumber(j + 1).floatValue();
                    iPen.onTouchMove(moveX, moveY);
                }
            }

            iPen.onTouchUp(endX, endY);
            iPen.onDraw(canvas);
        }
    }

    public static void parserDrawable(Context context,Canvas canvas, JsonArray jsonArray){
        int lenght = jsonArray.getLenght();
        for (int i = 0; i < lenght; i++) {
            IGraphic iGraphic = null;
            IPen iPen;
            JsonObject graphic = jsonArray.getJsonObject(i);
            float startX = graphic.getNumber(KEY_PEN_START_X).floatValue();
            float startY = graphic.getNumber(KEY_PEN_START_Y).floatValue();

            JsonObject drawable = graphic.getJsonObject(KEY_GRAPH_DRAWABLE);
            int bitmapId = drawable.getNumber(KEY_GRAPH_DRAWABLE_BITMAP).intValue();
            iGraphic = new Drawable(context,bitmapId);

            iPen = new DefaultPen();
            iPen.onTouchDown(startX, startY);
            iPen.onDraw(canvas);
        }
    }




}
