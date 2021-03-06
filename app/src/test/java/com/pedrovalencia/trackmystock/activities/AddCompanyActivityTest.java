package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.pedrovalencia.trackmystock.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 18/10/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class AddCompanyActivityTest {

    private ActivityController activityController;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(AddCompanyActivity.class).create();
    }

    @After
    public void tearDown() throws Exception {
        activityController.destroy();
    }

    @Test
    public void testActivityNotNull() throws Exception {
        Activity activity = (Activity)activityController.get();
        assertTrue("Activity is null", activity != null);
    }

    @Test
    public void testElements() throws Exception {
        Activity activity = (Activity)activityController.get();

        //EditText
        AutoCompleteTextView textView = (AutoCompleteTextView)activity.findViewById(R.id.add_company_list_view);
        assertTrue("Edit Text is null",
                textView != null);
        assertTrue("Edit Text hint does not match: " + textView.getHint().toString(),
                textView.getHint().toString().equals("Company name"));
        assertTrue("Edit Text hint colour does not match: " + textView.getHintTextColors().getDefaultColor(),
                textView.getHintTextColors().getDefaultColor() == activity.getResources().getColor(R.color.grey_700));


        //Button
        Button button = (Button)activity.findViewById(R.id.add_company_button);
        assertTrue("Button element is null", button != null);
        assertTrue("Button text does not match: "+button.getText().toString(),
                button.getText().toString().equals(activity.getResources().getString(R.string.add_company_accept_button)));


    }

    @Test
    public void testSearchWithResults() throws Exception {
        Activity activity = (Activity)activityController.get();

        //EditText
        AutoCompleteTextView textView = (AutoCompleteTextView)activity.findViewById(R.id.add_company_list_view);
        //Try to get the list
        textView.setText("Goo");
        assertTrue("Company list size does not match: (10): "+textView.getAdapter().getCount()
                , textView.getAdapter().getCount() == 10);

    }

    @Test
    public void testSearchWithNoResults() throws Exception {
        Activity activity = (Activity)activityController.get();

        //EditText
        AutoCompleteTextView textView = (AutoCompleteTextView)activity.findViewById(R.id.add_company_list_view);
        //No result
        textView.setText("Eyb");
        assertTrue("Company list size does not match: (0): "+textView.getAdapter().getCount()
                , textView.getAdapter().getCount() == 0);
    }

    @Test
    public void testButtonIsDisabled() throws Exception {
        Activity activity = (Activity)activityController.get();

        //EditText
        AutoCompleteTextView textView = (AutoCompleteTextView)activity.findViewById(R.id.add_company_list_view);
        //Button
        Button button = (Button)activity.findViewById(R.id.add_company_button);

        //Test the button is disabled when no result
        textView.setText("");
        assertTrue("Button is not disabled", !button.isEnabled());

    }

    @Test
    public void testButtonIsEnabledWhenResults() throws Exception {
        Activity activity = (Activity)activityController.get();

        //EditText
        final AutoCompleteTextView textView = (AutoCompleteTextView)activity.findViewById(R.id.add_company_list_view);
        //Button
        Button button = (Button)activity.findViewById(R.id.add_company_button);

        //Test the button enabled when results
        textView.setText("Goo");
        AdapterView adapterView = new AdapterView(activity) {
            @Override
            public Adapter getAdapter() {
                return textView.getAdapter();
            }

            @Override
            public void setAdapter(Adapter adapter) {

            }

            @Override
            public View getSelectedView() {
                return null;
            }

            @Override
            public void setSelection(int i) {

            }
        };
        textView.getOnItemClickListener().onItemClick(adapterView, null, 0, 0);
        assertTrue("Button is not enabled", button.isEnabled());

    }

    @Test
    public void testButtonIsDisabledWhenNoResults() throws Exception {
        Activity activity = (Activity)activityController.get();

        //EditText
        AutoCompleteTextView textView = (AutoCompleteTextView)activity.findViewById(R.id.add_company_list_view);
        //Button
        Button button = (Button)activity.findViewById(R.id.add_company_button);

        //Test the button disabled when results
        textView.setText("Eyb");
        assertTrue("Button is not disabled", !button.isEnabled());
    }


    @Test
    public void testAcceptButtonBehaviour() throws Exception {
        Activity activity = (Activity)activityController.get();

        //EditText
        final AutoCompleteTextView textView = (AutoCompleteTextView)activity.findViewById(R.id.add_company_list_view);
        //Button
        Button button = (Button)activity.findViewById(R.id.add_company_button);

        //Test the button enabled when results
        textView.setText("Goo");
        AdapterView adapterView = new AdapterView(activity) {
            @Override
            public Adapter getAdapter() {
                return textView.getAdapter();
            }

            @Override
            public void setAdapter(Adapter adapter) {

            }

            @Override
            public View getSelectedView() {
                return null;
            }

            @Override
            public void setSelection(int i) {

            }
        };
        textView.getOnItemClickListener().onItemClick(adapterView, null, 0, 0);
        assertTrue("Button is not enabled", button.isEnabled());
        button.performClick();

        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertTrue("Next activity is not CompanyListActivity: "+intent.getComponent().getClassName(),
                intent.getComponent().getClassName().equals(CompanyListActivity.class.getCanonicalName()));

        //TODO test when no results.
    }

    //TODO navigation to previous activity
}
