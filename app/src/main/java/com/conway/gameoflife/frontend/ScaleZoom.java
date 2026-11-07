package com.conway.gameoflife.frontend;
 import android.content.Context;
 import android.graphics.Matrix;
 import android.media.midi.MidiOutputPort;
 import android.view.MotionEvent;
 import android.view.ScaleGestureDetector;
 import android.view.View;
 import android.view.ViewConfiguration;
public class ScaleZoom{
    private final Matrix matrix =new Matrix();
    private final Matrix inverseMatrix=new Matrix();
    private final ScaleGestureDetector scaleDetector;
    private float scaleFactor=1.0f;
    private final float minScale=1.0f;
    private final float maxScale=5.0f;
    private float downX;
    private float downY;
    private float lastTouchX;
    private float lastTouchY;
    private final int touchSlop;
    private boolean moved=false;
    private boolean dragging=false;
    public ScaleZoom(Context context){
        scaleDetector=new ScaleGestureDetector(context,new ScaleListener());
        touchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
    }
    public boolean onTouchEvent(MotionEvent event){
        scaleDetector.onTouchEvent(event);
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                lastTouchX=event.getX();
                lastTouchY= event.getY();
                downX=event.getX();
                downY=event.getY();
                dragging=true;
                moved=false;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                moved=true;
                dragging=false;
                break;
            case MotionEvent.ACTION_MOVE:
                if(event.getPointerCount()==1 && !scaleDetector.isInProgress()){
                    float x= event.getX();
                    float y=event.getY();
                    float totalDx=x-downX;
                    float totalDy=y-downY;
                    if(!moved && (Math.abs(totalDy)>touchSlop)){
                        moved=true;
                    }
                    if(dragging && moved){
                        float dx=x-lastTouchX;
                        float dy=y-lastTouchY;
                        matrix.postTranslate(dx,dy);
                    }
                    lastTouchX=x;
                    lastTouchY=y;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int pointerIndex=event.getActionIndex()==0?1:0;
                if(pointerIndex<event.getPointerCount()){
                    lastTouchX=event.getX(pointerIndex);
                    lastTouchY=event.getY(pointerIndex);
                    downX=lastTouchX;
                    downY=lastTouchY;
                    dragging=true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case  MotionEvent.ACTION_CANCEL:
                dragging=false;
                break;
        }
        return true;


    }
    public Matrix getMatrix(){
        return matrix;
    }
    public boolean hasMoved(){
        return moved;
    }
    public float[] mapTouchToBoard(float x,float y){
        float[] point ={x,y};
        if(matrix.invert(inverseMatrix)){
            inverseMatrix.mapPoints(point);
        }
        return point;
    }



}
private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{

}
