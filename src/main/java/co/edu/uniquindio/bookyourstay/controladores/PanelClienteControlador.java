package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class PanelClienteControlador implements Initializable {
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final Cliente cliente;
    @FXML
    private Text nombre;



    public PanelClienteControlador(){
        ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
        this.cliente = controladorPrincipal.obtenerSesion();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(this.cliente != null){
            this.nombre.setText("Hola "+cliente.getNombre()+" ¿Qué desea hacer hoy?");
        }else{
            System.out.println("Es null");
        }

    }

    public void irCrearReserva() throws Exception{
        controladorPrincipal.navegarVentana("/crearReserva.fxml", "crearReserva");
        cerrarVentana();
    }


    public void irInicio(ActionEvent actionEvent) throws Exception {
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        controladorPrincipal.eliminarSesion();
        cerrarVentana();
    }

    public void irBilleteraVirtual(ActionEvent actionEvent) throws Exception {
        controladorPrincipal.navegarVentana("/billeteraVirtual.fxml", "Billetera virtual");
        cerrarVentana();
    }

    public void irReservas(ActionEvent actionEvent) throws Exception {
        controladorPrincipal.navegarVentana("/reservas.fxml", "Mis Reservas");
        cerrarVentana();
    }

    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(nombre);
    }

}


