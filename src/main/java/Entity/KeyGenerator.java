package Entity;

import java.io.*;
import java.util.ArrayList;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import java.util.List;

public class KeyGenerator {
    String algorithme;
    String provider;
    String clepriv;
    String clepub;
    int taille;

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }



    public String getAlgorithme() {
        return algorithme;
    }

    public void setAlgorithme(String algorithme) {
        this.algorithme = algorithme;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }


    public List<String> generateKeyPair(String algorithme, String provider, int taille) {

        List<String> list = new ArrayList<String>();

        SecureRandom secureRandom = new SecureRandom();
        byte bytes[] = new byte[20];
        secureRandom.nextBytes(bytes);
        try {

            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", "SunRsaSign");
            keyPairGen.initialize(taille, secureRandom);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // cle private
            PrivateKey priv = keyPair.getPrivate();
            list.add(ByteHex.bytesToHex(priv.getEncoded()));

            PublicKey pub = keyPair.getPublic();
            list.add(ByteHex.bytesToHex(pub.getEncoded()));
            return list;
            // list.add(ByteHex.bytesToHex(priv.getEncoded()));
            // System.out.println("cl� private RSA: "+ByteHex.bytesToHex(priv.getEncoded()));
            // cl� public

            // System.out.println("cl� public RSA: "+ByteHex.bytesToHex(pub.getEncoded()));
            // list.add(ByteHex.bytesToHex(pub.getEncoded()));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;

    }

    public List<String> createKeys(String algorithme, String provider, int taille) {
        List<String> keyList = this.generateKeyPair(algorithme, provider, taille);
        return keyList;
        //clepriv = keyList.get(0);
        // clepub = keyList.get(1);


    }

    public boolean saveKey(String clepriv, String clepub) {
        System.out.println(clepriv);
        System.out.println(clepub);

            try {

                ObjectOutputStream fichierPub = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("clepub.txt")));
                fichierPub.writeObject(clepriv);
                fichierPub.close();

                ObjectOutputStream fichierPriv = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("clepriv.txt")));
                fichierPriv.writeObject(clepub);
                fichierPriv.close();
                return  true;

            }
            catch (Exception e) {
                System.out.println((e.getMessage()));
                e.printStackTrace();
                return  false;

            }


        }

        public boolean loadkey(){
            try(FileReader fileReader = new FileReader("clepub.txt")) {
                int ch = fileReader.read();
                while(ch != -1) {
                   // System.out.print((char)ch);
                    fileReader.close();
                }
                return  true;


            } catch (FileNotFoundException e) {

        }
            catch (IOException e) {
                e.printStackTrace();
            }
            return  true;

        }
}




