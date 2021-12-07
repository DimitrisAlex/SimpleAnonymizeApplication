import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface FileFactory {

    ArrayList<String[]> getData();
    ArrayList<String> getConfigure();
    void readDataToArray(File dataFile, File configFile) throws IOException;
    ArrayList<String[]> encryptData(ArrayList<String[]> dataToEncrypt, ArrayList<String> configure);
    ArrayList<String[]> decryptData(ArrayList<String[]> dataToEncrypt, ArrayList<String> configure);
    void saveFile(String fileName);
}
