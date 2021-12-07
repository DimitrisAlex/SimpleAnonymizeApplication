import java.util.ArrayList;

public interface CryptoFactory {

    ArrayList<String[]> encrypt(ArrayList<String[]> data, ArrayList<String> config);
    ArrayList<String[]> decrypt(ArrayList<String[]> data, ArrayList<String> config);

}
