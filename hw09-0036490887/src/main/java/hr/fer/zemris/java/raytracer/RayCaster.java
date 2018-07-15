package hr.fer.zemris.java.raytracer;

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
 * Class {@link RayCaster} implements all necessary methods needed to run third
 * problem from the ninth homework. Methods implements
 * <a href="https://en.wikipedia.org/wiki/Phong_reflection_model"> Phong
 * reflection model</a>.
 * 
 * @author dario
 *
 */
public class RayCaster {
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

                System.out.println(height);

                Point3D zAxis = view.sub(eye).normalize();

                Point3D yAxis = viewUp.normalize().sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp))).normalize();

                Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

                Point3D G = view;

                Point3D screenCorner = G.sub(xAxis.scalarMultiply(horizontal / 2))
                        .add(yAxis.scalarMultiply(vertical / 2));

                Scene scene = RayTracerViewer.createPredefinedScene();

                short[] rgb = new short[3];
                int offset = 0;
                for (int y = 0; y < height; ++y) {
                    for (int x = 0; x < width; ++x) {
                        Point3D screenPoint = screenCorner
                                .add(xAxis.scalarMultiply((double) x / (width - 1) * horizontal))
                                .sub(yAxis.scalarMultiply((double) y / (height - 1) * vertical));

                        Ray ray = Ray.fromPoints(eye, screenPoint);

                        tracer(scene, ray, screenPoint, rgb);

                        red[offset] = (short) (rgb[0] > 255 ? 255 : rgb[0]);
                        green[offset] = (short) (rgb[1] > 255 ? 255 : rgb[1]);
                        blue[offset] = (short) (rgb[2] > 255 ? 255 : rgb[2]);

                        ++offset;
                    }
                }

                System.out.println("Izracuni gotovi...");
                observer.acceptResult(red, green, blue, requestNo);
                System.out.println("Dojava gotova...");
            }

        };
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

            if (findClosestIntersection(scene, lightRay) != null
                    && findClosestIntersection(scene, lightRay).getDistance() < closest.getDistance())
                continue;

            Point3D norm = closest.getNormal();
            Point3D lightRayNorm = lightRay.direction.normalize();
            double x = Math.max(0, lightRayNorm.scalarProduct(norm));

            double koef = 2.0 * lightRayNorm.scalarProduct(norm);

            Point3D R = norm.scalarMultiply(koef).sub(lightRayNorm);

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
