/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.etfbl.is.pozoriste.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.etfbl.is.pozoriste.model.dao.mysql.ConnectionPool;
import net.etfbl.is.pozoriste.model.dao.mysql.RadnikDAO;
import net.etfbl.is.pozoriste.model.dto.Radnik;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class PregledRadnikaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView radniciTableView;

    @FXML
    private TableColumn<Radnik, String> imeColumn;

    @FXML
    private TableColumn<Radnik, String> prezimeColumn;

    @FXML
    private TableColumn<Radnik, String> opisPoslaColumn;

    @FXML
    private TableColumn<Radnik, String> jmbColumn;

    @FXML
    private TableColumn<Radnik, Boolean> statusRadnikaColumn;

    @FXML
    private TableColumn<Radnik, String> kontaktColumn;

    @FXML
    private Button bDodaj;

    @FXML
    private Button bIzmjeni;

    @FXML
    private Button bPretrazi;

    @FXML
    private TextField tfPretraga;
    public static ObservableList<Radnik> radniciObservaleList = FXCollections.observableArrayList();

    @FXML
    void dodajRadnikaAction(ActionEvent event) {
        try {
            Parent dodajRadnikaController = FXMLLoader.load(getClass().getResource("/net/etfbl/is/pozoriste/view/DodajRadnika.fxml"));

            Scene dodajRadnikaScene = new Scene(dodajRadnikaController);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(dodajRadnikaScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void izmijeniRadnikaAction(ActionEvent event) {

    }

    @FXML
    void pretraziRadnikaAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RadnikDAO.ubaciUTabeluRadnik();
        ubaciKoloneUTabeluRadnik();
    }

    private void ubaciKoloneUTabeluRadnik() {
        imeColumn = new TableColumn("Ime");
        imeColumn.setCellValueFactory(new PropertyValueFactory<>("ime"));

        prezimeColumn = new TableColumn("Prezime");
        prezimeColumn.setCellValueFactory(new PropertyValueFactory<>("prezime"));

        opisPoslaColumn = new TableColumn("Opis posla");
        opisPoslaColumn.setCellValueFactory(new PropertyValueFactory<>("opisPosla"));
        radniciTableView.setItems(radniciObservaleList);
        radniciTableView.getColumns().addAll(imeColumn, prezimeColumn, opisPoslaColumn);

        /* jmbColumn = new TableColumn("jmb");
         jmbColumn.setCellValueFactory(new PropertyValueFactory<>("jmb"));
        
         statusRadnikaColumn = new TableColumn("Status radnika");
         statusRadnikaColumn.setCellValueFactory(new PropertyValueFactory<>("Status radnika"));*/
    }

  /*  private void ubaciUTabeluRadnik() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from radnik");
            while (rs.next()) {
                radniciObservaleList.add(new Radnik(rs.getString("ime"),
                        rs.getString("prezime"), rs.getString("opisPosla"), rs.getString("jmb"),
                        rs.getBoolean("statusRadnika"), rs.getString("kontakt")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PregledRadnikaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PregledRadnikaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
*/
}
