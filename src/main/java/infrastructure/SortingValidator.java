package infrastructure;

import java.util.List;

public class SortingValidator {

    public static boolean arrayIsSorted(List<Integer> array){

        for(int i = 0; i < array.size() - 1; i++){

            if(array.get(i) > array.get(i + 1)){
                return false;
            }
        }

        return true;
    }
}
