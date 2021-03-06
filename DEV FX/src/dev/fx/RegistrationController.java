/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.fx;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author M. Hartgring
 */
public class RegistrationController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label messageLabel;
    private ActionEvent ev;
    
    @FXML
    private void checkInput(ActionEvent event)
    {
        if(name.getText().trim().isEmpty() || surname.getText().trim().isEmpty() || 
                username.getText().trim().isEmpty() || password.getText().trim().isEmpty())
        {
            messageLabel.setText("Fill in all fields!");
        } 
        else if (checkNonExistingUser())
        {
            ev = event;
            register();
        }
        else
        {
            messageLabel.setText("User already exists.");
        }
    }
    
    private void register()
    {
        
        /*Characters newCharacter = new Characters();
        newCharacter.setName(username.getText());
        newCharacter.persist(newCharacter);*/
        Users newUser = new Users();
        newUser.setFirstName(name.getText());
        newUser.setLastName(surname.getText());
        newUser.setUserName(username.getText());
        newUser.setPassword(password.getText());
        newUser.setCharacterSlots(5);
        newUser.setBalance(0);
        newUser.setBanned(false);
        newUser.setMonthsPayed(0);
        newUser.persist(newUser);
        goToMainScreen(newUser);
        
    }
    
    private void goToMainScreen(Users u)
    {
        
        try {
            fxmlController x = new fxmlController(u);
            x.goToRegistrationForm(ev,"mainScreen.fxml", "Main", 1);
        } catch (IOException ex) {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void goToLogin(ActionEvent ev){
        try {
            fxmlController x = new fxmlController();
            x.goToRegistrationForm(ev,"mainfxml.fxml", "Login", 0);
        } catch(IOException ex) {
            Logger.getLogger(ShopFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean checkNonExistingUser()
    {
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("DEV_FXPU");
        EntityManager em = emf.createEntityManager();        
        List results = em.createNamedQuery("Users.findByUserName")
                .setParameter("userName", username.getText())
                .getResultList();
        return results.isEmpty();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
