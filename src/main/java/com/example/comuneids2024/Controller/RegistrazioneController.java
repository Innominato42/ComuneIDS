package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.Role;
import com.example.comuneids2024.Model.UtenteAutenticato;
import com.example.comuneids2024.Model.UtenteAutenticatoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RegistrazioneController {

    @Autowired
    private UtenteAutenticatoManager utenteAutenticatoManager;

    public boolean registrationUser(String email, String username, String password, Role role) {
        if (this.utenteAutenticatoManager.containsUtente(email, username)) {
            return false;
        }
        if (role.equals(Role.TURISTAAUTENTICATO) || role.equals(Role.CONTRIBUTOR)) {
            this.utenteAutenticatoManager.addRegistrationUser(new UtenteAutenticato(username, password, role, email));
            return true;
        } else {
            return false;
        }
    }

    public void refuseRegistration(String id) {
        this.utenteAutenticatoManager.rifiutaRegistration(id);
    }

    public void approveRegistration(String id) {
        this.utenteAutenticatoManager.approvaRegistration(id);
    }
}
