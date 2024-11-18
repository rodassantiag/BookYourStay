package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.Reserva;
import co.edu.uniquindio.bookyourstay.observador.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResenaControlador {

    private Reserva reserva;
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private ComboBox<Integer> calificacionComboBox;
    @FXML
    private TextArea comentarioTextArea;
    @FXML
    private Button guardarButton;

    public void inicializar(Reserva reserva) {
        this.reserva = reserva;
        calificacionComboBox.getItems().addAll(1, 2, 3, 4, 5); // Opciones de calificación
    }

    public void guardarResena(ActionEvent actionEvent) {
        try {
            Integer calificacion = calificacionComboBox.getValue();
            String comentario = comentarioTextArea.getText();

            if (calificacion == null || comentario.trim().isEmpty()) {
                throw new Exception("Debe ingresar una calificación y un comentario.");
            }

            controladorPrincipal.crearResena(reserva, calificacion, comentario);

            controladorPrincipal.crearAlerta("Reseña guardada exitosamente.", Alert.AlertType.INFORMATION);
            controladorPrincipal.cerrarVentana(guardarButton);

        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}

