package com.me.defaultvaluespinner.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.me.defaultvaluespinner.R;

/**
 * Created by WENCHAO.
 */
public class DefaultValueSpinner extends RelativeLayout implements View.OnClickListener{

    private SameSelectionSpinner mSpinner;
    private onSpinnerItemSelectedListener mListener;
    private DefaultSpinnerSelection mDefaultValue;
    private LayoutParams mParams;
    private boolean mIsSelected = false;

    public DefaultValueSpinner(Context c, AttributeSet attrs){
        super(c, attrs);
        init(c);
    }

    private void init(Context c) {
        mSpinner = new SameSelectionSpinner(c, Spinner.MODE_DROPDOWN);
        mDefaultValue = new DefaultSpinnerSelection(c);
        mParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mSpinner.setLayoutParams(mParams);
        mDefaultValue.setLayoutParams(mParams);
        addView(mDefaultValue);
        addView(mSpinner);
        mDefaultValue.setOnClickListener(this);
    }


    public DefaultValueSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, android.R.attr.spinnerStyle);
        init(context);
    }

    public void setAdapter(SpinnerAdapter adapter){
        mSpinner.setAdapter(adapter);
        mSpinner.setVisibility(View.INVISIBLE);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mListener == null)
                    throw new NullPointerException("onSpinnerItemSelectedListener is missing");
                else {
                    mListener.onItemSelected(parent, view, position, id);
                    mDefaultValue.setVisibility(View.INVISIBLE);
                    mSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if(mListener == null)
                    throw new NullPointerException("onSpinnerItemSelectedListener is missing");
                else
                    mListener.onNothingSelected(parent);
            }
        });
    }

    public void setOnItemSelectedListener(onSpinnerItemSelectedListener listener){
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        mSpinner.performClick();
    }

    public interface onSpinnerItemSelectedListener extends AdapterView.OnItemSelectedListener{

    }

    class DefaultSpinnerSelection extends TextView{

        public DefaultSpinnerSelection(Context context){
            super(context, null, android.R.attr.spinnerStyle);
            setText(R.string.please_select);
        }

        public DefaultSpinnerSelection(Context c, AttributeSet attrs){
            super(c, attrs, android.R.attr.spinnerStyle);
        }


        public DefaultSpinnerSelection(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, android.R.attr.spinnerStyle);
        }
    }

    class SameSelectionSpinner extends Spinner{
        private OnItemSelectedListener mListener;
        public SameSelectionSpinner(Context context, AttributeSet attrs)
        {
            super(context, attrs);
        }

        public SameSelectionSpinner(Context context){
            super(context);
        }

        public SameSelectionSpinner(Context context, int mode){
            super(context, mode);
        }

        @Override
        public void setSelection(int position){
            super.setSelection(position);
            //prevent the default selection of the spinner
            //also allows user to select the previous value again.
            if (position == getSelectedItemPosition() && mListener != null){
                mListener.onItemSelected(null, null, position, 0);
            }
        }

        public void setOnItemSelectedListener(OnItemSelectedListener listener){
            this.mListener = listener;
        }
    }
}
