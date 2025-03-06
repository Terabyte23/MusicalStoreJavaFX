package com.example.MusicalInstrumentStoreFX.service;

import com.example.MusicalInstrumentStoreFX.MusicalInstrumentStoreFxApplication;
import com.example.MusicalInstrumentStoreFX.controller.EditInstrumentsFormController;
import com.example.MusicalInstrumentStoreFX.controller.SelectedInstrumentFormController;
import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FormService {
    private SpringFXMLLoader springFXMLLoader;

    public FormService(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }
    public void loadLoginForm(){
        FXMLLoader fxmlLoader = springFXMLLoader.load("/user/loginForm.fxml");
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Nptv23JavaFX вход пользователя");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }
    public void loadMainForm(){
        FXMLLoader fxmlLoader = springFXMLLoader.load("/main/mainForm.fxml");
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Nptv23JavaFX Библиотека");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }
    private Stage getPrimaryStage(){
        return MusicalInstrumentStoreFxApplication.primaryStage;
    }
    public void loadNewInstrumentForm(){
        FXMLLoader fxmlLoader = springFXMLLoader.load("/instrument/newInstrumentForm.fxml");
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Добавление нового инструмента");
    }

    public void loadEditInstrumentForm(Instruments selectedInstrument) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/instrument/editInstrumentForm.fxml");
        try {
            Parent editInstrumentFormRoot = fxmlLoader.load();
            EditInstrumentsFormController editInstrumentsFormController = fxmlLoader.getController();
            editInstrumentsFormController.setEditInstruments(selectedInstrument);
            Scene scene = new Scene(editInstrumentFormRoot);
            getPrimaryStage().setTitle("Nptv23JavaFX Библиотека - Редактирование инструмента");
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setResizable(false);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Parent loadMenuForm(){
        FXMLLoader fxmlLoader = springFXMLLoader.load("/menu/menuForm.fxml");
        try {
            return fxmlLoader.load();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void loadBrandForm(){
        FXMLLoader fxmlLoader = springFXMLLoader.load("/brand/brandForm.fxml");
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового пользователя");
    }

    public void loadRegistrationForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/user/registrationForm.fxml");
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового пользователя");
    }
    public void loadSelectedInstrumentFormModality(Instruments instruments){
        FXMLLoader fxmlLoader = springFXMLLoader.load("/instrument/selectedInstrumentForm.fxml");
        Parent root = null;
        try {
            root = fxmlLoader.load();
            SelectedInstrumentFormController selectedInstrumentFormController = fxmlLoader.getController();
            selectedInstrumentFormController.setInstruments(instruments);

            Stage stage = new Stage();
            stage.setTitle("Информация о книге");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
