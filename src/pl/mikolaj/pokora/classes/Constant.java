package pl.mikolaj.pokora.classes;

import lombok.ToString;
import pl.mikolaj.pokora.classes.enums.DisplayOptions;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Boolean.parseBoolean;

@ToString
public class Constant {
    //default values
    public static int window_width = 720;
    public static int window_height = 480;
    public static DisplayOptions displayOption = DisplayOptions.WINDOW;
    public static int field_size = 40;
    public static int force_min_frame_rate = 24;
    public static boolean show_who_lost = true;

    //read v2
    public static Dictionary<String,String> options = new Hashtable<>();

    public static void read_options() throws IOException {
        FileReader fileReader = new FileReader("src/pl/mikolaj/pokora/other/options.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String value;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //window_width
        bufferedReader.readLine();
        value = bufferedReader.readLine();
        if (value.equals("-1")) {
            window_width = (int) screenSize.getWidth();
        } else {
            window_width = Integer.parseInt(value);
        }

        //window_height
        bufferedReader.readLine();
        value = bufferedReader.readLine();
        if (value.equals("-1")) {
            System.out.println("tak");
            window_height = (int) screenSize.getHeight();
        } else {
            System.out.println("nie");
            window_height = Integer.parseInt(value);
        }

        //window_display_option (FULL_SCREEN, BORDERLESS_WINDOW, WINDOW)
        bufferedReader.readLine();
        value = bufferedReader.readLine();
        //displayOption = DisplayOptions.valueOf(value);
        switch (value) {
            case ("WINDOW") -> displayOption = DisplayOptions.WINDOW;
            case ("BORDERLESS_WINDOW") -> displayOption = DisplayOptions.BORDERLESS_WINDOW;
            case ("FULL_SCREEN") -> displayOption = DisplayOptions.FULL_SCREEN;
        }

        //field_size
        bufferedReader.readLine();
        value = bufferedReader.readLine();
        field_size = Integer.parseInt(value);

        //force_min_frame_rate
        bufferedReader.readLine();
        value = bufferedReader.readLine();
        force_min_frame_rate = Integer.parseInt(value);
        //new ForceMinFrameRate(); //MakeThread

        bufferedReader.readLine();
        value = bufferedReader.readLine();
        show_who_lost = parseBoolean(value);

        bufferedReader.close();
    }


    public static void read_options_v2() throws IOException {
        File myObj = new File("src/pl/mikolaj/pokora/other/options_v2.txt");
        Scanner myReader = new Scanner(myObj);

        while (myReader.hasNextLine()) {
            String key = myReader.nextLine();
            options.put(key, myReader.nextLine());
            try {
                myReader.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*
        //do zrobienia przerzut na właściwe dane
        */

        myReader.close();
    }


    public static void readAllOptions() throws IOException {

        read_options();
        /*
        Thread read_optionsThread = new Thread(() -> {
            try {
                read_options();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        read_optionsThread.start();
        */


        Thread read_options_v2Thread = new Thread(() -> {
            try {
                read_options_v2();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        read_options_v2Thread.start();
    }
}
