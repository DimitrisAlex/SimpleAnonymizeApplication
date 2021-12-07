import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CsvFactory implements FileFactory{

    ArrayList<String[]> data;
    ArrayList<String> configure;
    File dataFile;
    File configFile;

    public CsvFactory( File dataFile, File configFile) {
        this.data = new ArrayList<String[]>();
        this.configure = new ArrayList<String>();
        this.dataFile = dataFile;
        this.configFile = configFile;
    }

    @Override
    public void readDataToArray(File dataFile, File configFile) throws IOException {
        String [] rowElements;
        try {
            Scanner scanner = new Scanner(dataFile);
            while (scanner.hasNext()) {
                rowElements = scanner.nextLine().split(",");
                data.add(rowElements);
            }
            scanner = new Scanner(configFile);
            while (scanner.hasNext()){
                configure.add(scanner.nextLine());
            }

            scanner.close();

        }catch (IOException e) {
            System.out.println("Error - End of file");
        }
    }

    @Override
    public void saveFile(String fileName) {
        try {
            FileWriter fileToSave = new FileWriter(fileName + ".cvs");
            for (int i = 0; i < data.size(); i++){
                for(String str: data.get(i)){
                    fileToSave.write(str + ",");
                }
                fileToSave.write("\n");
            }
            fileToSave.close();
        }catch (IOException e){
            System.out.println("Error Occurred");
            e.printStackTrace();
        }
    }
    @Override
    public ArrayList<String[]> encryptData(ArrayList<String[]> dataToEncrypt, ArrayList<String> configure) {
        Scanner input = new Scanner(System.in);
        CryptoFactory cryptoFactory;
        ArrayList<String[]> array;

        while(true) {
            try {
                System.out.println("Press 1 to Encrypt using AES");
                int in = input.nextInt();
                if (in == 1) {
                    cryptoFactory = new AESCrypto();
                    array = cryptoFactory.encrypt(dataToEncrypt,configure);
                    for (String[] s : array){
                        System.out.println(Arrays.toString(s));
                    }
                    break;

                }
            }catch (InputMismatchException e){
                System.out.println("Error - Wrong Input");
            }
        }
        return array;
    }

    @Override
    public ArrayList<String[]> decryptData(ArrayList<String[]> dataToEncrypt, ArrayList<String> configure) {
        Scanner input = new Scanner(System.in);
        CryptoFactory cryptoFactory;
        ArrayList<String[]> array;

        while(true) {
            try {
                System.out.println("Press 1 to Decrypt using AES");
                int in = input.nextInt();
                if (in == 1) {
                    cryptoFactory = new AESCrypto();
                    array = cryptoFactory.decrypt(dataToEncrypt,configure);
                    for (String[] s : array){
                        System.out.println(Arrays.toString(s));
                    }
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("Error - Wrong Input");
            }
        }
        return array;
    }

    public ArrayList<String[]> getData() {
        return data;
    }

    public ArrayList<String> getConfigure() {
        return configure;
    }
}

