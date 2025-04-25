package BinarySearch;

import java.util.ArrayList;

class Search {
    Search(ArrayList<Integer> list, int item) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int guess = list.get(mid);
            if (guess == item) {
                System.out.println("Элемент найден на позиции: " + mid);
                return;
            }
            if (guess < item) low = mid + 1;
            else high = mid - 1;
        }
        System.out.println("Элемент не найден.");
    }
}

public class BinarySearch {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            arrayList.add(i);
        }
        new Search(arrayList, 12);
    }
}
