package file;

import drivers.Track;

import java.io.File;
import java.io.IOException;

/**
 * @author Administrator
 */
public class Writer {
    private static String reName(String stable, int var){
        return stable + String.format("%02d",var);
    }
    public static void main(String[] args) throws IOException {

        String stable = "E:\\data\\gps_201611";
        for(int i = 22;i<=30;i++){

            String filename = reName(stable,i);
            File newpath = new File(reName("E:\\trafficdata\\",i));
            newpath.mkdir();
            Track.path = newpath.getPath();
            FileLoader fl = new FileLoader(filename);
        }
    }
}
