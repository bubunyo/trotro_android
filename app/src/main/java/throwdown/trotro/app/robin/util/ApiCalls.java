package throwdown.trotro.app.robin.util;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import throwdown.trotro.app.robin.data.model.Stop;

public final class ApiCalls {

    private static final String TAG = ApiCalls.class.getSimpleName();

    private ApiService apiService;
    static ApiCalls apiCalls;


    private ApiCalls() {
        apiService = ApiService.Creator.apiService();
    }

    public static void init() {
        if (apiCalls == null)
            apiCalls = new ApiCalls();
    }

    public static ApiCalls get() {
        if (apiCalls != null) {
            return apiCalls;
        } else throw new RuntimeException("ApiCalls class has not been instantiated");
    }


    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable ->
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Stop>> searchStop(String stop) {
        return apiService.searchStop(stop)
                .compose(applySchedulers());
    }

    public Observable<List<Stop>> nearestStop(LatLng location) {
        return apiService.nearestStop(Double.toString(location.latitude), Double.toString(location.longitude))
                .compose(applySchedulers());
    }

    public Observable<List<Stop>> nearestStop(Location location) {
        return apiService.nearestStop(Double.toString(location.getLatitude()), Double.toString(location.getLongitude()))
                .compose(applySchedulers());
    }
}
