public class Polynomial {
    double[] coeff;

    public Polynomial(){
        this.coeff = new double[]{0};
    }
    public Polynomial(double[] c){
        this.coeff = c;
    }

    public Polynomial add(Polynomial p){
        int h, l;
        if (p.coeff.length > this.coeff.length){
            h = p.coeff.length;
            l = this.coeff.length;
        } else {
            h = this.coeff.length;
            l = p.coeff.length;
        }
        double[] n = new double[h];
        for (int i=0; i<l; i++){
            n[i] = this.coeff[i]+ p.coeff[i];
        }
        if (p.coeff.length > this.coeff.length){
            for (int j=l; j<h; j++){
            n[j] = p.coeff[j];
            }
        } else {
            for (int j=l; j<h; j++){
            n[j] = this.coeff[j];
            }
        }
        Polynomial new_p = new Polynomial(n);
        return new_p;
    }

    public double evaluate(double x){
        double ans = 0;
        for (int i=0; i< coeff.length; i++){
            double p = 1;
            for (int j=i; j>0; j--){
                p = p*x;
            }
            ans += coeff[i] * p;
        }
        return ans;
    }

    public Boolean hasRoot(double x){
        if (this.evaluate(x) == 0){
            return true;
        }
        return false;
    }
}
