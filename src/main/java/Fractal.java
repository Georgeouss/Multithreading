import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Fractal {
    private int width;
    private int height;
    private int threadNumber;
    private String filename ;
    private boolean quiet;


    public Fractal(int width, int height, int threadNumber, String filename,boolean quiet) {
        this.width = width;
        this.height = height;
        this.threadNumber = threadNumber;
        this.filename = filename.trim().isEmpty() ? "zad20.png" : filename ;
        this.quiet = quiet ;
    }

    //the fractal generate function
    void generate() {
        Timer timer = new Timer();
        timer.start();
        BufferedImage fractalImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        int maximum = 800;
        int[] plate = new int[maximum];

        for (int i = 0; i < maximum; i++) {
            plate[i] = Color.HSBtoRGB(i / 256.0f, 1, i / (i + 8.0f));
        }

        int piece = height / threadNumber;

        int leftover = this.height % piece;
        Thread[] threads = new Thread[threadNumber];

        for (int i = 0; i < threadNumber; i++) {
            int start = i * piece;
            int end = (i + 1) * piece;

            if (i == threadNumber - 1) {
                start += leftover;
            }

            FractalRunnable fractal = new FractalRunnable(plate, fractalImage, start, end, i + 1, quiet);
            threads[i] = new Thread(fractal);
            threads[i].start();
        }

        for (int i = 0; i < threadNumber; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                System.out.println("Error when joining threads: " + e);
            }
        }

        try {
            ImageIO.write(fractalImage, "png", new File(filename));
        } catch (Exception e) {
            System.out.println("Error when writing to file : " + e);
        }
        if (!quiet) {
            System.out.println("Execution time: " + timer.stop() + " millis for threads: " + threadNumber);
        }
    }
}
