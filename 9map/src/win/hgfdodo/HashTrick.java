package win.hgfdodo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HashTrick {
    public static void main(String[] args) {
        List init = Arrays.asList("Aa", "BB");
        int level = 3;

        List<String> sameHash = null;
        for (int i = 0; i < level; i++) {
            sameHash = generate(init);
            init = sameHash;
        }

        int last = 0;
        boolean all = true;
        for (int i = 0; i < sameHash.size(); i++) {
            if(last==0){
                last = sameHash.get(i).hashCode();
            }else{
                if(last!=sameHash.get(i).hashCode()){
                    System.out.println(sameHash.get(i));
                    all = false;
                }
            }
        }
        System.out.println(all);
    }

    public static List<String> generate(List<String> seed) {
        List<String> res = new ArrayList<>();
        int n = seed.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res.add(seed.get(i) + seed.get(j));
            }
        }

        System.out.println("res:" + res);
        return res;
    }
}
