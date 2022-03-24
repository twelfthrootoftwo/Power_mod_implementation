import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Companions {

    public static ArrayList<Integer> naiveCompanions(int p) {
        assert p>1;
        ArrayList<Integer> companionCollection=new ArrayList<>();


        for(int g=1;g<p;g++){
            int[] modCollection=new int[p-1];


            for(int a=1;a<p;a++) {
                int newMod= (int) (Math.pow(g,a)%p);
                modCollection[a-1]=newMod;
            }

            Arrays.sort(modCollection);
            boolean isCompanion=true;
            for(int i=0;i<p-1;i++) {
                if(modCollection[i]!=i+1) isCompanion=false;
            }

            if(isCompanion) companionCollection.add((Integer) g);
        }

        System.out.println(companionCollection);
        return companionCollection;
    }

    public static int enhancedModulus(double p, double g, double s) {
        ArrayList<Integer> powsTwo=new ArrayList<>();
        ArrayList<Integer> powMods=new ArrayList<>();
        int i=1;
        double calcLimit=(double) p*p;

        //confirm target calc output
        System.out.println("Maximum calculated number: "+calcLimit);

        //construct powers of 2 and corresponding mods
        do {
            powsTwo.add((Integer) i);
            double powMod=(double) (Math.pow(g,i));
            powMod=powMod%p;
            powMods.add(powMod);
            i*=2;
        } while(i<=p);

        for(i=0;i<powsTwo.size();i++){
            System.out.println(powsTwo.get(i) +": "+powMods.get(i));
        }

        //split s into powers of 2
        int[] sAsPowerSum=new int[powsTwo.size()];
        int sLeft=s;
        String powRecord=s+"as powers of two: ";
        for(i=powsTwo.size()-1;i>=0;i--) {
            if(powsTwo.get(i)<sLeft) {
                sAsPowerSum[i]=1;
                sLeft-=powsTwo.get(i);
                powRecord+="2^"+i+" + ";
            } else sAsPowerSum[i]=0;
        }
        System.out.println(powRecord);

        //multiply mods together
        int modProduct=1;
        for(i=0;i<powsTwo.size();i++) {
            modProduct=modProduct*powMods.get(i)*sAsPowerSum[i];
            System.out.println("Mod product "+i+": "+modProduct);
        }

        return modProduct;

    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        //int p=Integer.parseInt(scanner.nextLine());
        ArrayList<Integer> companions=naiveCompanions(11);

        int mod=enhancedModulus(104651,24578,100418);
    }
}
