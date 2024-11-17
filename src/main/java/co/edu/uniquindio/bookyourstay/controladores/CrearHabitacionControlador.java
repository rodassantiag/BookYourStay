package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.Habitacion;
import co.edu.uniquindio.bookyourstay.observador.ObservableHabitaciones;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class CrearHabitacionControlador {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private TextField txtNumero, txtPrecio, txtCapacidad;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private ImageView imagenView;

    private ObservableHabitaciones observableHabitaciones;

    private ArrayList<Habitacion> habitaciones = new ArrayList<>();

    public void crearHabitacion(ActionEvent actionEvent){
        try {
            String numeroTexto = txtNumero.getText();
            String precioTexto = txtPrecio.getText();
            String capacidadTexto = txtCapacidad.getText();
            String descripcion = txtDescripcion.getText();
            Image imagen = imagenView.getImage();


            if (numeroTexto.isBlank()){
                throw new Exception("El numero de la habitación es obligatorio");
            }

            if (precioTexto.isBlank()){
                throw new Exception("El precio de la habitación es obligatorio");
            }

            if (capacidadTexto.isBlank()){
                throw new Exception("La capacidad es obligatoria");
            }

            int numero = Integer.parseInt(numeroTexto);
            double precio = Double.parseDouble(precioTexto);
            int capacidad = Integer.parseInt(capacidadTexto);

            Habitacion habitacion = controladorPrincipal.crearHabitacion(numero, precio, capacidad, imagen, descripcion);
            habitaciones.add(habitacion);
            if (observableHabitaciones != null){
                observableHabitaciones.notificar(habitaciones);
            }
            limpiarCampos();
            controladorPrincipal.crearAlerta("La habitación se ha creado exitosamente", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void limpiarCampos(){
        txtNumero.clear();
        txtPrecio.clear();
        txtCapacidad.clear();
        txtDescripcion.clear();
        imagenView.setImage(null);
    }

    public void seleccionarImagen(ActionEvent actionEvent){
        controladorPrincipal.abrirFileChooser(imagenView);
    }

    public void inicializarObservable(ObservableHabitaciones observableHabitaciones){
        this.observableHabitaciones = observableHabitaciones;

    }

}
