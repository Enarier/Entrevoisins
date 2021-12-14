
package com.openclassrooms.entrevoisins.neighbour_list;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;


import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.action.ViewActions.swipeLeft;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;

import static androidx.test.espresso.matcher.ViewMatchers.hasContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

import android.view.View;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private NeighbourApiService mApiService = DI.getNeighbourApiService();

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least one item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF")))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF")))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * Check recyclerview is in view and click on 9th neighbour in the list
     * Check that the detail activity shows up
     */

    @Test
    public void selectListItem_DetailActivityShowsUp() {
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).perform(actionOnItemAtPosition(8, click()));
        onView(withId(R.id.neighbourDetailLayout)).check(matches(isDisplayed()));
    }

    /**
     * Check recyclerview is in view and click on 9th neighbour in the list
     * Check that the selected neighbour appears with right info
     */
    @Test
    public void selectListItem_isDetailActivityVisibleWithRightData() {
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).perform(actionOnItemAtPosition(8, click()));
        onView(withId(R.id.neighbourDetailLayout)).check(matches(isDisplayed()));
        //Check info
        onView(withId(R.id.textViewName)).check(matches(withText(mApiService.getNeighbours().get(8).getName())));
        onView(withId(R.id.textViewAddress)).check(matches(withText(mApiService.getNeighbours().get(8).getAddress())));
        onView(withId(R.id.textViewPhone)).check(matches(withText(mApiService.getNeighbours().get(8).getPhoneNumber())));
        onView(withId(R.id.textViewAboutMe)).check(matches(withText(mApiService.getNeighbours().get(8).getAboutMe())));
    }

    /**
     *  Check if the favorite button displays on screen and changes it's color when it is clicked
     */
    @Test
    public void favButtonColorChange() {
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).perform(actionOnItemAtPosition(8, click()));
        onView(withId(R.id.neighbourDetailLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.floatingActionBtn)).check(matches(isDisplayed()));

        //Click favorite(star)button and check if it becomes yellow
        onView(withId(R.id.floatingActionBtn)).perform(click());
        onView(withId(R.id.floatingActionBtn)).check(matches(withTagValue(equalTo(R.drawable.ic_star_yellow_24dp))));
    }

    /**
     * Check that Favorite button clicked neighbour is added to favorite tab
     * Swipe screen to see favorite tab
     * Click on neighbor(in Favorite tab) to make sure that user can navigate to detail activity by this way also
     */
    @Test
    public void favoriteTabContainsFavButtonClickedNeighbour_navigateToDetailActivity () {
        mApiService.getNeighbourFavorite().clear();
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).perform(actionOnItemAtPosition(8, click()));
        onView(withId(R.id.neighbourDetailLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.floatingActionBtn)).perform(click());
        pressBack();
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).perform(actionOnItemAtPosition(9,click()));
        onView(withId(R.id.floatingActionBtn)).perform(click());
        pressBack();
        onView(withId(R.id.container)).perform(swipeLeft());
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("F"))).check(matches(hasMinimumChildCount(2)));
    }
    
    /**
     * Click on fav button to remove neighbour from favorite tab
     * Check if the favorite tab doesn't contain removed neighbour
     */
    @Test
    public void favButtonRemovesNeighbourFromFavoriteTab() {
        mApiService.getNeighbourFavorite().clear();
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("NF"))).perform(actionOnItemAtPosition(8, click()));
        onView(withId(R.id.neighbourDetailLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.floatingActionBtn)).perform(click());

        pressBack();
        onView((withContentDescription("Favorites"))).perform(click());
//        onView(withId(R.id.list_neighbours)).perform(swipeLeft());

        onView(allOf(withId(R.id.list_neighbours),withContentDescription("F"))).check(matches(hasMinimumChildCount(1)));
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("F"))).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.neighbourDetailLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.floatingActionBtn)).perform(click());

        pressBack();
        onView(allOf(withId(R.id.list_neighbours),withContentDescription("F"))).check(matches(hasMinimumChildCount(0)));
    }

}












