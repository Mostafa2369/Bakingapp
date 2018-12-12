package com.example.mostafa.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsListFragment extends Fragment implements DetailsListAdapter.DetailsListAdapterOnClickHandler {
    private RecipeList mList;
     int pos;
    @BindView(R.id.recipe) TextView recipe;
    @BindView(R.id.ingred) TextView ingred;
    @BindView(R.id.Recipe_recycle) RecyclerView  mView ;
    public DetailsListFragment() {
    }


    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(String[] list);

        void onNumSelected(int position);

        void onListSize(int size);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public void setList(RecipeList data) {
        mList = data;

    }

    Context con = getContext();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mList = savedInstanceState.getParcelable("list");

        }

        String[] dat = mList.getIngredients();


        View rootView = inflater.inflate(R.layout.fragment_details_list, container, false);

        ButterKnife.bind(this, rootView);
        String name =mList.getRecipeName();
       if(pos==2)
         recipe.setText("Brownies");
       else
            recipe.setText(mList.getRecipeName());
        for (int i = 0; i < dat.length; ++i) {
            String[] c = dat[i].split("@");
            ingred.append(c[0] + " " + c[1] + " of " + c[2] + "\n");

        }

        mView.setLayoutManager(new LinearLayoutManager(con));
        DetailsListAdapter mAdapter = new DetailsListAdapter(this);
        mAdapter.getData(mList.getRecipeSteps());
        mView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable("list", mList);

    }
public void setpostion(int p)
    {
        pos=p;
    }
    @Override
    public void onClick(int hello) {
        mCallback.onNumSelected(hello);
        mCallback.onStepSelected(mList.getRecipeSteps());
        mCallback.onListSize(mList.getRecipeSteps().length);
    }
}
