package Utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;

public class ImageManager implements Serializable {
    private final int MAX_DIM = 96;
    private final int MINIMIZED_DIM = 32;
    private static ImageManager instance;
    private HashMap<String, URL> images;
    public static ImageManager getInstance() {
        if(instance == null)
            instance = new ImageManager();
        return instance;
    }

    private ImageManager(){
        images = new HashMap<>();
        images.put("Default", getClass().getResource("/View/images/profile/default.png"));
        images.put("Defaultm", getClass().getResource("/View/images/profile/defaultm.png"));
        images.put("Alert", getClass().getResource("/View/images/profile/defaultToUpdate.png"));
    }

    public void setImages(HashMap<String, URL> images){
        this.images = images;
    }

    public HashMap<String, URL> getImages() {
        return images;
    }

    public BufferedImage getImage(String imgName) throws IOException {
        return ImageIO.read(images.get(imgName));
    }

    public boolean saveProfileImage(Image img, String saveName){
        String saveNameFormat = "src/View/images/profile/"+saveName+".png";
        String saveMinimised = "src/View/images/profile/"+saveName+"m"+".png";
        File output1 = new File(saveNameFormat);
        File output2 = new File(saveMinimised);
        try {
            BufferedImage im = SwingFXUtils.fromFXImage(img, null);
            if(ImageIO.write(im, "png", output1) && ImageIO.write(im, "png", output2)){
                images.put(saveName, output1.toURI().toURL());
                images.put(saveName+"m", output2.toURI().toURL());
                int h = im.getHeight(), w = im.getWidth();
                if(h > MAX_DIM || w > MAX_DIM){
                    double ratio = 1.0 * w / h;
                    int scaledDim = (int) ((ratio>1)?(1.0 * MAX_DIM / ratio):(1.0 * MAX_DIM * ratio));
                    int scaledDimMinimised = (int) ((ratio>1)?(1.0 * MINIMIZED_DIM / ratio):(1.0 * MINIMIZED_DIM * ratio));
                    if(w > h) {
                        resize(output1,saveNameFormat, MAX_DIM, scaledDim);
                        resize(output2, saveMinimised, MINIMIZED_DIM, scaledDimMinimised);
                        return true;
                    }
                    resize(output1,saveNameFormat, scaledDim, MAX_DIM);
                    resize(output2,saveMinimised, scaledDimMinimised, MINIMIZED_DIM);
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
