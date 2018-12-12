package com.example.mostafa.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

public class PrefIntentService extends IntentService {
    public static final String READ_PREFRENCES = "Read Perefrences";

    public PrefIntentService() {
        super("PrefIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.getAction().equals(READ_PREFRENCES)) {
            getPreferences();
        }
    }

    public static void startIntentService(Context context) {
        Intent intent = new Intent(context, PrefIntentService.class);
        intent.setAction(READ_PREFRENCES);
        context.startService(intent);
    }

    private void getPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("RecipesList", Context.MODE_PRIVATE);
        String listOfIngredients = sharedPref.getString("ings", null);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
        BakingAppWidget.updateAllWidgets(this, appWidgetManager, appWidgetIds, listOfIngredients);
    }

}
