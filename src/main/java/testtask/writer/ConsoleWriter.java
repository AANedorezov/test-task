package testtask.writer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class ConsoleWriter implements IWriter{

    private List<String> records;
    private int columnNumber;
    private final String FILE_PATH;

    public ConsoleWriter(List<String> records, int columnNumber, String filePath) {
        this.records = records;
        this.columnNumber = columnNumber;
        FILE_PATH = filePath;
    }

    @Override
    public String write() {
        StringBuilder report = new StringBuilder();
        try {
            File fileToRead = new File(System.getProperty("user.dir") + FILE_PATH);
            if(!fileToRead.exists())
                throw new FileNotFoundException();

            BufferedReader reader;
            String splitter = ",";
            String line;
            String[] separatedLine;
            for(String record : records) {
                reader = new BufferedReader(new FileReader(fileToRead));

                while((line = reader.readLine()) != null) {
                    separatedLine = line.split(splitter);
                    if(separatedLine[columnNumber - 1].replace("\"", "").equals(record)) {
                        report.append(getReportBlock(record, line));
                        break;
                    }
                }
            }

        } catch(FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("\nYou trying to access -> " + FILE_PATH);
            System.out.println("Such file not found. Terminating the program...");
            System.exit(-1);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return report.toString();
    }

    private String getReportBlock(String name, String info) {
        StringBuilder block = new StringBuilder();
        String separator = "\n=========\n";

        block.append(separator);
        block.append("Found: ");
        block.append(name);
        block.append("\nFull info: ");
        block.append(info);
        block.append(separator);

        return block.toString();
    }
}
