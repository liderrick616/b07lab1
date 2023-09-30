import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        this.coefficients = new double[] {0};
        this.exponents = new int[] {0};
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws IOException { //d.
        Scanner sc = new Scanner(file);
        String line = sc.nextLine();
        sc.close();

        String[] terms = line.split("(?=[-+])"); // split by positive and negative signs
        coefficients = new double[terms.length];
        exponents = new int[terms.length];

        for (int i = 0; i < terms.length; i++) {
            if (terms[i].contains("x")) {
                String[] parts = terms[i].split("x");
                coefficients[i] = parts.length > 0 && !parts[0].equals("-") && !parts[0].equals("+") ? Double.parseDouble(parts[0]) : (terms[i].startsWith("-") ? -1 : 1);
                exponents[i] = parts.length > 1 ? Integer.parseInt(parts[1].replace("^", "")) : 1;
            } else {
                coefficients[i] = Double.parseDouble(terms[i]);
                exponents[i] = 0;
            }
        }
    }

    public Polynomial add(Polynomial other) {
        int[] newExponents = concatenate(this.exponents, other.exponents);
        double[] newCoefficients = concatenate(this.coefficients, other.coefficients);
        for (int i = 0; i < newExponents.length; i++) {
            for (int j = i + 1; j < newExponents.length; j++) {
                if (newExponents[i] == newExponents[j]) {
                    newCoefficients[i] += newCoefficients[j];
                    newCoefficients[j] = 0;
                }
            } 
        }
        for (int i = 0; i < newCoefficients.length; i++) {
            double coefficient = newCoefficients[i];
            
            // Check if the coefficient is an integer
            if (coefficient == (int) coefficient) {
                newCoefficients[i] = (int) coefficient; // Round integer coefficients to integers
            }
        }
        return new Polynomial(newCoefficients, newExponents);
     
    }
    public Polynomial multiply(Polynomial other) {
        List<Double> coeffList = new ArrayList<>();
        List<Integer> expList = new ArrayList<>();

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                double productCoefficient = this.coefficients[i] * other.coefficients[j];
                int sumExponent = this.exponents[i] + other.exponents[j];

                if (productCoefficient == (int) productCoefficient) {
                    productCoefficient = (int) productCoefficient; // Round integer coefficients to integers
                }
            // Check if there is already a term with the same exponent
                int existingIndex = expList.indexOf(sumExponent);

                if (existingIndex != -1) {
                // Combine like terms by adding coefficients
                    coeffList.set(existingIndex,coeffList.get(existingIndex) + productCoefficient);
                } else {
                // Add the new term to the lists
                    coeffList.add(productCoefficient);
                    expList.add(sumExponent);
                }
            }
        }

        return new Polynomial(toArray(coeffList), toIntArray(expList));
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) < 1e-9;
    }
    public void saveToFile(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        StringBuilder sb = new StringBuilder();
    
        boolean lastWasOperator = false; // To keep track of the last character being an operator
    
        for (int i = 0; i < coefficients.length; i++) {
            double coefficient = coefficients[i];
            
            if (i == 0) {
                // The first term doesn't need a leading operator
                if (coefficient == (int)coefficient) {
                    sb.append((int)coefficient); // Output as integer if it's an integer
                } else {
                    sb.append(coefficient); // Output as double if it's not an integer
                }
            } else {
                // Handle operators and signs
                if (coefficient >= 0) {
                    if (lastWasOperator) {
                        if (coefficient == (int)coefficient) {
                            sb.append((int)coefficient); // Append positive integer coefficient without '+'
                        } else {
                            sb.append(coefficient); // Append positive double coefficient without '+'
                        }
                    } else {
                        if (coefficient == (int)coefficient) {
                            sb.append('+').append((int)coefficient); // Append '+' and positive integer coefficient
                        } else {
                            sb.append('+').append(coefficient); // Append '+' and positive double coefficient
                        }
                    }
                } else {
                    if (coefficient == (int)coefficient) {
                        sb.append((int)coefficient); // Append negative integer coefficient
                    } else {
                        sb.append(coefficient); // Append negative double coefficient
                    }
                }
            }
    
            if (exponents[i] != 0) {
                sb.append("x^").append(exponents[i]);
            }
    
            lastWasOperator = (coefficient >= 0); // Update lastWasOperator
        }
    
        writer.write(sb.toString());
        writer.close();
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        boolean hasTerms = false; //check non zero terms

        for (int i = 0; i < coefficients.length; i++) {
            double coefficient = coefficients[i];

        // Skip terms with coefficients of 0
            if (coefficient == 0.0) {
                continue;
            }

            if (hasTerms) {
                if (coefficient > 0) {
                    builder.append(" + ");
                } else {
                    builder.append(" "); // Space before the negative coefficient
                }
            }

            if (coefficient == (int) coefficient) {
                builder.append((int) coefficient); // Round integer coefficients to integers
            } else {
                builder.append(coefficient);
            }

            if (exponents[i] != 0) {
                builder.append("x");
                if (exponents[i] != 1) {
                    builder.append("^").append(exponents[i]);
                }
            }

            hasTerms = true;
        }

        // If all terms had coefficients of 0, return "0"
        if (!hasTerms) {
            return "0";
        }

        return builder.toString();
    }
    
    private static int[] concatenate(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static double[] concatenate(double[] a, double[] b) {
        double[] result = new double[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static double[] toArray(List<Double> list) {
        double[] result = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    private static int[] toIntArray(List<Integer> list) {
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

}
