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

    public static long enhancedModulus(long p, long g, long s) {
        long[] powsTwo=new long[Math.toIntExact(p)];
        long[] powMods=new long[Math.toIntExact(p)];
        int i=1;
        int j=0;
        long calcLimit=(long) p*p;
        int targetMod=3190;
        boolean foundRightMod=false;
        long rightMod=0;

        //confirm target calc output
        System.out.println("Maximum calculated number: "+calcLimit);

        //get values of g^t mod p
        long lastMod=1;
        long[] modCollection=new long[(int) p];
        for(i=0;i<p;i++) {
            lastMod=lastMod*g;
            lastMod=lastMod%p;
            System.out.println(lastMod);
            modCollection[i]=lastMod;
            if(lastMod==targetMod) {
                foundRightMod=true;
                rightMod=i+1;
            }
        }

        //construct powers of 2 and gather corresponding mods
        i=1;
        do {
            powsTwo[j]= i;
            powMods[j]=modCollection[i-1];
            System.out.println("This pow2: "+powsTwo[j]);
            i*=2;
            j++;
        } while(i<=p);
        j--;

        for(i=0;i<j;i++){
            System.out.println(powsTwo[i] +": "+powMods[i]);
        }

        //split s into powers of 2
        long[] sAsPowerSum=new long[powsTwo.length];
        long sLeft=s;
        String powRecord=s+" as powers of two: ";
        for(i=j;i>=0;i--) {
            if(powsTwo[i]<=sLeft) {
                sAsPowerSum[i]=1;
                sLeft-=powsTwo[i];
                System.out.println("Power: "+i+", remaining: "+sLeft);
                powRecord+="2^"+i+" + ";
            } else sAsPowerSum[i]=0;
        }
        System.out.println(powRecord);

        //multiply mods together
        long modProduct=1;
        for(i=0;i<j;i++) {
            System.out.println("modProduct: "+modProduct+", powMods: "+powMods[i]+", powerSum: "+sAsPowerSum[i]);
            if(sAsPowerSum[i]==1) {
                modProduct=modProduct*powMods[i];
                modProduct=modProduct%p;
            }
            System.out.println("Mod product "+i+": "+modProduct);
        }

        if(foundRightMod) {
            System.out.println("Found s with the correct mod! It's at power "+rightMod);
        }
        return modProduct;


    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        //int p=Integer.parseInt(scanner.nextLine());
        ArrayList<Integer> companions=naiveCompanions(11);

        long mod=enhancedModulus(5,2,6);
        long mod2=enhancedModulus(104651,24578,100418);
    }
}
