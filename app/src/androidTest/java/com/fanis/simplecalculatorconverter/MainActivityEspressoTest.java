package com.fanis.simplecalculatorconverter;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.fanis.simplecalculatorconverter.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkCalculatorAndStringNamesExist() {
        onView(withText("0")).check(matches(isDisplayed()));
        onView(withText("1")).check(matches(isDisplayed()));
        onView(withText("2")).check(matches(isDisplayed()));
        onView(withText("3")).check(matches(isDisplayed()));
        onView(withText("4")).check(matches(isDisplayed()));
        onView(withText("5")).check(matches(isDisplayed()));
        onView(withText("6")).check(matches(isDisplayed()));
        onView(withText("7")).check(matches(isDisplayed()));
        onView(withText("8")).check(matches(isDisplayed()));
        onView(withText("9")).check(matches(isDisplayed()));
        onView(withText("+")).check(matches(isDisplayed()));
        onView(withText("-")).check(matches(isDisplayed()));
        onView(withText("*")).check(matches(isDisplayed()));
        onView(withText("/")).check(matches(isDisplayed()));
        onView(withText("Convert")).check(matches(isDisplayed()));
        onView(withText("Convert to:")).check(matches(isDisplayed()));
        onView(withText("From Euro")).check(matches(isDisplayed()));
        onView(withText("Retrieve Latest Currencies")).check(matches(isDisplayed()));

    }

    @Test
    public void simpleClickOnButtonUpdatesInputNumberTextView() {
        onView(withId(R.id.bt_one))
                .perform(click())
                .check(matches(isDisplayed()));

        onView(withId(R.id.tv_input_number)).check(matches(withText("1")));

    }

    @Test
    public void simpleAddOperationUpdatesTextView() {
        onView(withId(R.id.bt_one))
                .perform(click());

        onView(withId(R.id.bt_zero))
                .perform(click());

        onView(withId(R.id.bt_add))
                .perform(click());

        onView(withId(R.id.bt_five))
                .perform(click());

        onView(withId(R.id.bt_equal))
                .perform(click());

        onView(withId(R.id.tv_input_number)).check(matches(withText("15.0")));

    }

    @Test
    public void simpleSubtractOperationUpdatesTextView() {

        onView(withId(R.id.bt_five))
                .perform(click());

        onView(withId(R.id.bt_subtract))
                .perform(click());

        onView(withId(R.id.bt_one))
                .perform(click());

        onView(withId(R.id.bt_equal))
                .perform(click());

        onView(withId(R.id.tv_input_number)).check(matches(withText("4.0")));

    }

    @Test
    public void simpleMultiplyOperationUpdatesTextView() {

        onView(withId(R.id.bt_five))
                .perform(click());

        onView(withId(R.id.bt_multiply))
                .perform(click());

        onView(withId(R.id.bt_two))
                .perform(click());

        onView(withId(R.id.bt_equal))
                .perform(click());

        onView(withId(R.id.tv_input_number)).check(matches(withText("10.0")));

    }

    @Test
    public void simpleDivideOperationUpdatesTextView() {

        onView(withId(R.id.bt_one))
                .perform(click());

        onView(withId(R.id.bt_two))
                .perform(click());

        onView(withId(R.id.bt_divide))
                .perform(click());

        onView(withId(R.id.bt_two))
                .perform(click());

        onView(withId(R.id.bt_equal))
                .perform(click());

        onView(withId(R.id.tv_input_number)).check(matches(withText("6.0")));

    }

    @Test
    public void simpleDivideByZeroOperationUpdatesTextView() {

        onView(withId(R.id.bt_one))
                .perform(click());

        onView(withId(R.id.bt_two))
                .perform(click());

        onView(withId(R.id.bt_divide))
                .perform(click());

        onView(withId(R.id.bt_zero))
                .perform(click());

        onView(withId(R.id.bt_equal))
                .perform(click());

        onView(withId(R.id.tv_input_number)).check(matches(withText("Error")));

    }

}