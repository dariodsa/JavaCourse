package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class {@link RayCasterParallel} runs exactly the same task as the class
 * {@link RayCaster}, but this implementation is parallel so it will be faster
 * on most of the systems which can run java files.
 * 
 * @author dario
 *
 */
public class RayCasterParallel {
    
    
   /**
     * Main method which shows and run main program.
     * 
     * @param args
     *            not used
     */
    public static void main(String[] args) {
        RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
                new Point3D(0, 0, 10), 20, 20);
    }

    private static IRayTracerProducer getIRayTracerProducer() {
        return new IRayTracerProducer() {
            @Override
            public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
                    int width, int height, long requestNo, IRayTracerResultObserver observer) {
                System.out.println("Započinjem izračune ...");
                short[] red = new short[width * height];
                short[] green = new short[width * height];
                short[] blue = new short[width * height];

                Point3D zAxis = view.sub(eye).normalize();

                Point3D yAxis = viewUp.normalize().sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp))).normalize();

                Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

                Point3D G = view;

                Point3D screenCorner = G.sub(xAxis.scalarMultiply(horizontal / 2))
                        .add(yAxis.scalarMultiply(vertical / 2));

                Scene scene = RayTracerViewer.createPredefinedScene();

                ForkJoinPool pool = new ForkJoinPool();
                
                pool.invoke(new Job(0, height - 1, width, height, xAxis, yAxis, zAxis, screenCorner, red, green, blue,
                        horizontal, vertical, scene, eye));

                pool.shutdown();
                System.out.println("Pool zatvoren.");
                System.out.println("Izracuni gotovi...");
                observer.acceptResult(red, green, blue, requestNo);
                System.out.println("Dojava gotova...");
            }

        };
    }

    /**
     * Class {@link Job} encapsulate one job which can be divided into two, that is
     * why it extends {@link RecursiveAction}, so execution of it will take less
     * then not dividing it into smaller jobs. It will need all necessary data in
     * the constructor.
     * 
     * @author dario
     *
     */
    private static class Job extends RecursiveAction {

        /**
         * 
         */
        private static final long serialVersionUID = 5171053293701838002L;
        /**
         * minimal difference in order to map one job to two
         */
        private static final int DIFFERENCE = 8;
        /**
         * minimal value of y
         */
        private int minY;
        /**
         * maximal value of y
         */
        private int maxY;
        /**
         * screen width
         */
        private int width;
        /**
         * screen height
         */
        private int height;
        /**
         * xAxis
         */
        private Point3D xAxis;
        /**
         * yAxis
         */
        private Point3D yAxis;
        /**
         * zAxis
         */
        private Point3D zAxis;
        /**
         * corner of screen , its location
         */
        private Point3D screenCorner;
        /**
         * value of red parameter
         */
        private short[] red;
        /**
         * value of green parameter
         */
        private short[] green;
        /**
         * values of blue parameter
         */
        private short[] blue;
        /**
         * horizontal width of observed space
         */
        private double horizontal;
        /**
         * vertical height of observed space
         */
        private double vertical;
        /**
         * location of eye, viewer location
         */
        private Point3D eye;
        /**
         * scene, on which will result be drawn, light object and non light be collected
         */
        private Scene scene;

        /**
         * Constructs new Job which will be divided in two if the job is too big. It
         * will be divided in two equal part.
         * 
         * @param minY
         *            minimal value of y
         * @param maxY
         *            maximal value of y
         * @param width
         *            screen width
         * @param height
         *            screen height
         * @param xAxis
         *            x axis of vector system
         * @param yAxis
         *            y axis of vector system
         * @param zAxis
         *            z axis of vector system
         * @param screenCorner
         *            needed for point remapping
         * @param red
         *            short array of red values
         * @param green
         *            short array of green values
         * @param blue
         *            short array of blue values
         * @param horizontal
         *            horizontal width of observed space
         * @param vertical
         *            vertical height of observed space
         * @param scene
         *            scene on which result will be shown
         * @param eye
         *            location of observer, so called eye
         */
        public Job(int minY, int maxY, int width, int height, Point3D xAxis, Point3D yAxis, Point3D zAxis,
                Point3D screenCorner, short[] red, short[] green, short[] blue, double horizontal, double vertical,
                Scene scene, Point3D eye) {
            this.minY = minY;
            this.maxY = maxY;
            this.width = width;
            this.height = height;
            this.xAxis = xAxis;
            this.yAxis = yAxis;
            this.zAxis = zAxis;
            this.screenCorner = screenCorner;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.horizontal = horizontal;
            this.vertical = vertical;
            this.eye = eye;
            this.scene = scene;
        }

        @Override
        protected void compute() {
            if (maxY - minY < DIFFERENCE) {
                computeDirect();
                return;
            }

            invokeAll(
                    new Job(minY, (maxY + minY) / 2, width, height, xAxis, yAxis, zAxis, screenCorner, red, green, blue,
                            horizontal, vertical, scene, eye),
                    new Job((maxY + minY) / 2 + 1, maxY, width, height, xAxis, yAxis, zAxis, screenCorner, red, green,
                            blue, horizontal, vertical, scene, eye));
        }

        /**
         * Calculates all data ( red, green, blue values) and sets it as the result. It
         * doesn't divide this job into smaller one.
         */
        private void computeDirect() {
            int offset = minY * width;
            for (int y = minY; y <= maxY; ++y) {
                for (int x = 0; x < width; ++x) {
                    Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((double) x / (width - 1) * horizontal))
                            .sub(yAxis.scalarMultiply((double) y / (height - 1) * vertical));

                    Ray ray = Ray.fromPoints(eye, screenPoint);
                    short[] rgb = new short[3];

                    tracer(scene, ray, screenPoint, rgb);

                    red[offset] = (short) (rgb[0] > 255 ? 255 : rgb[0]);
                    green[offset] = (short) (rgb[1] > 255 ? 255 : rgb[1]);
                    blue[offset] = (short) (rgb[2] > 255 ? 255 : rgb[2]);

                    ++offset;
                }
            }

        }

    }

    protected static void tracer(Scene scene, Ray ray, Point3D screenPoint, short[] rgb) {
        rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;

        RayIntersection closest = findClosestIntersection(scene, ray);

        if (closest == null) {
            return;
        }

        rgb[0] = 15;
        rgb[1] = 15;
        rgb[2] = 15;

        for (LightSource light : scene.getLights()) {

            Ray lightRay = Ray.fromPoints(closest.getPoint(), light.getPoint());

            if (findClosestIntersection(scene, lightRay) != null)
                continue;
            // findClosestIntersection(scene, lightRay).getDistance() <
            // closest.getDistance()) {

            Point3D norm = closest.getNormal();

            double x = Math.max(0, lightRay.direction.normalize().scalarProduct(norm));

            double koef = 2.0 * lightRay.direction.normalize().scalarProduct(norm);

            Point3D R = norm.scalarMultiply(koef).sub(lightRay.direction.normalize());

            double y = R.scalarProduct(ray.direction.negate());
            y = Math.max(0, y);
            y = Math.pow(y, closest.getKrn());

            rgb[0] += (double) light.getR() * (closest.getKdr() * x);
            rgb[1] += (double) light.getG() * (closest.getKdg() * x);
            rgb[2] += (double) light.getB() * (closest.getKdb() * x);

            rgb[0] += (double) light.getR() * (closest.getKrr() * y);
            rgb[1] += (double) light.getG() * (closest.getKrg() * y);
            rgb[2] += (double) light.getB() * (closest.getKrb() * y);

        }
    }

    /**
     * Fins the closest dot which is on the given intersection. It sets up point of
     * intersection, distance from beginning of ray. It returns null if there is no
     * one.
     * 
     * @param scene
     *            from which we will watch objects for intersection
     * @param ray
     *            ray for which we are looking intersects
     * @return {@link RayIntersection} status
     */
    private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {

        RayIntersection solution = null;

        for (GraphicalObject object : scene.getObjects()) {

            RayIntersection intersection = object.findClosestRayIntersection(ray);

            if (intersection == null)
                continue;

            if (solution == null) {
                solution = intersection;
            } else if (solution.getDistance() > intersection.getDistance()) {
                solution = intersection;
            }
        }
        return solution;
    }
}
