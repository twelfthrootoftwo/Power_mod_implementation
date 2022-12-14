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
        long calcLimit=(long) p*p;

        //confirm target calc output
        System.out.println("Maximum calculated number: "+calcLimit);

        //PART C1
        //construct powers of 2 and generate corresponding mods
        int i=1;
        int j=0;
        long lastMod=0;
        do {
            powsTwo[j]= i;

            //Special case for j=0
            //To run with the pattern, we want lastMod=sqrt(g)
            //However g might not be a square, so sqrt(g) would not be an integer
            //instead, just hard code the results for j=0 (that is, g^1 mod p)
            if(j==0) {
                lastMod=g;
            } else {
                //Square previous modulus and take mod p
                lastMod=lastMod*lastMod;
                lastMod=lastMod%p;
            }
            powMods[j]=lastMod;

            //output
            System.out.println("t: "+i+", g^t mod p: "+lastMod);

            i*=2;
            j++;
        } while(i<=p);
        j--;//point j to the last element in powsTwo and powMods

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
                powRecord+="2^"+i+" + ";
            } else sAsPowerSum[i]=0;
        }
        System.out.println(powRecord);

        //PART C4
        //multiply mods together
        long modProduct=1;
        int c=0;
        for(i=0;i<j;i++) {
            //For elements that make up s (in binary),multiply together the mods and take %p each time
            if(sAsPowerSum[i]==1) {
                modProduct=modProduct*powMods[i];
                modProduct=modProduct%p;
                c++;
                System.out.println("Modulus at iteration "+c+": "+modProduct);
            }
        }
        System.out.println("Final result: "+modProduct);
        return modProduct;
    }

    /**PART D)
     *
     * Produces a list of moduli for powers of g, to find the power that produces the target modulus.
     * @param g - integer to take powers of
     * @param p - take mod relative to this integer
     * @param targetMod - the modulus to search for
     * @return an integer representing the power of g where g^returnValue mod p =targetMod, or -1 if the targetMod was not found
     */
    public static int findTargetMod(long g, long p, long targetMod) {

        long lastMod=1;
        for(int i=0;i<p;i++) {
            //multiply previous modulus by g and take mod p
            lastMod=lastMod*g;
            lastMod=lastMod%p;

            //if modulus is the one we're looking for, store the power it occurred on
            if(lastMod==targetMod) {
                System.out.println("Found s with the correct mod! It's at power "+(i+1));
                return i+1;
            }
        }
        System.out.println("Target mod not found");
        return -1;
    }

    public static void testRun() {
        //Testing procedure
        //got expected results for everything, so it's all working!
        ArrayList<Integer> companions=naiveCompanions(5);
        System.out.println("Check 2 is a companion");
        long mod=enhancedModulus(5,2,1);//output 2
        mod=enhancedModulus(5,2,2);//output 4
        mod=enhancedModulus(5,2,3);//output 3
        mod=enhancedModulus(5,2,4);//output 1
        System.out.println("Check 3 is a companion");
        mod=enhancedModulus(5,3,1);//output 3
        mod=enhancedModulus(5,3,2);//output 4
        mod=enhancedModulus(5,3,3);//output 2
        mod=enhancedModulus(5,3,4);//output 1
    }

    public static void main(String[] args) {
        //testRun();
        ArrayList<Integer> companions=naiveCompanions(11);
        long mod2=enhancedModulus(104651,24578,100418);
        int targetPow=findTargetMod(24578,104651,3190);
    }
}
