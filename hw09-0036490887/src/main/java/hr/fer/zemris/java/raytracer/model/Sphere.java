package hr.fer.zemris.java.raytracer.model;

/**
 * Class {@link Sphere} encapsulate sphere which will appear in the graphical
 * interface. It contains its center of location and radius. It extends
 * {@link GraphicalObject} so it offers methods for detecting the intersection,
 * useful in detecting the color strength.
 * 
 * @author dario
 *
 */
public class Sphere extends GraphicalObject {

    /**
     * center of sphere
     */
    private Point3D center;
    /**
     * sphere's radius
     */
    private double radius;
    /**
     * diffuse component of red color, the ratio of reflection of the diffuse term
     * of incoming light
     */
    private double kdr;
    /**
     * diffuse component of green color, the ratio of reflection of the diffuse term
     * of incoming light
     */
    private double kdg;
    /**
     * diffuse component of blue color, the ratio of reflection of the diffuse term
     * of incoming light
     */
    private double kdb;
    /**
     * reflective component of red color
     */
    private double krr;
    /**
     * reflective component of green color
     */
    private double krg;
    /**
     * reflective component of blue color
     */
    private double krb;
    /**
     * shininess constant, which is larger for surfaces that are smoother and more
     * mirror-like, when this constant is large the specular highlight is small
     */
    private double krn;

    /**
     * Constructs {@link Sphere} with all needed mathematical attributes such as
     * center location, radius and some graphical attributes needed for drawing it.
     * 
     * @param center
     *            {@link Point3D} sphere's center location
     * @param radius
     *            raduis of sphere
     * @param kdr
     *            diffuse component of red color
     * @param kdg
     *            diffuse component of green color
     * @param kdb
     *            diffuse component of blue color
     * @param krr
     *            reflective component of red color
     * @param krg
     *            reflective component of green color
     * @param krb
     *            reflective component of blue color
     * @param krn
     *            shininess constant
     */
    public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
            double krn) {

        this.center = center;
        this.radius = radius;
        this.kdr = kdr;
        this.kdg = kdg;
        this.kdb = kdb;
        this.krr = krr;
        this.krg = krg;
        this.krb = krb;
        this.krn = krn;
    }

    @Override
    public RayIntersection findClosestRayIntersection(Ray ray) {

        double k = ray.direction.x;
        double q = ray.direction.y;
        double m = ray.direction.z;

        double b = 2 * k * (ray.start.x - center.x) + 2 * q * (ray.start.y - center.y)
                + 2 * m * (ray.start.z - center.z);
        double a = k * k + q * q + m * m;
        double c = (ray.start.x - center.x) * (ray.start.x - center.x)
                + (ray.start.y - center.y) * (ray.start.y - center.y)
                + (ray.start.z - center.z) * (ray.start.z - center.z) - radius * radius;

        // System.out.println(b*b-4*a*c);
        if (b * b - 4 * a * c >= 0) {

            double t0 = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
            double t1 = (-b - Math.sqrt(b * b - 4 * a * c)) / (2 * a);
            double sol;
            if (t0 > 0 && t0 < t1) {
                sol = t0;
            } else if (t1 > 0) {
                sol = t1;
            } else
                return null;

            return new Intersection(new Point3D(ray.start.x + sol * ray.direction.x,
                    ray.start.y + sol * ray.direction.y, ray.start.z + sol * ray.direction.z),
                    ray.direction.normalize().norm() * (sol), false);
        }
        return null;
    }

    /**
     * Class {@link Intersection} extends {@link RayIntersection} and offers
     * encapsulation for the intersection which might be found in
     * findClosestRayIntersection() method.
     * 
     * @author dario
     *
     */
    public class Intersection extends RayIntersection {

        protected Intersection(Point3D point, double distance, boolean outer) {
            super(point, distance, outer);
        }

        @Override
        public double getKdb() {
            return kdb;
        }

        @Override
        public double getKdg() {
            return kdg;
        }

        @Override
        public double getKdr() {
            return kdr;
        }

        @Override
        public double getKrb() {
            return krb;
        }

        @Override
        public double getKrg() {
            return krg;
        }

        @Override
        public double getKrn() {
            return krn;
        }

        @Override
        public double getKrr() {
            return krr;
        }

        @Override
        public Point3D getNormal() {
            return this.getPoint().sub(center).normalize();
        }

    }
}
