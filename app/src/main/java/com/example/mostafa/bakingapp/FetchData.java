package com.example.mostafa.bakingapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class FetchData {
    private HttpURLConnection urlConnection = null;


    public String getJsonStr(URL Url) throws IOException {
        URL url = Url;

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner mScan = new Scanner(inputStream);
            mScan.useDelimiter("\\A");
            boolean hasIn = mScan.hasNext();
            if (hasIn)
                return mScan.next();
            else
                return null;
        } finally {


            urlConnection.disconnect();
        }
    }

    public RecipeList[] getData(String JsonStr) throws JSONException {
        final String RESULTS = "results";
        String[] ingredients;
        String[] steps;
        JSONArray recipeRoot = new JSONArray(JsonStr);

        RecipeList[] mRecipeList = new RecipeList[recipeRoot.length()];
        for (int i = 0; i < recipeRoot.length(); i++) {

            JSONObject recipeInfo = recipeRoot.getJSONObject(i);
            mRecipeList[i] = new RecipeList();

            mRecipeList[i].setRecipeName(recipeInfo.getString("name"));
            mRecipeList[i].setNumOfServing("" + recipeInfo.getInt("servings"));
            mRecipeList[i].setRecipeImage(recipeInfo.getString("image"));
            JSONArray ingredientsList = recipeInfo.getJSONArray("ingredients");
            ingredients = new String[ingredientsList.length()];
            for (int j = 0; j < ingredientsList.length(); ++j) {
                JSONObject ingredOject = ingredientsList.getJSONObject(j);
                ingredients[j] = "" + ingredOject.getDouble("quantity") + "@"
                        + ingredOject.getString("measure") + "@"
                        + ingredOject.getString("ingredient");
            }
            mRecipeList[i].setIngredients(ingredients);

            JSONArray stepsList = recipeInfo.getJSONArray("steps");
            steps = new String[stepsList.length()];
            for (int e = 0; e < stepsList.length(); ++e) {
                JSONObject ingredOject2 = stepsList.getJSONObject(e);
                steps[e] = ingredOject2.getString("shortDescription") + "@"
                        + ingredOject2.getString("description") + "@"
                        + ingredOject2.getString("videoURL") + "@"
                        + ingredOject2.getString("thumbnailURL");
            }
            mRecipeList[i].setRecipeSteps(steps);
        }


        return mRecipeList;
    }
}
