package Utils;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;

import java.io.*;

/**
 * A utility class to write .docx files
 * @author Eddie Kanevsky
 */
public class DocxWriter {
    private static DocxWriter instance;
    private XWPFDocument template;
    private DocxWriter(){
        try{
            File f = new File("Template.docx");
            try(FileInputStream fi = new FileInputStream(f)){
                template = new XWPFDocument(fi);
            }
        }catch (IOException | NullPointerException e){
            template = null;
        }
    }

    public static DocxWriter getInstance() {
        if(instance==null)
            instance = new DocxWriter();
        return instance;
    }

    public boolean writeToDocx(final String fileName, final String docTitle ,String[] prompt){
        try(XWPFDocument doc = new XWPFDocument()){
            XWPFStyles styles = doc.createStyles();
            try{
                styles.setStyles(template.getStyle());
            } catch (XmlException | NullPointerException e) {
                System.out.println("couldn't load the template");
            }
            XWPFParagraph title = doc.createParagraph();
            generateParagraphRun(title, docTitle, Styles.Title);
            try{
                for(String s : prompt){
                    XWPFParagraph p = doc.createParagraph();
                    generateParagraphRun(p, s, Styles.Default);
                }
            }catch (NullPointerException e){
                e.getMessage();
            }
            try(FileOutputStream out = new FileOutputStream(fileName)){
                doc.write(out);
            }
            return true;
        } catch (IOException e){
            return false;
        }
    }

    /**
     * Generates a paragraph text using custom parameters
     * @param paragraphRun
     * the paragraph text wrapper
     * @param prompt
     * the text prompt
     * @param fontFamily
     * font family to use for the prompt
     * @param fontSize
     * font size to use for the prompt
     * @param bold
     * is the prompt bold
     * @param underlinePattern
     * underline property of the prompt
     * @param hexColor
     * hex code color of the prompt
     */
    private void generateParagraphRun(final XWPFRun paragraphRun,
                                      final String prompt,
                                      final String fontFamily,
                                      final int fontSize,
                                      final boolean bold,
                                      final UnderlinePatterns underlinePattern,
                                      final String hexColor){
        paragraphRun.setText(prompt);
        paragraphRun.setFontFamily(fontFamily);
        paragraphRun.setFontSize(fontSize);
        paragraphRun.setBold(bold);
        paragraphRun.setUnderline(underlinePattern);
        paragraphRun.setColor(hexColor);
    }

    /**
     * Generates a paragraph text, and applies its style from a template style
     * @param paragraph
     * the paragraph
     * @param prompt
     * the prompt text
     * @param style
     * the template style
     */
    private void generateParagraphRun(final XWPFParagraph paragraph,
                                      final String prompt,
                                      final Styles style){
        XWPFRun paragraphRun = paragraph.createRun();
        paragraph.setStyle(style.getName());
        paragraphRun.setText(prompt);
    }
}

enum Styles{
    Title("Title"),
    Heading1("Heading1"),
    Heading2("Heading2"),
    Default("Normal");

    private final String name;
    private Styles(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}