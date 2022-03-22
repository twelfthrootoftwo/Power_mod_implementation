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

    public static ArrayList<Integer> enhancedCompanions(int p, int g, int s) {
        ArrayList<Integer> powsTwo=new ArrayList<>();
        int i=1;

        //construct powers of 2
        do {
            powsTwo.add((Integer) i);
            i*=2;
        } while(i<=p);

        //split s into powers of 2
        int[] sAsPowerSum=new int[powsTwo.size()];
        int sLeft=s;
        for(i=powsTwo.size()-1;i>=0;i--) {
            if(powsTwo.get(i)<sLeft) {
                sAsPowerSum[i]=1;
                sLeft-=powsTwo.get(i);
            } else sAsPowerSum[i]=0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int p=Integer.parseInt(scanner.nextLine());
        ArrayList<Integer> companions=naiveCompanions(p);

    }
}
