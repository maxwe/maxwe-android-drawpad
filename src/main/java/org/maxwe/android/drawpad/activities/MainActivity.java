package org.maxwe.android.drawpad.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;
import org.maxwe.android.drawpad.R;
import org.maxwe.android.drawpad.core.IGraphic;
import org.maxwe.android.drawpad.core.IPen;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class MainActivity extends Activity {

    private MainView mainView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainView = new MainView(this);
        this.setContentView(this.mainView);
        ActionBar actionBar = this.getActionBar();
        actionBar.show();
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.functionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_function_setting:
                PreviewPicker previewPicker = new PreviewPicker(this,"",this.mainView);
                previewPicker.show();
                previewPicker.getWindow().setLayout(2000, 1000);
                break;
            case R.id.menu_function_graphics:
                GraphicsPicker graphicsPickerDialog = new GraphicsPicker(this, "图形选择",this.mainView.getIGraphic(), new GraphicsPicker.OnGraphicsChangedListener() {
                    @Override
                    public void onGraphicChanged(IGraphic graphic) {
                        mainView.setIGraphic(graphic);
                    }
                });
                graphicsPickerDialog.show();
                break;
            case R.id.menu_function_pen:
                PensPicker pensPicker = new PensPicker(this,"画笔设置",this.mainView.getIPen(),new PensPicker.OnPensChangedListener() {
                    @Override
                    public void onPensChanged(IPen iPen) {
                        mainView.setIPen(iPen);
                    }
                });
                pensPicker.show();
                break;
            case R.id.menu_function_color:
                ColorPicker colorPicker = new ColorPicker(this, this.mainView.getPenColor(), "颜色设置", new ColorPicker.OnColorChangedListener() {
                    public void colorChanged(int color) {
                        mainView.setPenColor(color);
                    }
                });
                colorPicker.show();
                break;
            case R.id.menu_function_back_ground_color:
                ColorPicker backgroundColor = new ColorPicker(this, this.mainView.getPenColor(), "颜色设置", new ColorPicker.OnColorChangedListener() {
                    public void colorChanged(int color) {
                        mainView.setBackgroundColor(color);
                    }
                });
                backgroundColor.show();
                break;
            case R.id.menu_function_drawable:
                DrawablePicker drawablePickerDialog = new DrawablePicker(this, "图片选择", new DrawablePicker.OnDrawableClickListener() {
                    @Override
                    public void onDrawableClick(IGraphic iGraphic) {
                        mainView.setIGraphic(iGraphic);
                    }
                });
                drawablePickerDialog.show();
                break;
            case R.id.menu_function_undo:
                if(this.mainView.isCanUndo()){
                    this.mainView.undo();
                }else{
                    Toast.makeText(this, "不能再后退了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_function_redo:
                if(this.mainView.isCanRedo()){
                    this.mainView.redo();
                }else{
                    Toast.makeText(this,"不能再前进了",Toast.LENGTH_SHORT).show();
                }
            case R.id.menu_function_clear:
                this.mainView.clear();
                break;
            case R.id.menu_function_save:
                try{
                    String path = "/mnt/sdcard/" + UUID.randomUUID().toString() + ".jpg";
                    boolean result = this.mainView.saveBitmap(path);
                    if(result){
                        Toast.makeText(this,"保存成功：" + path,Toast.LENGTH_LONG).show();
                    }
                }catch(IOException e){
                    Toast.makeText(this,"保存失败",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
