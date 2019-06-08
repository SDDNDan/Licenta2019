package algorithm;

import algorithm.helper.IndividualsHelper;
import algorithm.model.Individual;
import infrastructure.Helper;
import infrastructure.IndividualGenerator;
import network.model.Comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    private Float MUTATION_PROBABILITY;
    private Float CROSSOVER_PROBABILITY;

    private int sizeOfPopulation;
    private int numberOfInputs;
    private int numberOfComparators;

    private IndividualGenerator individualGenerator;

    private List<Individual> individuals;

    public GeneticAlgorithm(int sizeOfPopulation, int numberOfInputs, int numberOfComparators) {
        this.sizeOfPopulation = sizeOfPopulation;
        this.numberOfInputs = numberOfInputs;
        this.numberOfComparators = numberOfComparators;

        this.individualGenerator = new IndividualGenerator(this.numberOfInputs, this.numberOfComparators);
        this.individuals = this.individualGenerator.generateNIndividuals(this.sizeOfPopulation);

        this.MUTATION_PROBABILITY = 1f/this.numberOfComparators;
        this.CROSSOVER_PROBABILITY = 0.75f;
    }

    @Override
    public String toString() {
        System.out.println("Genetic Algorithm");
        for(Individual i : individuals){
            System.out.println(i.getFitness() + " " + i.getSortingNetwork().toString());
        }

        return "";
    }

    private void mutateIndividuals(){

        for(int i = 0; i < this.sizeOfPopulation; i++){
            for(int j = 0; j < this.numberOfComparators; j++){
                Float mutationRandomChoice = (float)Math.random();

                if(mutationRandomChoice < this.MUTATION_PROBABILITY){


                        do{
                            Comparator comparator = new Comparator();
                            comparator.setX(new Random().nextInt(this.numberOfInputs - 1));
                            Integer aux = new Random().nextInt(this.numberOfInputs - 1) + (comparator.getX() + 1);
                            while(aux >= this.numberOfInputs){
                                aux = new Random().nextInt(this.numberOfInputs - 1) + (comparator.getX() + 1);
                            }
                            comparator.setY(aux);
                            this.individuals.get(i).getSortingNetwork().getComparators().get(j).setComparator(comparator);
                        }while(!Helper.checkAllComparatorsAreDifferent(this.individuals.get(i).getSortingNetwork().getComparators(), j));



                }

            }
        }

    }

    public void crossOverIndividuals(){

        List<List<Comparator>> comparatorsGenerated = new ArrayList<List<Comparator>>();

        for(int i = 0; i < this.individuals.size() - 2; i++){
            Comparator comparatorAux = new Comparator();
            comparatorAux.setX(-1);
            comparatorAux.setY(-1);

            List<Integer> parents = Helper.getPairNotEqualNumbers(this.sizeOfPopulation);
            List<Comparator> newListOfComparators = new ArrayList<Comparator>();

            for(int j = 0; j < this.numberOfComparators; j++){
                Float crossOverRandomChoice = (float)Math.random();

                if(crossOverRandomChoice < this.CROSSOVER_PROBABILITY){
                    if(comparatorAux.Equals(this.individuals.get(parents.get(0)).getSortingNetwork().getComparators().get(j))){
                        newListOfComparators.add(this.individuals.get(parents.get(1)).getSortingNetwork().getComparators().get(j));
                        comparatorAux.setComparator(this.individuals.get(parents.get(1)).getSortingNetwork().getComparators().get(j));
                    }else{
                        newListOfComparators.add(this.individuals.get(parents.get(0)).getSortingNetwork().getComparators().get(j));
                        comparatorAux.setComparator(this.individuals.get(parents.get(0)).getSortingNetwork().getComparators().get(j));
                    }
                }else{
                    if(comparatorAux.Equals(this.individuals.get(parents.get(1)).getSortingNetwork().getComparators().get(j))){
                        newListOfComparators.add(this.individuals.get(parents.get(0)).getSortingNetwork().getComparators().get(j));
                        comparatorAux.setComparator(this.individuals.get(parents.get(0)).getSortingNetwork().getComparators().get(j));
                    }else{
                        newListOfComparators.add(this.individuals.get(parents.get(1)).getSortingNetwork().getComparators().get(j));
                        comparatorAux.setComparator(this.individuals.get(parents.get(1)).getSortingNetwork().getComparators().get(j));
                    }
                }

                comparatorsGenerated.add(newListOfComparators);
            }
        }

        // Sorteaza individuals
        Collections.sort(this.individuals, (o1, o2) -> Float.compare(o2.getFitness(), o1.getFitness()));

        comparatorsGenerated.add(this.individuals.get(0).getSortingNetwork().getComparators());
        comparatorsGenerated.add(this.individuals.get(1).getSortingNetwork().getComparators());
        for(int i = 0; i < this.individuals.size() - 2; i++){
            this.individuals.get(i).getSortingNetwork().setComparators(comparatorsGenerated.get(i));
        }

    }

    public void selectIndividuals() {

        List<Individual> selectedIndividuals = new ArrayList<>();

        Float totalFitness = IndividualsHelper.getTotalFitnessFromIndividuals(this.individuals);
        List<Float> listFitnessPercentages = IndividualsHelper.getListOfAllFitnessPercentage(totalFitness, this.individuals);

        Float mini = 0.0f;
        List<List<Float>> min_max_list = new ArrayList<>();

        for(int i = 0; i < listFitnessPercentages.size(); i++){

            List<Float> min_max  = new ArrayList<>();
            min_max.add(mini);
            min_max.add(mini + listFitnessPercentages.get(i));
            min_max_list.add(min_max);
            mini = mini + listFitnessPercentages.get(i);
        }

        Integer count = 0;

        while(count < this.sizeOfPopulation - 2){
            Float random = (float)Math.random();
            for(int j = 0; j < listFitnessPercentages.size() - 1; j++){
                if(min_max_list.get(j).get(0) < random && min_max_list.get(j).get(1) >= random){
                    selectedIndividuals.add((Individual)Helper.deepClone(this.individuals.get(j)));
                    count += 1;
                    break;
                }
            }
        }

        selectedIndividuals.add((Individual)Helper.deepClone(this.individuals.get(0)));
        selectedIndividuals.add((Individual)Helper.deepClone(this.individuals.get(1)));
        this.individuals = selectedIndividuals;

    }

    public void evaluateIndividuals(){

        for(Individual individual : this.individuals){
            individual.setFitness(individual.getSortingNetwork().evaluateSortingNetwork());
        }

        Collections.sort(this.individuals, (o1, o2) -> Float.compare(o2.getFitness(), o1.getFitness()));

        System.out.println("Best: " + this.individuals.get(0).toString() + " Fitness: " + this.individuals.get(0).getFitness());
    }

    public void evaluateGeneration(){
        this.evaluateIndividuals();
    }

    public void runAlgorithm(){
        this.selectIndividuals();
        this.crossOverIndividuals();
        this.mutateIndividuals();
    }


    public List<Individual> getIndividuals() {
        return individuals;
    }
}
