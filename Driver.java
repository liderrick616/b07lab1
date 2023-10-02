
import java.util.Arrays;

public class Driver {
        public static void main(String[] args) {
            // Test cases for add() method
            testAddition();
            
            // Test cases for multiply() method
            testMultiplication();
        }
    
        public static void testAddition() {
            Polynomial poly1 = new Polynomial(new double[] { 1, 2 }, new int[] { 1, 0 });
            Polynomial poly2 = new Polynomial(new double[] { 1, -2 }, new int[] { 1, 0 });


            Polynomial result = poly1.add(poly2);
            System.out.println(Arrays.toString(result.coefficients));
            System.out.println(Arrays.toString(result.exponents));

            System.out.println("Addition Test:");
            System.out.println("Actual Result: " + result);
        }
    
        public static void testMultiplication() {
            Polynomial poly1 = new Polynomial(new double[] { 1, 2 }, new int[] { 1, 0 });
            Polynomial poly2 = new Polynomial(new double[] { 1, -2 }, new int[] { 1, 0 });
    
            Polynomial result = poly1.multiply(poly2);
            System.out.println(Arrays.toString(result.coefficients));
            System.out.println(Arrays.toString(result.exponents));
            System.out.println("Multiplication Test:");
            System.out.println("Actual Result: " + result);
        }
    }
    
    /*import java.io.IOException;
    import java.util.Scanner;
    import java.io.File;
    public class Driver {
        public static void main(String[] args) {
            testSaveToFile();
        }
    
        public static void testSaveToFile() {
            Polynomial poly = new Polynomial(new double[] { -0.3, -2 }, new int[] { 1, 1 });
            
            try {
                String filename = "polynomial.txt";
                poly.saveToFile(filename);
                System.out.println("Save to File Test:");
    
                // Read the saved file to check the content
                Scanner fileScanner = new Scanner(new File(filename));
                String content = fileScanner.nextLine();
                fileScanner.close();
    
                // Expected content: "6 -2 +5.5x^3"
                System.out.println("Expected Content");
                System.out.println("Actual Content: " + content);
    
                // Clean up: delete the created file
                File fileToDelete = new File(filename);
                if (fileToDelete.delete()) {
                    System.out.println("File deleted successfully.");
                } else {
                    System.err.println("Failed to delete the file.");
                }
    
            } catch (IOException e) {
                System.err.println("Error occurred while testing saveToFile: " + e.getMessage());
            }
        }
    }*/
    
