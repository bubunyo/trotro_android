package throwdown.trotro.app.robin;

import android.app.Application;

import throwdown.trotro.app.robin.util.ApiCalls;

/**
 * Created by bubu on 5/27/16.
 */
public class RobinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApiCalls.init();
    }
}
