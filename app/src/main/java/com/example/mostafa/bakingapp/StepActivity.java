package com.example.mostafa.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {

    private int last;
    private int size;
    private int num;
    private String[] mStep;
    private String[] mList;
    @BindView(R.id.step_number) TextView stepNum;

    @BindView(R.id.prev_step) ImageButton prev;
    @BindView(R.id.next_step) ImageButton next;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("list", num);
        outState.putStringArray("l",mStep);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_activity);
        ButterKnife.bind(this);
        Log.d("StepActivity","on c");
        Intent intent = getIntent();
        mStep = intent.getStringArrayExtra("step");
        num = intent.getIntExtra("num", 0);
        if (savedInstanceState != null)
            num = savedInstanceState.getInt("list");
        if (savedInstanceState != null)
            mList=savedInstanceState.getStringArray("l");
        if (mStep != null&&mStep!=mList) {
            num += 1;
            size = intent.getIntExtra("size", 0);

            StepFragment step = new StepFragment();
            step.setStep(mStep);
            step.setNum(num);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.steps, step)
                    .commit();
            if (mStep != null)
                last = mStep.length;
            else
                last = 10;
        }

        if (num == 1)
            prev.setVisibility(View.GONE);
        else
            prev.setVisibility(View.VISIBLE);
        if (num == last)
            next.setVisibility(View.GONE);
        else
            next.setVisibility(View.VISIBLE);
        stepNum.setText("" + num);
        next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                num += 1;
                stepNum.setText("" + num);
                if (mStep != null) {
                    StepFragment step = new StepFragment();
                    step.setStep(mStep);
                    step.setNum(num);
                    Log.d("StepActivity","on save frag2");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.steps, step)
                            .commit();
                    if (num == 1)
                        prev.setVisibility(View.GONE);
                    else
                        prev.setVisibility(View.VISIBLE);
                    if (num == last)
                        next.setVisibility(View.GONE);
                    else
                        next.setVisibility(View.VISIBLE);
                }

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num--;
                stepNum.setText("" + num);
                StepFragment step = new StepFragment();
                step.setStep(mStep);
                step.setNum(num);
                Log.d("StepActivity","on save frag3");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.steps, step)
                        .commit();
                if (num == 1)
                    prev.setVisibility(View.GONE);
                else
                    prev.setVisibility(View.VISIBLE);
                if (num == last)
                    next.setVisibility(View.GONE);
                else
                    next.setVisibility(View.VISIBLE);
            }
        });


    }

}
