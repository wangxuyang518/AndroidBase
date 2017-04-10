package com.xinyi_tech.commonlibs.widget.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyi_tech.commonlibs.R;
import com.xinyi_tech.commonlibs.util.StringUtil;

/**
 * Created by studyjun on 2016/3/11.
 */
public class FieldLinearlayout extends LinearLayout{

    private TextView mLabelV;
    private String mLabel;
    private float mLabelSize;
    private float mLabelWidth;
    private int mLabelColor;
    private Drawable mLabelBgColor;
    private int mLabelPaddingLeft;
    private int mLabelPaddingRight;
    private int mLabelPaddingBottom;
    private int mLabelPaddingTop;


    public FieldLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }



    public FieldLinearlayout(Context context, String label) {
        super(context);
        setLabel(label);
    }


    protected void init(AttributeSet attrs){
        if (attrs!=null){
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.FieldLinearlayout);
            mLabel=ta.getString(R.styleable.FieldLinearlayout_label);
            mLabelSize=ta.getDimensionPixelSize(R.styleable.FieldLinearlayout_textSize, -1);
            mLabelWidth=ta.getDimensionPixelSize(R.styleable.FieldLinearlayout_labelWidth, -100);
            mLabelPaddingLeft=ta.getDimensionPixelSize(R.styleable.FieldLinearlayout_labelPaddingLeft, 0);
            mLabelPaddingTop=ta.getDimensionPixelSize(R.styleable.FieldLinearlayout_labelPaddingTop, 0);
            mLabelPaddingRight=ta.getDimensionPixelSize(R.styleable.FieldLinearlayout_labelPaddingRight, 0);
            mLabelPaddingBottom=ta.getDimensionPixelSize(R.styleable.FieldLinearlayout_labelPaddingBottom, 0);
            mLabelColor=ta.getColor(R.styleable.FieldLinearlayout_textColor, 0);
            mLabelBgColor=ta.getDrawable(R.styleable.FieldLinearlayout_labelBgColor);

            ta.recycle();
        }

        setOrientation(HORIZONTAL);
        mLabelV= new TextView(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLabelV.setLayoutParams(layoutParams);
        mLabelV.setGravity(Gravity.CENTER_VERTICAL);

//        mLabelV.setPadding(getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin),0,0,0);
        if (!StringUtil.isEmpty(mLabel)){
            mLabelV.setText(mLabel);
        }

        if (mLabelSize==-1)
            mLabelV.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        else
            mLabelV.setTextSize(TypedValue.COMPLEX_UNIT_PX,mLabelSize);

        if (mLabelColor!=0)
            mLabelV.setTextColor(mLabelColor);

        if (mLabelBgColor!=null)
            mLabelV.setBackgroundDrawable(mLabelBgColor);

        if (mLabelWidth!=-100)
            mLabelV.setWidth((int) mLabelWidth);

        mLabelV.setPadding(mLabelPaddingLeft,mLabelPaddingTop,mLabelPaddingRight,mLabelPaddingBottom);

        addView(mLabelV);
    }


    public void setClomn1Width(int px){
        mLabelV.setWidth(px);
    }


    public void setClomn1Weight(float weight){
        mLabelV.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight));
    }

    public void setLabel(String label){
        mLabelV.setText(label);
    }


    public void setMust(boolean must){

    }


}
