package Utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.HashMap;

public class ImageManager implements Serializable {
    private  final int MAX_DIM = 96;
    private static ImageManager instance;
    HashMap<String, String> images;
    public static ImageManager getInstance() {
        if(instance == null)
            instance = new ImageManager();
        return instance;
    }

    private ImageManager(){
        images = new HashMap<>();
    }

    public void setImages(HashMap<String, String> images){
        this.images = images;
    }

    public HashMap<String, String> getImages() {
        return images;
    }

    public BufferedImage getImage(String imgName) throws IOException {
        return ImageIO.read(getClass().getResource(images.get(imgName)));
    }

    public boolean saveProfileImage(Image img, String saveName){
        String saveNameFormat = "src\\View\\images\\profile\\"+saveName+".png";
        File output = new File(saveNameFormat);
        try {
            BufferedImage im = SwingFXUtils.fromFXImage(img, null);
            if(ImageIO.write(im, "png", output)){
                images.put(saveName, saveNameFormat);
                int h = im.getHeight(), w = im.getWidth();
                if(h > MAX_DIM || w > MAX_DIM){
                    double ratio = 1.0 * w / h;
                    int scaledDim = (int) ((ratio>1)?(1.0 * MAX_DIM / ratio):(1.0 * MAX_DIM * ratio));
                    if(w > h) {
                        resize(output,saveNameFormat, MAX_DIM, scaledDim);
                        return true;
                    }
                    resize(output,saveNameFormat, scaledDim, MAX_DIM);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void resize(File inputFile, String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        System.out.println(inputFile);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }
}
