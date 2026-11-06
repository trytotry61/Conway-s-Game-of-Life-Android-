package com.conway.gameoflife.frontend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class ScaleZoom  extends View {
    private Paint paint;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor=1.0f;
    private float focusX=0f;
    private float focusY=0f;
    public ScaleZoom(Context context){
        super(context);
        init(context);
    }
    public ScaleZoom(Context context, AttributeSet attr){
        super(context,attr);
        init(context);
    }
    private void init(Context context){
        paint=new Paint();
        paint.setColor(Color.BLUE);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }



}
