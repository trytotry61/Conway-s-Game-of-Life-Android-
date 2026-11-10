package com.conway.gameoflife;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AuthorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LinearLayout root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(dp(28),dp(24),dp(28),dp(24));
        root.setBackgroundColor(Color.rgb(247,249,248));

        TextView title =new TextView(this);
        title.setText("Game of Life");
        title.setTextSize(28);
        title.setTextColor(Color.rgb(31,41,35));
        title.setTypeface(null,Typeface.BOLD);
        title.setGravity(Gravity.CENTER);

        root.addView(title);

        TextView subtitle=new TextView(this);
        subtitle.setText("Cellular Automaton");
        subtitle.setTextSize(14);
        subtitle.setTextColor(Color.rgb(120,130,123));
        subtitle.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams subtitleParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        subtitleParams.topMargin=dp(8);
        root.addView(subtitle,subtitleParams);

        TextView authorLabel=new TextView(this);
        authorLabel.setText("Developer");
        authorLabel.setTextSize(11);
        authorLabel.setTextColor(Color.rgb(120,130,123));
        authorLabel.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams labelParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        labelParams.topMargin=dp(55);
        root.addView(authorLabel,labelParams);

    }

}
