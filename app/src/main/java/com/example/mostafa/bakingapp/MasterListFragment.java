package com.example.mostafa.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MasterListFragment extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private RecipeList[] mRecipeList = null;

    private boolean isConnected;
    private FetchData mFetchData = new FetchData();
    @BindView(R.id.recipe_grid_view)
    GridView gridview;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray("list", mRecipeList);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createSharedPreference();
        addToSharedPreference("hello");
        if (savedInstanceState != null) {
            mRecipeList = (RecipeList[]) savedInstanceState.getParcelableArray("list");
        }

        // Get a reference to the GridView in the fragment_master_list xml layout file
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            URL url = null;
            try {
                url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            new getRecipeData().execute(url);

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int positionn, long id) {
                   RecipeList position= mRecipeList[positionn];

                    Intent intent = new Intent(MasterListFragment.this, ListActivity.class);
                    intent.putExtra("recupe", position);
                    intent.putExtra("pos",positionn);
                    String name = position.getRecipeName();
                    String ingred = "";


                    for (int i = 0; i < position.getIngredients().length; i++) {
                        String[] part = position.getIngredients()[i].split("@");
                        ingred += part[0] + " " + part[1] + " of " + part[2] + "\n";

                    }
                    String listOfIngredients = name + " Ingredients:\n\n" + ingred;

                    SharedPreferences settings = MasterListFragment.this.getSharedPreferences("RecipesList", MasterListFragment.this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("ings", listOfIngredients);
                    editor.commit();
                    PrefIntentService.startIntentService(MasterListFragment.this);

                    startActivity(intent);






                }
            });
        } else {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_LONG).show();

        }


    }

    public void createSharedPreference() {
        sharedPreferences = this.getSharedPreferences("RecipesList", Context.MODE_PRIVATE);
    }


    public void addToSharedPreference(String list) {
        editor = sharedPreferences.edit();

        editor.putString("recipes", list);
        editor.commit();
        PrefIntentService.startIntentService(MasterListFragment.this);

    }
    public class getRecipeData extends AsyncTask<URL, Void, RecipeList[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected RecipeList[] doInBackground(URL... urls) {
            URL link = urls[0];
            try {
                mRecipeList = mFetchData.getData(mFetchData.getJsonStr(link));

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mRecipeList;
        }

        @Override
        protected void onPostExecute(RecipeList[] Recipe) {
            ArrayList<RecipeList> List = new ArrayList<RecipeList>(Arrays.asList(Recipe));
            MasterListAdapter adapter = new MasterListAdapter(MasterListFragment.this, R.layout.grid_item, List);

            gridview.setAdapter(adapter);


        }
    }
}
