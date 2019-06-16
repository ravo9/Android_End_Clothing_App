package development.dreamcatcher.endclothingapp;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import development.dreamcatcher.endclothingapp.features.feed.FeedActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FeedViewTest {

    @Rule
    public ActivityTestRule<FeedActivity> feedActivityTestRule =
            new ActivityTestRule<>(FeedActivity.class);

    @Test
    public void clickOnFilterButton_opensSideDrawer() throws Exception {

        // Click on the Filter button
        onView(withId(R.id.textView_filter)).perform(ViewActions.click());

        // Verify if the Side Drawer has been displayed
        onView(withId(R.id.side_drawer_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnCloseButton_closesSideDrawer() throws Exception {

        // Click on the Filter button
        onView(withId(R.id.textView_filter)).perform(ViewActions.click());

        // Click on the Close button.
        onView(withId(R.id.close_btn)).perform(ViewActions.click());

        // Verify if detailed view has been closed.
        onView(withId(R.id.side_drawer_layout)).check(doesNotExist());
    }
}