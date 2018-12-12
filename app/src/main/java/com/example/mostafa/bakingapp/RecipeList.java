package com.example.mostafa.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeList implements Parcelable {

    private String recipeName;
    private String recipeImage;
    private String numOfServing;
    private String[] ingredients;
    private String[] recipeSteps;

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = new String[ingredients.length];
        for (int i = 0; i < ingredients.length; ++i) {
            this.ingredients[i] = ingredients[i];
        }
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public void setRecipeSteps(String[] recipeSteps) {
        this.recipeSteps = new String[recipeSteps.length];
        for (int i = 0; i < recipeSteps.length; ++i) {
            this.recipeSteps[i] = recipeSteps[i];
        }
    }

    public void setNumOfServing(String numOfServing) {
        this.numOfServing = numOfServing;
    }

    public String getNumOfServing() {
        return numOfServing;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String[] getRecipeSteps() {
        return recipeSteps;
    }

    public RecipeList() {
    }

    private RecipeList(Parcel in) {
        recipeName = in.readString();
        recipeImage=in.readString();
        numOfServing = in.readString();
        ingredients = in.createStringArray();
        recipeSteps = in.createStringArray();
    }

    public static final Parcelable.Creator<RecipeList> CREATOR = new Parcelable.Creator<RecipeList>() {
        @Override
        public RecipeList createFromParcel(Parcel in) {
            return new RecipeList(in);
        }

        @Override
        public RecipeList[] newArray(int size) {
            return new RecipeList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeName);
        dest.writeString(recipeImage);
        dest.writeString(numOfServing);
        dest.writeStringArray(ingredients);
        dest.writeStringArray(recipeSteps);
    }
}
