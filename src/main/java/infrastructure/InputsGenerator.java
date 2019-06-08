package infrastructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputsGenerator implements Serializable {

    private int numberOfInputs;

    public InputsGenerator(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    public List<List<String>> generateAllCasesWhenArrayIsNotSorted(){

        List<List<String>> listOfBinaryNumbers = new ArrayList<List<String>>();
        for(int counter = 1; counter < Math.pow(2,this.numberOfInputs) - 1; counter ++){

            String binaryNumberGenerated = this.makeBinaryNumberOfLength(Integer.toBinaryString(counter));
            List<String> binaryArrayNumber = Arrays.asList(binaryNumberGenerated.split(""));
            listOfBinaryNumbers.add(binaryArrayNumber);
        }
        List<List<String>> out = new ArrayList<List<String>>();
        List<String> outList = new ArrayList<String>();
        outList.add("20");
        outList.add("15");
        outList.add("10");
        out.add(outList);
        return listOfBinaryNumbers;
    }

    public String makeBinaryNumberOfLength(String number){

        String finalBinaryNumber = number;

        for(int i = 0; i < this.numberOfInputs - number.length(); i++){
            finalBinaryNumber = "0" + finalBinaryNumber;
        }

        return finalBinaryNumber;
    }
}
