package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.factory.Apartamento;
import co.edu.uniquindio.bookyourstay.factory.Casa;
import co.edu.uniquindio.bookyourstay.factory.Hotel;
import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InicioControlador implements Initializable {

    @FXML
    private Button btnIniciarSesion;
    @FXML
    private TableView<Alojamiento> alojamientoTableView;
    @FXML
    private TableColumn<Alojamiento, ImageView> colImagen;
    @FXML
    private TableColumn<Alojamiento, String> colTipo, colNombre, colCiudad;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public void irInicioSesion(ActionEvent actionEvent) throws Exception {
        controladorPrincipal.navegarVentana("/login.fxml", "login");
        controladorPrincipal.cerrarVentana(btnIniciarSesion);
    }

    public void irRegistro(ActionEvent actionEvent) throws Exception {
        controladorPrincipal.cerrarVentana(btnIniciarSesion);
        controladorPrincipal.navegarVentana("/registro.fxml", "Registro");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarTablaInicio();
    }

    public void cargarTablaInicio() {
        List<Alojamiento> alojamientos = controladorPrincipal.obtenerAlojamientosInicio();
        alojamientoTableView.setItems(FXCollections.observableArrayList(alojamientos));

        colImagen.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            Image imagen = alojamiento.getImagen();
            ImageView imageView = new ImageView(imagen);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            return new SimpleObjectProperty<>(imageView);
        });

        colImagen.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(item);
                }
            }
        });

        colTipo.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            String tipo = switch (alojamiento) {
                case Hotel hotel -> "Hotel";
                case Casa casa -> "Casa";
                case Apartamento apartamento -> "Apartamento";
                case null, default -> "Otro";
            };
            return new SimpleStringProperty(tipo);
        });

        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCiudad())));
    }

}
