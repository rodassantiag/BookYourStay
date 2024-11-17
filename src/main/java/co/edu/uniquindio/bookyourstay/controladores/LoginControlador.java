package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import co.edu.uniquindio.bookyourstay.modelo.enums.Rol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginControlador {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtContrasena;



    public void iniciarSesion(ActionEvent actionEvent) throws Exception {
        try {

            String correo = txtCorreo.getText();
            String contrasena = txtContrasena.getText();

            Cliente clienteValidado = controladorPrincipal.validarCliente(correo, contrasena);
            controladorPrincipal.guardarSesion(clienteValidado);

            if (clienteValidado.isPrimerLogin() && clienteValidado.getRol() == Rol.CLIENTE){
                irValidacion();
            } else {
                if(clienteValidado.getRol() == Rol.CLIENTE){
                    irPanelCliente();
                }else{
                    irPanelAdmin();
                }
            }

        }catch (Exception e){
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(txtCorreo);
    }

    public void irPanelAdmin() throws Exception {
        controladorPrincipal.navegarVentana("/panelAdmin.fxml", "Panel Administrador");
        cerrarVentana();
    }

    public void irValidacion() throws Exception {
        controladorPrincipal.navegarVentana("/validacion.fxml", "Validacion cliente");
        cerrarVentana();
    }

    public void irPanelCliente() throws Exception {
        controladorPrincipal.navegarVentana("/panelCliente.fxml", "Panel cliente");
        cerrarVentana();
    }

    public void irInicio() throws Exception {
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
    }

}
