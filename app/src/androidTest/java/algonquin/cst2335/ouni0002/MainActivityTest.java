package algonquin.cst2335.ouni0002;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Testing the initial test
     */
    @Test
    public void mainActivityTest() {
        //find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in 12345
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check th text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Testing missing upper case
     */
    @Test
    public void testFindMissingUpperCase() {
        //find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("password123#$*"), closeSoftKeyboard());
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check th text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Testing missing lower case
     */
    @Test
    public void testFindMissingLowerCase() {
        //find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in PASSWORD123#$*
        appCompatEditText.perform(replaceText("PASSWORD123#$*"), closeSoftKeyboard());
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check th text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Testing missing number
     */
    @Test
    public void testFindMissingNumber() {
        //find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in PASSword#$*
        appCompatEditText.perform(replaceText("PASSword#$*"), closeSoftKeyboard());
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check th text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Testing missing special character
     */
    @Test
    public void testFindMissingSpecialCharacter() {
        //find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in PASSword123
        appCompatEditText.perform(replaceText("PASSword123"), closeSoftKeyboard());
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check th text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Testing valid password
     */
    @Test
    public void testComplexity() {
        //find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in PASSword123#$*
        appCompatEditText.perform(replaceText("PASSword123#$*"), closeSoftKeyboard());
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check th text
        textView.check(matches(withText("Your password meets the requirements")));
    }





    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
