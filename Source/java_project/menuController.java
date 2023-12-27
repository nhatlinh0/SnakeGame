import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class menuController {
    private Stage primaryStage;
    public static Scene GameMode;
    public static int value;

    @FXML
    private void Start(ActionEvent event){
        Image icon = new Image("img/icon_snake.png");
        switch (value) {
            case 1:
                ModeWall modeWall = new ModeWall();
                GameMode = modeWall.initGame();

                primaryStage = new Stage();

                primaryStage.getIcons().add(icon);
                primaryStage.setScene(GameMode);
                primaryStage.setTitle("Snake Game");
                primaryStage.show();
                modeWall.startGameLoop();

                break;
            case 2:
                ModeTimeAttack modeTimeAttack = new ModeTimeAttack();
                GameMode = modeTimeAttack.initGame();

                primaryStage = new Stage();

                primaryStage.getIcons().add(icon);
                primaryStage.setScene(GameMode);
                primaryStage.setTitle("Snake Game");
                primaryStage.show();
                modeTimeAttack.startGameLoop();
                break;
            case 3:
                Mode2 mode2 = new Mode2();
                GameMode = mode2.initGame();

                primaryStage = new Stage();

                primaryStage.getIcons().add(icon);
                primaryStage.setScene(GameMode);
                primaryStage.setTitle("Snake Game");
                primaryStage.show();
                mode2.startGameLoop();
                break;
            default:
                SnakeGame snakeGame = new SnakeGame();
                GameMode = snakeGame.initGame();

                primaryStage = new Stage();
                primaryStage.getIcons().add(icon);
                primaryStage.setScene(GameMode);
                primaryStage.setTitle("Snake Game");
                primaryStage.show();
                snakeGame.startGameLoop();
                break;
        }
//        primaryStage = new Stage();
//        primaryStage.setScene(GameMode);
//        primaryStage.setTitle("Snake Game");
//        primaryStage.show();
//        snakeGame.startGameLoop();
    }

    @FXML
    private void Mode(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("mode.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,600,400);
        Stage secondaryStage = new Stage();
        Image icon = new Image("img/icon_snake.png");
        secondaryStage.getIcons().add(icon);
        secondaryStage.setScene(scene);
        secondaryStage.setTitle("Select Mode");
        secondaryStage.showAndWait();
    }

    @FXML
    private void Shop(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("shop.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,700,440);
        Stage secondaryStage = new Stage();
        Image icon = new Image("img/icon_shop.png");
        secondaryStage.getIcons().add(icon);
        secondaryStage.setScene(scene);
        secondaryStage.setTitle("Select Mode");
        secondaryStage.showAndWait();
    }

    @FXML
    private void Exit(ActionEvent event){
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }



}
