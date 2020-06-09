package drivers;

import java.io.Serializable;

/**
 * @author cz
 */
public class Location implements Serializable {
    public int date;
    public float lon;
    public float lat;

    public Location(String date, String lon, String lat) {
        this.date = Integer.parseInt(date);
        this.lon = Float.parseFloat(lon);
        this.lat = Float.parseFloat(lat);
    }
}
