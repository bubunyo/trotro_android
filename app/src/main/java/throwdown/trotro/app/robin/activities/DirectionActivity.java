package throwdown.trotro.app.robin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import throwdown.trotro.app.robin.R;
import throwdown.trotro.app.robin.util.ApiCalls;

public class DirectionActivity extends AppCompatActivity {
    Subscription subscription;

    @Bind(R.id.from_txt)
    TextView from;

    @Bind(R.id.to_txt)
    TextView to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        ButterKnife.bind(this);

        String stopId = getIntent().getStringExtra("stop_id");
        String place = getIntent().getStringExtra("place");

        if (stopId == null) {
            Toast.makeText(this, "No stop supplied", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (place == null) {
            Toast.makeText(this, "No place supplied", Toast.LENGTH_SHORT).show();
            finish();
        }

        findViewById(R.id.relativeLayout2).setOnClickListener(v -> {
            startActivity(new Intent(this, MapAcitivity.class));
        });

        to.setText("To " + place);

        LocationRequest request = LocationRequest.create() //standard GMS LocationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(5)
                .setInterval(100);

        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(this);
        subscription = locationProvider.getUpdatedLocation(request)
                .doOnNext(location1 -> {
                    locationProvider.getReverseGeocodeObservable(location1.getLatitude(), location1.getLongitude(), 1)
                            .doOnError(Throwable::printStackTrace)
                            .subscribe(addresses -> {
                                from.setText("From " + addresses.get(0).getAddressLine(0));
                            });
                })
                .flatMap(location -> ApiCalls.get().nearestStop(location).onErrorResumeNext(throwable1 -> {
                    throwable1.printStackTrace();
                    return Observable.empty();
                }))
                .subscribe(stop -> {
                    Toast.makeText(this, stop.get(0).getStopName(), Toast.LENGTH_SHORT).show();
                    System.out.println(stop.get(0).getStopName());
//                    System.out.println(location.getLatitude() + "----" + location.getLongitude());
                });


    }

    @Override
    protected void onStop() {
        super.onStop();
        subscription.unsubscribe();
    }
}
