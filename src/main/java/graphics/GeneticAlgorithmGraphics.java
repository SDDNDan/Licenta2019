package graphics;

import algorithm.GeneticAlgorithm;
import graphics.helper.LineCoordinates;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GeneticAlgorithmGraphics extends Application {

    private LineCoordinates lineCoordinates;
    private GeneticAlgorithm geneticAlgorithm;
    private Group group;
    private ScrollPane scrollPane;
    private int generationNumber;

    private double spaceXBetweenGenerations = 200;

    public static void main(String[] args){
        launch(args);
    }

    public GeneticAlgorithmGraphics(){
        this.lineCoordinates = new LineCoordinates(50,100,250,100);
        this.geneticAlgorithm  = new GeneticAlgorithm(20,4,4);
        this.group = new Group();
        this.scrollPane = new ScrollPane();
        scrollPane.setContent(this.group);
        scrollPane.setPannable(true);
        this.generationNumber = 0;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sorting Network");
        Scene scene = new Scene(this.scrollPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        for(int i = 0; i< 5; i++){
            this.geneticAlgorithm.evaluateGeneration();
            this.generateGeneration();
            this.geneticAlgorithm.runAlgorithm();
        }



    }

    private void generateGeneration(){

        this.group.getChildren().add(this.createGenerationTitle());

        for(int i=0; i< 3; i++){
            IndividualRepresentation individualRepresentation = new IndividualRepresentation(this.geneticAlgorithm.getIndividuals().get(i), this.lineCoordinates, i);
            this.addIndividualToGroup(this.group, individualRepresentation);
            this.updateLineCoordinatesForNewIndividual(this.lineCoordinates,this.geneticAlgorithm.getIndividuals().get(i).getSortingNetwork().getNumberOfInputs());
        }

        this.generationNumber++;
        this.resetLineCoordinates();
        this.updateLineCoordinatesForNewGeneration();
    }

    private void updateLineCoordinatesForNewGeneration() {
        this.lineCoordinates.setStartX(this.lineCoordinates.getStartX() + (this.generationNumber * spaceXBetweenGenerations * 2));
        this.lineCoordinates.setEndX(this.lineCoordinates.getEndX() + (this.generationNumber * spaceXBetweenGenerations * 2));
    }

    private Label createGenerationTitle() {
        Label label = new Label();
        label.setText("Generation" + this.generationNumber);
        label.setLayoutX(this.lineCoordinates.getStartX() + 40);
        label.setLayoutY(this.lineCoordinates.getStartY() - 100);
        label.setFont(new Font("Arial", 22));

        return label;
    }

    private void updateLineCoordinatesForNewIndividual(LineCoordinates lineCoordinates, int numberOfInputs) {
        lineCoordinates.setStartY(lineCoordinates.getStartY() + (40 * (numberOfInputs - 1)));
        lineCoordinates.setEndY(lineCoordinates.getEndY() + (40 * (numberOfInputs - 1)));
    }

    public void addIndividualToGroup(Group group, IndividualRepresentation individualRepresentation){
        IndividualGraphics.addIndividualToGroup(group, individualRepresentation);
    }

    private void resetLineCoordinates(){
        this.lineCoordinates = new LineCoordinates(50,100,250,100);
    }
}