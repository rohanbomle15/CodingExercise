package rohanbomle.codingexercise;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.not;


import rohanbomle.codingexercise.database.DatabaseHelper;
import rohanbomle.codingexercise.view.SplashActivity;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;

    @Rule
    public ActivityTestRule<SplashActivity> mActivityRule = new ActivityTestRule<>(SplashActivity.class);


    @Test
    public void loadSplashScreen(){
        Intent nextIntent = new Intent(InstrumentationRegistry.getTargetContext(), SplashActivity.class);
        mActivityRule.launchActivity(nextIntent);
    }

    @Test
    public void ensureOfflineButtonDisabled(){
        mDBHelper = new DatabaseHelper(InstrumentationRegistry.getTargetContext());
        mDatabase = mDBHelper.getReadableDatabase();
        if(!mDBHelper.isAlbumDetailsAvailable(mDatabase)){
            onView(withId(R.id.btnOfflineMusic)).check(matches(not(isEnabled())));
        }
    }

    @Test
    public void ensureOfflineButtonEnabled(){
        mDBHelper = new DatabaseHelper(InstrumentationRegistry.getTargetContext());
        mDatabase = mDBHelper.getReadableDatabase();
        if(mDBHelper.isAlbumDetailsAvailable(mDatabase)){
            onView(withId(R.id.btnOfflineMusic)).check(matches(isEnabled()));
        }
    }



}
