package com.example.MusicalInstrumentStoreFX.service;

import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.model.repository.InstrumentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InstrumentsService {
    private InstrumentRepository instrumentRepository;

    public InstrumentsService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    public void create(Instruments instruments) {
        instrumentRepository.save(instruments);
    }

    public ObservableList<Instruments> getListInstruments() {
        List<Instruments> instruments = instrumentRepository.findAll();
        ObservableList<Instruments> listInstruments = FXCollections.observableArrayList();
        listInstruments.addAll(instruments);
        return listInstruments;
    }
}
