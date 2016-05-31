package throwdown.trotro.app.robin.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import throwdown.trotro.app.robin.R;

public class MapAcitivity extends AppCompatActivity {


    GoogleMap map;
    ArrayList<LatLng> markerPoints;
    LatLng source;
    LatLng destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_acitivity);

        // Initializing array List
        markerPoints = new ArrayList<>();

        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        source = getIntent().getParcelableExtra("src");
        if (source == null) {
            finish();
            return;
        }

        destination = getIntent().getParcelableExtra("dest");
        if (destination == null) {
            finish();
            return;
        }

        fm.getMapAsync(googleMap -> {


            if (ActivityCompat.checkSelfPermission(MapAcitivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(MapAcitivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            map.setMyLocationEnabled(true);


//            GMapV2Direction md = new GMapV2Direction();
//            Document doc = md.getDocument(source, destination,
//                    GMapV2Direction.MODE_DRIVING);
//
//            ArrayList<LatLng> directionPoint = md.getDirection(doc);
//            PolylineOptions rectLine = new PolylineOptions().width(3).color(
//                    Color.RED);
//
//            for (int i = 0; i < directionPoint.size(); i++) {
//                rectLine.add(directionPoint.get(i));
//            }
//            map.addPolyline(rectLine);
        });
    }
}

