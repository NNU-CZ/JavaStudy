package test;

import drivers.Track;
import file.FileLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author cz
 */

public class Main {
    private static String reName(String stable, int var){
        return stable + String.format("%02d",var);
    }
    public static void main(String[] args) throws IOException {
        String stale = "E:\\jetJava\\data\\gps_201611";
        for(int i = 7;i<=30;i++){
            String filename = reName(stale,i);
            String path = reName("E:\\jetJava\\data\\",i);
            File mkdir = new File(path);
            if(!mkdir.exists()){
                mkdir.mkdir();
            }
            Track.path = path;
            FileLoader fr = new FileLoader(filename);
        }
    }
}
