package com.ebs.rental.uidesigns;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebs.rental.general.R;


public class Spinnerview extends LinearLayout {

    View view;
    ImageView imageView;
    TextView textView;
    View v1,v2;

    public Spinnerview(Context context){
        this(context,null);
    }

    public Spinnerview(Context context,  AttributeSet attrs) {
        super(context, attrs);

        @SuppressLint("CustomViewStyleable")
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.options,0,0);

        String texttitle = array.getString(R.styleable.options_titleText);
        @SuppressLint("ResourceAsColor")
        int color = array.getColor(R.styleable.options_linecolor, R.color.material_blue);
        int drawable = array.getInt(R.styleable.options_image,R.drawable.ic_drop_down_arrow);

        float textsize = array.getDimension(R.styleable.options_Textsize,30);

        array.recycle();

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        inflater.inflate(R.layout.spinnerview,this,true);

        LinearLayout parentLayout = (LinearLayout) getChildAt(0);

        LinearLayout linearLayout = (LinearLayout) parentLayout.getChildAt(0);

        textView = (TextView) linearLayout.getChildAt(0);
        textView.setText(texttitle);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textsize);
        textView.setTextColor(getResources().getColor(R.color.black));



        imageView = (ImageView) linearLayout.getChildAt(1);
        imageView.setImageDrawable(getResources().getDrawable(drawable));

        view = parentLayout.getChildAt(1);
        view.setBackgroundColor(color);

    }

    public void setlineColor(int color){
        view.setBackgroundColor(color);
    }

    public void setTitle(String title){
        textView.setText(title);
    }

    public void setImage(int drawable1){
        imageView.setImageDrawable(getResources().getDrawable(drawable1));
    }

    public String getText(){
        return textView.getText().toString();
    }

}
