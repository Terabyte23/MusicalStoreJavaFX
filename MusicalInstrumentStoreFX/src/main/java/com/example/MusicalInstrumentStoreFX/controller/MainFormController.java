package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import com.example.MusicalInstrumentStoreFX.service.FormService;
import com.example.MusicalInstrumentStoreFX.service.InstrumentsService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import javax.sound.midi.Instrument;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class MainFormController implements Initializable {

    private FormService formService;
    private InstrumentsService instrumentsService;

    @FXML private VBox vbMainFormRoot;
    @FXML private TableView<Instruments> tvListInstruments;
    @FXML private TableColumn<Instruments, String> tcId;
    @FXML private TableColumn<Instruments, String> tcTitle;
    @FXML private TableColumn<Instruments, String> tcBrands;
    @FXML private TableColumn<Instruments, String> tcPublicationYear;
    @FXML private TableColumn<Instruments, String> tcQuantity;
    @FXML private TableColumn<Instruments, String> tcCount;
    @FXML private HBox hbEditInstruments;

    public MainFormController(FormService formService, InstrumentsService instrumentsService) {
        this.formService = formService;
        this.instrumentsService = instrumentsService;
    }

    @FXML private void showEditInstrumentForm() {
        formService.loadEditInstrumentForm(tvListInstruments.getSelectionModel().getSelectedItem());
    }
    private void openInstrumentsDetails(Instruments instruments) {
        formService.loadSelectedInstrumentFormModality(instruments);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vbMainFormRoot.getChildren().addFirst(formService.loadMenuForm());
        tvListInstruments.setItems(instrumentsService.getListInstruments());
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcBrands.setCellValueFactory(cellData -> {
            Instruments instruments = cellData.getValue(); // Получаем объект Book из строки таблицы
            if (instruments.getBrand() == null || instruments.getBrand().isEmpty()) {
                return new SimpleStringProperty("");
            }
            // Преобразуем коллекцию авторов в строку
            String brands = instruments.getBrand().stream()
                    .map(brand -> brand.getFirstName())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(brands);
        });
        tcPublicationYear.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tcCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        tvListInstruments.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Instruments>() {
            @Override
            public void changed(ObservableValue<? extends Instruments> observable, Instruments oldValue, Instruments newValue) {
                if (newValue != null) {
                    if(AppUserService.currentUser.getRoles().contains(AppUserService.ROLES.MANAGER.toString())) {
                        hbEditInstruments.setVisible(true);
                    }else{
                        hbEditInstruments.setVisible(false);
                    }
                }
            }
        });
        tvListInstruments.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tvListInstruments.getSelectionModel().isEmpty()) {
                Instruments selectedBook = tvListInstruments.getSelectionModel().getSelectedItem();
                openInstrumentsDetails(selectedBook);
            }
        });
    }
}
