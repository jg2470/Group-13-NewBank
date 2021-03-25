package newbank.server;

import newbank.client.ExampleClient;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;


public class Report {

    //fields
    private ArrayList<Account> accounts;
    private DatabaseHandler dbHandler;
    private List<ArrayList<String>> dbCopy;

    //constructor
    public Report() throws FileNotFoundException, IOException {
        dbHandler = new DatabaseHandler();
        dbCopy = dbHandler.scanFullDB();
    }

    //methods
    //get all customer from the db
    public String allCustomers(){
        String outputString = "";
        for(ArrayList<String> arr : dbCopy){
            outputString += arr.toString()+"\n";
        }
        return outputString;
    }

    //get total number of accounts
    public String getNoAccounts(){
        Integer count = 0;
        int i = 0;
        List types = Arrays.asList(Account.AccountType.values());
        for(ArrayList<String> arr : dbCopy){
            for(String s: arr){
                try{
                    if(types.contains(Account.AccountType.valueOf(s.toUpperCase()))){
                        count++;
                    }
                }catch (Exception e){

                }

            }
            i++;
        }
        return "Number of accounts: "+count.toString();
    }

    //get total amount of deposits
    public String getTotalDeposits(){
        int total = 0;

        for(ArrayList<String> arr : dbCopy) {
            for(int i = 1; i < arr.size(); i++){
                try{
                    if(arr.get(i).matches("[-+]?\\d*\\.?\\d+")){
                        total += Double.parseDouble(arr.get(i));
                    }
                }catch (Exception e){
                }
            }
        }
        return "Total deposits: "+total;
    }
    //get total amount of accounts providing loans

    //get total amount of accounts receiving loans

    //get total amount of customers providing loans

    //get total amount of customers receiving loans

    //total amount of repayments by week and by month

    //generate report
    public void generateReport(){
        String fileOutput = "";
        fileOutput += allCustomers()+"\n";
        fileOutput += getNoAccounts()+"\n";
        fileOutput += getTotalDeposits()+"\n";
//        fileOutput += accountsProvidingLoans()+"\n";
//        fileOutput += accountsReceivingLoans()+"\n";
//        fileOutput += customersProvidingLoans()+"\n";
//        fileOutput += customersReceivingLoans()+"\n";
        System.out.println(fileOutput);
        writeToFile(fileOutput);
    }

    //create output file
    public void writeToFile(String output){
        try{
            // if the file doesn't exist yet, create it here
            File myFile = new File("auditReport.txt");
            if(myFile.createNewFile()){
                //now write output to the file
                System.out.println("File created: "+myFile.getName());
                FileWriter myWriter = new FileWriter("report.txt");
                myWriter.write(output);
                myWriter.close();
                System.out.println("Successfully wrote output to the file.");

            // if the file already exists, overwrite it with new data
            } else {
                System.out.println("File already exists. Overwriting with new output");
                try {
                    FileWriter myWriter = new FileWriter("auditReport.txt");
                    myWriter.write(output);
                    myWriter.close();
                    System.out.println("Successfully wrote output to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        Report report = new Report();
        report.generateReport();
    }
}
