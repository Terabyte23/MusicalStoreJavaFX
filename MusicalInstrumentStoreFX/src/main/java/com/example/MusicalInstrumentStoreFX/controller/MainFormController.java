package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import com.example.MusicalInstrumentStoreFX.service.FormService;
import com.example.MusicalInstrumentStoreFX.service.InstrumentsService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    // В классе MainFormController
    public void openInstrumentsDetails(Instruments instrument) throws IOException {
        // Загружаем FXML файл
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/instrument/selectedInstrumentForm.fxml"));

        // Получаем контроллер
        SelectedInstrumentFormController selectedInstrumentFormController = loader.getController();

        // Устанавливаем ссылку на MainFormController
        if (selectedInstrumentFormController != null) {
            selectedInstrumentFormController.setMainFormController(this);  // Теперь можно безопасно вызвать метод
        } else {
            System.out.println("Контроллер не был инициализирован.");
        }

        // Устанавливаем выбранный инструмент в форму
        selectedInstrumentFormController.setInstruments(instrument);

        // Загружаем и показываем окно
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vbMainFormRoot.getChildren().addFirst(formService.loadMenuForm());

        // Загружаем только те инструменты, у которых count > 0
        tvListInstruments.setItems(instrumentsService.getListInstruments());

        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcBrands.setCellValueFactory(cellData -> {
            Instruments instruments = cellData.getValue(); // Получаем объект Instruments из строки таблицы
            if (instruments.getBrand() == null || instruments.getBrand().isEmpty()) {
                return new SimpleStringProperty("");
            }
            // Преобразуем коллекцию брендов в строку
            String brands = instruments.getBrand().stream()
                    .map(brand -> brand.getFirstName())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(brands);
        });
        tcPublicationYear.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tcCount.setCellValueFactory(new PropertyValueFactory<>("count"));

        // Добавление прослушивателя для изменения видимости кнопки редактирования
        tvListInstruments.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Instruments>() {
            @Override
            public void changed(ObservableValue<? extends Instruments> observable, Instruments oldValue, Instruments newValue) {
                if (newValue != null) {
                    if (AppUserService.currentUser.getRoles().contains(AppUserService.ROLES.MANAGER.toString())) {
                        hbEditInstruments.setVisible(true);
                    } else {
                        hbEditInstruments.setVisible(false);
                    }
                }
            }
        });

        // Дабл-клик по инструменту для открытия подробностей
        tvListInstruments.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tvListInstruments.getSelectionModel().isEmpty()) {
                Instruments selectedInstrument = tvListInstruments.getSelectionModel().getSelectedItem();
                try {
                    openInstrumentsDetails(selectedInstrument);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void updateTable() {
        // Обновляем список инструментов, исключая те, у которых count == 0
        tvListInstruments.setItems(instrumentsService.getListInstruments().filtered(instrument -> instrument.getCount() > 0));
    }


}
