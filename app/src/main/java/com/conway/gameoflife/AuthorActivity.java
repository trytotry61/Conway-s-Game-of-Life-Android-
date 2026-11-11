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

import org.w3c.dom.Text;

import java.util.concurrent.ScheduledExecutorService;


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

        scrollView.addView(root);

        LinearLayout titleCard=createCard();
        TextView title=createText(
                "Game of Life",
                28,
                TEXT_COLOR,
                Typeface.BOLD
        );
        titleCard.addView(title);

        TextView subtitle=createText(
                "Cellular Automaton",
                14,
                SECONDARY_COLOR,
                Typeface.NORMAL
        );
        LinearLayout.LayoutParams subtittleParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        subtittleParams.topMargin=dp(8);
        titleCard.addView(subtitle,subtittleParams);


        root.addView(titleCard);

        LinearLayout authorCard=createCard();
        TextView authorLabel=createText(
                "Developer",
                12,
                SECONDARY_COLOR,
                Typeface.BOLD
        );
        authorCard.addView(authorLabel);
        TextView authorName=createText(
                 "Kurakin Egor",
                 25,
                 GREEN_COLOR,
                 Typeface.BOLD
        );
        LinearLayout.LayoutParams namePrams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        namePrams.topMargin=dp(12);
        authorCard.addView(authorName,namePrams);
        root.addView(authorCard);


        LinearLayout projectCard=createCard();
        TextView projectTitle=createText(
                "ABOUT PROJECT",
                12,
                SECONDARY_COLOR,
                Typeface.BOLD
        );
        projectCard.addView(projectTitle);

        TextView projectInfo=createText(
                "Learning project\nAndroid Java\n2026",
                15,
                TEXT_COLOR,
                Typeface.NORMAL
        );
        projectInfo.setLineSpacing(dp(6),1);
        LinearLayout.LayoutParams infoParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        infoParams.topMargin=dp(14);
        projectCard.addView(projectInfo,infoParams);
        root.addView(projectCard);


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
        backButton.setTextSize(15);
        backButton.setBackgroundResource(R.drawable.button_primary);
        backButton.setOnClickListener(v->finish());

        LinearLayout.LayoutParams buttonParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.topMargin=dp(55);
        root.addView(backButton,buttonParams);
        setContentView(scrollView);
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
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin=dp(16);
        card.setLayoutParams(params);

        return card;
    }
    private TextView createText(
            String text,
            int size,
            int color,
            int typeface
    ){
        TextView textView=new TextView(this);
        textView.setText(text);
        textView.setTextSize(size);
        textView.setTextColor(color);
        textView.setTypeface(null,typeface);
        textView.setGravity(Gravity.CENTER);

        return textView;
    }

}
