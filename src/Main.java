import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        FileFactory factory = configureApplication();
        Scanner input = new Scanner(System.in);

        int in;
        boolean encrypt;
        String fileName;

        while (true){
            System.out.println("Press 1 to Encrypt your Dataset\nPress 2 to Decrypt your Dataset\nPress 3 to Quit the Application");
            try {
                in = Integer.parseInt(input.nextLine());
                if (in == 1){
                    encrypt = true;
                    break;
                }else if (in == 2){
                    encrypt = false;
                    break;
                }else if (in == 3){
                    exit(-1);
                }
            }catch (NoSuchElementException | NumberFormatException e){
                System.out.println("Wrong input. Try again!");
            }
        }

        System.out.println("Give a name for the Output File: ");
        fileName = input.nextLine();

        if (encrypt){
            factory.encryptData(factory.getData(), factory.getConfigure());
            factory.saveFile(fileName);
        } else {
            factory.decryptData(factory.getData(),factory.getConfigure());
            factory.saveFile(fileName);
        }

    }


    private static FileFactory configureApplication() {
        FileHandler fileHandler = new FileHandler();
        FileFactory factory;

        fileHandler.takeInput();
        String typeOfFile = fileHandler.getExtensionInput().toLowerCase();

        if (typeOfFile.equals("txt")) {
            factory = new TxtFactory(fileHandler.getFileInput(), fileHandler.getFileConfig());
            try {
                factory.readDataToArray(fileHandler.getFileInput(), fileHandler.getFileConfig());
                return factory;
            } catch (IOException e) {
                System.out.println("Error - An error occurred");
            }
        } else if (typeOfFile.equals("csv")) {
            factory = new CsvFactory(fileHandler.getFileInput(), fileHandler.getFileConfig());
            try {
                factory.readDataToArray(fileHandler.getFileInput(), fileHandler.getFileConfig());
                return factory;
            } catch (IOException e) {
                System.out.println("Error - An error occurred");
            }
        }
        return null;

//
    }
}
