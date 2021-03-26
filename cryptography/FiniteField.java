package cryptography;

import java.math.BigInteger;
import java.lang.ExceptionInInitializerError;

public class FiniteField {
    private BigInteger prime;
    private BigInteger num;

    public FiniteField(BigInteger num, BigInteger prime) throws ExceptionInInitializerError {
        if (num.compareTo(BigInteger.ZERO) < 0 || num.compareTo(prime) >= 0)
            throw new ExceptionInInitializerError("Error: Invalid FiniteField Input. num must be in range [0,prime)");

        this.prime = prime;
        this.num = num;        
    }

    public BigInteger getPrime() {
        return this.prime;
    }

    public BigInteger getNum() {
        return this.num;
    }

    public FiniteField add(FiniteField other)  throws Exception {
        if (this.prime != other.getPrime())
            throw new Exception("Error: Invalid input. FiniteFields must have same value of prime");

        BigInteger newNum = (this.num.add(other.getNum())).mod(this.prime);
        return new FiniteField(newNum, this.prime);
    }

    public FiniteField multiply(FiniteField other) throws Exception {
        if (this.prime != other.getPrime())
            throw new Exception("Error: Invalid Input. FiniteFields must have same value of prime");

        BigInteger newNum = (this.num.multiply(other.getNum())).mod(this.prime);
        return new FiniteField(newNum, this.prime);
    }

    public boolean equals(FiniteField other) {
        return (this.prime.compareTo(other.getPrime()) == 0 && this.num.compareTo(other.getNum()) == 0);
    }

    public FiniteField subtract(FiniteField other) throws Exception {
        if (this.prime.compareTo(other.getPrime()) != 0)
            throw new Exception("Error: Invalid Input. FiniteFields must have same value of prime");

        BigInteger newNum = (this.num.subtract(other.getNum())).mod(this.prime);
        return new FiniteField(newNum, this.prime);
    }

    public FiniteField pow(int exp) {
        BigInteger bigIntExp = BigInteger.valueOf(exp);
        BigInteger n = bigIntExp.mod(this.prime.subtract(BigInteger.ONE));
        BigInteger newNum = this.num.pow(n.intValueExact()).mod(this.prime);

        return new FiniteField(newNum, this.prime);
    }

    public FiniteField divide(FiniteField other) throws Exception {
        if (this.prime.compareTo(other.getPrime()) != 0)
            throw new Exception("Error: Invalid Input. FiniteFields must have same value of prime");

        BigInteger a = other.getNum().pow(this.prime.intValueExact()-2).mod(this.prime);
        BigInteger newNum = this.num.multiply(a);

        return new FiniteField(newNum, this.prime);
    }

    @Override
    public String toString() {
        return "FiniteField({prime: " + this.prime + " , num: " + this.num + "})";
    }
}