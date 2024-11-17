package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroControlador {

    ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtNumeroTelefono;
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtContrasena;

    public void registroCliente(ActionEvent actionEvent) throws Exception{
        try {
            String cedula = txtCedula.getText();
            String nombre = txtNombre.getText();
            String numeroTelefono = txtNumeroTelefono.getText();
            String correo = txtCorreo.getText();
            String contrasena = txtContrasena.getText();

            Cliente cliente = controladorPrincipal.agregarCliente(cedula, nombre, numeroTelefono, correo, contrasena);
            controladorPrincipal.crearAlerta("El usuario se creó correctamente, se ha enviado un código de " +
                    "verificación al correo para activar la cuenta", Alert.AlertType.INFORMATION);
            irInicioSesion();
            controladorPrincipal.EnviarCodigoRegistro(cliente);
            System.out.println(cliente.getId());




        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void irInicioSesion() throws Exception {
        controladorPrincipal.navegarVentana("/login.fxml","Inicio Sesión");
        cerrarVentana();
    }

    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(txtCedula);
    }

    public void irInicio() throws Exception {
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
    }

}
