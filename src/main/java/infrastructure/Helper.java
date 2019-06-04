package infrastructure;

import network.model.ComparatorSet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Helper {

    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
            oos.writeObject(object);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Integer> getPairNotEqualNumbers(Integer maxNumber){

        Integer x = 0;
        Integer y = 0;
        while (x == y){
            x = new Random().nextInt(maxNumber - 1);
            y = new Random().nextInt(maxNumber - 1);
        }

        List<Integer> aux = new ArrayList<Integer>();
        aux.add(x);
        aux.add(y);

        return aux;
    }

    public static boolean checkAllComparatorsAreDifferent(List<ComparatorSet> comparatorSetList, Integer j){
        for(int i=0 ;i<comparatorSetList.size();i++){
            if(j != i)
                    if(comparatorSetList.get(i).getY() == comparatorSetList.get(j).getY()){
                        if(comparatorSetList.get(i).getX() == comparatorSetList.get(j).getX()){
                            return false;
                        }
                    }
                }



        return true;
    }
}
