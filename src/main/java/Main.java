import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        String src = "C:/Users/Света/Desktop/source";
        String dst = "C:/Users/Света/Desktop/dest";

        File scrDir = new File(src);

        File[] files = scrDir.listFiles();
        System.out.println("Fotos: " + files.length);

        long start = System.currentTimeMillis();

        int count = Runtime.getRuntime().availableProcessors();
        System.out.println("Processors = " + count);
        int part = (int) (files.length / count);
        if(part == 0) {
            part = 1;
        } else if (part > 0 && (files.length/count) + 0.5 >= part + 1) {
            part = part + 1;
        } else {}

        System.out.println("Part: " + part);

        for (int i = 1; (i <= count && i <= files.length); i++) {
            File[] filesPart = new File[0];
            if (i < count && i < files.length) {
                filesPart = new File[part];
                  System.arraycopy(files, part*(i-1), filesPart,0,filesPart.length);

            } else if (i == count || i == files.length) {
                filesPart = new File[files.length - (part * (i-1))];
                  System.arraycopy(files, part*(i-1), filesPart,0,filesPart.length);
                }
            // Runnable, обычный метод
            ImageResizer resizer = new ImageResizer(300, filesPart, dst, start);
            new Thread(resizer).start();

            // Thread, метод с imgscalr
//            ImgChange change = new ImgChange(200,150, filesPart, dst, start);
//            change.start();

        }

    }


}