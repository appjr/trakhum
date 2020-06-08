package trakhum;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {

    public MainView(Stage primaryStage) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label id1 = new Label("a");
        Label x1 = new Label("x1");
        Label y1 = new Label("y1");
        Label z1 = new Label("z1");
        addComponents(gridPane,id1, x1, y1, z1, 0);

        Label id2 = new Label("b");
        Label x2 = new Label("x2");
        Label y2 = new Label("y2");
        Label z2 = new Label("z2");
        addComponents(gridPane,id2, x2, y2, z2, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100/gridPane.getColumnCount());
        gridPane.getColumnConstraints().add(cc);


        primaryStage.setScene(new Scene(gridPane, 300, 275));
        primaryStage.show();
    }

    private void addComponents(GridPane gridPane, Label id, Label x, Label y, Label z, int line){
        setLabel(gridPane,id,1,line);
        setLabel(gridPane,x,2,line);
        setLabel(gridPane,y,3,line);
        setLabel(gridPane,z,4,line);
    }

    private void setLabel(GridPane gridPane, Label label, int column, int row){
        Font font = new Font("Arial", 50);
        double minWidth = 200;
        label.setMinWidth(minWidth);
        label.setFont(font);
        gridPane.add(label, column, row, 1, 1);
    }

}
