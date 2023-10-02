import java.io.File;

public class Driver {
    public static void main(String [] args) {
        double [] c1 = {1, -2, -1};
        int [] e1 = {0, 1, 3};
        Polynomial p1 = new Polynomial(c1, e1);  // Create Polynomial p1
        // 1 - 2x - x^3

        double [] c2 = {1, -0.5};
        int [] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);  // Create Polynomial p2
        // x - 0.5x^4

        double [] c3 = {4};
        int [] e3 = {3};
        Polynomial p3 = new Polynomial(c3, e3);  // Create Polynomial p3
        // 4x^3

        Polynomial s1 = p1.add(p3);  // Add p1 and p3
        System.out.println(s1.evaluate(1));  // Evaluate s1 at x = 1
        System.out.println(s1.evaluate(-2)); // Evaluate s1 at x = -2

        Polynomial s2 = p2.add(p1);  // Add p2 and p1
        System.out.println(s2.evaluate(1));  // Evaluate s2 at x = 1
        System.out.println(s2.evaluate(-2)); // Evaluate s2 at x = -2

        Polynomial m1 = p3.multiply(p2);  // Multiply p3 and p2
        Polynomial m2 = p2.multiply(p1);  // Multiply p2 and p1
        System.out.println(m1.evaluate(2));  // Evaluate m1 at x = 2
        System.out.println(m2.evaluate(-3)); // Evaluate m2 at x = -3

        // Save m1 to a file and load it back
        m2.saveToFile("f11");
        Polynomial f1 = new Polynomial(new File("f11"));
        System.out.println(f1.evaluate(2));
    }
}