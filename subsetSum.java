/**
 * subsetSum.java
 * Instructor: PB
 * Final Project
 * 
 * Author: RM and HN
 *
 * Subset Sum (SUM)
 * Input: S[1 ... n] a list of n positive integers and t a target integer.
 * Output: true, and A -> {1, ..., n} a set of indices such that the sum S[i] = t
 * if such an A exists, and false otherwise.
 */
import java.lang.*;
import java.util.*;

/**
 * This class ia a driver program using Population and Genome classes
 * for Assignment 2.
 *
 * @author RM and HN
 * @version 12 March 2020
 */
public class tcss343 {
    /**
     * Main function, driving Population and Genome class.
     *
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Hello World!");
        List<Integer> list = Arrays.asList(2, 3, 5, 7, 9);
        List<Integer> list2 = Arrays.asList(1, 2, 3, 5, 7, 9, 10, 11);
        ArrayList<Integer> newList = new ArrayList<Integer>(list2);
        //System.out.println(newList);
        //all_possible_subsets(newList);

        // 7 or (111)b, 7 >> 1 means Shift 7 or (111)b to the right by 1 into 3 or (11)b
        //System.out.println( Integer.toBinaryString(7 >> 1) );

        //all_possible_subsets(newList);

        //brute_force(newList, 20);

       // Boolean[][] myTable = dynamic_programming(newList, 20);
        //dp_recover(newList, 20, myTable);

        // List to keep the runtimes of each program
        ArrayList<Long> timeList = new ArrayList<>(50);
        
        //Driver(30, 1000, true);
        try {   

            for(int x = 5; x <= 35; x++) 
            {
            timeList.add(Driver(x, 1000000, false));
            System.gc();
            }

        } catch(Error e) {
            System.out.println(e);
            System.out.println(timeList);
        }

        
        System.out.println(timeList);
        
        //Driver(20, 1000, true);
        //Driver(100, 1000, false);
        //Driver(100, 1000, true);
        //clever_algorithm(newList, 20);
        

    }

    /**
     * Driver program to test the runtimes of the Subset Sum Algorithms.
     *  Creates a random set of size 'n' random elements with range 1 ~ 'r'
     * 
     * @param n - Number of elements in the randomized set
     * @param r - Max Range of values
     * @param v - Boolean. If true, guarantees a solution. If false, makes sure
     * there is no solution to the target.
     * @return
     */
    public static long Driver(int n, int r, Boolean v) {

        Random rand = new Random();
        ArrayList<Integer> mySet = new ArrayList<>(n);
        
        int randTarget = 0;
        ArrayList<Integer> randSet = new ArrayList<>();

        Integer sum = 0;

        for (int i = 0; i < n; i++)
        {
            mySet.add(rand.nextInt(r) + 1);
        }
        // Number of digits in the random subset
        int digits = rand.nextInt(n) + 1;
        int randelement;
        int bound = 1;
        for (int x = 0; x < digits; x++) 
        {
            if(mySet.size() - 1 > 1) {
                bound = mySet.size() - 1;
            } else {
                bound = 1;
            }
            randelement = rand.nextInt(bound);
            randSet.add(mySet.get(randelement));
            mySet.remove(randelement);
        }
        mySet.addAll(randSet);
        System.out.println("mySet: "+ mySet);
        System.out.println("randSet: " + randSet);
        
        /* If v is true, let the sum of randSet's elements be the random target.
         * 
         * Else, (v is false), Find the total sum of all subsets. Set that total
         * sum as the minimum possible number to choose a random target.
         */
        if (v) {
            for(Integer i: randSet) {
                randTarget += i;
            }
        } else {

            List<Integer> subset;
            sum = 0;
            // Iterate to each subset to check if each one sums to the target
            for (Integer i : mySet)
            {
                sum += i;
            }
            //System.out.println("sum: " + sum);
            randTarget = rand.nextInt(sum) + rand.nextInt(1000);
        }

        System.out.println("n: " + n + " r: " + r + " v: " + v +" random target: " + randTarget);
        long startTime;
        long endTime;
        long totalTime = 0;;
        
        // Measure start time
        /*
        if(n < 25) {
            System.out.print("Brute Force: ");
            startTime = System.currentTimeMillis();
            //System.out.println("Brute Force Answer: " + brute_force(mySet, randTarget));
            brute_force(mySet, randTarget);
            // Timekeeping
            endTime   = System.currentTimeMillis();
            totalTime = endTime - startTime;
            System.out.println(mySet.size() + " elements Brute Force Time: " + totalTime + " ms\n");
        
        }*/
        
        //System.out.println("Dynamic Programming: ");
        // Measure start time
        //startTime = System.currentTimeMillis();
        
        // Run dynamic programming
        //Boolean [][] dpTable = dynamic_programming(mySet, randTarget);
        //System.out.println("Dynamic Programming Recover from Table Answer: " + dp_recover(mySet, randTarget, dpTable));
        //dp_recover(mySet, randTarget, dpTable);
        // Timekeeping
        //endTime   = System.currentTimeMillis();
        ////totalTime = endTime - startTime;
        //System.out.println(mySet.size() + " elements DP Time: " + totalTime + " ms\n");


        
        System.out.print("Clever Algorithm ");
        // Measure start time
        startTime = System.currentTimeMillis();

        // Run Clever Algorithm
        clever_algorithm(mySet, randTarget);

        // Timekeeping
        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println(mySet.size() + " elements Clever Time: " + totalTime + " ms\n");
        

        return totalTime;
    }
    /**
     * "Clever Algorithm" That solves the Subset Sum problem
     * 
     * @param theSet - The given set to find if a subset of it adds up to target.
     * @param theTarget - The given target that a subset must sum up to.
     * @return - ArrayList. Returns an Subset of theSet that sums to the target. 
     */
    public static ArrayList<Integer> clever_algorithm(ArrayList<Integer> theSet, int theTarget) {
        ArrayList<Integer> firstHalf = new ArrayList<>(theSet.subList(0, theSet.size()/2));
        ArrayList<Integer> secondHalf = new ArrayList<>(theSet.subList(theSet.size()/2, theSet.size()));

        //System.out.println(theSet);
        //System.out.println("First Half:" + firstHalf);
        //System.out.println(secondHalf);

        // Find all possible subsets in the set
        ArrayList<List<Integer>> firstHalfSubsetsL = all_possible_subsets(firstHalf);
        ArrayList<List<Integer>> secondHalfSubsetsH = all_possible_subsets(secondHalf);

        //System.out.println("1st Subsets" + firstHalfSubsetsL);
        //System.out.println("2nd Subsets " + secondHalfSubsetsH);
        // Create a list of subsets to store the answers
        ArrayList<List<Integer>> T = new ArrayList<>();
        ArrayList<List<Integer>> W = new ArrayList<>();

        // Declare a sum variable
        Integer sum;
        List<Integer> subset;
        List<Integer> subsetj;

        // Iterate to each subset to check if each one sums to the target
        for (Iterator<List<Integer>> i = firstHalfSubsetsL.iterator(); i.hasNext();)
        {
            // Reset the sum for every new subset
            sum = 0;

            // Enter the next subset
            subset = i.next();

            // For each integer in the subset, add to sum
            for(int j = 0; j < subset.size(); j++)
            {
                sum += subset.get(j);
            }
            //System.out.println(sum);
            // Check if the sum is LESS than or equal to the target
            // If equal, we are done. Therefore return this subset.
            // Else if LESS than, Add it to table T
            if(sum.equals(theTarget)) {
                System.out.println("TRUE. A subset was found to sum to the target. ");
                return new ArrayList<Integer>(subset);
            } else if (sum < theTarget ) {
                T.add(subset);
            }
        }

        // Iterate to each subset to check if each one sums to the target
        for (Iterator<List<Integer>> i = secondHalfSubsetsH.iterator(); i.hasNext();)
        {
            // Reset the sum for every new subset
            sum = 0;
            // Enter the next subset
            subset = i.next();

            // For each integer in the subset, add to sum
            for(int j = 0; j < subset.size(); j++)
            {
                sum += subset.get(j);
            }
            //System.out.println(sum);
            // Check if the sum is LESS than the target
            // If so, add the subset to List 'T'
            if(sum.equals(theTarget)) {
                System.out.println("TRUE. A subset was found to sum to the target. ");
                System.out.print("Subset Sum: " + subset + "\n");
                return new ArrayList<Integer>(subset);
            } else if (sum < theTarget ) {
                W.add(subset);
            }
        }

        //System.out.println("Clever\n T: " + T + "\n W: " + W);

        Collections.sort(W, new ListSumComparator());
        Collections.sort(T, new ListSumComparator());
        //System.out.println("Sorted W: " + W + "\n");
        //System.out.println("W Sums: ");

        /* 
         * 5. For each entry I in table T, find the subset  J subset H that 
         * yields the maximum weight not exceeding t when joined to I. If
         * equality holds for some I and some J, then return TRUE and I join J
         * and stop.
         * 
         */
        for(Iterator<List<Integer>> i = T.iterator(); i.hasNext();) {

            // Reset the sum for every new subset
            sum = 0;
            // Enter the next subset in T
            subset = i.next();

            // Find the Sum of the elements in I.
            for(int x = 0; x < subset.size(); x++) 
            {
                sum += subset.get(x);
            }
            // Now find the Sum of each subset in W,.
            for(Iterator<List<Integer>> j = W.iterator(); j.hasNext();) 
            {
                // Enter next subset in W
                subsetj = j.next();
                // For each element in this subset, add it to the sum.
                for(int y = 0; y < subsetj.size(); y++) 
                {
                    sum += subsetj.get(y);
                }
                // Compare this sum to the target. If it's equal, join I and J
                // Then return this subset.
                if (sum.equals(theTarget)) {
                    ArrayList<Integer> subsetSum= new ArrayList<>();
                    subsetSum.addAll(subset);
                    subsetSum.addAll(subsetj);
                    return subsetSum;
                }
                
            }
        }
        
        System.out.println("FALSE. No subset found.");
        return new ArrayList<Integer>();
    }
    /**
     * Dynamic Programming implementation to solve the Subset Sum problem. This
     * algorithm creates a table of n by t. n denoted by the size of the set and
     * t, the target integer.
     * 
     * @param theSet - The given set that one of its subset must sum to the target.
     * @param theTarget - The given target that a subset of the set must sum to.
     * @return - Return an n by t boolean table. For the table to answer the 
     * Subset Sum problem, one of the elements of the last column must be true.
     */
    public static Boolean[][] dynamic_programming(ArrayList<Integer> theSet, long theTarget) {

        // Variable Initialization
        int setSize = theSet.size();

        // Let A[1...n][0...t] be an array of integers of size n x (t+1)
        Boolean[][] myTruthTable = new Boolean[setSize][(int)theTarget+1];

        // Initialize all elements in the table to be false.
        for(int x = 0; x < myTruthTable.length; x++) {
            for(int y = 0; y < myTruthTable[0].length; y++) {
                myTruthTable[x][y] = false;
            }
        }

        // For i =1 to n do:
        //      Let A[i][0] = true
        for(int i = 0; i < setSize; i++) {
            myTruthTable[i][0] = true;
        }

        /* For j = 1 to t:
         *      If S[1] = j Let A[1][j] = true
         *      Else Let A[1][j] = false
         */
        for (int j = 1; j <= theTarget; j++)
        {
            if((theSet.get(0).equals(j))) {
                myTruthTable[0][j] = true;
            } else {
                myTruthTable[0][j] = false;
            }
        }

        /**
         * For i = 2 to n do:
         *      For j = 1  to t:
         *          If j >= S[i] Let A[i][j] = A[i - 1][j] or A[i - 1][j - S[i]]
         *          Else Let A[i][j] = A[i - 1][j]
         */
        for(int i = 1; i < setSize; i++)
        {
            for (int j = 1; j <= theTarget; j++) {
                if (j >= theSet.get(i)) {
                    myTruthTable[i][j] = myTruthTable[i - 1][j];
                    myTruthTable[i][j] =  myTruthTable[i-1][j]|| myTruthTable[i - 1][j - theSet.get(i)];
                } else {
                    myTruthTable[i][j] = myTruthTable[i - 1][j];
                }
            }
        }

        //System.out.println("Size: " + myTruthTable.size + " bytes");
        
        // Return A[n][t]
        return myTruthTable;
    }
    /**
     * Recovers the subset that is shown to have been found by dynamic
     * Programming's table. 
     * 
     * This algorithm recovers a solution from the table Dynamic Programming 
     * creates by backtracking, starting in the bottom right most corner. Once 
     * it finds a true element in the last column, it starts subtracting 
     * theTarget until it is 0. (#)The target is subtracted by referencing the
     * current element's row position and obtaining theSet's element on that row
     * number. For example, if the current position in the table is [12][4],
     * then the 4th integer in the set is used to subtract from theTarget integer.
     * 
     * The new target integer is then referenced as the next column. So if the
     * 4th integer was 9, then the current position is [3][4]. Now we go up a 
     * row until we hit a false or limit above the current position. Then we 
     * repeat the steps above by subtracting the sum.
     * 
     * @param theTable The given Boolean[][] table created by Dynamic Programming
     * @return
     */
    public static ArrayList<Integer> dp_recover(ArrayList<Integer> theSet, long theTarget, Boolean[][] theTable) {
        Boolean noTrueFound = true;

        // Check if the Dynamic Programming gave us a good table!
        for(int i = 0; i < theTable.length; i++) {
            if(theTable[i][theTable[0].length - 1]) {
                noTrueFound = false;
            }
        }

        // If no True was found in the last column, Dynamic Programming did not
        // give us a good table.
        if(noTrueFound) {
            System.out.println("No possible subsets that sum to the target");
            return null;
        }

        // Initialize Variables
        ArrayList<Integer> recoveredAnswer = new ArrayList<>();
        int row = theTable.length - 1; // 4
        int col = theTable[0].length - 1; // 11
        //System.out.print(col);
        Boolean currentElement = false;
        int counter = 0; // 5
        long sum = theTarget; // 12
        
        int currentRow, currentCol;
        currentRow = row;
        currentCol = col;

        //System.out.println("theTable: " + Arrays.deepToString(theTable));

        // Iteration and backtracking to find the subset that sum to the target.
        while( sum > 0) {
            
            // System.out.println("Counter: " + counter);

            // Check if the a row above the currentRow exists 
            // AND if the element directly above it is true or false.
            // If true then we move up
            // ELSE we move left by subtracting the 
            if( currentRow - 1 >= 0 && theTable[currentRow - 1][currentCol])
            {
                currentRow = currentRow - 1;
                currentElement = theTable[currentRow][currentCol];
            } else {
                sum = sum - theSet.get(currentRow);
                currentCol = currentCol - theSet.get(currentRow);
                recoveredAnswer.add(theSet.get(currentRow));
                counter = -1;
            }
            //Uncomment to check the step by step of recovering the set
            //System.out.println("Sum: " + sum);
            //System.out.println( "Col: " + currentCol + " Row: " + currentRow);
            //System.out.println(recoveredAnswer);
            //System.out.println();
            counter += 1;
            
        }
        //System.out.println(recoveredAnswer);
        
        return recoveredAnswer;
    }

