import java.io.Console;
import java.io.IOException;
import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;


public class testClassPleaseIgnore {
    public static void main(String[] args) throws InterruptedException, IOException {

        System.out.println("hello world");
        System.out.println("hello world2");
        int x = 1;
        int y = 1;
        int z = 2;
        System.out.println(areAllEqual(1,x,y,z));

        ArrayList<Integer> array1 = new ArrayList<>();
        ArrayList<Integer> array2 = new ArrayList<>();
        ArrayList<Integer> finalArray = new ArrayList<>();

        array1.add(0);
        array1.add(1);
        array1.add(2);

        array2.add(3);
        array2.add(4);
        array2.add(5);

        finalArray.addAll(array1);
        finalArray.addAll(array2);

        for (int i: finalArray)
        {
            System.out.println(i + " ");
        }

        p("sdsad");

    }

    private static void p(Object o) {System.out.println(o);}
    private static boolean areAllEqual(int value, int... values)
    {
        if (values.length == 1)
        {return true;}

        for (int i = 0; i < values.length; i++) {
            if (values[i] != value)
            {return false;}
        }

        return true;
    }
    public static void print2DArray(String[][] array) {
        int rows = array.length;
        int columns = array[0].length;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                System.out.print("[" + array[x][y] + "] ");
            }
            System.out.println();
        }
    }


}


