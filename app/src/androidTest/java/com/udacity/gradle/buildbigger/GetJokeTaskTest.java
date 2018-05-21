package com.udacity.gradle.buildbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.Service;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import com.icerrate.jokemodule.JokerActivity;
import com.udacity.gradle.builditbigger.GetJokeAsyncTask;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Ivan Cerrate.
 */
@RunWith(AndroidJUnit4.class)
public class GetJokeTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGetJokeAsyncTask() throws InterruptedException {
        activityTestRule.launchActivity(null);
        onView(withId(R.id.tell)).perform(click());
        final CountDownLatch latch = new CountDownLatch(1);
        GetJokeAsyncTask testTask = new GetJokeAsyncTask(activityTestRule.getActivity()) {
            @Override
            protected void onPostExecute(String result) {
                assertNotNull(result);
                assertTrue(result.length() > 0);
                latch.countDown();
            }
        };
        testTask.execute();
        latch.await();
    }
}
