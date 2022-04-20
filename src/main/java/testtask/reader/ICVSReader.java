package testtask.reader;

import testtask.utils.MyAVLTree;

import java.util.List;

public interface ICVSReader {
    List<String> readColumnToList(int columnNumber);
    MyAVLTree readColumnToTree(int columnNumber);
}
