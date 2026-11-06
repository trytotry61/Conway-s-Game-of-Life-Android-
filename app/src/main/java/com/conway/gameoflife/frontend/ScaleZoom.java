package com.conway.gameoflife.frontend;
 import android.graphics.Matrix;
 import android.view.MotionEvent;
 import android.view.ScaleGestureDetector;
 import android.view.View;
public class ScaleZoom  extends View {
    private final Matrix matrix =new Matrix();
    private final ScaleGestureDetector scaleDetector;
    private float scaleFactor=1.0f;
    private float lastTouchX;
    private float lastTouchY;
    private float posX=0f;
    private float posY=0f;
    private final View viewToInvalidate;
    public ScaleZoom(View view){
        this.viewToInvalidate=view;
        this.scaleDetector=new ScaleGestureDetector(view.getContext(),new ScaleListener());
    }



}
private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{

}
