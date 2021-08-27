package Utils;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A utility class to write .docx files
 * @author Eddie Kanevsky
 */
public class DocxWriter {
    private static DocxWriter instance;
    private XWPFDocument template;
    private enum Styles{
        Title("Title"),
        Subtitle("Subtitle"),
        Heading1("Heading1"),
        Heading2("Heading2"),
        Default("Normal");

        final String name;
        Styles(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
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

    /**
     *
     * @param fileName
     * @param docTitle
     * @param prompt
     * An array of texts inside the paragraphs - might include decorator to indicate the style and alignment of the text
     * example for one of the prompts:
     * -CENTER~example
     * where "-CENTER" indicates the text would be aligned at the center with the default style,
     * and "~" indicates the end of the decorator
     *
     * The method assumes that no-decorator prompt corresponds to "-LEFT~<REST of the prompt>" prompt
     * @return
     */
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
                    String[] args = {s};
                    if(s.contains("~"))
                        args = s.split("~");
                    if(args.length == 1)
                    {
                        XWPFParagraph p = doc.createParagraph();
                        p.setAlignment(ParagraphAlignment.LEFT);
                        generateParagraphRun(p, s, Styles.Default);
                    }
                    else if(args.length == 2){
                        String[] pStyle = args[0].split("-");

                        XWPFParagraph p = doc.createParagraph();
                        switch (pStyle[1]) {
                            case "LEFT" -> p.setAlignment(ParagraphAlignment.LEFT);
                            case "RIGHT" -> p.setAlignment(ParagraphAlignment.RIGHT);
                            case "CENTER" -> p.setAlignment(ParagraphAlignment.CENTER);
                            case "" -> p.setAlignment(ParagraphAlignment.DISTRIBUTE);
                        }
                        switch (pStyle[0]){
                            case "TITLE" -> generateParagraphRun(p, args[1], Styles.Title);
                            case "SUBTITLE" -> generateParagraphRun(p, args[1], Styles.Subtitle);
                            case "HEADING1" -> generateParagraphRun(p, args[1], Styles.Heading1);
                            case "HEADING2" -> generateParagraphRun(p, args[1], Styles.Heading2);
                            case "" -> generateParagraphRun(p, args[1], Styles.Default);
                        }
                    }
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