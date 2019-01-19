package rohanbomle.codingexercise;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.widget.Toast;
import org.junit.Rule;
import org.junit.Test;
import rohanbomle.codingexercise.common.Base;
import rohanbomle.codingexercise.database.DatabaseHelper;
import rohanbomle.codingexercise.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.not;



public class MainActivityTest {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickOnlineMusic(){
        Intent nextIntent = new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class);
        nextIntent.putExtra(Base.ONLINE_MUSIC, true);
        nextIntent.putExtra(Base.OFFLINE_MUSIC, false);
        mActivityRule.launchActivity(nextIntent);
    }

    @Test
    public void clickOfflineMusic(){
        Intent nextIntent = new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class);
        nextIntent.putExtra(Base.ONLINE_MUSIC, false);
        nextIntent.putExtra(Base.OFFLINE_MUSIC, true);
        mActivityRule.launchActivity(nextIntent);
    }

    @Test
    public void populateAlbumListFromDatabase(){
        onView(withId(R.id.btnToggleSort)).perform(click());
    }

    @Test
    public void sortToggleAlbumList(){
        onView(withId(R.id.btnToggleSort)).perform(click());
        onView(withId(R.id.btnToggleSort)).perform(click());
        onView(withId(R.id.btnToggleSort)).perform(click());
    }

    @Test
    public void checkInternetConnection(){
        Context context = InstrumentationRegistry.getTargetContext();
        if(!Base.isConnectedToInternet(context)){
            Toast.makeText(context, R.string.no_internet_error, Toast.LENGTH_SHORT).show();
        }else{
            clickOnlineMusic();
        }
    }
}
