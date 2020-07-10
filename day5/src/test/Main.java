package test;

import drivers.Order;
import drivers.Track;

import java.io.*;

/**
 * @author cz
 */

public class Main {
    public static Track deSerialize(String filename) {
        Track track = null;
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
        }catch(ClassNotFoundException c)
        {
            System.out.println("Track class not found");
            c.printStackTrace();
        }
        finally{
            System.out.println("Deserialize Track...");
            return track;
        }
    }
    public static boolean load_files(String filepath) throws FileNotFoundException, IOException {
        try {
            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 1; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        String filename = readfile.getPath();
                        Track track = deSerialize(filename);
                        Order order = new Order(track);
//                      System.out.println("读取");
                        order.write();
                    } else if (readfile.isDirectory()) {
                        load_files(filepath + "\\" + filelist[i]);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("load_files()   Exception:" + e.getMessage());
        }
        return true;
    }
    public static void write_csv(String line) throws Exception {
        //第一步：设置输出的文件路径
        //如果该目录下不存在该文件，则文件会被创建到指定目录下。如果该目录有同名文件，那么该文件将被覆盖。
        File writeFile = new File("E:\\JavaStudy\\data\\test.csv");

        //第二步：通过BufferedReader类创建一个使用默认大小输出缓冲区的缓冲字符输出流
        BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile,true));
        writeText.write(line);
        writeText.newLine();
        writeText.close();
    }


    private static String reName(String stable, int var){
        return stable + String.format("%02d",var);
    }
    public static void main(String[] args) throws IOException {
        load_files("E:\\trafficdata\\10");
    }
}
