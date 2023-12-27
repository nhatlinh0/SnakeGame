import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.awt.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RewardController implements Initializable {
    @FXML
    public Label lbCode;
    @FXML
    public void openLink (ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://shopee.vn/"));
    }

    @FXML
    public void btnOK (ActionEvent event){
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbCode.setText(ShopController.code);
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
            String sql = "delete Reward where Reward.RewardID = "+ShopController.id+" ";
            int rs = st.executeUpdate(sql);

            con.close();

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
