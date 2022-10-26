package collect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Remove {
    public static void main(String[] args) {

        List<Integer> l = new ArrayList();
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);
        System.out.println(l);


        //正常
        Iterator<Integer> iterator = l.iterator();

        while (iterator.hasNext()){
            Integer item = iterator.next();
            System.out.println(item);
            iterator.remove();
        }

        System.out.println(l);

        //正常
        for (int i = l.size()-1; i >=0 ; i--) {
            l.remove(i);
        }

        //报错
        for (Integer integer : l) {
            l.remove(1);
        }

    }
}
