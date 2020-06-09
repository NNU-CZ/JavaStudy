package drivers;

import org.gavaghan.geodesy.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author cz
 */
public class Driver {
    static double speed_limit = 80;
    static double acc_limit = 34.6;
    static double dec_limit = 34.6;

    public String driver_id;
    //人为因素属性
    public Map<Integer,Double> exceed;
    public Map<Integer,Double> jerk_acc;
    public Map<Integer,Double> jerk_dec;

    public double max_speed;
    public double min_speed;
    public double aver_speed;
    public double d_total_times;

    //驾驶时间可以认为是物
    public boolean d_night;

    public void writer(){
    }

    public Driver(Track track) {
        this.driver_id = track.driver_id;
        Location start = track.locations.get(0);
        this.d_night = isNight(start.date);
        for(Location location:track.locations){

        }
        this.exceed = new HashMap<>();
        this.jerk_acc = new HashMap<>();
        this.jerk_dec = new HashMap<>();
        this.max_speed = 0;
        this.min_speed = 0;
        this.aver_speed = 0;
        this.d_total_times = 0;
    }


    public boolean isNight(int time){
        String timestampString = String.valueOf(time);
        String formats = "HH:mm:ss yyyy-MM-dd";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        System.out.println(date);
        int dtime = Integer.parseInt(date.split(":")[0]);
        if((dtime>=20&&dtime<=24)||dtime<=6) {
            return true;
        }
        else {
            return false;
        }
    }
    public double getSpeed(Location oldLocation, Location newLocation){
        double times = newLocation.date - oldLocation.date;
        GlobalCoordinates oldPosition = new GlobalCoordinates(oldLocation.lat,oldLocation.lon);
        GlobalCoordinates newPosition = new GlobalCoordinates(newLocation.lat,newLocation.lon);
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, oldPosition, newPosition);
        return geoCurve.getEllipsoidalDistance()/times;
    }
    public double getAcc(double oldSpeed,double newSpeed,double time){
        return (newSpeed-oldSpeed)/time;
    }
    public boolean isExceed(double speed){
        if (speed > speed_limit){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isJerk(double acc){
        if(acc>acc_limit||acc<-dec_limit){
            return true;
        }
        else {
            return false;
        }
    }
/*    public static void main(String[] args) {
        Location l1 = new Location("170","106.486654","29.490295");
        Location l2 = new Location("174","106.581515","29.615467");
        System.out.println(getSpeed(l1,l2));
    }*/
}
