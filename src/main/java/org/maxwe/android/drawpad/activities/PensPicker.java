package org.maxwe.android.drawpad.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import org.maxwe.android.drawpad.R;
import org.maxwe.android.drawpad.core.IPen;
import org.maxwe.android.drawpad.core.pens.*;

/**
 * Created by dingpengwei on 11/4/14.
 */
public class PensPicker extends AlertDialog implements View.OnClickListener {
    private Context context = null;
    private String title = null;
    private IPen iPen = new DefaultPen();
    private OnPensChangedListener onPensChangedListener = null;


    public PensPicker(Context context, String title, IPen iPen, OnPensChangedListener onPensChangedListener) {
        super(context);
        this.context = context;
        this.title = title;
        this.iPen = iPen;
        this.onPensChangedListener = onPensChangedListener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View graphics = this.getLayoutInflater().inflate(R.layout.dialog_pens, null);
        SeekBar seekBar = (SeekBar) graphics.findViewById(R.id.sb_dialog_pens_seek_bar);
        seekBar.setMax(50);
        seekBar.setProgress((int)this.iPen.getSize());

        seekBar.setOnSeekBarChangeListener(new OnPenSizeChange());

        Switch filled = (Switch) graphics.findViewById(R.id.sw_dialog_pens_filled_switch);
        if(this.iPen.getFilled()){
            filled.setChecked(true);
        }else{
            filled.setChecked(false);
        }
        filled.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    iPen.setFilled(true);
                }else{
                    iPen.setFilled(false);
                }
                PensPicker.this.dismiss();
            }
        });

        graphics.findViewById(R.id.bt_dialog_pens_default).setOnClickListener(this);
        graphics.findViewById(R.id.bt_dialog_pens_dotted).setOnClickListener(this);
        graphics.findViewById(R.id.bt_dialog_pens_blur).setOnClickListener(this);
        graphics.findViewById(R.id.bt_dialog_pens_emboss).setOnClickListener(this);
        graphics.findViewById(R.id.bt_dialog_pens_eraser).setOnClickListener(this);
        setContentView(graphics);
        setTitle(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_dialog_pens_default :
                this.onPensChangedListener.onPensChanged(new DefaultPen());
                break;
            case R.id.bt_dialog_pens_dotted :
                this.onPensChangedListener.onPensChanged(new DottedPen());
                break;
            case R.id.bt_dialog_pens_blur :
                this.onPensChangedListener.onPensChanged(new BlurPen());
                break;
            case R.id.bt_dialog_pens_emboss :
                this.onPensChangedListener.onPensChanged(new EmbossPen());
                break;
            case R.id.bt_dialog_pens_eraser :
                this.onPensChangedListener.onPensChanged(new EraserPen());
                break;
        }
        this.dismiss();
    }

    public interface OnPensChangedListener {
        void onPensChanged(IPen iPen);
    }

    public class OnPenSizeChange implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            onPensChangedListener.onPensChanged(iPen);
            PensPicker.this.dismiss();
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            iPen.setSize(progress);
        }
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public OnPensChangedListener getOnPensChangedListener() {
        return onPensChangedListener;
    }

    public void setOnPensChangedListener(OnPensChangedListener onPensChangedListener) {
        this.onPensChangedListener = onPensChangedListener;
    }
}
