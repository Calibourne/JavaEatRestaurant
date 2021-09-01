package View;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) {
        // See https://stackoverflow.com/a/52654791/3956070 for explanation
        try{
            CSV_Main.main(args);
        }catch (IOException e){
            e.getMessage();
        }
        //SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Startup.wav"); //Too irritating, had to disable for now
        Main.main(args);
    }
}