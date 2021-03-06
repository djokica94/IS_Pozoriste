package net.etfbl.is.pozoriste.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.etfbl.is.pozoriste.main.Pozoriste;
import net.etfbl.is.pozoriste.model.dao.mysql.ConnectionPool;
import net.etfbl.is.pozoriste.model.dao.mysql.ConnectionPool;

/**
 * FXML Controller class
 *
 * @author Grupa6
 */
public class LogInController implements Initializable {

    @FXML // fx:id="tfKorisnickoIme"
    private TextField tfKorisnickoIme; // Value injected by FXMLLoader

    @FXML // fx:id="tfLozinka"
    private TextField tfLozinka; // Value injected by FXMLLoader

    @FXML // fx:id="bPotvrda"
    private Button bPotvrda; // Value injected by FXMLLoader

    private String tipKorisnika = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ovde incijalizovati login formu i onda nekom metodom , ili kako vec ako prodje login forma ovde napraviti metodu koja ce uzitati PozoristeController
        //bar po meni

        //bPotvrda.setOnAction(e -> provjeraAutentifikacije(tfKorisnickoIme.getText(), tfLozinka.getText()));
    }

    private boolean provjeraAutentifikacije(String username, String password) {
        String passwordHash = hashSHA256(password);
        String userType = postojiUBazi(username, passwordHash);
        if ("".equals(userType)) {
            return false;
        }
        return true;
    }

    private String postojiUBazi(String username, String passwordHash) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call provjeraLozinkeIKorisnickogImena(?,?)}");
            callableStatement.setString(1, username);
            callableStatement.setString(2, passwordHash);

            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                tipKorisnika = resultSet.getString("tipKorisnika");
                return tipKorisnika;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
        }
        return "";
    }

    private String hashSHA256(String value) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] encodedhash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        String hash = bytesToHex(encodedhash);
        return hash;
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @FXML
    void potvrdaAction(ActionEvent event) throws IOException {
        if (provjeraAutentifikacije(tfKorisnickoIme.getText(), tfLozinka.getText())) {
            if ("administrator".equals(tipKorisnika)) {
                Pozoriste.administrator = true;
                try {
                    Parent pozoristeController = FXMLLoader.load(getClass().getResource("/net/etfbl/is/pozoriste/view/Admin.fxml"));

                    Scene pozScene = new Scene(pozoristeController);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(pozScene);
                    window.show();
                } catch (IOException ex) {
                    Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if ("biletar".equals(tipKorisnika)) {
                System.out.println("ovo je sad da se otvori za biletara");
                Pozoriste.biletar = true;
            }

        } else {
            upozorenjeLozinka();
        }
    }

    private void upozorenjeLozinka() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa lozinke!");
        alert.setHeaderText(null);
        alert.setContentText("Netacna lozinka");
        alert.showAndWait();
    }

}
