package VigenereEncryption;

/**
 * Created by davidhughes on 15/12/18.
 */
public class vigenereMain {


    public static void main(String args[]) {

        VigenereBreaker vB = new VigenereBreaker();

        vB.breakVigenere();

        System.out.println("Hello!");
    }

}
