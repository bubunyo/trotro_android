package throwdown.trotro.app.robin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.AutocompletePrediction;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import throwdown.trotro.app.robin.R;
import throwdown.trotro.app.robin.activities.DirectionActivity;

/**
 * Created by bubu on 5/27/16.
 */
public class LocationSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<AutocompletePrediction> list;
    Context context;

    public LocationSearchAdapter(List<AutocompletePrediction> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_location_layout, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            Holder h = (Holder) holder;
            AutocompletePrediction place = getItem(position);
            h.stopName.setText(place.getPrimaryText(null));
            h.stopId.setText(place.getPlaceId());
            h.distance.setText(place.getSecondaryText(null));
            h.image.setOnClickListener(v -> {
                Intent intent = new Intent(context, DirectionActivity.class);
                intent.putExtra("place_id", place.getPlaceId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private AutocompletePrediction getItem(int position) {
        return list.get(position);
    }

    class Holder extends RecyclerView.ViewHolder {
        @Bind(R.id.location_name)
        TextView stopName;

        @Bind(R.id.stop_id)
        TextView stopId;

        @Bind(R.id.distance)
        TextView distance;


        @Bind(R.id.imageView)
        ImageView image;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
