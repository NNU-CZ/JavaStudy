package file;

import drivers.Location;
import drivers.Track;

import java.io.*;

/**
 * @author cz
 */
public class DeSerialize
{
    public static void deSerialize(String filename)
    {
        Track track;
        try
        {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            track = (Track)in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
        System.out.println("Deserialized Employee...");
        System.out.println(track.driver_id);
        System.out.println(track.orders);
        for(Location location:track.locations){
            System.out.println(location.date);
            System.out.println(location.lon);
            System.out.println(location.lat);
        }
    }
}
