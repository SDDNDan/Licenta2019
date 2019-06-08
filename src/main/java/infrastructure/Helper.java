package infrastructure;

import network.model.Comparator;

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

    public static boolean checkAllComparatorsAreDifferent(List<Comparator> comparatorList, Integer j){
        for(int i = 0; i< comparatorList.size(); i++){
            if(j != i)
                    if(comparatorList.get(i).getY() == comparatorList.get(j).getY()){
                        if(comparatorList.get(i).getX() == comparatorList.get(j).getX()){
                            return false;
                        }
                    }
                }



        return true;
    }
}
