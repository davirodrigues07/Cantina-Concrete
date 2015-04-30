package br.com.concretesolutions.br.cantina.instrumentation.activity;

import android.provider.MediaStore;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.concretesolutions.br.cantina.instrumentation.lib.ActivityRule;
import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.ui.activity.RegisterProductActivity_;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RegisterProductActivityTest {
    @Rule
    public final ActivityRule<RegisterProductActivity_>
            main = new ActivityRule<>(RegisterProductActivity_.class);


    @Test
    public void saveInputsEmpty() {
        onView(withId(R.id.save)).perform(click());
    }

    @Test
    public void saveNotPicture(){
        onView(withId(R.id.name)).perform(typeText("Banana"));
        onView(withId(R.id.price)).perform(typeText("3.50"));
        onView(withId(R.id.amount)).perform(typeText("2"));
        onView(withId(R.id.save)).perform(click());
    }

    @Test
    public void saveOk(){
        onView(withId(R.id.name)).perform(typeText("Banana"));
        onView(withId(R.id.price)).perform(typeText("3.50"));
        onView(withId(R.id.amount)).perform(typeText("2"));
        onView(withId(R.id.save)).perform(click());
    }
}
