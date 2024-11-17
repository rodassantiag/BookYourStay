package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidacionControlador {

    ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    private final Cliente cliente;

    @FXML
    private TextField txtCodigoVerificacion;

    public ValidacionControlador(){
        this.cliente = controladorPrincipal.obtenerSesion();
    }

    public void verificarCodigo(ActionEvent actionEvent) throws Exception {

        try {
            String codigoVerificacion = txtCodigoVerificacion.getText().trim();

            if (controladorPrincipal.verificarCodigo(codigoVerificacion)){
                controladorPrincipal.crearAlerta("Su cuenta se ha validado exitosamente", Alert.AlertType.INFORMATION);
                cerrarVentana();
                irPanelCliente();
            }
        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void nuevoCodigo(ActionEvent actionEvent) throws Exception {

        try {
            controladorPrincipal.crearAlerta("Se ha reenviado el codigo", Alert.AlertType.INFORMATION);
            controladorPrincipal.reenviarCodigoRegistro(cliente);

        }catch (Exception e){
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }




    public void irPanelCliente() throws Exception {
        controladorPrincipal.navegarVentana("/panelCliente.fxml", "Panel cliente");
    }


    public void cerrarVentana() throws Exception{
        controladorPrincipal.cerrarVentana(txtCodigoVerificacion);
    }

}
