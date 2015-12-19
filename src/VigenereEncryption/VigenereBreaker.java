/**
 * Created by davidhughes on 15/12/18.
 */

package VigenereEncryption;

import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {

    public String sliceString(String message, int whichSlice, int totalSlices) {
        //This method returns a String consisting of every totalSlices-th character
        //from message, starting at the whichSlice-th character.
        //String message, representing the encrypted message,
        //integer whichSlice, indicating the index the slice should start from,
        //integer totalSlices, indicating the length of the key
        StringBuilder sBuild = new StringBuilder();

        for (int k = whichSlice; k < message.length(); k=k+totalSlices)
        {
            sBuild.append(message.charAt(k));
        }

        return sBuild.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        //This method should make use of the CaesarCracker class, as well as the
        //sliceString method, to find the shift for each index in the key. You should
        //fill in the key (which is an array of integers) and return it.
        //String encrypted that represents the encrypted message,
        //integer klength that represents the key length,
        //character mostCommon that indicates the most common character in the language
        //of the message

        int[] key = new int[klength];

        for (int k = 0; k < klength; k++)
        {
            String messageSlice = sliceString(encrypted,k,klength);
            CaesarCracker cCrack = new CaesarCracker(mostCommon);
            key[k] = cCrack.getKey(messageSlice);
        }

        return key;
    }


    public HashSet<String> readDictionary(FileResource fr)
    {
        //This method should first make a new HashSet of Strings, then read each line
        //in fr (which should contain exactly one word per line), convert that line
        //to lowercase, and put that line into the HashSet that you created. The method
        //should then return the HashSet representing the words in a dictionary

        HashSet<String> hs_dictionaryWords = new HashSet<String>();

        for (String word : fr.lines())
        {
            hs_dictionaryWords.add(word.toLowerCase().trim());
        }

        return hs_dictionaryWords;

    }


    public int countWords(String message, HashSet<String> dictionary)
    {
        //This method should split the message into words (use .split(“\\W”), which
        //returns a String array), iterate over those words, and see how many of them
        //are “real words”—that is, how many appear in the dictionary.
        //Recall that the words in dictionary are lowercase. This method should return
        //the integer count of how many valid words it found.

        String[] messageWords;
        messageWords = message.toLowerCase().split("\\W");

        int countOfRealWords = 0;

        //System.out.println(message);


        for (String testWord : messageWords)
        {
            if (dictionary.contains(testWord.trim()))
            {
                countOfRealWords++;
            }
            else
            {
                //System.out.println("Not found in dictionary: \t " + testWord);
            }
        }

        return countOfRealWords;
    }

    public void test_countWords()
    {
        String path = "/Users/davidhughes/IdeaProjects/Vigenere Encryption/dictionaries/";
        //String path = "/Users/davidhughes/Dropbox/Personal/Self-Development/Coursera/1. Java Programming - Arrays Lists and Structured Data/Vigenere Cipher/VigenereProgram/dictionaries/";
        String filename = "English";
        FileResource fr_dictionary = new FileResource(path+filename);

        FileResource fr_message = new FileResource();
        String message = fr_message.asString().toLowerCase();
        int count = countWords(message, readDictionary(fr_dictionary));
        //System.out.println(readDictionary(fr_dictionary));
        System.out.println("Nr of valid words in the message = " + count);

    }



    public char mostcommonCharin(HashSet<String> dictionary)
    {
        //This method should find out which character, of the letters
        //in the English alphabet, appears most often in the words in
        //dictionary. It should return this most commonly occurring character.

        HashMap<Character, Integer> hmLetterFreqs = new HashMap<Character, Integer>();

        //iterate over all the words in the dictionary
        for (String word : dictionary)
        {
            char [] letterArray = word.toCharArray();

            //iterate over all the letters in the word
            for (char letter : letterArray)
            {

                if (hmLetterFreqs.containsKey(letter))
                {
                    //increment the count for the letter in the hmletterFreqs map.
                    hmLetterFreqs.put(letter,hmLetterFreqs.get(letter)+1);
                }
                else
                {
                    //add the letter to the hmletterFreqs map
                    hmLetterFreqs.put(letter, 1);
                }

            }
        }


        int maxFreq = 0;
        char maxLetter = ' ';

        //iterate over all the letters in the hashmap to find the one with the most frequencies
        for (char letter : hmLetterFreqs.keySet())
        {
            int letterCount = hmLetterFreqs.get(letter);
            if (letterCount > maxFreq)
            {
                maxFreq = letterCount;
                maxLetter = letter;
            }
        }

        return maxLetter;
    }


    public void test_mostCommonCharin(String filename)
    {
        //Read the dictionary in
        String path = "/Users/davidhughes/IdeaProjects/Vigenere Encryption/dictionaries/";
        //String path = "/Users/davidhughes/Dropbox/Personal/Self-Development/Coursera/1. Java Programming - Arrays Lists and Structured Data/Vigenere Cipher/VigenereProgram/dictionaries/";
        //String filename = "English";
        FileResource fr_dictionary = new FileResource(path+filename);
        HashSet<String> dictionary = readDictionary(fr_dictionary);

        System.out.println("Most common letter in " + filename + " dictionary is = " + mostcommonCharin(dictionary));


    }



    public String breakForLanguage(String encrypted, HashSet<String> dictionary)
    {
        //This method should try all key lengths from 1 to 100 (use your tryKeyLength
        //method to try one particular key length) to obtain the best decryption for
        //each key length in that range. For each key length, your method should decrypt
        //the message (using VigenereCipher’s decrypt method as before), and count how
        //many of the “words” in it are real words in English, based on the dictionary
        //passed in (use the countWords method you just wrote). This method should
        //figure out which decryption gives the largest count of real words, and return
        //that String decryption.

        int bestKLength = 0;
        int bestCount = 0;
        String bestDecrypt="";
        String bestKey = "";
        //char mostCommon = 'e';
        char mostCommon = mostcommonCharin(dictionary);
        for (int kLength = 1; kLength < 101; kLength++)
        {
            int[] key = tryKeyLength(encrypted,kLength,mostCommon);

            //Create Vigenere Cipher with this key
            VigenereCipher vC = new VigenereCipher(key);

            //Use Vigenere decrypt method
            String decrypted = vC.decrypt(encrypted);

            int currentWordCount = countWords(decrypted, dictionary);
            if(currentWordCount > bestCount)
            {
                bestCount = currentWordCount;
                bestKLength = kLength;
                bestDecrypt = decrypted;
                bestKey = vC.toString();
            }

            //System.out.println("Finished with kLength = " + kLength);
        }

        System.out.println("The Vigenere Key = " + bestKey);
        System.out.println("The key length is " + bestKLength + " and the number of real words found in the dictionary were " + bestCount);

        return bestDecrypt;

    }







    public String breakForAllLanguages(String encrypted, HashMap<String, HashSet<String>> hmLanguages)
    {
        //This method tries to break the encryption for each language passed as the parameter hmLanguages.
        //The decryption that results in the highest number of words found in a given dictionary is chosen
        //as the return value.

        String BestDecryptionSoFar = "";
        String BestLanguageSoFar = "";
        int BestRealWordCountSoFar = 0;

        //iterate over all languages in the HashMap
        for (String language : hmLanguages.keySet())
        {

            //Get the dictionary for the language key
            HashSet<String> dictionary = hmLanguages.get(language);

            //Decrypt for a language
            System.out.println("\nTrying language...." + language);
            String decrypted = breakForLanguage(encrypted, dictionary);
            int realWordCount = countWords(decrypted, dictionary);

            //Check if the real-word count relative to that dictionary is the best so far
            if (realWordCount > BestRealWordCountSoFar)
            {
                //if true then store the decrypted message and language in BestSoFar buffer
                BestRealWordCountSoFar = realWordCount;
                BestDecryptionSoFar = decrypted;
                BestLanguageSoFar = language;
            }
        }

        //return the BestSoFar decryption and language
        System.out.println("\n\nBest language for decryption of this messages is : " + BestLanguageSoFar);
        return BestDecryptionSoFar;

    }



    public void breakVigenere () {
        //Create a directory resource of all the dictionary files
        System.out.println("Choose your dictionaries....");
        DirectoryResource dr = new DirectoryResource();
        HashMap<String, HashSet<String>> hmLanguages = new HashMap<String, HashSet<String>>();
        String path = "/Users/davidhughes/IdeaProjects/Vigenere Encryption/dictionaries/";
        //String path = "/Users/davidhughes/Dropbox/Personal/Self-Development/Coursera/1. Java Programming - Arrays Lists and Structured Data/Vigenere Cipher/VigenereProgram/dictionaries/";

        //Iterate over all the dictionary files in the directory resource
        for (File f : dr.selectedFiles())
        {
            String filename = f.getName();
            //String dictionaryName = filename.substring(0,filename.lastIndexOf('.'));
            String dictionaryName = filename;
            FileResource fr_dictionary = new FileResource(path+filename);
            System.out.println("Reading in dictionary: " + filename);
            HashSet<String> dictionary = readDictionary(fr_dictionary);
            hmLanguages.put(dictionaryName, dictionary);
        }
        System.out.println("Completed the import of all dictionaries into the HashMap.");

        System.out.println("Choose your Vigenere-encrypted message to break....");
        //Get the encrypted message
        FileResource fr = new FileResource();
        String encryptedMessage = fr.asString();

        //Decrypt the message
        System.out.println("Starting the decryption process..");
        String decryptedMessage = breakForAllLanguages(encryptedMessage, hmLanguages);

        //Print out the decrypted message.
        System.out.println("The first line of the decrypted Vigenere message is: \n");
        //System.out.println(decryptedMessage);
        System.out.println(decryptedMessage.substring(0,decryptedMessage.indexOf("\n")) + "\n");

        String[] encryptedWords;
        encryptedWords = encryptedMessage.split("\\W");
        System.out.println("Total words in original message: " + encryptedWords.length);

    }





}
