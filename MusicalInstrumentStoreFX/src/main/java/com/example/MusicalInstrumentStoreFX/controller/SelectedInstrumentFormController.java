package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SelectedInstrumentFormController implements Initializable {
    @FXML private VBox vbSelectedInstrumentRoot;
    private Instruments instruments;
    
    public void setInstruments(Instruments instruments) {
        this.instruments = instruments;
    }

    @FXML private void takeOnInstrument(){
        Stage stage = (Stage) vbSelectedInstrumentRoot.getScene().getWindow();
        stage.close();
    }
    @FXML private void returnInstrument(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
