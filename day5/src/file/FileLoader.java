package file;

import drivers.Track;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cz
 */
public class FileLoader {

    private BufferedReader bufLoader(String filename) throws IOException {
        File fl = new File(filename);
        FileInputStream fis = new FileInputStream(fl);
        BufferedInputStream bis = new BufferedInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(bis),5*1024*1024);
        return br;
    }

    public FileLoader(String filename) throws IOException {
        BufferedReader br = bufLoader(filename);
        String line = "";
        Track odr = new Track("0","0");
        Track track = null;
        while ((line = br.readLine())!=null){
            String[] sp_line = line.split(",");
            if(!sp_line[1].equals(odr.orders)){
                track = new Track(sp_line[0],sp_line[1]);
                if(odr!=null){
                    odr.writer();
                }
                odr = track;
            }
            track.addLocation(sp_line[2],sp_line[3],sp_line[4]);
        }
        track.writer();
        br.close();
        System.out.println("完成");
    }

}
