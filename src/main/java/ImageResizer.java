import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable{


    private  int newWidth;
    private File[] files;
    private String dst;
    private long start;

    public ImageResizer(int newWidth, File[] files, String dst, long start) {
        this.newWidth = newWidth;
        this.files = files;
        this.dst = dst;
        this.start = start;
    }


    public void run() {
        try
        {   for (File file : files) {
            BufferedImage image = ImageIO.read(file);
            if(image == null) {
                System.out.println(file.getName() + " - image is null!");
                continue;}

            int newHeight = (int) Math.round(image.getHeight() / (image.getWidth()) / (double) newWidth);
            BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            int widthStep = image.getWidth()/newWidth;
            int heightStep = image.getHeight()/newHeight;

            for(int x = 0; x < newWidth; x++) {
                for(int y = 0; y < newHeight; y++) {
                    int rgb = image.getRGB(x*widthStep, y*heightStep);
                    newImage.setRGB(x,y,rgb);
                }
            }

            File newFile = new File(dst + "/" + file.getName());
            if (newFile.exists()) {
                newFile = new File(dst + "/" + "_" + file.getName());
            }

            //  System.out.println(newFile.getName());
            ImageIO.write(newImage, getFormatName(file.getName()), newFile);
        }

        }
        catch (Exception ex) {ex.getStackTrace();}
        System.out.println(System.currentTimeMillis() - start);
    }


    public static String getNameImage (String fileName) {
        String name = "";
        int end = fileName.lastIndexOf(".");
        name = fileName.substring(0, end - 1);
        return name;
    }

    public static String getFormatName (String fileName) {
        String format = "";
        int end = fileName.length()-1;
        int start = fileName.lastIndexOf(".", end);
        format = fileName.substring(start+1);
        return format;
    }

}

