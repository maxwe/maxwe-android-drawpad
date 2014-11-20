package org.maxwe.android.drawpad.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import org.maxwe.android.drawpad.R;
import org.maxwe.android.drawpad.core.IGraphic;
import org.maxwe.android.drawpad.core.graphics.*;

public class GraphicsPicker extends AlertDialog implements View.OnClickListener{

    private Context context = null;
    private String title = null;
    private IGraphic currentGraphic = new AnyLine();
    private OnGraphicsChangedListener onGraphicsChangedListener = null;


    public GraphicsPicker(Context context, String title, IGraphic iGraphic, OnGraphicsChangedListener onGraphicsChangedListener) {
        super(context);
        this.context = context;
        this.title = title;
        this.currentGraphic = iGraphic;
        this.onGraphicsChangedListener = onGraphicsChangedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View graphics = this.getLayoutInflater().inflate(R.layout.dialog_graphics, null);
        graphics.findViewById(R.id.bt_dialog_graphics_path).setOnClickListener(this);
        graphics.findViewById(R.id.bt_dialog_graphics_line).setOnClickListener(this);
        graphics.findViewById(R.id.bt_dialog_graphics_circle_stroke).setOnClickListener(this);

        graphics.findViewById(R.id.bt_dialog_graphics_oval_stroke).setOnClickListener(this);
        graphics.findViewById(R.id.bt_dialog_graphics_rect_stroke).setOnClickListener(this);
        graphics.findViewById(R.id.bt_dialog_graphics_rect_round_stroke).setOnClickListener(this);
        setContentView(graphics);
        setTitle(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_dialog_graphics_path :
                this.onGraphicsChangedListener.onGraphicChanged(new AnyLine());
                break;
            case R.id.bt_dialog_graphics_line :
                this.onGraphicsChangedListener.onGraphicChanged(new StraightLIne());
                break;
            case R.id.bt_dialog_graphics_circle_stroke :
                this.onGraphicsChangedListener.onGraphicChanged(new PerfectCircle());
                break;
            case R.id.bt_dialog_graphics_oval_stroke :
                this.onGraphicsChangedListener.onGraphicChanged(new Ellipse());
                break;
            case R.id.bt_dialog_graphics_rect_stroke :
                this.onGraphicsChangedListener.onGraphicChanged(new RightAngleRectangle());
                break;
            case R.id.bt_dialog_graphics_rect_round_stroke :
                this.onGraphicsChangedListener.onGraphicChanged(new RoundedRectangle());
                break;
        }
        this.dismiss();
    }

    public interface OnGraphicsChangedListener {
        void onGraphicChanged(IGraphic iGraphic);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OnGraphicsChangedListener getOnGraphicsChangedListener() {
        return onGraphicsChangedListener;
    }

    public void setOnGraphicsChangedListener(OnGraphicsChangedListener onGraphicsChangedListener) {
        this.onGraphicsChangedListener = onGraphicsChangedListener;
    }
}
