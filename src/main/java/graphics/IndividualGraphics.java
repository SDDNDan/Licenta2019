package graphics;

import algorithm.model.Individual;
import graphics.helper.LineCoordinates;
import infrastructure.InputsGenerator;
import infrastructure.SortingValidator;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import network.model.Comparator;

import java.util.ArrayList;
import java.util.List;

public class IndividualGraphics extends Application {


    private ScrollPane scrollPane;
    private Group group;
    private LineCoordinates lineCoordinates;
    private IndividualRepresentation individualRepresentation;
    private InputsGenerator inputsGenerator;
    private List<Label> labels;

    public IndividualGraphics(Individual individual, int individualNumber) {
        this.labels = new ArrayList<>();
        this.group = new Group();
        this.scrollPane = new ScrollPane();
        scrollPane.setContent(this.group);
        scrollPane.setPannable(true);
        this.lineCoordinates = new LineCoordinates(50,100,250,100);
        this.individualRepresentation = new IndividualRepresentation(individual, this.lineCoordinates, 0);
        addIndividualToGroup(group, individualRepresentation);
        this.individualRepresentation.getIndividualShowButton().setVisible(false);
        this.inputsGenerator = new InputsGenerator(this.individualRepresentation.getIndividual().getSortingNetwork().getNumberOfInputs());
        this.generateListOfInputsForSorting();
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Individual Visualization");
        Scene scene = new Scene(this.scrollPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void addIndividualToGroup(Group group, IndividualRepresentation individualRepresentation) {
        group.getChildren().addAll(individualRepresentation.getWires());
        group.getChildren().addAll(individualRepresentation.getCircles());
        group.getChildren().add(individualRepresentation.getIndividualLabel());
        group.getChildren().addAll(individualRepresentation.getComparatorsCircles());
        group.getChildren().addAll(individualRepresentation.getComparatorsLines());
        group.getChildren().add(individualRepresentation.getIndividualFitness());
        group.getChildren().addAll(individualRepresentation.getWireElements());
        group.getChildren().add(individualRepresentation.getIndividualShowButton());
    }

    public void generateListOfInputsForSorting(){
        List<List<String>> inputsPossibility = this.inputsGenerator.generateAllCasesWhenArrayIsNotSorted();
        double startPosition = this.lineCoordinates.getStartX();
        for(List<String> input : inputsPossibility){
            List<Integer> inputAux = new ArrayList<Integer>();
            for(String i : input){
                inputAux.add(Integer.valueOf(i));
            }

            if(SortingValidator.arrayIsSorted(inputAux)){
                this.createLabelForInput(inputAux, true);
            }else{
                this.createLabelForInput(inputAux, false);
            }


            for(Comparator i : this.individualRepresentation.getIndividual().getSortingNetwork().getComparators()){

                if(inputAux.get(i.getX()) > inputAux.get(i.getY())){
                    Integer aux = inputAux.get(i.getX());
                    inputAux.set(i.getX(), inputAux.get(i.getY()));
                    inputAux.set(i.getY(), aux);
                }

                this.lineCoordinates.setStartX(this.lineCoordinates.getStartX() + 200);

                if(SortingValidator.arrayIsSorted(inputAux)){
                    this.createLabelForInput(inputAux, true);
                }

                this.createLabelForInput(inputAux, false);
                this.createResultLabel();
            }

            this.lineCoordinates.setStartY(this.lineCoordinates.getStartY() + 50);
            this.lineCoordinates.setStartX(startPosition);
        }



        this.group.getChildren().addAll(this.labels);
    }

    private void createLabelForInput(List<Integer> input, boolean sorted) {
        Label label = new Label();
        label.setText(input.toString());
        label.setLayoutX(this.lineCoordinates.getStartX());
        label.setLayoutY(this.lineCoordinates.getStartY());
        label.setFont(new Font("Arial", 22));

        if(sorted){
            label.setStyle("-fx-background-color: #00ff00;");
        }

        this.labels.add(label);
    }

    private void createResultLabel(){
        Label label = new Label();
        label.setText("=>");
        label.setLayoutX(this.lineCoordinates.getStartX() - 40);
        label.setLayoutY(this.lineCoordinates.getStartY());
        label.setFont(new Font("Arial", 22));
        this.labels.add(label);
    }
}

