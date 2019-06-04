package network;

import infrastructure.Helper;
import infrastructure.InputsGenerator;
import infrastructure.SortingValidator;
import network.model.ComparatorSet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SortingNetwork implements Serializable {

    private int numberOfInputs;
    private int numberOfComparators;

    List<ComparatorSet> comparatorSets;
    InputsGenerator inputGenerator;

    public SortingNetwork(int numberOfInputs, int numberOfComparators) {
        this.numberOfInputs = numberOfInputs;
        this.numberOfComparators = numberOfComparators;
        this.comparatorSets = this.generateListOfComparators();
        this.inputGenerator = new InputsGenerator(this.numberOfInputs);
    }

    public List<ComparatorSet> getComparatorSets() {
        return comparatorSets;
    }

    @Override
    public String toString() {
        return "SortingNetwork{" +
                "comparatorSets=" + comparatorSets +
                '}';
    }

    public void setComparatorSets(List<ComparatorSet> comparatorSets) {
        this.comparatorSets = comparatorSets;
    }

    public List<ComparatorSet> generateListOfComparators() {

        ComparatorSet lastComparatorSet = new ComparatorSet();
        lastComparatorSet.setY(0);
        lastComparatorSet.setY(0);

        int numberOfComparatorsSoFar = 0;
        List<ComparatorSet> comparatorSets = new ArrayList<ComparatorSet>();

        while (numberOfComparatorsSoFar < this.numberOfComparators) {

            ComparatorSet comparatorSet = new ComparatorSet();

            comparatorSet.setX(new Random().nextInt(this.numberOfInputs - 1));
            Integer aux = new Random().nextInt(this.numberOfInputs - 1);
            while(aux + (comparatorSet.getX() + 1) >= this.numberOfInputs){
                aux = new Random().nextInt(this.numberOfInputs - 1);
            }
            comparatorSet.setY(aux + (comparatorSet.getX() + 1));

            comparatorSets.add(comparatorSet);

            if(!Helper.checkAllComparatorsAreDifferent(comparatorSets, comparatorSets.size() - 1)){
                comparatorSets.remove(comparatorSets.size() - 1);
                numberOfComparatorsSoFar--;
            }

            numberOfComparatorsSoFar++;
            lastComparatorSet.setX(comparatorSet.getX());
            lastComparatorSet.setY(comparatorSet.getY());

        }

        return comparatorSets;
    }

    public List<Integer> sortArrayWithComparators(List<Integer> inputs){

        try{for(ComparatorSet comparatorSet : this.comparatorSets){
            if(inputs.get(comparatorSet.getX()) > inputs.get(comparatorSet.getY())){
                Integer aux = inputs.get(comparatorSet.getX());
                inputs.set(comparatorSet.getX(), inputs.get(comparatorSet.getY()));
                inputs.set(comparatorSet.getY(), aux);
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

            List<Integer> inputAux = new ArrayList<Integer>();
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
