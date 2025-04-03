package com.example.labtestappfront;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.example.labtestappfront.TestRecordRVAdapter.clickChildViewWithId;

import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSearchButtonClickedWithNonExistingID() {
        onView(withId(R.id.etSearch)).perform(typeText("$"));
        onView(withId(R.id.btnSearch)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Toast should appear
        activityRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
            TestRecordRVAdapter adapter = (TestRecordRVAdapter) recyclerView.getAdapter();

            Assert.assertEquals("RecyclerView should have 0 items for an invalid ID", 0, adapter.getItemCount());
        });
    }

    @Test
    public void testSearchButtonClickedWithWrongID() {
        onView(withId(R.id.etSearch)).perform(typeText("12345"));
        onView(withId(R.id.btnSearch)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        activityRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
            TestRecordRVAdapter adapter = (TestRecordRVAdapter) recyclerView.getAdapter();

            Assert.assertEquals("RecyclerView should have 0 items for an invalid ID", 0, adapter.getItemCount());
        });
    }

    @Test
    public void testSearchButtonClickedWithGoodId() {
        onView(withId(R.id.etSearch)).perform(typeText("2"));
        onView(withId(R.id.btnSearch)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        activityRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = null;
            while (recyclerView == null) {
                recyclerView = activity.findViewById(R.id.recyclerView);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            TestRecordRVAdapter adapter = (TestRecordRVAdapter) recyclerView.getAdapter();
            Assert.assertEquals(1, adapter.getItemCount());
        });


    }

    @Test
    public void testSearchButtonEmptyInput() {
        onView(withId(R.id.etSearch)).perform(typeText(""));
        onView(withId(R.id.btnSearch)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        activityRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = null;
            TestRecordRVAdapter adapter = null;
            while (adapter == null) {
                recyclerView = activity.findViewById(R.id.recyclerView);
                adapter = (TestRecordRVAdapter) recyclerView.getAdapter();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            Log.d("TestLog", "Adapter item count: " + adapter.getItemCount());
            Assert.assertTrue(adapter.getItemCount() >= 1);
        });
    }

    @Test
    public void testSpinnerSelectionPatientNameReversed() {
        onView(withId(R.id.etSearch)).perform(typeText(""));
        onView(withId(R.id.btnSearch)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.spinnerSort)).perform(click());
        onView(withText("Patient Name (Z-A)")).perform(click());
        onView(withId(R.id.spinnerSort)).check(matches(withSpinnerText("Patient Name (Z-A)")));
    }

    @Test
    public void testDeleteItemFromRecyclerview() {
        onView(withId(R.id.btnSearch)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        AtomicInteger count = new AtomicInteger();
        activityRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = null;
            TestRecordRVAdapter adapter = null;
            while (adapter == null) {
                recyclerView = activity.findViewById(R.id.recyclerView);
                adapter = (TestRecordRVAdapter) recyclerView.getAdapter();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            count.set(adapter.getItemCount());
            Assert.assertTrue(adapter.getItemCount() >= 1);
        });

        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.btnDelete)));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        activityRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = null;
            TestRecordRVAdapter adapter = null;
            while (adapter == null) {
                recyclerView = activity.findViewById(R.id.recyclerView);
                adapter = (TestRecordRVAdapter) recyclerView.getAdapter();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Assert.assertTrue(adapter.getItemCount() < count.get());
        });



    }
}
