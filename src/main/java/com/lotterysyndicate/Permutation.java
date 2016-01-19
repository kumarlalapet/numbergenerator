package com.lotterysyndicate;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by mlalapet on 1/18/16.
 */
public class Permutation {

    private Set<String> tickets = new LinkedHashSet<String>();

    Set<String> finalTickets = new LinkedHashSet<String>();

    void weedOutDuplicates() {
        Set<String> alreadyPresent = new LinkedHashSet<String>();

        for (String s : tickets) {
            int num[] = new int[5];
            StringTokenizer tokens = new StringTokenizer(s);
            num[0] = Integer.parseInt(tokens.nextToken());
            num[1] = Integer.parseInt(tokens.nextToken());
            num[2] = Integer.parseInt(tokens.nextToken());
            num[3] = Integer.parseInt(tokens.nextToken());
            num[4] = Integer.parseInt(tokens.nextToken());

            int maxValue = 0;
            for (int value : num) {
                if (value > maxValue) {
                    maxValue = value;
                }
            }

            int minValue = 10000;
            for (int value : num) {
                if (value < minValue) {
                    minValue = value;
                }
            }

            int secnum[] = new int[3];
            int index = 0;
            for (int value : num) {
                if (value == maxValue || value == minValue) {
                    continue;
                } else {
                    //System.out.println(value+" "+maxValue+" "+minValue);
                    secnum[index] = value;
                    index++;
                }
            }

            int secondmaxValue = 0;
            for (int value : secnum) {
                if (value > secondmaxValue) {
                    secondmaxValue = value;
                }
            }

            int secondminValue = 10000;
            for (int value : secnum) {
                if (value < secondminValue) {
                    secondminValue = value;
                }
            }

            int midnum[] = new int[1];
            index = 0;
            for (int value : secnum) {
                if (value == secondmaxValue || value == secondminValue) {
                    continue;
                } else {
                    midnum[index] = value;
                    index++;
                }
            }

            String key = minValue + "" + secondminValue + "" + midnum[0] + "" + secondmaxValue + "" + maxValue;


            //while(tokens.hasMoreTokens()) {
            //    total = total + Integer.parseInt(tokens.nextToken());
            //}
            //System.out.println(total);
            if (!alreadyPresent.contains(key)) {
                alreadyPresent.add(key);
                finalTickets.add(s);
            }
        }
    }

    /* arr[]  ---> Input Array
        data[] ---> Temporary array to store current combination
        start & end ---> Staring and Ending indexes in arr[]
        index  ---> Current index in data[]
        r ---> Size of a combination to be printed */
    void combinationUtil(int arr[], int data[], int start,
                         int end, int index, int r) {

        // Current combination is ready to be printed, print it
        if (index == r) {
            String ticket = "";
            for (int j = 0; j < r; j++) {
                String ls = data[j] + " ";
                ticket = ticket + ls;
                //
            }
            tickets.add(ticket);
            //System.out.print(ticket);
            //System.out.println("");
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data[index] = arr[i];
            combinationUtil(arr, data, i + 1, end, index + 1, r);
        }
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    void printCombination(int arr[], int n, int r) {
        // A temporary array to store all combination one by one
        int data[] = new int[r];

        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, data, 0, n - 1, 0, r);
    }

    /*Driver function to check for above function*/
    public static void main(String[] args) {
        Permutation pm = new Permutation();
        int arr[] = {21, 22, 20, 45, 46, 44, 51, 52, 50, 55, 56, 54, 62, 63, 61, 10, 11, 9, 18, 19, 17, 37, 38, 36, 53, 60, 59, 23, 43, 48, 49, 47, 58, 40, 41, 39, 64, 65, 3, 4, 2, 24, 25, 1, 31, 32, 30};
        //int arr[] = {21, 22, 20, 45, 46, 44, 51, 52, 50, 55, 56, 54, 62, 63, 61};//, 10, 11, 9, 18, 19, 17, 37, 38, 36, 53, 60, 59, 23, 43, 48, 49, 47, 58, 40, 41, 39, 64, 65, 3, 4, 2, 24, 25, 1, 31, 32, 30};
        int r = 5;
        int n = arr.length;
        pm.printCombination(arr, n, r);
        pm.weedOutDuplicates();
        System.out.println(pm.tickets.size() + " " + pm.finalTickets.size());
    }

}
