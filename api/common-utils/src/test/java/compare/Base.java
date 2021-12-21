package compare;

public class Base {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b =a;
        System.out.println(a==b);

        int a1=1;
        Integer b1=Integer.parseInt("1");
        Integer c1=Integer.parseInt("1");
        System.out.println(a1==b1);
        System.out.println(b1==c1);
        System.out.println(b1==a1);

    }
}
