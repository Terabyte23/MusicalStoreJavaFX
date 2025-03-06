package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import com.example.MusicalInstrumentStoreFX.service.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class RegistrationFormController {
    private AppUserService appUserService;
    private FormService formService;

    @FXML private TextField tfLastName;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfUsername;
    @FXML private TextField tfPassword;

    public RegistrationFormController(AppUserService appUserService, FormService formService) {
        this.appUserService = appUserService;
        this.formService = formService;
    }

    @FXML private void registration() {
        try{
            AppUser newUser = new AppUser();
            newUser.setFirstname(tfFirstName.getText());
            newUser.setLastname(tfLastName.getText());
            newUser.setUsername(tfUsername.getText());
            newUser.setPassword(tfPassword.getText());
            newUser.getRoles().add(AppUserService.ROLES.USER.toString());
            appUserService.add(newUser);
            formService.loadLoginForm();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        }
    }

