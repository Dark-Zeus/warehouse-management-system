package com.warehouse.util;

import java.io.IOException;

import com.warehouse.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class CFXMLLoader {
    /**
     * Load FXML file
     * @param fxml
     * @return Parent - {@code Parent} @see {@link Parent}
     * @throws IOException
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Get FXML Loader
     * @param fxml
     * @return FXMLLoader - {@code FXMLLoader} @see {@link FXMLLoader}
     */
    public static FXMLLoader getFXMLLoader(String fxml) {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }
}
