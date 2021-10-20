import tester.*;

sealed interface Number {
    int numerator();
    int denominator();
    Number add(Number other);
    Number multiply(Number other);
    String toText();
    double toDouble();
}
record WholeNumber(int n) implements Number {
    public int numerator() { return n; }
    public int denominator() { return 1; }
    public Number add(Number other) {
        if(other.denominator() == 1) {
            return new WholeNumber(this.n + other.numerator());
        }
        else {
            int newNum = this.n * other.denominator() + other.numerator();
            return new Fraction(newNum, other.denominator());
        }
    }
    public Number multiply(Number other) {
        if(other.denominator() == 1) {
            return new WholeNumber(this.n * other.numerator());
        }
        else {
            return new Fraction(other.numerator() * this.n, other.denominator());
        }
    }

    public String toText() {
        return Integer.toString(this.n);
    }

    public double toDouble() {
        return this.n;
    }
}
record Fraction(int n, int d) implements Number {
    public int numerator() { return this.n; }
    public int denominator() { return this.d; }
    public Number add(Number other) {
        int newNum = this.n * other.denominator() + this.d * other.numerator();
        return new Fraction(newNum, other.denominator() * this.d);
    }
    public Number multiply(Number other) {
        return new Fraction(other.numerator() * this.n, other.denominator() * this.d);
    }
    public String toText() {
        return Integer.toString(this.n) + "/" + Integer.toString(this.d);
    }
    public double toDouble() {
        return this.n * 1.0 / this.d;
    }
}

class ExamplesNumbers {
    Number n1 = new WholeNumber(5);
    Number n2 = new WholeNumber(7);
    Number n3 = new Fraction(7, 2);
    Number n4 = new Fraction(1, 2);

    void testAdd(Tester t) {
        t.checkExpect(this.n1.add(this.n2).toDouble(), 12.0);
        t.checkExpect(this.n1.add(this.n3).toDouble(), 5 + 7.0/2.0);
        t.checkExpect(this.n3.add(this.n3).toDouble(), 7.0);
    }

    void testMultiply(Tester t) {
        t.checkExpect(this.n1.multiply(this.n4).toDouble(), 2.5);
        t.checkExpect(this.n3.multiply(this.n4).toDouble(), 7.0/4.0);
    }

    void testNumDem(Tester t) {
        t.checkExpect(this.n3.numerator(), 7);
        t.checkExpect(this.n1.numerator(), 5);
        t.checkExpect(this.n4.denominator(), 2);
        t.checkExpect(this.n2.denominator(), 1);
    }

    void testToString(Tester t) {
        t.checkExpect(this.n4.toText(), "1/2");
        t.checkExpect(this.n3.toText(), "7/2");
        t.checkExpect(this.n2.toText(), "7");
    }
    int result = switch(this.n1) {
            case WholeNumber n -> n.denominator();
            case Fraction f -> f.numerator();
    };
}