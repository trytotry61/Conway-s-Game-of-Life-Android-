package com.conway.gameoflife.frontend;
 import android.content.Context;
 import android.graphics.Matrix;
 import android.graphics.RectF;
 import android.view.MotionEvent;
 import android.view.ScaleGestureDetector;
 import android.view.View;
 import android.view.ViewConfiguration;
public class ScaleZoom{
    private final Matrix matrix =new Matrix();
    private final Matrix inverseMatrix=new Matrix();
    private final ScaleGestureDetector scaleDetector;
    private final View targetView;
    private final RectF contentBounds=new RectF();
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
    public ScaleZoom(View view){
        targetView=view;
        scaleDetector=new ScaleGestureDetector(view.getContext(),new ScaleListener());
        touchSlop=ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
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
                    if(!moved && (Math.abs(totalDx)>touchSlop) || (Math.abs(totalDy)>touchSlop)){
                        moved=true;
                    }
                    if(dragging && moved && scaleFactor>minScale){
                        float dx=x-lastTouchX;
                        float dy=y-lastTouchY;
                        matrix.postTranslate(dx,dy);
                        constrainMatrix();
                        targetView.invalidate();
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
    public void setContentBounds( float left,float top, float right,float bottom){
        contentBounds.set(left,top,right,bottom);
        constrainMatrix();

    }
    private void constrainMatrix(){
        if(contentBounds.isEmpty() || targetView.getWidth()==0 || targetView.getHeight()==0){
            return;
        }
        RectF transformBounds=new RectF(contentBounds);
        matrix.mapRect(transformBounds);
        float minVisible=64*targetView.getResources().getDisplayMetrics().density;
        float dx=0;
        float dy=0;
        float viewWidth=targetView.getWidth();
        float viewHeight=targetView.getHeight();
        if(transformBounds.width()<viewWidth){
            dx=viewWidth/2f-transformBounds.centerX();
        } else if (transformBounds.left>0) {
            dx=-transformBounds.left;
        } else if (transformBounds.right<viewWidth) {
            dx=viewWidth-transformBounds.right;

        }
        if(transformBounds.height()<=viewHeight){
            dy=viewHeight/2f -transformBounds.centerY();
        } else if (transformBounds.top>0) {
            dy=-transformBounds.top;

        }else if(transformBounds.bottom<viewHeight){
            dy=viewHeight-transformBounds.bottom;
        }
    }
    public float[] mapTouchToBoard(float x,float y){
        float[] point ={x,y};
        if(matrix.invert(inverseMatrix)){
            inverseMatrix.mapPoints(point);
        }
        return point;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector){
            float factor=detector.getScaleFactor();
            float newScale=scaleFactor*factor;
            newScale=Math.max(minScale,Math.min(newScale,maxScale));
            factor=newScale/scaleFactor;
            scaleFactor=newScale;
            if(scaleFactor<=minScale){
                matrix.reset();
            }else{
                matrix.postScale(factor,factor,detector.getFocusX(),detector.getFocusY());
                constrainMatrix();
            }

            moved=true;
            return true;
        }

    }



}

