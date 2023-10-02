import java.io.File;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    double[] coeff;
    int[] expo;

    public Polynomial(){
        this.coeff = new double[]{0};
        this.expo = new int[]{0};
    }
    public Polynomial(double[] c, int[] e){
        this.coeff = c;
        this.expo = e;
    }

    public Polynomial(File f){
        try {
        Scanner scan = new Scanner(f);
        String s = scan.nextLine();
        String[] compo = s.split("(?=[-+])");
        int[] e_arr = new int[compo.length];
        double[] c_arr = new double[compo.length];

        for (int i = 0; i < compo.length; i++) {
            String c = compo[i];
            if (c.indexOf("x") == -1) {
                c_arr[i] = Double.parseDouble(c);
                e_arr[i] = 0;
            } else {
                if (c.charAt(0) == '+') {
                    c = c.split("\\+", 2)[1];
                }
                c_arr[i] = Double.parseDouble(c.split("x", 2)[0]);
                e_arr[i] = Integer.parseInt(c.split("x", 2)[1]);
            }
        }

        this.coeff = c_arr;
        this.expo = e_arr;
    } catch (Exception e) {
        this.coeff = new double[]{0};
        this.expo = new int[]{0};
    }
    }

    public Polynomial no_unused(double[] c, double[] e, int count){
        Polynomial poly = new Polynomial(new double[count], new int[count]);
        int x = 0;
        for (int i=0; i<e.length; i++){
            if(e[i]!=0.5){
                poly.coeff[x] = c[i];
                poly.expo[x] = (int)e[i];
                x++;
            }
        }
        return poly;
    }

    public int findMaxExpo(int[] e){
        int max_e = 0;
        for (int i=0; i<e.length; i++){
            if (e[i]>max_e){
                max_e = e[i];
            }
        }
        return max_e;
    }

    public Polynomial add(Polynomial p){
        if (p.coeff.length == 0){
            Polynomial n = new Polynomial(this.coeff, this.expo);
            return n;
        }
        int t_max = findMaxExpo(this.expo);
        int p_max = findMaxExpo(p.expo);
        int max_expo = -1;
        if (t_max > p_max){
            max_expo = t_max;
        } else {
            max_expo = p_max;
        }
        int expo_count = 0;
        double[] temp_c = new double[max_expo+1];
        double[] temp_e = new double[max_expo+1];
        for (int i=0; i<max_expo+1; i++){
            temp_e[i] = 0.5;
            temp_c[i] = 0.5;
        }
        for (int i=0; i<max_expo+1; i++){
            Boolean add_expo = false;
            for (int j=0; j<this.coeff.length; j++){
                if (this.expo[j]==i){
                    temp_c[i] = this.coeff[j];
                    temp_e[i] = this.expo[j];
                    add_expo = true;
                }
            }
            for (int j=0; j<p.coeff.length; j++){
                if (p.expo[j]==i){
                    if (add_expo){
                        temp_c[i] += p.coeff[j];
                    } else {
                        temp_c[i] = p.coeff[j];
                        temp_e[i] = p.expo[j];
                    }
                    add_expo = true;
                }
            }
            if (add_expo){
                expo_count++;
            }
        }
        int[] expo_arr = new int[expo_count];
        double[] coeff_arr = new double[expo_count];
        int x = 0;
        for (int i=0; i<max_expo+1; i++){
            if (temp_e[i]!=0.5){
                expo_arr[x]= (int)temp_e[i];
                coeff_arr[x]=temp_c[i];
                x++;
            }
        }
        Polynomial new_p = new Polynomial(coeff_arr, expo_arr);
        return new_p;
    }

    public double evaluate(double x){
        double ans = 0;
        for (int i=0; i< coeff.length; i++){
            ans += this.coeff[i]*Math.pow(x, this.expo[i]);
        }
        return ans;
    }

    public Boolean hasRoot(double x){
        if (this.evaluate(x) == 0){
            return true;
        }
        return false;
    }

    public Polynomial multiply(Polynomial p){
        if (this.expo == null| p.expo == null){
            return new Polynomial();
        }
        int l = this.expo.length + p.expo.length;
        int y = this.expo.length * p.expo.length;
        Polynomial temp = new Polynomial(new double[y], new int[y]);
        int largest_expo = -1;
        int c = 0;
        //temp poly, multiplied
        for (int i=0; i<this.coeff.length; i++){
            for (int j=0; j<p.coeff.length; j++){
                temp.coeff[c] = this.coeff[i]*p.coeff[j];
                temp.expo[c] = this.expo[i]+p.expo[j];
                if (temp.expo[c] > largest_expo){
                    largest_expo = temp.expo[c];
                }
                c++;
            }
        }
        //getting rid of redundant expo
        double[] expo_arr = new double[l*l];
        double[] coeff_arr = new double[l*l];
        for (int x=0; x<expo_arr.length; x++){
            expo_arr[x] = 0.5;
            coeff_arr[x] = 0;
        }
        for (int i=0; i<largest_expo+1; i++){
            for (int j=0; j<y; j++){
                if (i==temp.expo[j]){
                    coeff_arr[i] += temp.coeff[j];
                    expo_arr[i] = temp.expo[j];
                }
            }
        }
        int count = 0;
        for (int i=0; i<expo_arr.length; i++){
            if(expo_arr[i]!=0.5){
                count++;
            }
        }
        return no_unused(coeff_arr, expo_arr, count);
    }

    public void saveToFile(String s){
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(s));
            
            for (int i=0; i<this.coeff.length; i++){
                String st = "";
                String sign = "";
                if(this.coeff[i]>0){
                    sign = "+";
                //first num no sign before coeff
                    if (i==0){
                        sign = "";
                    }
                    if (this.expo[i]!=0){
                        if (this.coeff[i]!=1){
                            if (this.expo[i]==1){
                                st = st + sign + this.coeff[i]  + "x";
                            } else {
                                st = st + sign + this.coeff[i]  + "x" + this.expo[i];
                            }
                        } else {
                            st = st + sign + "x" + this.expo[i];
                        }
                    } else {
                        st = st + sign + this.coeff[i];
                    }
                }
                w.write(st);
            }
            
            w.close();
        } catch (Exception e){
        }
    }
}
