package com.example.mostafa.bakingapp;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    public static final String name="Brownies";
    @Rule
    public ActivityTestRule<MasterListFragment> mActivityTestRule = new ActivityTestRule<>(MasterListFragment.class);




    @Test
    public void textViewTest() {

        onView(withId(R.id.text)).check(matches(withText("Baking App")));

    }
    @Test
    public void clickGridViewItem_OpensOrderActivity() {

        onData(anything()).inAdapterView(withId(R.id.recipe_grid_view)).atPosition(2).perform(click());


        onView(withId(R.id.recipe)).check(matches(withText(name)));


    }



}
