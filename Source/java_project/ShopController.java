import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;

public class ShopController implements Initializable {
    public static int score;
    public static int id;
    public static String code;

    @FXML
    public Label lbScore;

    @FXML
    public AnchorPane pane;


    @FXML
    public void Ticket(ActionEvent event) {
        score = Integer.parseInt(lbScore.getText());
        score = score - 10;

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
            String sql = "UPDATE Score SET Score = "+score+" FROM Score, PlayerAccounts WHERE Score.PlayerID = PlayerAccounts.PlayerID and PlayerAccounts.Username = '"+LoginController.globalUsername+"'";
            if(score >=0)
            {
                int rs = st.executeUpdate(sql);
                if(rs != 0)
                {
                    lbScore.setText(String.valueOf(score));
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bạn không đủ điểm");
                alert.showAndWait();
            }

            con.close();

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        ------------REWARD-----------
        try(Connection con = ds.getConnection())
        {
            Statement st = con.createStatement();
            String sql = "select * from Reward ";
            ResultSet rs = st.executeQuery(sql);
            if(score >=0)
            {
                while(rs.next())
                {
                    id = rs.getInt("RewardID");
                    code = rs.getString("Code");

                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("reward.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root,270,176);
                    Image icon = new Image("img/icon_shop.png");
                    Stage primaryStage = new Stage();
                    primaryStage.getIcons().add(icon);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Snake Game");
                    primaryStage.show();
                    break;
                }
            }
            con.close();

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                String str = ""+rs.getInt("Score");
                lbScore.setText(str);
            }
            con.close();

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
