import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Companions {

    /**PART A)
     *
     * Finds all companions of prime integer p, using naive brute force method.
     * @param p - prime to find companions for
     * @return a list of integers which are companions of p
     */
    public static ArrayList<Integer> naiveCompanions(int p) {
        assert p>1;
        ArrayList<Integer> companionCollection=new ArrayList<>();

        //Iterate over all natural numbers less than p
        for(int g=1;g<p;g++){
            int[] modCollection=new int[p-1];

            //Iterate (again) over all natural numbers less than p
            for(int a=1;a<p;a++) {
                //Get the mod(p) of g^a. This calculates g^a first, before finding the modulus.
                //g^a ranges in value from 1 to (p-1)^(p-1)
                int newMod= (int) (Math.pow(g,a)%p);
                modCollection[a-1]=newMod;
            }

            //Put the collection of mods in order, so we can compare the two sets
            //since order matters in this type of array
            Arrays.sort(modCollection);

            //Check if the modulus collection covers all integers 1 - p-1
            boolean isCompanion=true;
            for(int i=0;i<p-1;i++) {
                if(modCollection[i]!=i+1) isCompanion=false;
            }

            //If the full range is covered, add this integer to the list of companions
            if(isCompanion) companionCollection.add((Integer) g);
        }

        System.out.println(companionCollection);
        return companionCollection;
    }

    /**
     * PART C)
     *
     * Find the value of g^s mod p, using the procedure detailed in Part C of the assignment
     *
     * @param p
     * @param g
     * @param s
     * @return
     */
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

        //PART C1
        //get values of g^t mod p
        long lastMod=1;
        long[] modCollection=new long[(int) p];
        for(i=0;i<p;i++) {
            //multiply previous modulus by g and take mod p
            lastMod=lastMod*g;
            lastMod=lastMod%p;
            System.out.println(lastMod);
            modCollection[i]=lastMod;

            //PART D)
            //if modulus is the one we're looking for, store the power it occurred on
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

        //PART C2
        //split s into powers of 2
        long[] sAsPowerSum=new long[powsTwo.length];
        long sLeft=s;
        String powRecord=s+" as powers of two: ";
        for(i=j;i>=0;i--) {
            //if 2^i fits into the (remaining) s, record i as a relevant power and subtract 2^i from remaining s
            if(powsTwo[i]<=sLeft) {
                sAsPowerSum[i]=1;
                sLeft-=powsTwo[i];
                System.out.println("Power: "+i+", remaining: "+sLeft);
                powRecord+="2^"+i+" + ";
            } else sAsPowerSum[i]=0;
        }
        System.out.println(powRecord);

        //PART C4
        //multiply mods together
        long modProduct=1;
        for(i=0;i<j;i++) {
            System.out.println("modProduct: "+modProduct+", powMods: "+powMods[i]+", powerSum: "+sAsPowerSum[i]);
            //For elements that make up s (in binary),multiply together the mods and take %p each time
            if(sAsPowerSum[i]==1) {
                modProduct=modProduct*powMods[i];
                modProduct=modProduct%p;
                System.out.println("Modulus at iteration "+i+1+": "+modProduct);
            }
            System.out.println("Mod product "+i+": "+modProduct);
        }

        //finally square and take mod p to find the final value
        modProduct*=modProduct;
        modProduct=modProduct%p;


        if(foundRightMod) {
            System.out.println("Found s with the correct mod! It's at power "+rightMod);
        }
        return modProduct;


    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        //int p=Integer.parseInt(scanner.nextLine());
        ArrayList<Integer> companions=naiveCompanions(11);

        //long mod=enhancedModulus(5,2,6);
        //long mod2=enhancedModulus(104651,24578,100418);
    }
}
