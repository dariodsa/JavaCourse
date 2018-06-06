package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class {@link NewtonProducer} is responsible for accepting task of getting
 * polynom, deriving it and others so it can calculate given result and
 * represent data. It's obligation is to create separated small jobs and to give
 * it to thread to process them.
 * <a href="https://en.wikipedia.org/wiki/Newton%27s_method"> Newton-Raphson
 * iteration</a>
 * 
 * @author dario
 *
 */
public class NewtonProducer implements IFractalProducer {

    /**
     * number of threads in pool
     */
    private static final int NUM_OF_THREADS = 8 * Runtime.getRuntime().availableProcessors();
    /**
     * pool of working threads
     */
    private ExecutorService pool;

    /**
     * polynom which will be used in drawing process
     */
    private ComplexPolynomial polynom;
    /**
     * {@link ComplexRootedPolynomial} version of polynom mentioned before
     */
    private ComplexRootedPolynomial rootedPolynom;

    /**
     * Constructs {@link NewtonProducer} with {@link ComplexRootedPolynomial} as the
     * parameter which will be used in drawing process.
     * 
     * @param polynom
     *            will be used in drawing process
     */
    public NewtonProducer(ComplexRootedPolynomial polynom) {

        this.rootedPolynom = polynom;
        this.polynom = polynom.toComplexPolynom();

        this.pool = Executors.newFixedThreadPool(NUM_OF_THREADS, new ThreadFactory() {

            @Override
            public Thread newThread(Runnable runnable) {
                Thread t = new Thread(runnable);
                t.setDaemon(true);
                return t;
            }
        });
    }

    @Override
    public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
            IFractalResultObserver observer) {

        System.out.println("Zapocinjem izracun ....");
        int brojTraka = 16 * 16;
        int brojYPoTraci = height / brojTraka;
        short[] data = new short[height * width];

        List<Future<Void>> results = new ArrayList<>();
        for (int i = 0; i < brojTraka; i++) {
            int yMin = i * brojYPoTraci;
            int yMax = (i + 1) * brojYPoTraci - 1;
            if (i == brojTraka - 1) {
                yMax = height - 1;
            }
            Job posao = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data);
            results.add(pool.submit(posao));
        }

        for (Future<Void> posao : results) {
            try {
                posao.get();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
        }

        pool.shutdown();
        System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
        observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);

    }

    /**
     * Class {@link Job} implements {@link Callable} interface parameterized with
     * Void. It encapsulate job which need to be done giving it all necessary data
     * and region which should be calculate.
     * 
     * @author dario
     *
     */
    public class Job implements Callable<Void> {

        /**
         * maximal number of iterations
         */
        private static final int MAX_ITERATION = 16 * 16;
        /**
         * convergence threshold
         */
        private static final double THRESHOLD = 1E-3;
        /**
         * acceptable root distance
         */
        private static final double ACCEPTABLE_ROOT_DISTANCE = 0.002;
        /**
         * minimal value of real part
         */
        private double reMin;
        /**
         * maximal value of real part
         */
        private double reMax;
        /**
         * minimal value of imaginary part
         */
        private double imMin;
        /**
         * maximal value of imaginary part
         */
        private double imMax;
        /**
         * width of screen
         */
        private int width;
        /**
         * height of screen
         */
        private int height;
        /**
         * minimal value of y, from which I need to calculate data
         */
        private int yMin;
        /**
         * maximal value of y, to which I need to calculate data
         */
        private int yMax;
        /**
         * reference on which I will set my result, short array
         */
        private short[] data;

        /**
         * Creates {@link Job} with specific data needed for doing the job done.
         * 
         * @param reMin
         *            minimal value of real part
         * @param reMax
         *            maximal value of real part
         * @param imMin
         *            minimal value of imaginary part
         * @param imMax
         *            maximal value of imaginary part
         * @param width
         *            width of screen
         * @param height
         *            height of screen
         * @param yMin
         *            minimal value of y, from
         * @param yMax
         *            maximal value of y, to
         * @param data
         *            data array on which data, results be saved
         */
        public Job(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax,
                short[] data) {

            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.data = data;
        }

        @Override
        public Void call() throws Exception {

            int offset = yMin * width;

            for (int y = yMin; y <= yMax; ++y) {
                for (int x = 0; x < width; ++x) {

                    Complex c = mapToComplex(x, y);

                    ComplexPolynomial derived = polynom.derive();

                    double module = 0;
                    int iter = 0;
                    do {
                        Complex numerator = polynom.apply(c);
                        Complex denominator = derived.apply(c);
                        Complex fraction = numerator.divide(denominator);
                        Complex cn = c.sub(fraction);

                        module = cn.sub(c).module();
                        ++iter;
                        c = cn;

                    } while (module > THRESHOLD && iter < MAX_ITERATION);
                    int index = rootedPolynom.indexOfClosestRootFor(c, ACCEPTABLE_ROOT_DISTANCE);

                    if (index == -1) {
                        data[offset++] = 0;
                    } else {
                        data[offset++] = (short) (index + 1);
                    }

                }
            }

            return null;
        }

        /**
         * Maps two integer which are coordinates on screen to the complex coordinate
         * system with different scale, minimal and maximal value.
         * 
         * @param x
         *            x parameter
         * @param y
         *            y parameter
         * @return {@link Complex} value mapped from (x,y)
         */
        private Complex mapToComplex(int x, int y) {
            double real = reMin + ((double) (reMax - reMin) / (double) (width - 1)) * x;
            double imag = imMin + ((double) (imMax - imMin) / (double) (height - 1)) * (height - y - 1.0);
            return new Complex(real, imag);
        }

    }
}
