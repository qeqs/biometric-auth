package biometric;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Main extends Application {

    private static ApplicationContext context;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        context.getAutowireCapableBeanFactory().autowireBean(controller);
        controller.init();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 729,358 ));
        primaryStage.show();
    }


    public static void main(String[] args) {

        context = SpringApplication.run(Main.class, args);
        launch(args);
    }
}
