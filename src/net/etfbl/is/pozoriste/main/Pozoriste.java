package net.etfbl.is.pozoriste.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.etfbl.is.pozoriste.controller.LogInController;

/**
 *
 * @author Grupa6
 */
public class Pozoriste extends Application {

    private static List<Stage> listaOtvorenihProzora = new ArrayList<>();

    public static boolean administrator = false;

    public static boolean biletar = false;

    @Override
    public void start(Stage stage) throws Exception {
        Handler handler = null;
        try {
            handler = new FileHandler("./error.log");
            Logger.getLogger("").addHandler(handler);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(Pozoriste.class.getName()).log(Level.SEVERE, null, ex);
        }

        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/net/etfbl/is/pozoriste/view/LogIn.fxml"));
        LogInController logInController1 = null;
        loader.setController(logInController1);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.getIcons().add(new Image(Pozoriste.class.getResourceAsStream("/net/etfbl/is/pozoriste/resursi/drama.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void pozoristeClose() {
        administrator = false;
        biletar = false;
        if (listaOtvorenihProzora.size() == 0) {
            return;
        }
        listaOtvorenihProzora.forEach(e -> {
            if (e.isShowing()) {
                e.close();
            }
        });
    }

    public static void dodajOtvoreniProzor(Stage stage) {
        listaOtvorenihProzora.add(stage);
    }

}
