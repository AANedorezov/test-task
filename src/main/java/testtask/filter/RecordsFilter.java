package testtask.filter;

import testtask.utils.MyAVLTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecordsFilter implements IRecordsFilter{

    private List<String> recordsToFilter;
    private MyAVLTree recordsTree;
    private String mask;

    public RecordsFilter(List<String> recordsToFilter, String mask) {
        this.recordsToFilter = recordsToFilter;
        this.mask = mask;
    }

    public RecordsFilter(MyAVLTree recordsToFilter, String mask) {
        this.recordsTree = recordsToFilter;
        this.mask = mask;
    }

    @Override
    public List<String> filter() {
        List<String> filtered = new ArrayList<>();
        int maskIndex = Collections.binarySearch(recordsToFilter, mask,
                this::compare); // Returns index of record that matches the mask in recordsToFilter

        System.out.println(maskIndex);

        // Check both sides of the found index
        if(maskIndex >= 0) {
            for(int i = maskIndex - 1; i >= 0; i--) { // Check records before the found one
                if(!isMatchingTheMask(i)) break;
                filtered.add(recordsToFilter.get(i));
            }

            Collections.reverse(filtered);
            filtered.add(recordsToFilter.get(maskIndex));

            for(int i = maskIndex + 1; i < recordsToFilter.size(); i++) { // Check records after the found one
                if(!isMatchingTheMask(i)) break;
                filtered.add(recordsToFilter.get(i));
            }
        }

        return filtered;
    }

    @Override
    public List<String> filterTree() {
        List<String> filtered = new ArrayList<>();

        String rootValue = recordsTree.getRootValue();
        if(getMaskSizeSubstring(rootValue).compareTo(mask) >
                mask.compareTo(getMaskSizeSubstring(rootValue))
        ) {
            recordsTree.leftPreOrderTraversal(mask);
        }
        else {
            recordsTree.rightPreOrderTraversal(mask);
        }

        filtered = recordsTree.getFilteredValues();

        return filtered;
    }

    private boolean isMatchingTheMask(int index) {
        return recordsToFilter.get(index).startsWith(mask);
    }

    private int compare(String record, String mask) {
        // Matching the String from beginning to the size of the mask to mask itself
        return getMaskSizeSubstring(record).compareTo(mask);
    }

    private String getMaskSizeSubstring(String text) {
        return text.substring(0, Math.min(mask.length(), text.length()));
    }
}
