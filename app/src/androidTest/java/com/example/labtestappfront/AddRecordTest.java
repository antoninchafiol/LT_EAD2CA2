package com.example.labtestappfront;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static com.example.labtestappfront.TestRecordRVAdapter.clickChildViewWithId;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;
public class AddRecordTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testBackButton(){
        onView(withId(R.id.addTest)).perform(click());
        onView(withId(R.id.btnBack)).perform(click());
        onView(withId(R.id.addTest)).check(matches(isDisplayed()));
    }

    @Test
    public void testFillFormAndSubmit() {
        onView(withId(R.id.addTest)).perform(click());

        onView(withId(R.id.btnSubmit)).check(matches(isDisplayed()));

        onView(withId(R.id.etPatientName)).perform(typeText("John Doe"), closeSoftKeyboard());
        onView(withId(R.id.etTestType)).perform(typeText("Blood Test"), closeSoftKeyboard());
        onView(withId(R.id.etTestDate)).perform(typeText("2025-04-01"), closeSoftKeyboard());
        onView(withId(R.id.etResult)).perform(typeText("Positive"), closeSoftKeyboard());

        onView(withId(R.id.btnSubmit)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));

    }

    @Test
    public void testEmptyFormSubmit() {
        onView(withId(R.id.addTest)).perform(click());

        onView(withId(R.id.btnSubmit)).check(matches(isDisplayed()));


        onView(withId(R.id.btnSubmit)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.btnSubmit)).check(matches(isDisplayed()));
    }

    @Test
    public void testHalfFilledFormSubmit() {
        onView(withId(R.id.addTest)).perform(click());

        onView(withId(R.id.btnSubmit)).check(matches(isDisplayed()));

        onView(withId(R.id.etTestType)).perform(typeText("Blood Test"), closeSoftKeyboard());
        onView(withId(R.id.etTestDate)).perform(typeText("2025-04-01"), closeSoftKeyboard());

        onView(withId(R.id.btnSubmit)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.btnSubmit)).check(matches(isDisplayed()));
    }



}
