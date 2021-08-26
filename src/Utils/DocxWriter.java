package Utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;

public class DocxWriter {
    private static DocxWriter instance;
    private final String fontFamily;
    private DocxWriter(){
        fontFamily = "Courier";
    }

    public static DocxWriter getInstance() {
        if(instance==null)
            instance = new DocxWriter();
        return instance;
    }

    public boolean writeToDocx(final String fileName, final String docTitle ,String[] prompt){
        try(XWPFDocument doc = new XWPFDocument()){
            XWPFParagraph title = doc.createParagraph();
            title.createRun();
            return true;
        } catch (IOException e){
            return false;
        }
    }
}
