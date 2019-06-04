package algorithm;

public class AlgorithmStarter {
    public static void main(String[] args) {

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(10,5,4);

        for(int i = 0 ;i < 1000; i++){
            geneticAlgorithm.runAlgorithm();
            System.out.println(geneticAlgorithm.toString());
        }


    }

}
