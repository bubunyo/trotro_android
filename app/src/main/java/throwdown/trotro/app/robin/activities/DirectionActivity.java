package throwdown.trotro.app.robin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import throwdown.trotro.app.robin.R;
import throwdown.trotro.app.robin.data.model.Stop;
import throwdown.trotro.app.robin.util.ApiCalls;

public class DirectionActivity extends AppCompatActivity {
    Subscription subscription;
    LatLng source;
    LatLng destination;

    @Bind(R.id.from_txt)
    TextView from;

    @Bind(R.id.to_txt)
    TextView to;

    @Bind(R.id.from_small)
    TextView fromSmall;

    @Bind(R.id.to_small)
    TextView toSmall;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        ButterKnife.bind(this);

        Stop stopId = (Stop) getIntent().getSerializableExtra("stop_id");
        String place = getIntent().getStringExtra("place");

        if (stopId == null) {
            Toast.makeText(this, "No stop supplied", Toast.LENGTH_SHORT).show();
            finish();
        }
        assert stopId != null;
        destination = new LatLng(Double.valueOf(stopId.getStopLat()), Double.valueOf(stopId.getStopLong()));

        toSmall.setText("Closest bus stop: " + stopId.getStopName());

        if (place == null) {
            Toast.makeText(this, "No place supplied", Toast.LENGTH_SHORT).show();
            finish();
        }

        findViewById(R.id.relativeLayout2).setOnClickListener(v -> {
            if (source != null && destination != null) {
//                progress = new ProgressDialog(this);
//                progress.setMessage("Loading Map");
//                progress.setCancelable(false);
//                progress.show();
//                startActivity(new Intent(this, MapAcitivity.class));
//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", destination.latitude, destination.longitude);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?saddr=" + source.latitude + "," + source.longitude + "&daddr=" + destination.longitude + "," + destination.latitude));
//                startActivity(intent);
                Uri googleMapsUri = Uri.parse("google.navigation:q=" + source.latitude + "," + source.longitude);
                Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, googleMapsUri);
                googleMapsIntent.setPackage("com.google.android.apps.maps");
//                progress.hide();
                startActivity(googleMapsIntent);
            }
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
                            .onErrorResumeNext(throwable -> {
                                throwable.printStackTrace();
                                return Observable.empty();
                            })
                            .subscribe(addresses -> {
                                from.setText("From " + addresses.get(0).getAddressLine(0));
                            });
                })
                .flatMap(location -> ApiCalls.get().nearestStop(location).onErrorResumeNext(throwable1 -> {
                    throwable1.printStackTrace();
                    return Observable.empty();
                }))
                .subscribe(stop -> {
                    Toast.makeText(this, "Source ready", Toast.LENGTH_SHORT).show();
                    System.out.println(stop.get(0).getStopName());
                    source = new LatLng(Double.valueOf(stop.get(0).getStopLat()), Double.valueOf(stop.get(0).getStopLong()));
                    fromSmall.setText("Closest bus stop: " + stop.get(0).getStopName());
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        subscription.unsubscribe();
    }
}