    /**
     * Brute force solution to the Subset Sum problem.
     * 
     * @param theSet - A list of n positive integers.
     * @param theTarget - A target integer that the subset should sum to.
     * @return - A list of subsets that sum to the target integer. If there are 
     *           no such subset, then an empty list of subsets is returned.
     */
    public static ArrayList<Integer> brute_force(ArrayList<Integer> theSet, Integer theTarget) {
        
        

        // Find all possible subsets in the set
        ArrayList<List<Integer>> allSubsets = all_possible_subsets(theSet);


        // Declare a sum variable
        Integer sum;

        // Iterate to each subset to check if each one sums to the target
        for (Iterator<List<Integer>> i = allSubsets.iterator(); i.hasNext();)
        {
            // Reset the sum for every new subset
            sum = 0;
            // Enter the next subset
            List<Integer> item = i.next();

            // For each integer in the subset, add to sum
            for(int j = 0; j < item.size(); j++)
            {
                sum += item.get(j);
            }
            //System.out.println(sum);
            // Check if the sum is equal to the target
            if(sum.equals(theTarget))
            {
                //System.out.println("Sum: " + sum + " matches target: " + theTarget);
                //System.out.println("Valid Answers: " + item);
                
                return new ArrayList<Integer>(item);
            }
        }

        return null;
    }
    /**
     * Given a set, this function finds all the subsets of that set and 
     * stores it in a ArrayList of lists
     * @param theSet - The set that the functions uses to find its subsets
     * @return A List with each element containing a subset of the given set.
     */
    public static ArrayList<List<Integer>> all_possible_subsets(ArrayList<Integer> theSet) {
        // Measure start time
        long startTime = System.currentTimeMillis();

        // Variable Declarations and Initializations
        ArrayList<List<Integer>> theSubSets = new ArrayList<List<Integer>>(1000);
        ArrayList<Integer> subSet;
        int setSize = theSet.size();
        int i, j = 0;

        // Source: https://www.geeksforgeeks.org/finding-all-subsets-of-a-given-set-in-java/
        // References an element in the set to a binary position. Ex. 01 is the
        // first element in the set. If we have n integers, then we have 2^n
        // bits. One bit for each integer in the set.
        // Example:
        // Set: {2, 3, 5, 7, 9}. (101b) is the subset: {2, 5}
        // (1111)b is the subset {2, 3, 5, 7} 
        // Outer For loop to iterator per subset. (1 << setSize) = 2^{setSize} -1
        for(i = 0; i < (1 << setSize); i++) {
            // Create a new subset
            subSet = new ArrayList<Integer>();
            
            // For each integer in the set
            for(j = 0; j < setSize;  j++)
            {
                // Bitwise i & (1 << j)
                // Example: i = 1, 
                if ((i & (1 << j)) > 0) {
                    //System.out.print(theSet.get(j) + " ");
                    subSet.add(theSet.get(j));
                } 
            }
            //System.out.println();
            theSubSets.add(subSet);
        }

        //System.out.println(theSubSets);

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        //System.out.println("Finding all possible subsets Time: " + totalTime + " ms");

        return theSubSets;
    }

    static class ListSumComparator implements Comparator<List<Integer>> { 
  
        @Override
        public int compare(List<Integer> first, List<Integer> second) { 
    
            int firstSum = 0;
            int secondSum = 0;
            for (int x : first )
            {
                firstSum += x;
            }
            for (int y : second )
            {
                secondSum += y;
            }
    
            return firstSum - secondSum; 
        } 
    } 
}


