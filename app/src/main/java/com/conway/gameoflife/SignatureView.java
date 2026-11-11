package com.conway.gameoflife;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Xml;

import androidx.core.graphics.PathParser;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SignatureView extends View {
    private static class SignatureElement{
        Path path;
        float length;
        boolean isDot;
        float dotX;
        float dotY;
        float dotRadius;

    }
    private final List<SignatureElement> elements=new ArrayList<>();
    private final Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private final PathMeasure pathMeasure=new PathMeasure();
    private final Path drawingPath=new Path();
    private ValueAnimator animator;
    private float animationProgress=0;
    private static final float SVG_WIDTH=685f;
    private static final float SVG_HEIGHT=350f;
    private static final long STROKE_DURATION=450;

    public SignatureView(Context context){
        super(context);
        init();
    }
    public SignatureView(Context context,AttributeSet attr){
        super(context,attr);
        init();
    }
    private void init(){
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        loadSignature();
    }
    private void loadSignature() {

        try {

            InputStream inputStream =
                    getContext().getAssets().open("signature_paths.svg");

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, "UTF-8");

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {

                    String tag = parser.getName();

                    if ("path".equals(tag)) {

                        String pathData =
                                parser.getAttributeValue(null, "d");

                        if (pathData != null) {

                            Path path =
                                    PathParser.createPathFromPathData(pathData);

                            if (path != null) {

                                SignatureElement element =
                                        new SignatureElement();

                                element.path = path;

                                pathMeasure.setPath(path, false);
                                element.length = pathMeasure.getLength();

                                elements.add(element);
                            }
                        }
                    }
                    if ("ellipse".equals(tag)) {

                        SignatureElement element =
                                new SignatureElement();

                        element.isDot = true;

                        element.dotX = Float.parseFloat(
                                parser.getAttributeValue(null, "cx")
                        );

                        element.dotY = Float.parseFloat(
                                parser.getAttributeValue(null, "cy")
                        );

                        element.dotRadius = Float.parseFloat(
                                parser.getAttributeValue(null, "rx")
                        );

                        elements.add(element);
                    }
                }

                eventType = parser.next();
            }

            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
