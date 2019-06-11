package development.dreamcatcher.endclothingapp;

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
    public void clickOnListedArticle_opensDetailedView() throws Exception {

        // Click on the first article.
        onView(withId(R.id.recyclerView_products))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Verify if detailed view has been displayed.
        onView(withId(R.id.detailed_view_container)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnCrossButton_closesDetailedView() throws Exception {

        // Click on the first article.
        onView(withId(R.id.recyclerView_products))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click on the Cross button.
        onView(withId(R.id.btn_cross)).perform(ViewActions.click());

        // Verify if detailed view has been closed.
        onView(withId(R.id.detailed_view_container)).check(doesNotExist());
    }
}