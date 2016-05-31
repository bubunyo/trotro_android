package throwdown.trotro.app.robin.util;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import throwdown.trotro.app.robin.data.model.Stop;

/**
 * Created by bubu on 3/21/16.
 */
public interface ApiService {

    String PRODUCTION_ENDPOINT = "http://4064e177.ngrok.io/";

    @GET("stops/search/{search}")
    Observable<List<Stop>> searchStop(
            @Path("search") String search
    );

    @GET("stops/nearest/{lat}/{long}")
    Observable<List<Stop>> nearestStop(
            @Path("lat") String  lat,
            @Path("long") String longi
    );


    /********
     * Util class that sets up a new services
     *******/
    class Creator {
        public static ApiService apiService() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PRODUCTION_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(ApiService.class);
        }
    }

}
