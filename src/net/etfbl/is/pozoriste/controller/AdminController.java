package net.etfbl.is.pozoriste.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.etfbl.is.pozoriste.main.Pozoriste;
import net.etfbl.is.pozoriste.model.dao.mysql.ConnectionPool;
import net.etfbl.is.pozoriste.model.dao.mysql.ScenaDAO;

/**
 *
 * @author Grupa6
 */
public class AdminController implements Initializable {

    @FXML
    private Button bPregledPredstave;

    @FXML
    private Button bPregledRadnika;

    @FXML
    private Button bPregledRepertoara;

    @FXML // fx:id="karteButtton"
    private Button karteButtton; // Value injected by FXMLLoader

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConnectionPool.getInstance();//da otvori odmah konekciju ka bazi 
        karteButtton.setOnAction(e -> otvoriFormu());
    }

    public void PregledRadnikaAction(ActionEvent event) {
        try {
            Parent radnikController = FXMLLoader.load(getClass().getResource("/net/etfbl/is/pozoriste/view/PregledRadnika.fxml"));

            Scene radnikScene = new Scene(radnikController);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(radnikScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void PregledRepertoaraAction(ActionEvent event) {
        try {
            Parent repertoarController = FXMLLoader.load(getClass().getResource("/net/etfbl/is/pozoriste/view/PregledRepertoara.fxml"));

            Scene repertoarScene = new Scene(repertoarController);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(repertoarScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void PregledPredstavaAction(ActionEvent event) {
        try {
            Parent predstavaController = FXMLLoader.load(getClass().getResource("/net/etfbl/is/pozoriste/view/PregledPredstava.fxml"));
            Scene predstavaScene = new Scene(predstavaController);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(predstavaScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void otvoriFormu(){

        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/net/etfbl/is/pozoriste/view/PregledKarata.fxml"));
        PregledKarataController pregledKarataController = null;
        PregledKarataController.scenaZaPrikaz = ScenaDAO.scene().get(0);
        loader.setController(pregledKarataController);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Karte "+ "za scenu "+PregledKarataController.scenaZaPrikaz.getNazivScene());
        stage.getIcons().add(new Image(Pozoriste.class.getResourceAsStream("/net/etfbl/is/pozoriste/resursi/drama.png")));
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

}
