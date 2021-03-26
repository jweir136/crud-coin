package cryptography.ecc;

import cryptography.FiniteField;
import java.math.BigInteger;

public class Point {
    private FiniteField a;
    private FiniteField b;
    private FiniteField x;
    private FiniteField y;

    public Point(FiniteField a, FiniteField b, FiniteField x, FiniteField y) throws Exception {
        this.a = a;
        this.b = b;
        this.x = x;
        this.y = y;

        FiniteField left = this.y.pow(2);
        FiniteField right = this.x.pow(3).add(this.a).multiply(this.x).add(this.b);

        if (!left.equals(right))
            throw new Exception("Error: (" + this.x + "," + this.y + ") is not on the curve");
    }

    public Point(FiniteField a, FiniteField b) throws Exception {
        this.a = a;
        this.b = b;
        this.x = null;
        this.y = null;
    }

    public FiniteField getA() {
        return this.a;
    }

    public FiniteField getB() {
        return this.b;
    }

    public FiniteField getX() {
        return this.x;
    }

    public FiniteField getY() {
        return this.x;
    }

    public boolean equals(Point other) {
        return (this.x.equals(other.getX()) && this.y.equals(other.getY()) && this.a.equals(other.getA()) && this.b.equals(other.getB()));
    }

    // does BigInteger do ops in place or return a new object?
    public Point add(Point other) throws Exception {
        if (!this.a.equals(other.getA()) || !this.b.equals(other.getB()))
            throw new Exception("Error: The points are not on the same curve");
        if (this.a.getPrime().compareTo(this.a.getPrime()) != 0 && this.x.getPrime().compareTo(this.y.getPrime()) != 0 && this.x.getPrime().compareTo(this.b.getPrime()) != 0)
            throw new Exception("Error: Primes are not equal");

        FiniteField ZERO = new FiniteField(BigInteger.valueOf(0), this.a.getPrime());


        if (this.x == null)
            return other;
        if (other.getX() == null)
            return this;
        if (this.x.equals(other.getX()) && !this.y.equals(other.getY()))
            return new Point(this.a, this.b);
        if (!this.x.equals(other.getX())) {
            FiniteField s = (other.getY().subtract(this.y)).divide(other.getX().subtract(this.x));
            FiniteField x = s.pow(2).subtract(this.x).subtract(other.getX());
            FiniteField y = s.multiply(this.x.subtract(other.getX())).subtract(this.y);
            return new Point(x, y, this.a, this.b);
        }
        if (this == other) {
            if (this.a.getPrime().compareTo(this.a.getPrime()) != 0 && this.x.getPrime().compareTo(this.y.getPrime()) != 0 && this.x.getPrime().compareTo(this.b.getPrime()) != 0)
                throw new Exception("Error: Primes are not equal");

            final FiniteField THREE = new FiniteField(BigInteger.valueOf(3), this.a.getPrime());
            final FiniteField TWO = new FiniteField(BigInteger.valueOf(2), this.a.getPrime());
            FiniteField s = (THREE.multiply(this.x.pow(2)).add(this.a)).divide(TWO.multiply(this.y));
            FiniteField x = s.pow(2).subtract(TWO.multiply(this.x));
            FiniteField y = s.multiply(this.x.subtract(x)).subtract(this.y);
            return new Point(x, y, this.a, this.b);
        }
        if (this == other && this.y.equals(ZERO.multiply(this.x)))
            return new Point(this.a, this.b);

        return this;
    }
}
