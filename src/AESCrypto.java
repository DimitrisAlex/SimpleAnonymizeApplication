import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;

public class AESCrypto implements CryptoFactory {

    private static final String SECRET_KEY = "123456789";
    private static final String SALTVALUE = "abcdefg";

    public AESCrypto() {
    }

    @Override
    public ArrayList<String[]> encrypt(ArrayList<String[]> data, ArrayList<String> config) {
        int configurationSize = config.size();
        ArrayList<Integer> index = new ArrayList<Integer>();
        String strToEncrypt;

        for (String columnToEncrypt : config) {
            for (int i = 0; i < data.get(0).length; i++) {
                if (columnToEncrypt.equals(data.get(0)[i])) {
                    index.add(i);
                }
            }
        }
        for (String[] columnToEncrypt : data.subList(1, data.size())) {
            for (int i = 0; i < columnToEncrypt.length; i++) {
                if (index.contains(i)) {
                    strToEncrypt = columnToEncrypt[i];

                    try {
                        /* Declare a byte array. */
                        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        IvParameterSpec ivspec = new IvParameterSpec(iv);
                        /* Create factory for secret keys. */
                        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                        /* PBEKeySpec class implements KeySpec interface. */
                        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALTVALUE.getBytes(), 65536, 256);
                        SecretKey tmp = factory.generateSecret(spec);
                        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

                        /* Retruns encrypted value. */
                        columnToEncrypt[i] = Base64.getEncoder()
                                .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
                    } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                        System.out.println("Error occured during encryption: " + e.toString());
                    }
                }
            }
        }
        return data;
    }

    @Override
    public ArrayList<String[]> decrypt(ArrayList<String[]> data, ArrayList<String> config) {
        ArrayList<Integer> index = new ArrayList<Integer>();
        String strToDecrypt;

        for (String columnToDecrypt : config) {
            for (int i = 0; i < data.get(0).length; i++) {
                if (columnToDecrypt.equals(data.get(0)[i])) {
                    index.add(i);
                }
            }
        }

        for (String[] columnToDecrypt : data.subList(1, data.size())) {
            for (int i = 0; i < columnToDecrypt.length; i++) {
                if (index.contains(i)) {
                    strToDecrypt = columnToDecrypt[i];
                    try {
                        /* Declare a byte array. */
                        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        IvParameterSpec ivspec = new IvParameterSpec(iv);
                        /* Create factory for secret keys. */
                        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                        /* PBEKeySpec class implements KeySpec interface. */
                        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALTVALUE.getBytes(), 65536, 256);
                        SecretKey tmp = factory.generateSecret(spec);
                        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
                        /* Retruns decrypted value. */
                        columnToDecrypt[i] = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
                    } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalArgumentException | IllegalBlockSizeException | NoSuchPaddingException e) {
                        System.out.println("Error occured during decryption: " + e.toString());
                    }
                }
            }
        }
        return data;
    }
}