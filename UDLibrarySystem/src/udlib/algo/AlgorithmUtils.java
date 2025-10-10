package udlib.algo;

import java.util.*;
import javax.swing.table.DefaultTableModel;

public class AlgorithmUtils {

    // QuickSort for table data (by Title)
    public static void quickSort(DefaultTableModel model, int low, int high) {
        long startTime = System.nanoTime(); 

        if (low < high) {
            int pi = partition(model, low, high);
            quickSort(model, low, pi - 1);
            quickSort(model, pi + 1, high);
        }

        long endTime = System.nanoTime(); 
        double runtimeMs = (endTime - startTime) / 1_000_000.0;
        System.out.println("QuickSort runtime: " + runtimeMs + " ms");
    }

    private static int partition(DefaultTableModel model, int low, int high) {
        String pivot = model.getValueAt(high, 0).toString().toLowerCase();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            String current = model.getValueAt(j, 0).toString().toLowerCase();
            if (current.compareTo(pivot) <= 0) {
                i++;
                swap(model, i, j);
            }
        }
        swap(model, i + 1, high);
        return i + 1;
    }

    private static void swap(DefaultTableModel model, int i, int j) {
        int colCount = model.getColumnCount();
        Object[] temp = new Object[colCount];
        for (int c = 0; c < colCount; c++)
            temp[c] = model.getValueAt(i, c);

        for (int c = 0; c < colCount; c++)
            model.setValueAt(model.getValueAt(j, c), i, c);

        for (int c = 0; c < colCount; c++)
            model.setValueAt(temp[c], j, c);
    }

    // LinearSearch (for keyword search)
    public static List<Integer> linearSearch(DefaultTableModel model, String keyword) {
        long startTime = System.nanoTime(); 

        List<Integer> matches = new ArrayList<>();
        keyword = keyword.toLowerCase();

        for (int i = 0; i < model.getRowCount(); i++) {
            boolean found = false;
            for (int j = 0; j < model.getColumnCount() - 1; j++) {
                Object value = model.getValueAt(i, j);
                if (value != null && value.toString().toLowerCase().contains(keyword)) {
                    found = true;
                    break;
                }
            }
            if (found) matches.add(i);
        }

        long endTime = System.nanoTime(); 
        double runtimeMs = (endTime - startTime) / 1_000_000.0;
        System.out.println("LinearSearch runtime: " + runtimeMs + " ms");

        return matches;
    }
}
