package io.vaxly.venda;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.interceptors.ParseLogInterceptor;

/**
 * Created by vaxly on 9/9/16.
 */
public class ParseInit extends Application {

    private static String APPID = "venda";
    private static String APPURL = "http://5.189.168.22:1337/parse/";

    @Override

    public void onCreate() {
        super.onCreate();


        Fresco.initialize(this);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(APPID) // should correspond to APP_ID env variable
                .addNetworkInterceptor(new ParseLogInterceptor())
                .enableLocalDataStore()
                .server(APPURL).build());

        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}
