package network;

import infrastructure.Helper;
import infrastructure.InputsGenerator;
import infrastructure.SortingValidator;
import network.model.Comparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SortingNetwork implements Serializable {

    private int numberOfInputs;
    private int numberOfComparators;

    List<Comparator> comparators;
    InputsGenerator inputGenerator;

    public SortingNetwork(int numberOfInputs, int numberOfComparators) {
        this.numberOfInputs = numberOfInputs;
        this.numberOfComparators = numberOfComparators;
        this.comparators = this.generateListOfComparators();
        this.inputGenerator = new InputsGenerator(this.numberOfInputs);
    }

    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    public List<Comparator> getComparators() {
        return comparators;
    }

    @Override
    public String toString() {
        return "SortingNetwork{" +
                "comparators=" + comparators +
                '}';
    }

    public void setComparators(List<Comparator> comparators) {
        this.comparators = comparators;
    }

    public List<Comparator> generateListOfComparators() {

        Comparator lastComparator = new Comparator();
        lastComparator.setY(0);
        lastComparator.setY(0);

        int numberOfComparatorsSoFar = 0;
        List<Comparator> comparators = new ArrayList<Comparator>();

        while (numberOfComparatorsSoFar < this.numberOfComparators) {

            Comparator comparator = new Comparator();

            comparator.setX(new Random().nextInt(this.numberOfInputs - 1));
            Integer aux = new Random().nextInt(this.numberOfInputs - 1);
            while(aux + (comparator.getX() + 1) >= this.numberOfInputs){
                aux = new Random().nextInt(this.numberOfInputs - 1);
            }
            comparator.setY(aux + (comparator.getX() + 1));

            comparators.add(comparator);

            if(!Helper.checkAllComparatorsAreDifferent(comparators, comparators.size() - 1)){
                comparators.remove(comparators.size() - 1);
                numberOfComparatorsSoFar--;
            }

            numberOfComparatorsSoFar++;
            lastComparator.setX(comparator.getX());
            lastComparator.setY(comparator.getY());

        }

        return comparators;
    }

    public List<Integer> sortArrayWithComparators(List<Integer> inputs){

        try{for(Comparator comparator : this.comparators){
            if(inputs.get(comparator.getX()) > inputs.get(comparator.getY())){
                Integer aux = inputs.get(comparator.getX());
                inputs.set(comparator.getX(), inputs.get(comparator.getY()));
                inputs.set(comparator.getY(), aux);
            }
        }}catch(Exception e){
            System.out.println(e);
        }


        return inputs;
    }

    public Float evaluateSortingNetwork(){

        List<List<String>> inputsPossibility = this.inputGenerator.generateAllCasesWhenArrayIsNotSorted();
        int numberOfListThatAreSorted = 0;

        for(List<String> input : inputsPossibility){

            List<Integer> inputAux = new ArrayList<>();
            for(String i : input){
                inputAux.add(Integer.valueOf(i));
            }

            if(SortingValidator.arrayIsSorted(sortArrayWithComparators(inputAux))){
                numberOfListThatAreSorted += 1;
            }
        }

        return (100f*numberOfListThatAreSorted/inputsPossibility.size());
    }

    public static void main(String[] args) {
        SortingNetwork sortingNetwork = new SortingNetwork(3,3);
        System.out.println(sortingNetwork.evaluateSortingNetwork());
    }
}
