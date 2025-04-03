package com.example.labtestappfront;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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
