package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

@Getter
@Setter
public class MasRentablesControlador implements Initializable {

    private ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private PieChart pc;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarDatosPieChart();
    }

    public void cargarDatosPieChart(){
        pc.getData().clear();
        Map<TipoAlojamiento, Double> mapaMasRentables = controladorPrincipal.obtenerPorcentajeMasRentable();

        if(mapaMasRentables != null) {

            for (Map.Entry<TipoAlojamiento, Double> entry : mapaMasRentables.entrySet()) {
                PieChart.Data data = new PieChart.Data(String.valueOf(entry.getKey()), entry.getValue());
                pc.getData().add(data);
            }
        }
    }

    public void irPanelAdmin(ActionEvent actionEvent) throws Exception{
        controladorPrincipal.navegarVentana("/panelAdmin.fxml", "Panel Admin ");
        controladorPrincipal.cerrarVentana(pc);
    }

}
