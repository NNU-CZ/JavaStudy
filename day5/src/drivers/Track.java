package drivers;

import java.io.*;
import java.util.LinkedList;

/**
 * @author cz
 */
public class Track implements Serializable {
    public String driver_id;
    public String orders;
    public LinkedList<Location> locations;
    public static String path;

    public void writer(){
        try {
            String filename = path+"\\"+this.orders+".ser";
            File filter = new File(filename);
            if(filter.exists()){
                filename = filename+".ser";
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(this);
            System.out.println("Serialized data is saved");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//            out.writeObject(this.driver_id);
//            out.writeObject(this.orders);
//            for(Location location:this.locations){
//                out.writeObject(location);
//            }
//            out.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public Track(String driver_id, String orders) {
        this.driver_id = driver_id;
        this.orders = orders;
        locations = new LinkedList<>();
    }

    public void addLocation(String date,String lon,String lat){
        Location location = new Location(date,lon,lat);
        locations.add(location);
    }

}
