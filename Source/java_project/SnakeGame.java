import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SnakeGame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private static final int SQUARE_SIZE = 20;
    private double snakeSpeed;
    private int scoreIncreaseCount;
    private static final int SPEED_INCREASE_THRESHOLD = 50;

    private Snake snake;
    private Canvas canvas;
    private GraphicsContext gc;
    private Timeline gameLoop;
    private Food food;
    private Background background;
    private Score score;
    private Image foodImage;
    // test mode




    public Scene initGame() {
        score = new Score();

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("123");
        ds.setServerName("DESKTOP-KSD365U");
        ds.setPortNumber(1433);
        ds.setDatabaseName("SnakeGame");
        ds.setEncrypt(false);

        try(Connection con = ds.getConnection())
        {
            Statement st = con.createStatement();
            String sql = "select Score from Score,PlayerAccounts where Score.PlayerID = PlayerAccounts.PlayerID and PlayerAccounts.Username = '"+LoginController.globalUsername+"'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                int str = rs.getInt("Score");
                score.setScore(str);
            }
            con.close();

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        snake = new Snake();
        snakeSpeed = 100.0;
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        food = new Food(20, 20);
        food.randomizePosition(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE);
        foodImage = food.getImage();
//Test mode
       
//
        background = new Background(WIDTH, HEIGHT, SQUARE_SIZE, Color.web("AAD751"), Color.web("A2D149"));

        gameLoop = new Timeline(new KeyFrame(Duration.millis(100), e -> gameUpdate()));
        gameLoop.setCycleCount(Animation.INDEFINITE);

        Scene gameScene = new Scene(new BorderPane(canvas));
        gameScene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));


        return gameScene;
    }

    public void startGameLoop() {
        gameLoop.play();
    }

    private void gameUpdate() {
        snake.move();
        
        if (snake.getBody().getFirst().getX() == food.getX() && snake.getBody().getFirst().getY() == food.getY()) {
            
            snake.grow();
           food.randomizePosition(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE);
            foodImage = food.getImage();
            score.increaseScore(1);
            updateSnakeSpeed();
            
        }
       

        if (snake.checkWallCollision(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE)) {
            int saveScore = score.getScore();

            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser("sa");
            ds.setPassword("123");
            ds.setServerName("DESKTOP-KSD365U");
            ds.setPortNumber(1433);
            ds.setDatabaseName("SnakeGame");
            ds.setEncrypt(false);

            try(Connection con = ds.getConnection())
            {
                Statement st = con.createStatement();
                String sql = "UPDATE Score SET Score = "+saveScore+" FROM Score, PlayerAccounts WHERE Score.PlayerID = PlayerAccounts.PlayerID and PlayerAccounts.Username = '"+LoginController.globalUsername+"'";
                int rs = st.executeUpdate(sql);
                con.close();

            } catch (SQLServerException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            gameLoop.stop();
            //Scene gameScene = new Scene(new BorderPane(canvas));
            //gameScene.setOnKeyPressed(event -> restartGame(event.getCode()));

            displayGameOver();
            return;
        }

        if (snake.checkSelfCollision()) {
            int saveScore = score.getScore();

            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser("sa");
            ds.setPassword("123");
            ds.setServerName("DESKTOP-KSD365U");
            ds.setPortNumber(1433);
            ds.setDatabaseName("SnakeGame");
            ds.setEncrypt(false);

            try(Connection con = ds.getConnection())
            {
                Statement st = con.createStatement();
                String sql = "UPDATE Score SET Score = "+saveScore+" FROM Score, PlayerAccounts WHERE Score.PlayerID = PlayerAccounts.PlayerID and PlayerAccounts.Username = '"+LoginController.globalUsername+"'";
                int rs = st.executeUpdate(sql);
                con.close();

            } catch (SQLServerException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            gameLoop.stop();
            displayGameOver();
            //Scene gameScene = new Scene(new BorderPane(canvas));
            //gameScene.setOnKeyPressed(event -> restartGame(event.getCode()));

        } else {
            snake.updateHeadImage();
            renderGame();
        }


    }

    private void renderGame() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        background.draw(gc);
        snake.draw(gc, SQUARE_SIZE);
        drawScore();
       
        gc.drawImage(foodImage, food.getX() * SQUARE_SIZE, food.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void handleKeyPress(KeyCode code) {
        int newDirection = -1;
        int currentDirection = snake.getCurrentDirection();
        switch (code) {
            case UP:
                if (currentDirection != Snake.DOWN)
                    newDirection = Snake.UP;
                break;
            case DOWN:
                if (currentDirection != Snake.UP)
                    newDirection = Snake.DOWN;
                break;
            case LEFT:
                if (currentDirection != Snake.RIGHT)
                    newDirection = Snake.LEFT;
                break;
            case RIGHT:
                if (currentDirection != Snake.LEFT)
                    newDirection = Snake.RIGHT;
                break;
            case R:
                restartGame();
        }
        if (newDirection != -1) {
            snake.setCurrentDirection(newDirection);
        }
    }

    private void displayGameOver() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 50));
        gc.fillText("Game Over" + "\n", WIDTH / 2 - 100, HEIGHT / 2);

        gc.fillText("PRESS R TO RESTART",WIDTH / 2 - 250, HEIGHT / 2 + 100);

    }

    private void restartGame() {
        snake = new Snake();
        snakeSpeed = 100.0;
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        background.draw(gc);
        snake.draw(gc, SQUARE_SIZE);
        drawScore();
        gc.drawImage(foodImage, food.getX() * SQUARE_SIZE, food.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        food.randomizePosition(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE);
        foodImage = food.getImage();

        // Restart game loop
        gameLoop.play();
    }
    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Score: " + score.getScore(), 10, 30);
    }


    private void updateSnakeSpeed() {
        
        if (scoreIncreaseCount > 0 && scoreIncreaseCount % SPEED_INCREASE_THRESHOLD == 0) {
            snakeSpeed += 10.0; 
            gameLoop.setRate(snakeSpeed / 100.0);
        }
    }
    
}