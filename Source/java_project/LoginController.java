import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    public static boolean check = false;
    @FXML
    public TextField tfUsername, tfPassword;
    public static String globalUsername = "";


    public void Submit (ActionEvent event) throws IOException {
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        globalUsername = username;
        Boolean check = false;

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
            String sql = "select * from PlayerAccounts where PlayerAccounts.Username = '" + username + "' and PlayerAccounts.Password = '"+ password +"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next())
            {
                check = true;
            }
            con.close();

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (check) {
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,1200,700);

            Stage primaryStage = new Stage();
            Image icon = new Image("img/icon_snake.png");
            primaryStage.getIcons().add(icon);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Snake Game");
            primaryStage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Tên đăng nhập hoặc mật khẩu không đúng");
            alert.showAndWait();
        }
    }
}
