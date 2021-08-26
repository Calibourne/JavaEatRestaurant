package View;

import Utils.DocxWriter;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) {
        // See https://stackoverflow.com/a/52654791/3956070 for explanation
        DocxWriter.getInstance().writeToDocx("test.docx", "This is a Test", new String[]{});
        try{
            CSV_Main.main(args);
        }catch (IOException e){
            e.getMessage();
        }
        Main.main(args);
    }
}
