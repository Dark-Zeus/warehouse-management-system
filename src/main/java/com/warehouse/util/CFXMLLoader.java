package com.warehouse.util;

import java.io.IOException;

import com.warehouse.App;

import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class CFXMLLoader {
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
