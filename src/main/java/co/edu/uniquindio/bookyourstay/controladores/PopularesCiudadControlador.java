package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.enums.Ciudad;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Getter
@Setter
public class PopularesCiudadControlador implements Initializable {

    private ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private ComboBox<Ciudad> ciudadComboBox;
    @FXML
    private BarChart<String, Number> barChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ciudadComboBox.setItems(FXCollections.observableArrayList(Ciudad.values()));
        cargarDatosBarChart();
    }

    @FXML
    private void cargarDatosBarChart() {
        barChart.getData().clear();
        Ciudad ciudad = ciudadComboBox.getValue();

        Map<TipoAlojamiento, Integer> mapaMasPopulares = controladorPrincipal.obtenerAlojamientoMasPopular(ciudad);

        if (mapaMasPopulares != null && ciudad != null) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Alojamientos en " + ciudad.name());

            for (Map.Entry<TipoAlojamiento, Integer> entry : mapaMasPopulares.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey().name(), entry.getValue()));
            }

            barChart.getData().add(series);
        }
    }

    public void irPanelAdmin(ActionEvent actionEvent) throws Exception {
        controladorPrincipal.navegarVentana("/panelAdmin.fxml", "Panel Admin");
        controladorPrincipal.cerrarVentana(barChart);
    }


}
