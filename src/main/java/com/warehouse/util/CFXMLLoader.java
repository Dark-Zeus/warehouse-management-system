package com.warehouse.util;

import java.io.IOException;

import com.warehouse.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class CFXMLLoader {
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static FXMLLoader getFXMLLoader(String fxml) {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }
}
