package throwdown.trotro.app.robin.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bubu on 5/27/16.
 */
public class Stop {
    @SerializedName("id")
    private Integer id;
    @SerializedName("stop_id")
    private String stopId;
    @SerializedName("stop_name")
    private String stopName;
    @SerializedName("stop_lat")
    private String stopLat;
    @SerializedName("stop_long")
    private String stopLong;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The stopId
     */
    public String getStopId() {
        return stopId;
    }

    /**
     * @param stopId The stop_id
     */
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    /**
     * @return The stopName
     */
    public String getStopName() {
        return stopName;
    }

    /**
     * @param stopName The stop_name
     */
    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    /**
     * @return The stopLat
     */
    public String getStopLat() {
        return stopLat;
    }

    /**
     * @param stopLat The stop_lat
     */
    public void setStopLat(String stopLat) {
        this.stopLat = stopLat;
    }

    /**
     * @return The stopLong
     */
    public String getStopLong() {
        return stopLong;
    }

    /**
     * @param stopLong The stop_long
     */
    public void setStopLong(String stopLong) {
        this.stopLong = stopLong;
    }

}
