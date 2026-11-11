package com.conway.gameoflife;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class AuthorActivity extends Activity {
    private final int BACKGROUND_COLOR=Color.rgb(235,244,238);
    private final int CARD_COLOR=Color.WHITE;
    private final int TEXT_COLOR= Color.rgb(31,41,35);
    private final int SECONDARY_COLOR=Color.rgb(120,130,123);
    private final int GREEN_COLOR=Color.rgb(34,160,107);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ScrollView scrollView=new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(BACKGROUND_COLOR);

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

        TextView authorName=new TextView(this);
        authorName.setText("Name VorName");
        authorName.setTextSize(25);
        authorName.setTextColor(Color.rgb(34,160,107));
        authorName.setTypeface(null,Typeface.BOLD);
        authorName.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams nameParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        nameParams.topMargin=dp(10);
        root.addView(authorName,nameParams);

        TextView projectInfo=new TextView(this);
        projectInfo.setText("Учебный проект\n Android Java\n2026");
        projectInfo.setTextSize(14);
        projectInfo.setTextColor(Color.rgb(100,112,103));
        projectInfo.setGravity(Gravity.CENTER);
        projectInfo.setLineSpacing(dp(5),1);

        LinearLayout.LayoutParams infoParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        infoParams.topMargin=dp(20);
        root.addView(projectInfo,infoParams);

        TextView signature =new TextView(this);
        signature.setText("Designed and developen by Egor Kurakin");
        signature.setTextSize(13);
        signature.setTextColor(Color.rgb(120,130,123));
        signature.setGravity(Gravity.CENTER);
        signature.setTypeface(null,Typeface.ITALIC);

        LinearLayout.LayoutParams signatureParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        signatureParams.topMargin=dp(35);
        root.addView(signature,signatureParams);

        Button backButton=new Button(this);
        backButton.setText("Back Game");
        backButton.setAllCaps(false);
        backButton.setTextColor(Color.WHITE);
        backButton.setBackgroundResource(R.drawable.button_primary);
        backButton.setOnClickListener(v->finish());

        LinearLayout.LayoutParams buttonParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.topMargin=dp(55);
        root.addView(backButton,buttonParams);
        setContentView(root);
    }
    private int dp(int value){
        return (int)(value*getResources().getDisplayMetrics().density);
    }
    private LinearLayout createCard(){
        LinearLayout card=new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER);
        card.setPadding(dp(20),dp(24),dp(20),dp(24));

        card.setBackgroundResource(R.drawable.board_background);
        LinearLayout.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin=dp(16);
        card.setLayoutParams(params);

        return card;
    }

}
