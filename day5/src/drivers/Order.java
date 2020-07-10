package drivers;

import org.gavaghan.geodesy.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author cz
 */
public class Order implements Serializable {
    static double speed_limit = 22;
    static double acc_limit = 3;

    public String order;
    public String driver;

    public Map<Integer,Double> exceed;
    public Map<Integer,Double> jerk_acc;
    public Map<Integer,Double> jerk_dec;

    public double jerk_acc_max;
    public double jerk_dec_max;

    public double max_speed;
    public double min_speed;
    public double aver_speed;

    public boolean d_night;

    public String features(){
        String line = driver+","+order+","
                +String.valueOf(exceed.size())+","
                +String.valueOf(jerk_acc.size())+","
                +String.valueOf(jerk_dec.size())+","
                +String.valueOf(jerk_acc_max)+","
                +String.valueOf(jerk_dec_max)+","
                +String.valueOf(max_speed)+","
                +String.valueOf(min_speed)+","
                +String.valueOf(aver_speed)+","
                +String.valueOf(d_night);
        return line;
    }
    public void write(){
        String line = features();
        try {
            BufferedWriter csv = new BufferedWriter(new FileWriter("E:\\trafficdata\\test.csv",true));
            csv.write(line);
            csv.newLine();
            csv.close();
            System.out.println("写入特征");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Order(Track track) {
        this.order = track.orders;
        this.driver = track.driver_id;
        this.d_night = isNight(track.locations.get(0).date);
        this.exceed = new HashMap<>();
        this.jerk_acc = new HashMap<>();
        this.jerk_dec = new HashMap<>();
        this.max_speed = 0;
        this.min_speed = 1000000;
        int length = track.locations.size()-1;
        this.aver_speed = 0;


        double total_speed = 0;
        int n = 0;
        for (int i = 0 ; i < track.locations.size() - 2;i++){
            Location start = track.locations.get(i);
            Location mid = track.locations.get(i+1);
            Location end = track.locations.get(i+2);
            double speed1 = getSpeed(start,mid);
            double speed2 = getSpeed(mid,end);
            if (speed1 != 0) {
                total_speed+=speed1;
                n++;
                if(speed1>max_speed){
                    max_speed = speed1;
                }
                if(speed1<min_speed){
                    min_speed = speed1;
                }
            }

            double acc = getAcc(speed1,speed2,(end.date-start.date));
            if(isExceed(speed1)){
                exceed.put((end.date-start.date)/2,speed1);
            }
            if(isJerk(acc)){
                if(acc>0){
                    jerk_acc.put(mid.date,acc);
                }
                else {
                    jerk_dec.put(mid.date,acc);
                }
            }
        }
        this.aver_speed = total_speed/n;
        if(jerk_dec.size()==0){
            jerk_dec_max=0;
        }
        else{
            Collection<Double> dec_cl = jerk_dec.values();
            Object[] DEC = dec_cl.toArray();
            Arrays.sort(DEC);
            this.jerk_dec_max = (double) DEC[DEC.length-1];
        }
        if(jerk_acc.size()==0){
            jerk_acc_max = 0;
        }
        else {
            Collection<Double> acc_cl = jerk_acc.values();
            Object[] ACC = acc_cl.toArray();
            Arrays.sort(ACC);
            this.jerk_acc_max = (double) ACC[0];
        }
        if(exceed.size()>0){
            System.out.println("发现急加速");
        }
    }

    public boolean isNight(int time){
        String timestampString = String.valueOf(time);
        String formats = "HH:mm:ss yyyy-MM-dd";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        System.out.println(date);
        int dtime = Integer.parseInt(date.split(":")[0]);
        if((dtime>=20 && dtime<=24)||dtime<=6) {
            return true;
        }
        else {
            return false;
        }
    }
    public double getSpeed(Location oldLocation, Location newLocation){
        if(oldLocation.lat==newLocation.lat&&oldLocation.lon==newLocation.lon){
           return 0;
        }
        double times = newLocation.date - oldLocation.date;
        GlobalCoordinates oldPosition = new GlobalCoordinates(oldLocation.lat,oldLocation.lon);
        GlobalCoordinates newPosition = new GlobalCoordinates(newLocation.lat,newLocation.lon);
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, oldPosition, newPosition);
        double distance = geoCurve.getEllipsoidalDistance();
        return distance/times;
    }
    public double getAcc(double oldSpeed,double newSpeed,double time){
        if(oldSpeed==newSpeed){
            return 0;
        }
        else {
            return 2*(newSpeed-oldSpeed)/time;
        }
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
        if(acc>acc_limit||acc<-acc_limit){
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
