import org.apache.commons.math3.complex.Complex;

import java.awt.image.BufferedImage;


public class FractalRunnable implements Runnable {
    //the colors plate
    private int[] plate;
    //the image
    private BufferedImage fractalImage;
    private int start;
    private int end;
    //the threads count
    private int thread;
    private int maximum;
    private boolean quiet;


    public FractalRunnable(int[] plate, BufferedImage fractalImage, int start, int end, int thread, boolean quiet) {
        this.plate = plate;
        this.fractalImage = fractalImage;
        this.start = start;
        this.end = end;
        this.thread = thread;
        this.maximum = plate.length;
        this.quiet = quiet;
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.start();
        if (!quiet) {
            System.out.println("Started thread[" + thread + "]");
        }
        int width = fractalImage.getWidth();
        int height = fractalImage.getHeight();

        for (int i = 0; i < width; i++) {

            double real = (i - width / 2.0) * (4.0 / width);

            for (int j = start; j < end; j++) {
                double imaginary = (j - height / 2.0) * (4.0 / width);

                int stepCount = set(new Complex(real, imaginary));

                if (stepCount < maximum) {
                    fractalImage.setRGB(i, j, plate[stepCount]);
                } else {
                    fractalImage.setRGB(i, j, plate[0]);
                }
            }
        }
        if (!quiet) {
            System.out.println("Thread[" + thread + "] stopped. Time for execution: " + timer.stop() + " millis.");
        }
    }

    private int set(Complex number) {
        int stepCount = 0;
        //the escape radius
        double radius = 2.0;
        Complex z = new Complex(0.0, 0.0);

        while (z.abs() <= radius && stepCount < maximum) {
            //formula
            z = number.add((z.multiply(z)).multiply((z.multiply(z)).exp()));

            stepCount++;
        }
        return stepCount;
    }
}
