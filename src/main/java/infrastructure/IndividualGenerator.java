package infrastructure;

import algorithm.model.Individual;
import network.SortingNetwork;

import java.util.ArrayList;
import java.util.List;

public class IndividualGenerator {

    private int numberOfInputs;
    private int numberOfComparators;

    public IndividualGenerator(int numberOfInputs, int numberOfComparators) {
        this.numberOfInputs = numberOfInputs;
        this.numberOfComparators = numberOfComparators;
    }

    public List<Individual> generateNIndividuals(Integer n){
        List<Individual> individuals = new ArrayList<Individual>();

        for(int i = 0; i < n; i++){
            individuals.add(generateIndividual());
        }

        return individuals;
    }

    public Individual generateIndividual(){
        return new Individual(new SortingNetwork(this.numberOfInputs, this.numberOfComparators));
    }
}
