package com.example.mostafa.bakingapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterListAdapter extends ArrayAdapter<RecipeList> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<RecipeList> data;

    public MasterListAdapter(Context context, int layoutResourceId, ArrayList<RecipeList> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    static class ViewHolder {
        @BindView(R.id.recipe_name) TextView mRecipeName;
        @BindView(R.id.surving) TextView mServing;
        @BindView(R.id.recipeImageView) ImageView mImage;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        RecipeList currentRecipe = data.get(position);

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.mRecipeName.setText(currentRecipe.getRecipeName());
        holder.mServing.setText("Serving: " + currentRecipe.getNumOfServing());
        if (!currentRecipe.getRecipeImage().equals("")) {
            Picasso.get().load(currentRecipe.getRecipeImage()).error(R.drawable.no_image).into(holder.mImage);
        }
        return convertView;
    }

}
