public class Polynomial {

    // i
    private double[] coefficients;

    // ii
    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    // iii
    public Polynomial(double[] coefficients) {
        if(coefficients == null || coefficients.length == 0) {

        }
        else{
            this.coefficients = new double[coefficients.length];
            for (int i = 0; i < coefficients.length; i++)
            {
                this.coefficients[i] = coefficients[i];
            }
        }      
    }

    // iv. 
    public Polynomial add(Polynomial other) {
        double[] longer, shorter;

        if(this.coefficients.length > other.coefficients.length) {
            longer = this.coefficients;
            shorter = other.coefficients;
        } else {
            longer = other.coefficients;
            shorter = this.coefficients;
        }
        double[] result = new double[longer.length];
        for(int i = 0; i < shorter.length; i++) {
            result[i] = shorter[i] + longer[i];
        }
        for(int i = shorter.length; i < longer.length; i++) {
            result[i] = longer[i];
        }
        return new Polynomial(result);
    }

    public double evaluate(double x) {
        double result = 0; // store final result.
        double currentPowerOfX = 1; 
        for (int i = 0; i < coefficients.length; i++) {
            double termCoefficient = coefficients[i];
            double termValue = termCoefficient * currentPowerOfX;
            result += termValue;
            currentPowerOfX *= x;
        }
        return result;
    }
    // vi.
    public boolean hasRoot(double x) {
        double valueAtX = evaluate(x);
        if(valueAtX < 0){
            valueAtX = valueAtX * -1;
        }
        double tolerance = 1e-9;
        if (valueAtX < tolerance) {
            return true;  // x is a root
        }

        return false; // x is not a root
    }
}
