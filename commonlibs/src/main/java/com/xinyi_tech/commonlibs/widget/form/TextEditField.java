package com.xinyi_tech.commonlibs.widget.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xinyi_tech.commonlibs.R;
import com.xinyi_tech.commonlibs.util.StringUtil;
import com.xinyi_tech.commonlibs.util.UI;

/**
 * Created by studyjun on 2016/3/11.
 */
public class TextEditField extends EditText implements IFormField {

    private String name;
    private boolean must;
    private String prefix="";
    private String warnText2;
    private String warnText;

    private int maxLength;
    private int minLength;
    private int fixedLength;
    private String overLengthWarn = "当前输入内容已超过限制";
    private String lessLengthWarn = "输入内容过短";
    private String fixedLengthWarn = "长度不符合";
    private Bundle extraInfo;


    private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                if (maxLength > 0 && maxLength < getText().toString().length()) {
                    Toast.makeText(getContext(), overLengthWarn, Toast.LENGTH_SHORT);
                }
                if (minLength > 0 && minLength > getText().toString().length()) {
                    Toast.makeText(getContext(), lessLengthWarn, Toast.LENGTH_SHORT);
                }
                if (fixedLength > 0 && fixedLength != getText().toString().length()) {
                    Toast.makeText(getContext(), fixedLengthWarn, Toast.LENGTH_SHORT);
                }
            }
        }
    };

    public TextEditField(Context context) {
        super(context);
    }

    public TextEditField(Context context, String name) {
        super(context);
        this.name = name;
    }

    public TextEditField(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextEditField);
        must = ta.getBoolean(R.styleable.TextEditField_must, false);
        warnText2 = ta.getString(R.styleable.TextEditField_warnText);
        name = ta.getString(R.styleable.TextEditField_name);
        maxLength = ta.getInt(R.styleable.TextEditField_maxLength, 0);
        minLength = ta.getInt(R.styleable.TextEditField_minLength, 0);
        fixedLength = ta.getInt(R.styleable.TextEditField_fixedLength, 0);
        overLengthWarn = ta.getString(R.styleable.TextEditField_overLengthWarn);
        lessLengthWarn = ta.getString(R.styleable.TextEditField_lessLengthWarn);
        fixedLengthWarn = ta.getString(R.styleable.TextEditField_fixedLengthWarn);
        ta.recycle();

        if (must) {
            Drawable drawable = getContext().getResources().getDrawable(R.mipmap.icon_must);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            setCompoundDrawables(null, null, drawable, null);
            int paddingRight = getPaddingRight();
            if (paddingRight == 0) {
                int paddingLeft = getPaddingLeft();
                int paddingTop = getPaddingTop();
                int pddingBottom = getPaddingBottom();
                setPadding(paddingLeft, paddingTop, UI.dip2px(getContext(), 8), pddingBottom);
            }
            if (!TextUtils.isEmpty(warnText)) {
                setError(warnText, drawable);
            }

        }

        if (maxLength > 0) {
            setMaxLength(maxLength);
        }
        if (minLength > 0) {
            setMinLength(minLength);
        }
        if (fixedLength > 0) {
            setFixedLength(fixedLength);
        }

        setOnFocusChangeListener(focusChangeListener);

    }


    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isVaild() {
        if (isMust()) {
            if (StringUtil.isEmpty(getText().toString())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isMust() {
        return isShown()?must:false;
    }

    @Override
    public String getValue() {
        if (!isShown())
            return null;
        if (maxLength > 0 && maxLength < getText().toString().length()) {
            warnText=overLengthWarn;
            return null;
        }
        if (minLength > 0 && minLength > getText().toString().length()) {
            if (isMust()&&getText().toString().length()==0){
                warnText=warnText2;
            } else {
                warnText=lessLengthWarn;
            }
            return null;
        }
        if (fixedLength > 0 && fixedLength != getText().toString().length()) {
            if (isMust()&&getText().toString().length()==0){
                warnText=warnText2;
            } else {
                warnText=fixedLengthWarn;
            }
            return null;
        }
        warnText=warnText2;
        return TextUtils.isEmpty(prefix)?getText().toString():prefix+getText().toString();
    }

    @Override
    public String warning() {
        return warnText;
    }


    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        setFilters(new InputFilter[]{new MyLengthFilter(maxLength)});
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public void setFixedLength(int fixedLength) {
        this.fixedLength = fixedLength;
    }

    @Override
    public void setVaule(String value) {
        setText(value);
    }

    @Override
    public void setExtraInfo(Bundle bundle) {
        this.extraInfo= bundle;
    }

    public void setMust(boolean must) {
        this.must = must;
    }

    /**
     * This filter will constrain edits not to make the length of the text
     * greater than the specified length.
     */
    public class MyLengthFilter implements InputFilter {
        private final int mMax;

        public MyLengthFilter(int max) {
            mMax = max;
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {
            int keep = mMax - (dest.length() - (dend - dstart));
            if (keep <= 0) {
                Toast.makeText(getContext(), overLengthWarn, Toast.LENGTH_SHORT).show();
                return "";
            } else if (keep >= end - start) {
                return null; // keep original
            } else {
                Toast.makeText(getContext(), overLengthWarn, Toast.LENGTH_SHORT).show();
                keep += start;
                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                    --keep;
                    if (keep == start) {
                        return "";
                    }
                }
                return source.subSequence(start, keep);
            }
        }

        /**
         * @return the maximum length enforced by this input filter
         */
        public int getMax() {
            return mMax;
        }
    }

    @Override
    public Bundle getExtraInfo() {
        return extraInfo;
    }
}
