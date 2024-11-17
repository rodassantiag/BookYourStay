package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class PanelAdminControlador implements Initializable {

    private Cliente cliente;

    @FXML
    private Text nombre;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private Button boton;

    public PanelAdminControlador(){
        this.cliente = controladorPrincipal.obtenerSesion();
    }

    public void irCrearAlojamiento()throws Exception{
        controladorPrincipal.navegarVentana("/crearAlojamiento.fxml", "Crear Alojamiento");
        cerrarVentana();
    }

    public void irEstadisticas()throws Exception{
        controladorPrincipal.navegarVentana("/crearAlojamiento.fxml", "Crear Alojamiento");
        cerrarVentana();
    }

    public void irPopularesCiudad()throws Exception{
        controladorPrincipal.navegarVentana("/popularesCiudad.fxml", "Alojamientos más populares por ciudad");
        cerrarVentana();
    }

    public void irMasrentables()throws Exception{
        controladorPrincipal.navegarVentana("/masRentables.fxml", "Alojamientos más rentables ");
        cerrarVentana();
    }

    public void cerrarSesion(ActionEvent actionEvent) throws Exception {
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        controladorPrincipal.eliminarSesion();
        cerrarVentana();
    }

    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(boton);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (cliente != null){
            nombre.setText("Hola "+cliente.getNombre()+". ¿Qué desea hacer hoy?");
        }
    }
}
