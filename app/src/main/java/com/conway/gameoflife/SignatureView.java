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
}
