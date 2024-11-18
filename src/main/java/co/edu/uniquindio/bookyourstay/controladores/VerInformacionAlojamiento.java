package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import co.edu.uniquindio.bookyourstay.modelo.Resena;
import co.edu.uniquindio.bookyourstay.modelo.Reserva;
import co.edu.uniquindio.bookyourstay.observador.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class VerInformacionAlojamiento {

    private Alojamiento alojamiento;
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private ImageView imageView;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private TableView<Resena> resenaTableView;
    @FXML
    private TableColumn<Resena, String> colPuntuacion, colComentario;

    public void inicializar(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
        cargarDatos();
    }



    public void cargarDatos(){
        try {
            if (alojamiento != null){
                imageView.setImage(alojamiento.getImagen());
                txtNombre.setText(alojamiento.getNombre());
                txtDescripcion.setText(alojamiento.getDescripcion());

                colPuntuacion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCalificacion())));
                colComentario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComentario()));
                resenaTableView.setItems(FXCollections.observableArrayList(alojamiento.getResenas()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
