package com.appnext.fidelio;

import android.app.Application;
import android.util.Log;

import com.appnext.fidelio.utils.ParseConstants;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Manfredi on 28/05/15.
 */
public class ChattitApplication extends Application {

    @Override
    public void onCreate() {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        super.onCreate();
        Parse.initialize(this, "WlzbTG0DTGHJukNdEqPqZl4ZOivUXGxNNJakbeAV", "urMs2YdjAJFmDmnf3R2oBP6z5z6tyIaWwIb32bmm");

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }


    public static void updateParseInstallation(ParseUser user) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
        installation.saveInBackground();
    }
}
