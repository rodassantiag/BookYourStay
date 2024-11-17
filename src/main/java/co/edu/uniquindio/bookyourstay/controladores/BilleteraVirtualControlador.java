package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import co.edu.uniquindio.bookyourstay.modelo.Movimiento;
import co.edu.uniquindio.bookyourstay.observador.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class BilleteraVirtualControlador implements Initializable, Observable {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    private Cliente cliente;
    @FXML
    private TextField txtSaldo;
    @FXML
    private TableView<Movimiento> movimientoTableView;
    @FXML
    private TableColumn<Movimiento, String> colFecha, colConcepto,colMonto;

    public BilleteraVirtualControlador(){
        this.cliente = controladorPrincipal.obtenerSesion();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actualizarSaldo();
        cargarDatosTabla();
    }

    public void cargarDatosTabla(){
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFecha())));
        colConcepto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        colMonto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
        movimientoTableView.setItems(FXCollections.observableArrayList(cliente.getBilleteraVirtual().getMovimientos()));
    }

    public void irPanelAdmin(ActionEvent actionEvent) throws Exception {
        controladorPrincipal.navegarVentana("/panelCliente.fxml", "Panel Cliente");
        cerrarVentana();
    }

    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(txtSaldo);
    }

    private void actualizarSaldo() {
        if (cliente != null && cliente.getBilleteraVirtual() != null) {
            txtSaldo.setText(String.format("%.2f", cliente.getBilleteraVirtual().getSaldo()));
        }
    }


    public void irRecargar(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = controladorPrincipal.navegarVentana("/recarga.fxml", "Recargar");
        RecargaControlador recargaControlador = loader.getController();
        recargaControlador.inicializarObservable(this);
    }

    @Override
    public void notificar() {
        movimientoTableView.setItems(FXCollections.observableArrayList(cliente.getBilleteraVirtual().getMovimientos()));
        txtSaldo.setText(String.format("%.2f", cliente.getBilleteraVirtual().getSaldo()));
    }
}
