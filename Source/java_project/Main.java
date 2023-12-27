import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showGameScreen();
    }


    private void showGameScreen() throws IOException {
        // Create an instance of the SnakeGame class (assuming SnakeGame is your game logic class)
        //Mode2 snakeGame = new Mode2();

        // Initialize the game, set up the scene, and start the game loop
        //Scene gameScene = snakeGame.initGame();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,700,440);
        Image icon = new Image("img/icon_snake.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();

        // Start the game loop
        //snakeGame.startGameLoop();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
