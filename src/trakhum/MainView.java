package trakhum;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {

    GridPane dataPane;
    TextArea logView;
    String [] ids = new String[2];
    Label id1;
    Label x1;
    Label y1;
    Label z1;
    Label id2;
    Label x2;
    Label y2;
    Label z2;
    public MainView(Stage primaryStage) throws IOException {
        dataPane = getGridPane();
        setupLogView();

        SplitPane horizontalDataPane = new SplitPane();
        horizontalDataPane.setOrientation(Orientation.VERTICAL);
        horizontalDataPane.getItems().addAll(dataPane,logView);

        SplitPane verticalMainPane = new SplitPane();
        verticalMainPane.setOrientation(Orientation.HORIZONTAL);
        Area3D stage3d = buildAxes();
        verticalMainPane.getItems().addAll(stage3d,horizontalDataPane);

        primaryStage.setScene(new Scene(verticalMainPane, 300, 275));


        primaryStage.show();
    }

    private void setupLogView() {
        logView = new TextArea();
        logView.setScrollTop(0);
        logView.setScrollLeft(0);

    }

    private GridPane getGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(50);
        gridPane.setHgap(50);

        id1 = new Label("a");
        x1 = new Label("x1");
        y1 = new Label("y1");
        z1 = new Label("z1");
        addComponents(gridPane,id1, x1, y1, z1, 0);

        id2 = new Label("b");
        x2 = new Label("x2");
        y2 = new Label("y2");
        z2 = new Label("z2");
        addComponents(gridPane,id2, x2, y2, z2, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100/gridPane.getColumnCount());
        gridPane.getColumnConstraints().add(cc);
        return gridPane;
    }

    private void addComponents(GridPane gridPane, Label id, Label x, Label y, Label z, int line){
        setLabel(gridPane,id,1,line);
        setLabel(gridPane,x,2,line);
        setLabel(gridPane,y,3,line);
        setLabel(gridPane,z,4,line);
    }

    private void setLabel(GridPane gridPane, Label label, int column, int row){
        Font font = new Font("Arial", 30);
        double minWidth = 300;
        label.setMinWidth(minWidth);
        label.setFont(font);
        gridPane.add(label, column, row, 1, 1);
    }

    public void updateLog(String line){
        logView.appendText(line);
    }

    public void updateDataSet1(String [] data){
        id1.setText(data[0]);
        x1.setText(data[1]);
        y1.setText(data[2]);
        z1.setText(data[3]);
    }

    public void updateDataSet2(String [] data){
        id2.setText(data[0]);
        x2.setText(data[1]);
        y2.setText(data[2]);
        z2.setText(data[3]);
    }

    private Area3D buildAxes() {
        Area3D world = new Area3D();
        final Area3D axisGroup = new Area3D();
        double AXIS_LENGTH = 250.0;
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(true);
        world.getChildren().addAll(axisGroup);
        return world;
    }

}
