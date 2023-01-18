import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImgChange extends Thread {

    private int newWidth;
    private int newHeight;
    private File[] files;
    private String dst;
    private long start;

    public ImgChange(int newWidth, int newHeight, File[] files, String dst, long start) {
        this.newWidth = newWidth;
        this.newHeight = newHeight;
        this.files = files;
        this.dst = dst;
        this.start = start;
    }

    @Override
    public void run() {

        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    System.out.println(file.getName() + " - image is null!");
                    continue;
                }

                BufferedImage newImage = Scalr.resize(image, newWidth, newHeight);

                File newFile = new File(dst + "/" + file.getName());
                if (newFile.exists()) {
                    newFile = new File(dst + "/" + "_" + file.getName());
                }

              //  System.out.println(newFile.getName());
                ImageIO.write(newImage, getFormatName(file.getName()), newFile);
            }

        } catch (Exception ex) {
            ex.getStackTrace();
        }

        System.out.println("Time: " + (System.currentTimeMillis() - start) + " ms");
    }


    public static String getNameImage(String fileName) {
        String name = "";
        int end = fileName.lastIndexOf(".");
        name = fileName.substring(0, end - 1);
        return name;
    }

    public static String getFormatName(String fileName) {
        String format = "";
        int end = fileName.length() - 1;
        int start = fileName.lastIndexOf(".", end);
        format = fileName.substring(start + 1);
        return format;
    }

}


