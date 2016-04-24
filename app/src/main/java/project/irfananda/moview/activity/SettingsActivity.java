package project.irfananda.moview.activity;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import project.irfananda.moview.GlobalData;
import project.irfananda.moview.R;

public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    private AppCompatDelegate mDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(GlobalData.theme.equalsIgnoreCase("dark"))
            setTheme(R.style.AppThemeDark_Base);
        else if(GlobalData.theme.equalsIgnoreCase("light"))
            setTheme(R.style.AppThemeLight_Base);
        else
            setTheme(R.style.AppThemeDark_Base);
        super.onCreate(savedInstanceState);
        // Add 'general' preferences, defined in the XML file
        // TODO: Add preferences from XML
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getDelegate().setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addPreferencesFromResource(R.xml.pref_general);

        // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
        // updated when the preference changes.
        // TODO: Add preferences
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sort_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_theme_key)));
    }

    /**
     * Attaches a listener so the summary is always updated with the preference value.
     * Also fires the listener once, to initialize the summary (so it shows up before the value
     * is changed.)
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            if (listPreference.getTitle().equals(getResources().getString(R.string.pref_sort_label))) {
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    GlobalData.refresh = !GlobalData.key.equals(listPreference.getEntryValues()[prefIndex].toString());
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            }else if (listPreference.getTitle().equals(getResources().getString(R.string.pref_theme_label))) {
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    //set theme to preferences and restart apps
                    Log.i("infoirfan","Selected Theme : "+listPreference.getEntries()[prefIndex].toString());
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                    if(!GlobalData.theme.equals(listPreference.getEntryValues()[prefIndex].toString())) {
                        GlobalData.theme = listPreference.getEntryValues()[prefIndex].toString();
                        GlobalData.restart = true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home ) {
            finish();
        }
        // other menu select events may be present here
        return super.onOptionsItemSelected(item);
    }


    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }
}