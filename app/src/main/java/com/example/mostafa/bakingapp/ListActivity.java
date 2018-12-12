package com.example.mostafa.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements DetailsListFragment.OnStepClickListener {
    private int num = 0;
    private int last;
    private String[] mStep;
    @BindView (R.id.step_number) TextView stepNum;

    @BindView (R.id.prev_step) ImageButton prev;
    @BindView (R.id.next_step) ImageButton next;

    String [] mList;

    @BindView (R.id.navi_bar) RelativeLayout bar;
    private int sizee = 0;
    private boolean inTab = false;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("list", mStep);
        outState.putInt("num", num);
        outState.putStringArray("lists",mList);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);




        if (findViewById(R.id.istab) != null) {
            inTab = true;

            bar.setVisibility(View.INVISIBLE);
        }



        Intent intent = getIntent();
        RecipeList l = intent.getParcelableExtra("recupe");
        int pos= intent.getIntExtra("pos",0);
        DetailsListFragment detail = new DetailsListFragment();
        detail.setList(l);
        detail.setpostion(pos);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details, detail)
                .commit();
        if (savedInstanceState != null && inTab) {
            mStep = savedInstanceState.getStringArray("list");
            num = savedInstanceState.getInt("num");
            mList=savedInstanceState.getStringArray("lists");
            bar.setVisibility(View.VISIBLE);
            ButterKnife.bind(this);
            if (mStep != null)
                last = mStep.length;
            if (mStep != null&&mStep!=mList) {
                Log.d("ListActivity","creat");
                StepFragment step = new StepFragment();
                step.setStep(mStep);
                step.setNum(num);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.steps, step)
                        .commit();

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

    public void onNumSelected(int position) {
        num = position;

    }


    public void onListSize(int size) {
        sizee = size;
    }

    public void onStepSelected(String[] position) {
        if (!inTab) {
            Intent intent = new Intent(ListActivity.this, StepActivity.class);
            intent.putExtra("step", position);

            intent.putExtra("num", num);
            intent.putExtra("size", sizee);


            startActivity(intent);
        } else {
            mList=position;
            mStep = position;
            if (mStep != null) {
                num += 1;

                StepFragment step = new StepFragment();
                step.setStep(mStep);
                step.setNum(num);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.steps, step)
                        .commit();

                bar.setVisibility(View.VISIBLE);
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


}







