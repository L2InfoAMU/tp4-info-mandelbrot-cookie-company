package mandelbrot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComplexTest {
    private final Complex onePlusI = new Complex(1,1);
    private final Complex minusI = new Complex(0,-1);
    private final Complex minusOne = new Complex(-1,0);
    private final Complex oneMinusI = new Complex(1, -1);
    private final Complex twoI = new Complex(0,2);
    private final Complex two = new Complex(2,0);
    private final double real = -12;
    private final double imaginary = 10;


    @Test
    void testConstructor(){
        assertEquals(0., twoI.real, Helpers.EPSILON);
        assertEquals(2., twoI.imaginary, Helpers.EPSILON);
        assertEquals(1., oneMinusI.real, Helpers.EPSILON);
        assertEquals(-1., oneMinusI.imaginary, Helpers.EPSILON);
        assertEquals(2., two.real, Helpers.EPSILON);
        assertEquals(0., two.imaginary, Helpers.EPSILON);
    }

    @Test
    void testGetReal(){
        assertEquals(2., two.getReal(), Helpers.EPSILON);
        assertEquals(1., oneMinusI.getReal(), Helpers.EPSILON);
        assertEquals(-1., new Complex(-1,1).getReal(), Helpers.EPSILON);
        assertEquals(real, new Complex(real, imaginary).getReal(), Helpers.EPSILON);
    }

    @Test
    void testGetImaginary(){
        assertEquals(2., twoI.getImaginary(), Helpers.EPSILON);
        assertEquals(1., new Complex(-1, 1).getImaginary(), Helpers.EPSILON);
        assertEquals(-1., oneMinusI.getImaginary(), Helpers.EPSILON);
        assertEquals(imaginary, new Complex(real, imaginary).getImaginary(), Helpers.EPSILON);
    }

    @Test
    void testOne(){
        assertEquals(1., Complex.ONE.getReal());
        assertEquals(0., Complex.ONE.getImaginary());
    }

    @Test
    void testI(){
        assertEquals(0, Complex.I.getReal());
        assertEquals(1, Complex.I.getImaginary());
    }

    @Test
    void testZero(){
        assertEquals(0, Complex.ZERO.getReal());
        assertEquals(0, Complex.ZERO.getImaginary());
    }

    @Test
    void testNegate(){
        assertEquals(minusOne, Complex.ONE.negate());
        assertEquals(Complex.I, minusI.negate());
        assertEquals(new Complex(-1, 1), oneMinusI.negate());
        assertEquals(new Complex(real, imaginary), new Complex(-real,-imaginary).negate());
    }

    @Test
    void testReciprocal(){
        assertEquals(Complex.ONE, Complex.ONE.reciprocal());
        assertEquals(Complex.I, minusI.reciprocal());
        assertEquals(new Complex(0.5,0), two.reciprocal());
        assertEquals(new Complex(0.5,0.5), oneMinusI.reciprocal());
    }

    @Test
    void testReciprocalOfZero(){
        assertThrows(ArithmeticException.class, ()->Complex.ZERO.reciprocal());
    }

    @Test
    void testSubstract(){
        assertEquals(minusOne, Complex.ZERO.subtract(Complex.ONE));
        assertEquals(oneMinusI, Complex.ONE.subtract(Complex.I));
        assertEquals(new Complex(real-1,imaginary-1),
                new Complex(real, imaginary).subtract(onePlusI));
    }

    @Test
    void testDivide(){
        assertEquals(onePlusI, onePlusI.divide(Complex.ONE));
        assertEquals(new Complex(0.5, 0), Complex.ONE.divide(two));
        assertEquals(minusI,oneMinusI.divide(onePlusI));
    }

    @Test
    void testDivideByZero(){
        assertThrows(ArithmeticException.class, ()->Complex.ONE.divide(Complex.ZERO));
    }

    @Test
    void testConjugate(){
        assertEquals(Complex.ZERO, Complex.ZERO.conjugate());
        assertEquals(Complex.ONE, Complex.ONE.conjugate());
        assertEquals(onePlusI, oneMinusI.conjugate());
        assertEquals(new Complex(real, -imaginary), new Complex(real, imaginary).conjugate());
    }

    @Test
    void testRotation(){
        assertEquals(Complex.I, Complex.rotation(Math.PI/2));
        assertEquals(minusI, Complex.rotation(-Math.PI/2));
        assertEquals(Complex.ONE, Complex.rotation(0));
        assertEquals(new Complex(Math.sqrt(2)/2., Math.sqrt(2)/2.),
                Complex.rotation(Math.PI/4));
        assertEquals(new Complex(1./2., Math.sqrt(3)/2.),
                Complex.rotation(Math.PI/3));
    }

    @Test
    void testToString(){
        assertEquals("Complex{real=1.0, imaginary=-1.0}", oneMinusI.toString());
        assertEquals("Complex{real="+real+", imaginary="+imaginary+"}", new Complex(real, imaginary).toString());
    }

    @Test
    void testHashCode() {
        Complex c1 = new Complex(real, imaginary);
        Complex c2 = new Complex(real, imaginary);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testMakeReal() {
        Complex c = Complex.real(1.0);
        assertEquals(Complex.ONE, c);
        c = Complex.real(0.0);
        assertEquals(Complex.ZERO, c);
        c = Complex.real(5);
        assertEquals(new Complex(5,0), c);
    }
    @Test
    void testAdd() {
        Complex sum = Complex.ONE.add(Complex.ONE);
        assertEquals(new Complex(2,0), sum);
        sum = sum.add(sum);

        assertEquals(new Complex(4,0), sum);

        sum = new Complex(2,3);
        sum = sum.add(sum);

        assertEquals(new Complex(4,6), sum);
        assertEquals(onePlusI, Complex.ONE.add(Complex.I));
        assertEquals(two, Complex.ONE.add(Complex.ONE));
        assertEquals(twoI, Complex.I.add(Complex.I));
    }

    @Test
    void testSubtract(){
        Complex subtract = Complex.ONE.subtract(Complex.ONE);
        assertEquals(Complex.ZERO, subtract);
        subtract = subtract.subtract(subtract);

        assertEquals(Complex.ZERO, subtract);

        assertEquals(oneMinusI, Complex.ONE.subtract(Complex.I));
        assertEquals(minusOne, Complex.ZERO.subtract(Complex.ONE));
        assertEquals(minusI, Complex.ZERO.subtract(Complex.I));
    }

    @Test
    void testMultiply(){
        assertEquals(Complex.ZERO, Complex.ZERO.multiply(Complex.ONE));
//        assertEquals(Complex.ONE, Complex.ONE.multiply(Complex.ONE));
        assertEquals(new Complex(-3,4), new Complex(1,2).multiply(new Complex(1,2)));
    }

    @Test
    void testSqaredModulus() {
        assertEquals(4, two.squaredModulus());
        assertEquals(2, onePlusI.squaredModulus());
        assertEquals(2, oneMinusI.squaredModulus());
        assertEquals(8, new Complex(2,2).squaredModulus());
        assertEquals(10, new Complex(1,3).squaredModulus());
    }

    @Test
    void testModulus(){
        assertEquals(Math.sqrt(4), two.modulus());
        assertEquals(Math.sqrt(2), oneMinusI.modulus());
        assertEquals(Math.sqrt(2), onePlusI.modulus());
        assertEquals(Math.sqrt(8), new Complex(2,2).modulus());
        assertEquals(Math.sqrt(10), new Complex(1,3).modulus());
    }

    @Test
    void testScale(){
        assertEquals(Complex.ZERO, two.scale(0));
        assertEquals(Complex.ZERO, twoI.scale(0));
        assertEquals(new Complex(2,-2), oneMinusI.scale(2));
        assertEquals(two, Complex.ONE.scale(2));
        assertEquals(twoI, Complex.I.scale(2));
    }

    @Test
    void testPow(){
        assertEquals(new Complex(-3,4), new Complex(1,2).pow(2));
        assertEquals(Complex.ONE, Complex.ZERO.pow(0));
        assertEquals(Complex.ONE, onePlusI.pow(0));
        assertEquals(Complex.ONE, two.pow(0));
        assertEquals(onePlusI.multiply(onePlusI), onePlusI.pow(2));
        assertEquals(Complex.ONE, Complex.ONE.pow(2));
        assertEquals(Complex.I.multiply(Complex.I), Complex.I.pow(2));
        assertEquals(minusI.multiply(minusI), minusI.pow(2));
    }

    @Test
    void equals() {
        Complex c1 = new Complex(0,0);
        Complex c2 = new Complex(1,0);

        assertNotEquals(c1, c2);
        assertNotEquals(Complex.ZERO, Complex.ONE);
        assertEquals(Complex.ZERO, c1);
        assertEquals(Complex.ONE, c2);
    }
}