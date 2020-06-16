package trakhum;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainView mainView = new MainView(primaryStage);
        Controller controller = new Controller();
        controller.startProcessing(mainView);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
