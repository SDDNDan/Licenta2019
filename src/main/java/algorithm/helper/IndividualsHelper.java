package algorithm.helper;

import algorithm.model.Individual;

import java.util.ArrayList;
import java.util.List;

public class IndividualsHelper {

    public static Float getTotalFitnessFromIndividuals(List<Individual> individuals){
        Float totalFitness = 0.0f;
        for(Individual individual : individuals){
            totalFitness += individual.getFitness();
        }

        return totalFitness;
    }

    public static List<Float> getListOfAllFitnessPercentage(Float totalFitness, List<Individual> individuals){
        List<Float> fitnessPercentage = new ArrayList<>();

        for(Individual individual : individuals){
            fitnessPercentage.add(individual.getFitness()/totalFitness);
        }

        return fitnessPercentage;
    }

    public static Float getSumOfAllFitnessPercentages(List<Float> listOfPercentages){
        Float sumOfFitnessPercentages = 0.0f;

        for(Float fitness: listOfPercentages){
            sumOfFitnessPercentages += fitness;
        }

        return sumOfFitnessPercentages;
    }
}
