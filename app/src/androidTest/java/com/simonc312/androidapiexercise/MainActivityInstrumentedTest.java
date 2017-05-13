package com.simonc312.androidapiexercise;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.simonc312.androidapiexercise.espresso.OkHttpIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Simon on 5/10/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Inject
    OkHttpClient okHttpClient;

    @Rule
    public IntentsTestRule<MainActivity> activityTestRule = new IntentsTestRule<>(MainActivity.class);
    private IdlingResource idlingResource;

    @Before
    public void setUp() {
        TestApplication.getInstance().getFakeMainAppComponent().injectsMainActivityInstrumentedTest(this);
        idlingResource = new OkHttpIdlingResource(okHttpClient.dispatcher());
        Espresso.registerIdlingResources(idlingResource);
    }

    @After
    public void tearDown() {
        Espresso.unregisterIdlingResources(idlingResource);
        idlingResource = null;
    }

    @Test
    public void validateOpenUrlOnGuideClick() {
        onView(withId(R.id.activity_main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        intended(toPackage("com.android.chrome"));
    }

}
