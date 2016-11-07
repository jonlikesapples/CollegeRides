/**
 * Created by JonWong on 11/2/16.
 */
public class testReversePleaseIgnore {
    public static void main(String[] args)
    {
        int x = 123;
        int rev = 0;

        while(x != 0){
            rev = rev*10 + x%10;
            x = x/10;
            p("rev: " + rev);
            p("x: " + x);
        }

        p(rev);

    }

    public static void p(Object o)
    {
        System.out.println(o.toString());
    }
}
