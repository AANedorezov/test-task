package testtask.reader;

import testtask.utils.MyAVLTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader implements ICVSReader {
    private final String FILE_PATH;

    public CSVReader(String filePah) {
        FILE_PATH = filePah;
    }

    @Override
    public List<String> readColumnToList(int columnNumber) {
        List<String> columnRecords = new ArrayList<>();
        try {
            File fileToRead = new File(System.getProperty("user.dir") + FILE_PATH);
            if(!fileToRead.exists())
                throw new FileNotFoundException();

            BufferedReader reader = new BufferedReader(new FileReader(fileToRead)); // Read file
            String splitter = ",";
            String line;
            String[] separatedLine;

            while((line = reader.readLine()) != null) {
                separatedLine = line.split(splitter);
                columnRecords.add(cleanRecord(separatedLine[columnNumber - 1])); // Write necessary column
            }
            reader.close();

        } catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("\nYou trying to access -> " + FILE_PATH);
            System.out.println("Such file not found. Terminating the program...");
            System.exit(-1);

        } catch(Exception e) {
            e.printStackTrace();
        }

        return columnRecords;
    }

    @Override
    public MyAVLTree readColumnToTree(int columnNumber) {
        MyAVLTree columnRecords = new MyAVLTree();

        try {
            File fileToRead = new File(System.getProperty("user.dir") + FILE_PATH);
            if(!fileToRead.exists())
                throw new FileNotFoundException();

            BufferedReader reader = new BufferedReader(new FileReader(fileToRead)); // Read file
            String splitter = ",";
            String line;
            String[] separatedLine;

            while((line = reader.readLine()) != null) {
                separatedLine = line.split(splitter);
                columnRecords.insert(cleanRecord(separatedLine[columnNumber - 1])); // Write necessary column
            }
            reader.close();

        } catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("\nYou trying to access -> " + FILE_PATH);
            System.out.println("Such file not found. Terminating the program...");
            System.exit(-1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return columnRecords;
    }

    private String cleanRecord(String text) {
        return text.replace("\"", "");
    }
}
