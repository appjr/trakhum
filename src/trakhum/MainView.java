package trakhum;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {

    GridPane gridPane;
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
        gridPane = getGridPane();
        setupLogView();

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(gridPane,logView);

        primaryStage.setScene(new Scene(splitPane, 300, 275));
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


}
