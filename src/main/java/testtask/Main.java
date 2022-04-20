package testtask;

import testtask.filter.IRecordsFilter;
import testtask.filter.RecordsFilter;
import testtask.reader.CSVReader;
import testtask.reader.ICVSReader;
import testtask.utils.ColumnPicker;
import testtask.utils.MyAVLTree;
import testtask.writer.ConsoleWriter;
import testtask.writer.IWriter;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String FILE_PATH = "/src/main/resources/airports.csv";

    public static void main(String[] args) {
        // Get column to read
        int columnNumber = new ColumnPicker(args).getColumnNumber();
        System.out.println("\nSelected column: " + columnNumber);

        // Read the column from CSV file
        ICVSReader reader = new CSVReader(FILE_PATH);
        List<String> recordsList = null;
        MyAVLTree recordsTree = null;
        if(args.length == 0  || args[1].equals("-tree") || args[0].equals("-tree")) {
            System.out.println("AVLTree will be used for searching...");
            recordsTree = reader.readColumnToTree(columnNumber);
        }
        else if(args[1].equals("-binary") || args[0].equals("-binary")) {
            System.out.println("Binary search will be used...");
            recordsList = reader.readColumnToList(columnNumber);
            Collections.sort(recordsList);
        }

        String mask = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter mask to filter records: ");
        while (mask.equals("")) {
            mask = scanner.nextLine();
        }

        List<String> filtered;
        var startTime = System.currentTimeMillis();
        if(recordsList != null)
            filtered = new RecordsFilter(recordsList, mask).filter();
        else
            filtered = new RecordsFilter(recordsTree, mask).filterTree();

        var endTime = System.currentTimeMillis();
        var searchingTime = endTime - startTime;

        Collections.sort(filtered);
        IWriter writer = new ConsoleWriter(filtered, columnNumber, FILE_PATH);
        System.out.println(writer.write());

        System.out.println("Searching time: " + searchingTime + "ms");
        System.out.println("Items found: " + filtered.size());
    }
}
