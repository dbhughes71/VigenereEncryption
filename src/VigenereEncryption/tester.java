/**
 * Created by davidhughes on 15/12/18.
 */

package VigenereEncryption;

import edu.duke.*;

public class tester {

    public void simpleTestOfCaesarCipher()
    {
        FileResource fr = new FileResource ();
        String message = fr.asString();


        System.out.println("The original message is: \n");
        System.out.println(message + "\n");

        CaesarCipher CC = new CaesarCipher(5);

        String encrypted = CC.encrypt(message);
        System.out.println("The encrypted message is: \n");
        System.out.println(encrypted + "\n");

        String decrypted = CC.decrypt(encrypted);
        System.out.println("The decrypted message is: \n");
        System.out.println(decrypted + "\n");

        if (decrypted.equals(message))
        {
            System.out.println("TEST PASSES!");
        }
        else
        {
            System.out.println("TEST FAILS!");
        }

    }


    public void simpleEnglishCaesarCrack()
    {
        FileResource fr = new FileResource ();
        String message = fr.asString();

        System.out.println("The original encrypted message is: \n");
        System.out.println(message + "\n");

        CaesarCracker cCrack = new CaesarCracker();

        String cracked = cCrack.decrypt(message);
        System.out.println("The cracked message is: \n");
        System.out.println(cracked + "\n");

    }

    public void simplePortugeseCaesarCrack()
    {
        FileResource fr = new FileResource ();
        String message = fr.asString();

        System.out.println("The original encrypted message is: \n");
        System.out.println(message + "\n");

        //Most common character in Portugese is 'a'
        CaesarCracker cCrack = new CaesarCracker('a');

        String cracked = cCrack.decrypt(message);
        System.out.println("The cracked message is: \n");
        System.out.println(cracked + "\n");

    }


    public void simpleTestOfVigenereCipher()
    {
        //FileResource fr = new FileResource ();
        //String message = fr.asString();

        String message = "apple";

        System.out.println("The original message is: \n");
        System.out.println(message + "\n");

        int k[] = new int[12];
        k[0] = 5;
        k[1] = 14;
        k[2] = 8;
        k[3] = 12;
        k[4] = 4;


        VigenereCipher vC = new VigenereCipher(k);

        String encrypted = vC.encrypt(message);
        System.out.println("The encrypted message is: \n");
        System.out.println(encrypted + "\n");

        String decrypted = vC.decrypt(encrypted);
        System.out.println("The decrypted message is: \n");
        System.out.println(decrypted + "\n");

        if (decrypted.equals(message))
        {
            System.out.println("TEST PASSES!");
        }
        else
        {
            System.out.println("TEST FAILS!");
        }

    }




}
