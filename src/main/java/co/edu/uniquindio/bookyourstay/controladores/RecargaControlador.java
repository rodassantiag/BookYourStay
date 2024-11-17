package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import co.edu.uniquindio.bookyourstay.observador.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class RecargaControlador implements Initializable {

    private Observable observable;
    private Cliente cliente;
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private TextField txtMonto, txtSaldo;

    public RecargaControlador(){
        this.cliente = controladorPrincipal.obtenerSesion();
    }

    public void recargarSaldo(ActionEvent actionEvent){
        try {
            String montoTxt = txtMonto.getText();

            if (!montoTxt.matches("\\d+")){
                throw new Exception("Monto inválido");
            }

            double monto = Double.parseDouble(montoTxt);
            cliente.getBilleteraVirtual().recargar(monto);
            controladorPrincipal.crearAlerta("El saldo se ha actualizado", Alert.AlertType.INFORMATION);
            actualizarSaldo();
            observable.notificar();
            cerrarVentana();
        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void actualizarSaldo() {
        if (cliente != null && cliente.getBilleteraVirtual() != null) {
            txtSaldo.setText(String.format("%.2f", cliente.getBilleteraVirtual().getSaldo()));
        }
    }


    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(txtMonto);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actualizarSaldo();
    }

    public void inicializarObservable(Observable observable){
        this.observable = observable;
    }
}
