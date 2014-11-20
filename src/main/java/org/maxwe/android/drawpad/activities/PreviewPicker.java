package org.maxwe.android.drawpad.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import org.maxwe.android.drawpad.Constants;
import org.maxwe.android.drawpad.Preview;
import org.maxwe.android.drawpad.R;
import org.maxwe.android.drawpad.core.IGraphic;
import org.maxwe.android.drawpad.core.IPen;
import org.maxwe.android.drawpad.core.graphics.*;
import org.maxwe.android.drawpad.core.pens.*;


/**
 * Created by dingpengwei on 11/4/14.
 */
public class PreviewPicker extends AlertDialog implements View.OnClickListener ,
        SeekBar.OnSeekBarChangeListener,CompoundButton.OnCheckedChangeListener ,
        ColorPickerView.OnColorChangeLintener {

    private MainView mainView;
    private IPen iPen = new DefaultPen();
    private IGraphic iGraphic = new AnyLine();

    private Button reset;
    private Button close;
    private Preview preview;

    private Button bt_dialog_draw_setting_graphics_any_line;
    private Button bt_dialog_draw_setting_graphics_straight_line;
    private Button bt_dialog_draw_setting_graphics_perfect_circle;
    private Button bt_dialog_draw_setting_graphics_ellipse;
    private Button bt_dialog_draw_setting_graphics_right_rectangle;
    private Button bt_dialog_draw_setting_graphics_round_rectangle;

    private Button bt_dialog_draw_setting_pens_default;
    private Button bt_dialog_draw_setting_pens_dotted;
    private Button bt_dialog_draw_setting_pens_blur;
    private Button bt_dialog_draw_setting_pens_emboss;
    private Button bt_dialog_draw_setting_pens_eraser;

    private ColorPickerView colorPickerView;
    private SeekBar seekBar;
    private Switch filledSwitch;
    private String title = null;


    public PreviewPicker(Context context, String title, MainView mainView) {
        super(context);
        this.mainView = mainView;
        this.iPen = this.mainView.getIPen().clone();
        this.iGraphic = this.mainView.getIGraphic().clone();
        this.title = title;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_draw_setting);

        this.reset = (Button) this.findViewById(R.id.bt_dialog_draw_setting_reset);
        this.reset.setOnClickListener(this);
        this.close = (Button) this.findViewById(R.id.bt_dialog_draw_setting_close);
        this.close.setOnClickListener(this);


        this.preview = (Preview) this.findViewById(R.id.drawpad_preview);
        this.preview.setiPen(this.mainView.getIPen());
        this.preview.setiGraphic(this.mainView.getIGraphic());

        this.bt_dialog_draw_setting_graphics_any_line = (Button) this.findViewById(R.id.bt_dialog_draw_setting_graphics_any_line);
        this.bt_dialog_draw_setting_graphics_any_line.setOnClickListener(this);
        this.bt_dialog_draw_setting_graphics_straight_line = (Button) this.findViewById(R.id.bt_dialog_draw_setting_graphics_straight_line);
        this.bt_dialog_draw_setting_graphics_straight_line.setOnClickListener(this);
        this.bt_dialog_draw_setting_graphics_perfect_circle = (Button) this.findViewById(R.id.bt_dialog_draw_setting_graphics_perfect_circle);
        this.bt_dialog_draw_setting_graphics_perfect_circle.setOnClickListener(this);
        this.bt_dialog_draw_setting_graphics_ellipse = (Button) this.findViewById(R.id.bt_dialog_draw_setting_graphics_ellipse);
        this.bt_dialog_draw_setting_graphics_ellipse.setOnClickListener(this);
        this.bt_dialog_draw_setting_graphics_right_rectangle = (Button) this.findViewById(R.id.bt_dialog_draw_setting_graphics_right_rectangle);
        this.bt_dialog_draw_setting_graphics_right_rectangle.setOnClickListener(this);
        this.bt_dialog_draw_setting_graphics_round_rectangle = (Button) this.findViewById(R.id.bt_dialog_draw_setting_graphics_round_rectangle);
        this.bt_dialog_draw_setting_graphics_round_rectangle.setOnClickListener(this);
        this.bt_dialog_draw_setting_pens_default = (Button) this.findViewById(R.id.bt_dialog_draw_setting_pens_default);
        this.bt_dialog_draw_setting_pens_default.setOnClickListener(this);
        this.bt_dialog_draw_setting_pens_dotted = (Button) this.findViewById(R.id.bt_dialog_draw_setting_pens_dotted);
        this.bt_dialog_draw_setting_pens_dotted.setOnClickListener(this);
        this.bt_dialog_draw_setting_pens_blur = (Button) this.findViewById(R.id.bt_dialog_draw_setting_pens_blur);
        this.bt_dialog_draw_setting_pens_blur.setOnClickListener(this);
        this.bt_dialog_draw_setting_pens_emboss = (Button) this.findViewById(R.id.bt_dialog_draw_setting_pens_emboss);
        this.bt_dialog_draw_setting_pens_emboss.setOnClickListener(this);
        this.bt_dialog_draw_setting_pens_eraser = (Button) this.findViewById(R.id.bt_dialog_draw_setting_pens_eraser);
        this.bt_dialog_draw_setting_pens_eraser.setOnClickListener(this);
        this.colorPickerView = (ColorPickerView) this.findViewById(R.id.drawpad_setting_color);
        this.colorPickerView.setIsPreview(true);
        this.colorPickerView.setOnColorChangeLintener(this);
        this.seekBar = (SeekBar) this.findViewById(R.id.sb_dialog_draw_setting_seek_bar);
        this.seekBar.setProgress(this.preview.getSize());
        this.seekBar.setOnSeekBarChangeListener(this);
        this.filledSwitch = (Switch) this.findViewById(R.id.sw_dialog_draw_setting_switch);
        this.filledSwitch.setChecked(this.preview.getFilled());
        this.filledSwitch.setOnCheckedChangeListener(this);
        setTitle(title);
        this.mainView.invalidate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_dialog_draw_setting_reset:
                this.iPen = new DefaultPen();
                this.iPen.setColor(Constants.DEFAULT_VALUE_PEN_COLOR);
                this.iPen.setSize(Constants.DEFAULT_VALUE_PEN_SIZE);
                this.iPen.setFilled(Constants.DEFAULT_VALUE_PEN_FILLED);
                this.colorPickerView.reset();
                this.seekBar.setProgress(Constants.DEFAULT_VALUE_PEN_SIZE);
                this.filledSwitch.setChecked(Constants.DEFAULT_VALUE_PEN_FILLED);
                this.iGraphic = new AnyLine();
                break;
            case R.id.bt_dialog_draw_setting_close:
                this.mainView.setIPen(this.iPen.clone());
                this.mainView.setIGraphic(this.iGraphic.clone());
                this.dismiss();
                break;

            case R.id.bt_dialog_draw_setting_graphics_any_line:
                this.iGraphic = new AnyLine();
                break;
            case R.id.bt_dialog_draw_setting_graphics_straight_line:
                this.iGraphic = new StraightLIne();
                break;
            case R.id.bt_dialog_draw_setting_graphics_perfect_circle:
                this.iGraphic = new PerfectCircle();
                break;
            case R.id.bt_dialog_draw_setting_graphics_ellipse:
                this.iGraphic = new Ellipse();
                break;
            case R.id.bt_dialog_draw_setting_graphics_right_rectangle:
                this.iGraphic = new RightAngleRectangle();
                break;
            case R.id.bt_dialog_draw_setting_graphics_round_rectangle:
                this.iGraphic = new RoundedRectangle();
                break;

            case R.id.bt_dialog_draw_setting_pens_default:
                this.iPen = new DefaultPen();
                break;
            case R.id.bt_dialog_draw_setting_pens_dotted:
                this.iPen = new DottedPen();
                break;
            case R.id.bt_dialog_draw_setting_pens_blur:
                this.iPen = new BlurPen();
                break;
            case R.id.bt_dialog_draw_setting_pens_emboss:
                this.iPen = new EmbossPen();
                break;
            case R.id.bt_dialog_draw_setting_pens_eraser:
                this.iPen = new EraserPen();
                this.iGraphic = new AnyLine();
                this.filledSwitch.setChecked(false);
                break;
        }

        if(this.iPen instanceof EraserPen && !(this.iGraphic instanceof AnyLine)){
            this.iPen = new DefaultPen();
        }

        this.preview.setiPen(this.iPen);
        this.preview.setiGraphic(this.iGraphic);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.preview.setSize(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.preview.setFilled(isChecked);
    }

    @Override
    public void onColorChanged(int color) {
        if(this.iPen instanceof EraserPen){
            this.preview.setColor(Color.WHITE);
            this.iPen.setColor(Color.WHITE);
        }else{
            this.preview.setColor(color);
            this.iPen.setColor(color);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
