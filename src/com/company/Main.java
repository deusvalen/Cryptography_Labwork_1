package com.company;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static final String TEXT_PATH = "C:\\text.txt";
    public static final String KEY_PATH = "C:\\key.txt";
    public static final String ENC_PATH = "C:\\enc.txt";
    public static final String DEC_PATH = "C:\\dec.txt";
    
    public static void main(String[] args) {

        zadanie1();
        System.out.println("-------ZADANIE 2-------");
        zadanie2();
        System.out.println("-------ZADANIE 3-------");
        zadanie3();

    }


    private static String readFromFile (String path) {

        StringBuilder stringBuilder = new StringBuilder();
        try(FileReader reader = new FileReader(path))
        {
            int c;
            while((c=reader.read())!=-1){
                stringBuilder.append((char)c);
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static boolean writeInFile (String path, String text, boolean append) {
        try(FileWriter writer = new FileWriter(path, append))
        {
            writer.write(text);
            writer.flush();
            return true;
        }
        catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
    }

    private static String arrayListToString (ArrayList<Character> arrayList) {

        StringBuilder stringBuilder = new StringBuilder();
        for (char c:
             arrayList) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static ArrayList<Character> stringToArrayList (String s) {

        ArrayList <Character>  arrayList = new ArrayList <>();
        for (char c:
             s.toCharArray()) {
        arrayList.add(c);
        }
        return arrayList;
    }

    private static void fillAlphabet(ArrayList <Character> alphabet) {
        for (int i = 65; i <= 90; i++) {
            alphabet.add((char) i);
        }

        for (int i = 97; i <= 122; i++) {
            alphabet.add((char) i);
        }
    }

    private  static String generateKey(ArrayList <Character> alphabet) {

        StringBuilder key = new StringBuilder();
        ArrayList<Character> alphabet2 = (ArrayList <Character>) alphabet.clone();
        Collections.shuffle(alphabet2);
        for (char symbol:
             alphabet2) {
            key.append(symbol);
        }
        return key.toString();
    }

    private static String encrypt(ArrayList <Character> alphabet, ArrayList <Character> key, String str) {
        char[] text = str.toCharArray();
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < text.length; i++) {
            for (char symbol:
                 alphabet) {
                if (text[i] == (int) symbol) {
                    encrypted.append(key.get(alphabet.indexOf(symbol)));
                }
            }
        }
        return encrypted.toString();
    }

    private static String decrypt(ArrayList <Character> alphabet, ArrayList <Character> key, String str) {
        char[] text = str.toCharArray();
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < text.length; i++) {
            for (char symbol:
                    key) {
                if (text[i] == (int) symbol) {
                    decrypted.append(alphabet.get(key.indexOf(symbol)));
                }
            }
        }
        return decrypted.toString();
    }

    private static void zadanie1 () {
        ArrayList<Character> alphabet = new ArrayList();
        fillAlphabet(alphabet);
        writeInFile(KEY_PATH, generateKey(alphabet), false);

        String str = readFromFile(TEXT_PATH);
        String enc = encrypt(alphabet, stringToArrayList(readFromFile(KEY_PATH)) ,str);
        writeInFile(ENC_PATH, enc , false);

        String dec = decrypt(alphabet, stringToArrayList(readFromFile(KEY_PATH)), enc);
        writeInFile(DEC_PATH, dec, false);

    }

    private static void zadanie2 () {
        int[][] key = {{3,2},{5,7}};
        HillCipher hillCipher = new HillCipher(key, 2, "^%@%Valentin1241%");
        String enc = hillCipher.Encrypt(hillCipher.getKey());
        System.out.println(enc);
        String dec = hillCipher.Decrypt(hillCipher.getDecryptionKey(), enc);
        System.out.println(dec);
    }

    private static void zadanie3 () {

        ArrayList<CharacterX> xAlphavite = new ArrayList <>();
        xAlphavite.add(new CharacterX('E', 12.7));
        xAlphavite.add(new CharacterX('T', 9.06));
        xAlphavite.add(new CharacterX('A', 8.17));
        xAlphavite.add(new CharacterX('O', 7.51));
        xAlphavite.add(new CharacterX('I', 6.97));
        xAlphavite.add(new CharacterX('N', 6.75));
        xAlphavite.add(new CharacterX('S', 6.33));
        xAlphavite.add(new CharacterX('H', 6.09));
        xAlphavite.add(new CharacterX('R', 5.99));
        xAlphavite.add(new CharacterX('D', 4.25));
        xAlphavite.add(new CharacterX('L', 4.03));
        xAlphavite.add(new CharacterX('C', 2.78));
        xAlphavite.add(new CharacterX('U', 2.76));
        xAlphavite.add(new CharacterX('M', 2.41));
        xAlphavite.add(new CharacterX('W', 2.36));
        xAlphavite.add(new CharacterX('F', 2.23));
        xAlphavite.add(new CharacterX('G', 2.02));
        xAlphavite.add(new CharacterX('Y', 1.97));
        xAlphavite.add(new CharacterX('P', 1.93));
        xAlphavite.add(new CharacterX('B', 1.49));
        xAlphavite.add(new CharacterX('V', 0.98));
        xAlphavite.add(new CharacterX('K', 0.77));
        xAlphavite.add(new CharacterX('X', 0.15));
        xAlphavite.add(new CharacterX('J', 0.15));
        xAlphavite.add(new CharacterX('Q', 0.1));
        xAlphavite.add(new CharacterX('Z', 0.05));


        String enc = readFromFile("D:\\Учеба\\Крипта\\7-1\\ct8.txt");
        System.out.println(enc);

        ArrayList<CharacterX> xAlphavite2 = new ArrayList <>();
        int firstChar = 65;
        for (int i = 0; i < 26; i++) {
            int priority = charCounter(enc, (char) firstChar);
            xAlphavite2.add(new CharacterX((char)firstChar, priority));
            firstChar++;
        }

        Collections.sort(xAlphavite2);

        for (CharacterX c:
             xAlphavite2) {
            System.out.println(c.toString());
        }

        StringBuilder stringBuilder = new StringBuilder();
        char[] encText = enc.toCharArray();
        for (char c:
             encText) {
            for (CharacterX characterX:
                 xAlphavite2) {
                if( (int)c == (int)characterX.getCharacter() ) {
                    int index = xAlphavite2.indexOf(characterX);
                    stringBuilder.append(xAlphavite.get(index).getCharacter());
                }
            }
        }

        System.out.println(xAlphavite.size());
        System.out.println(xAlphavite2.size());
        System.out.println(stringBuilder);

    }

    public static int charCounter (String s, char c) {
        int counter = 0;
        char [] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if((int)chars[i] == (int)c) {
                counter++;
            }
        }
        return counter;
    }

}
