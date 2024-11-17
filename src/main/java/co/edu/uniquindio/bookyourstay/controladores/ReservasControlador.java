package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.factory.Apartamento;
import co.edu.uniquindio.bookyourstay.factory.Casa;
import co.edu.uniquindio.bookyourstay.factory.Hotel;
import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import co.edu.uniquindio.bookyourstay.modelo.Reserva;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class ReservasControlador implements Initializable {
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private Cliente cliente;
    @FXML
    private TableView<Reserva> reservaTableView;
    @FXML
    private TableColumn<Reserva, String> colTipoAlojamiento, colNombre, colCiudad, colFechaEntrada, colFechaSalida, colTotalPagado;

    public ReservasControlador(){
        this.cliente = controladorPrincipal.obtenerSesion();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarDatosTabla();
    }

    public void cargarDatosTabla(){
        colTipoAlojamiento.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue().getAlojamiento();
            String tipo = switch (alojamiento) {
                case Hotel hotel -> "Hotel";
                case Casa casa -> "Casa";
                case Apartamento apartamento -> "Apartamento";
                case null, default -> "Otro";
            };

            return new SimpleStringProperty(tipo);
        });

        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlojamiento().getNombre()));
        colCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAlojamiento().getCiudad())));
        colFechaEntrada.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaEntrada())));
        colFechaSalida.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaSalida())));
        colTotalPagado.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalPagar())));
        reservaTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.listarReservasCliente(cliente)));
    }

    public void irPanelCliente(ActionEvent actionEvent) throws Exception {
        controladorPrincipal.navegarVentana("/panelCliente.fxml", "Panel Cliente");
        controladorPrincipal.cerrarVentana(reservaTableView);
    }

    public void cancelarReserva(ActionEvent actionEvent) {
        try {
            Reserva reserva = reservaTableView.getSelectionModel().getSelectedItem();

            if (reserva == null){
                throw new Exception("Elija una reserva");
            }

            controladorPrincipal.eliminarReserva(reserva);
            reservaTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.listarReservasCliente(cliente)));
            controladorPrincipal.crearAlerta("Su reserva ha sido cancelada exitosamente. El monto total de la reserva ha sido reembolsado a su billetera virtual.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
