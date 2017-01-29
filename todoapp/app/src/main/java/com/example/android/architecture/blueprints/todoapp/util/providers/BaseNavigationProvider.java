package com.example.android.architecture.blueprints.todoapp.util.providers;

/**
 * Handles navigation between Activities in the app.
 */

public interface BaseNavigationProvider {

    /**
     * Finish an Activity with a result.
     *
     * @param resultCode the result code to be set when finishing the Activity.
     */
    void finishActivityWithResult(int resultCode);

    /**
     * Start a new Activity for a result.
     *
     * @param cls         the Activity class to be opened.
     * @param requestCode the request code that will be passed to the opened Activity.
     */
    void startActivityForResult(Class cls, int requestCode);

    /**
     * Start a new Activity for a result with an extra
     *
     * @param cls        the Activity class to be opened.
     * @param extraKey   the key for the extra that is passed in the Intent.
     * @param extraValue the value for the extra the is passed in the Intent.
     */
    void startActivityWithExtra(Class cls, String extraKey, String extraValue);
}
