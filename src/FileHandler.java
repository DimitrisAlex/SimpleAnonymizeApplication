import java.io.File;
import java.util.Scanner;

public class FileHandler {
    private File fileInput;
    private File fileConfig;
    private File fileExport;
    private String extensionInput;
    private String extensionConfig;
    private String extensionOutput;

    public FileHandler(){
        this.fileInput = null;
        this.fileConfig = null;
        this.fileExport = null;
        this.extensionInput = null;
        this.extensionConfig = null;
        this.extensionOutput = null;
    }

    public void takeInput(){
        Scanner userInput = new Scanner(System.in);
        String fileNameInput;
        String path = System.getProperty("user.dir");

        //Load Configuration File
        do {
            System.out.println("Currently only .txt and .csv files are accepted");
            System.out.println("Working Directory: " + path);
            System.out.println("Enter Dataset file name: ");
            fileNameInput = userInput.nextLine();
            this.fileInput = new File(fileNameInput);
            setExtensionInput(fileNameInput);
            if (this.fileInput.isFile()) {
                if(extensionInput != null) {
                    if (extensionInput.equals("txt") || extensionInput.equals("csv"))
                        break;
                    System.out.println("Error - file format is not accepted");
                }else
                    System.out.println("Error - file has no extension");
            }else
            System.out.println("Error - file not found!\n");
        }while (true);

        //Load Configuration File
        do {
            System.out.println("Only .txt files are accepted as Configuration");
            System.out.println("Enter Configuration file name: ");
            fileNameInput = userInput.nextLine();
            this.fileConfig = new File(fileNameInput);
            setExtensionConfig(fileNameInput);
            if(this.fileConfig.isFile()){
                if (extensionConfig != null){
                    if (extensionConfig.equals("txt")) {
                        if (!this.fileInput.getName().equals(fileConfig.getName())) {
                            break;
                        }
                        System.out.println("Error - Configuration file cannot be the same with Dataset!");
                    }else
                        System.out.println("Error - file format is not accepted");
                }else{
                    System.out.println("Error - file has no extension");
                }
            }else{
                System.out.println("Error - file not found");
            }
        }while(true);
    }



    public void setExtensionInput(String fileNameInput){
        int index = fileNameInput.lastIndexOf('.');
        if (index > 0)
            this.extensionInput = fileNameInput.substring(index + 1);
    }

    public String getExtensionInput(){
        return extensionInput;
    }


    public void setExtensionConfig(String fileNameInput){
        int index = fileNameInput.lastIndexOf('.');
        if (index > 0)
            this.extensionConfig = fileNameInput.substring(index + 1);
    }



    public File getFileInput(){
        return fileInput;
    }

    public File getFileConfig(){
        return fileConfig;
    }
}
