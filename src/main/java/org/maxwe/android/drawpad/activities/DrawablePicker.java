package org.maxwe.android.drawpad.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import org.maxwe.android.drawpad.Constants;
import org.maxwe.android.drawpad.R;
import org.maxwe.android.drawpad.core.IGraphic;
import org.maxwe.android.drawpad.core.graphics.Drawable;

public class DrawablePicker extends AlertDialog implements View.OnClickListener,Constants {

    Context context = null;
    private String title = null;
    private OnDrawableClickListener onDrawableClickListener = null;


    public DrawablePicker(Context context, String title, OnDrawableClickListener onDrawableClickListener) {
        super(context);
        this.context = context;
        this.title = title;
        this.onDrawableClickListener = onDrawableClickListener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager manager = getWindow().getWindowManager();
        View graphics = this.getLayoutInflater().inflate(R.layout.dialog_drawable, null);
        graphics.findViewById(R.id.iv_dialog_drawable_a).setOnClickListener(this);
        graphics.findViewById(R.id.iv_dialog_drawable_b).setOnClickListener(this);
        graphics.findViewById(R.id.iv_dialog_drawable_c).setOnClickListener(this);
        graphics.findViewById(R.id.iv_dialog_drawable_d).setOnClickListener(this);
        graphics.findViewById(R.id.iv_dialog_drawable_e).setOnClickListener(this);
        graphics.findViewById(R.id.iv_dialog_drawable_f).setOnClickListener(this);
        graphics.findViewById(R.id.iv_dialog_drawable_g).setOnClickListener(this);
        graphics.findViewById(R.id.iv_dialog_drawable_h).setOnClickListener(this);
        setContentView(graphics);
        setTitle(title);
    }

    @Override
    public void onClick(View v) {
        int drawableId = -1;
        switch (v.getId()){
            case R.id.iv_dialog_drawable_a :
                drawableId = R.drawable.drawable_a;
                break;
            case R.id.iv_dialog_drawable_b :
                drawableId = R.drawable.drawable_b;
                break;
            case R.id.iv_dialog_drawable_c :
                drawableId = R.drawable.drawable_c;
                break;
            case R.id.iv_dialog_drawable_d :
                drawableId = R.drawable.drawable_d;
                break;
            case R.id.iv_dialog_drawable_e :
                drawableId = R.drawable.drawable_e;
                break;
            case R.id.iv_dialog_drawable_f :
                drawableId = R.drawable.drawable_f;
                break;
            case R.id.iv_dialog_drawable_g :
                drawableId = R.drawable.drawable_g;
                break;
            case R.id.iv_dialog_drawable_h :
                drawableId = R.drawable.drawable_h;
                break;

        }
        this.onDrawableClickListener.onDrawableClick(new Drawable(this.getContext(),drawableId));
        this.dismiss();
    }

    public interface OnDrawableClickListener {
        void onDrawableClick(IGraphic iGraphic);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OnDrawableClickListener getOnDrawableClickListener() {
        return onDrawableClickListener;
    }

    public void setOnDrawableClickListener(OnDrawableClickListener onDrawableClickListener) {
        this.onDrawableClickListener = onDrawableClickListener;
    }
}
