package graphics;

import algorithm.model.Individual;
import graphics.helper.LineCoordinates;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

class IndividualRepresentation {

    private double circleRadius = 5;
    private double lineCoordinatesDifference = 40;

    private Individual individual;
    private List<Line> wires;
    private List<Circle> circles;

    private List<Label> wireElements;
    private List<Circle> comparatorsCircles;
    private List<Line> comparatorsLines;

    private LineCoordinates lineCoordinates;
    private LineCoordinates originalLineCoordinates;
    private Label individualLabel;
    private Label individualFitness;

    private int individualNumber;


    IndividualRepresentation(Individual individual, LineCoordinates lineCoordinates, int individualNumber){
        this.individual = individual;
        this.wires = new ArrayList<>();
        this.circles = new ArrayList<>();
        this.comparatorsCircles = new ArrayList<>();
        this.comparatorsLines = new ArrayList<>();
        this.wireElements = new ArrayList<>();
        this.lineCoordinates = lineCoordinates;
        this.originalLineCoordinates = new LineCoordinates(this.lineCoordinates.getStartX(),this.lineCoordinates.getStartY(),this.lineCoordinates.getEndX(),this.lineCoordinates.getEndY());
        this.individualNumber = individualNumber;
        this.individualLabel = this.createIndividualLabel();
        this.individualFitness = this.createIndividualFitness();

        this.createLinesAndCircles();
        this.createComparators();
    }

    private void createLinesAndCircles(){
        for(int lineIndex = 0; lineIndex < this.individual.getSortingNetwork().getNumberOfInputs(); lineIndex++){

            this.wireElements.addAll(createWireElement(lineIndex));
            this.circles.add(createStartCircle());
            this.circles.add(createEndCircle());
            this.wires.add(createLine());

            this.updateLineCoordinates();
        }

        this.resetLineCoordinates();
    }

    private void resetLineCoordinates() {
        this.lineCoordinates = new LineCoordinates(this.originalLineCoordinates.getStartX(),
                this.originalLineCoordinates.getStartY(),
                this.originalLineCoordinates.getEndX(),
                this.originalLineCoordinates.getEndY());
    }

    private void createComparators(){
        System.out.println(this.individual.getSortingNetwork().toString());
        double distanceBetweenComparators = (this.lineCoordinates.getEndX() - this.lineCoordinates.getStartX() - 30)/this.individual.getSortingNetwork().getComparators().size();
        for(int lineIndex = 0; lineIndex < this.individual.getSortingNetwork().getComparators().size(); lineIndex++){
            Circle circle = new Circle(30 + this.lineCoordinates.getStartX() + (distanceBetweenComparators * lineIndex),
                                       this.lineCoordinates.getStartY() + (this.individual.getSortingNetwork().getComparators().get(lineIndex).getX() * this.lineCoordinatesDifference),
                                        this.circleRadius);
            Circle circle2 = new Circle(30 + this.lineCoordinates.getStartX() + (distanceBetweenComparators * lineIndex),
                                        this.lineCoordinates.getStartY() + (this.individual.getSortingNetwork().getComparators().get(lineIndex).getY() * this.lineCoordinatesDifference),
                                        this.circleRadius);

            Line line = new Line(30 + this.lineCoordinates.getStartX() + (distanceBetweenComparators * lineIndex),
                                 this.lineCoordinates.getStartY() + (this.individual.getSortingNetwork().getComparators().get(lineIndex).getX() * this.lineCoordinatesDifference),
                                 30 + this.lineCoordinates.getStartX() + (distanceBetweenComparators * lineIndex),
                                 this.lineCoordinates.getStartY() + (this.individual.getSortingNetwork().getComparators().get(lineIndex).getY() * this.lineCoordinatesDifference));

            this.comparatorsCircles.add(circle);
            this.comparatorsCircles.add(circle2);
            this.comparatorsLines.add(line);
        }

        this.resetLineCoordinates();
    }

    private Label createIndividualLabel(){
        Label label = new Label();
        label.setText("Individual " + this.individualNumber);
        label.setLayoutX(this.lineCoordinates.getStartX());
        label.setLayoutY(this.lineCoordinates.getStartY() - 50);
        label.setFont(new Font("Arial", 16));

        return label;
    }

    private Label createIndividualFitness(){
        Label label = new Label();
        label.setText("Fitness " + this.individual.getFitness());
        label.setLayoutX(this.lineCoordinates.getStartX() + 100);
        label.setLayoutY(this.lineCoordinates.getStartY() - 50);
        label.setFont(new Font("Arial", 16));

        return label;
    }

    private void updateLineCoordinates(){

        this.lineCoordinates.setStartY(this.lineCoordinates.getStartY() + lineCoordinatesDifference);
        this.lineCoordinates.setEndY(this.lineCoordinates.getEndY() + lineCoordinatesDifference);
    }

    private Line createLine(){
        Line line = new Line();
        line.setStartX(lineCoordinates.getStartX());
        line.setStartY(lineCoordinates.getStartY());
        line.setEndX(lineCoordinates.getEndX());
        line.setEndY(lineCoordinates.getEndY());

        return line;
    }

    private List<Label> createWireElement(int lineIndex) {
        List<Label> labels = new ArrayList<>();
        Label label = new Label();
        label.setText("X" + lineIndex);
        label.setLayoutX(this.lineCoordinates.getStartX() - 30);
        label.setLayoutY(this.lineCoordinates.getStartY() - 10);
        label.setFont(new Font("Arial", 16));

        Label label2 = new Label();
        label2.setText("Y" + lineIndex);
        label2.setLayoutX(this.lineCoordinates.getEndX() + 20);
        label2.setLayoutY(this.lineCoordinates.getStartY() - 10);
        label2.setFont(new Font("Arial", 16));

        labels.add(label);
        labels.add(label2);

        return labels;
    }

    private Circle createStartCircle(){
        return new Circle(this.lineCoordinates.getStartX(), this.lineCoordinates.getStartY(),circleRadius);
    }

    private Circle createEndCircle(){
        return new Circle(this.lineCoordinates.getEndX(), this.lineCoordinates.getStartY(),circleRadius);
    }

    List<Line> getWires() {
        return wires;
    }

    Label getIndividualLabel() {
        return individualLabel;
    }

    List<Circle> getCircles() {
        return circles;
    }

    List<Circle> getComparatorsCircles() {
        return comparatorsCircles;
    }

    List<Line> getComparatorsLines() {
        return comparatorsLines;
    }

    Label getIndividualFitness() {
        return individualFitness;
    }

    List<Label> getWireElements() {
        return wireElements;
    }
}
