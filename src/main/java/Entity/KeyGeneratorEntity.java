package Entity;

import javax.crypto.SecretKey;
import java.io.*;


import java.math.BigInteger;
import java.security.*;
import java.util.ArrayList;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.List;
import  javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


public class KeyGeneratorEntity {
    String algorithme;
    String provider;
    String clepriv;
    String clepub;
    String message;
    String file;

    public String getHashingAlgo() {
        return hashingAlgo;
    }

    public void setHashingAlgo(String hashingAlgo) {
        this.hashingAlgo = hashingAlgo;
    }

    public String getSigningAlgo() {
        return signingAlgo;
    }

    public void setSigningAlgo(String signingAlgo) {
        this.signingAlgo = signingAlgo;
    }

    String hashingAlgo, signingAlgo;
    public MessageDigest getMd() {
        return md;
    }

    public void setMd(MessageDigest md) {
        this.md = md;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    MessageDigest md;
    Signature signature;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isAsymetrique() {
        return asymetrique;
    }

    public void setAsymetrique(boolean asymetrique) {
        this.asymetrique = asymetrique;
    }

    boolean asymetrique = false;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClepriv() {
        return clepriv;
    }

    public void setClepriv(String clepriv) {
        this.clepriv = clepriv;
    }

    public String getClepub() {
        return clepub;
    }

    public void setClepub(String clepub) {
        this.clepub = clepub;
    }

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


    public List<String> generateKeyPair(String algorithme, String provider, int taille,boolean isSymetrique) {

        List<String> list = new ArrayList<String>();

        SecureRandom secureRandom = new SecureRandom();
        byte bytes[] = new byte[20];
        secureRandom.nextBytes(bytes);

        System.out.println("avant le if "+algorithme);
        System.out.println("avant le if "+this.asymetrique);
        try {

           if(isSymetrique)
           {
               System.out.println("aprés le if "+algorithme);
               KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithme, provider);
               keyPairGen.initialize(taille, secureRandom);
               KeyPair keyPair = keyPairGen.generateKeyPair();
               // cle private
               PrivateKey priv = keyPair.getPrivate();
               String clepriv = Base64.getEncoder().encodeToString(priv.getEncoded());
               System.out.println("la cle prive"+clepriv);
               list.add(clepriv);

               PublicKey pub = keyPair.getPublic();
               String clepub = Base64.getEncoder().encodeToString(pub.getEncoded());
               System.out.println(clepub);
               //System.out.println(ByteHex.bytesToHex(pub.getEncoded()));
               list.add(clepub);
           }
           else {
               System.out.println("dans le else "+algorithme);
               KeyGenerator keyGen = KeyGenerator.getInstance(algorithme, provider);
               keyGen.init(taille);
               SecretKey secretKey = keyGen.generateKey();
               String clepriv = Base64.getEncoder().encodeToString(secretKey.getEncoded());
               list.add(clepriv);

              // list.add(ByteHex.bytesToHex(secretKey.getEncoded()));
               System.out.println("ma cle " + secretKey.getAlgorithm() + " " + secretKey.getFormat() + " "
                       + ByteHex.bytesToHex(secretKey.getEncoded()));
               list.add(clepriv);
           }
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

    public List<String> createKeys(String algorithme, String provider, int taille,boolean isSymetrique) {
        List<String> keyList = this.generateKeyPair(algorithme, provider, taille ,isSymetrique);
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


        public String ChiffrementSymetriqueMessage( String clesecret,String message,String algorithme){
            byte[] textCipher = new byte[0];
            byte[] messageBytes = message.getBytes();
          //  byte[] secretKey= new BigInteger(secretKey, 16).toByteArray();
            try {
                Cipher cipher = Cipher.getInstance( algorithme );
                try {

                   /* byte[] data =  clesecret.getBytes();
                    SecretKey secretkey = new SecretKeySpec( data, algorithme);*/
                    byte[] decodedKey = Base64.getDecoder().decode(clesecret);
                // rebuild key using SecretKeySpec
                    SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithme);

                    System.out.println( originalKey.toString() );

                    cipher.init( Cipher.ENCRYPT_MODE, originalKey );
                    try {
                        textCipher = cipher.doFinal( messageBytes);
                        System.out.println( "le chiffre"+ textCipher);
                        return  ByteHex.bytesToHex( textCipher ) ;
                    } catch ( IllegalBlockSizeException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch ( BadPaddingException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } catch ( InvalidKeyException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (NoSuchPaddingException | NoSuchAlgorithmException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //System.out.println(textCipher);
            return  ByteHex.bytesToHex( textCipher );
        }


public String ChiffrerFichierSymetrique(String algorithme,String file,int taille){
String filename =file+taille+".txt";
    try {
        KeyGenerator keyGen=KeyGenerator.getInstance(algorithme);
        keyGen.init(taille);
        SecretKey secretKey=keyGen.generateKey();

        FileInputStream fl=new FileInputStream(file);
        FileOutputStream fos=new FileOutputStream(new File(filename));
        Cipher cipher=Cipher.getInstance(algorithme);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        CipherInputStream cis=new CipherInputStream(fl,cipher);
        byte[] buf =new byte[8];
        int i =cis.read(buf);
        while(i!=-1) {
            fos.write(buf, 0, i);
            i=cis.read(buf);
        }
        cis.close();
        fos.close();
        return "fichier chiffré avec succes";


    }
    catch (NoSuchAlgorithmException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }catch (NoSuchPaddingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }catch (InvalidKeyException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return "fichier chiffré avec succes";



}


   public KeyGeneratorEntity DigitalSignature(PrivateKey priv, String signingAlgo, String hashingAlgo)
            throws NoSuchAlgorithmException, InvalidKeyException{
       KeyGeneratorEntity ke=new KeyGeneratorEntity();
        this.md = MessageDigest.getInstance(hashingAlgo);
        this.signature = Signature.getInstance(signingAlgo);
        this.signature.initSign(priv, new SecureRandom());
        ke.setMd(this.md);
        ke.setSignature(this.signature);
        return ke;

    }
    public byte [] generateDigest(String text) throws Exception{
        this.md.update(text.getBytes("UTF8"));
        return this.md.digest();
    }

    public byte[] sign(byte[] digest)throws Exception{
        this.signature.update(digest);
        return this.signature.sign();
    }

    public  String SignatureMeassage(String algorithme,String hashingAlgo,String signingAlgo,String clesecret,String text) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(clesecret);
        // rebuild key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithme);
        KeyGeneratorEntity ds = DigitalSignature((PrivateKey) originalKey, signingAlgo, hashingAlgo);
        byte [] digest = ds.generateDigest(text);
        byte [] signature = ds.sign(digest);
        System.out.println("Digital Signature: "+  ByteHex.bytesToHex(signature));
        return   ByteHex.bytesToHex(signature);
    }


    }





