package com.example.mostafa.bakingapp;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StepActivityTest {


    @Rule
    public ActivityTestRule<StepActivity> mActivityTestRule =
            new ActivityTestRule<>(StepActivity.class);

    @Test
    public void clickDecrementButton_ChangesQuantityAndCost() {

        // Check the initial quantity variable is zero
        onView((withId(R.id.step_number))).check(matches(withText("0")));





    }





}
