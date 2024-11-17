package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.factory.Apartamento;
import co.edu.uniquindio.bookyourstay.factory.Casa;
import co.edu.uniquindio.bookyourstay.factory.Hotel;
import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import co.edu.uniquindio.bookyourstay.modelo.Habitacion;
import co.edu.uniquindio.bookyourstay.modelo.enums.Ciudad;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.bookyourstay.observador.ObservableHabitaciones;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CrearAlojamientoControlador implements Initializable, ObservableHabitaciones {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private ComboBox<TipoAlojamiento> tipoAlojamientoComboBox;
    @FXML
    private ComboBox<Ciudad> ciudadComboBox;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private TextField txtPrecioNoche;
    @FXML
    private TextField txtCapacidad;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField txtCostoAseo;
    @FXML
    private TextField txtCostoMantenimiento;
    @FXML
    private TableView<Habitacion> habitacionTableView;
    @FXML
    private TableColumn<Habitacion, String> colNumero;
    @FXML
    private TableColumn<Habitacion, String> colPrecio;
    @FXML
    private TableColumn<Habitacion, String> colCapacidad;
    @FXML
    private TableView<Alojamiento> alojamientoTableView;
    @FXML
    private TableColumn<Alojamiento, String> colTipoAlojamiento, colCiudad, colNombre, colCapacidadAlojamiento, colCostoMantenimiento, colCostoAseo, colPrecioNoche;
    @FXML
    private Button botonHabitacion, botonEliminarHabitacion;
    @FXML
    private Button botonCrear;




    public void crearAlojamiento(ActionEvent actionEvent){
        try {
            TipoAlojamiento tipoAlojamiento = tipoAlojamientoComboBox.getValue();
            Ciudad ciudad = ciudadComboBox.getValue();
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            String capacidadTexto = txtCapacidad.getText();
            Image image = imageView.getImage();


            if (!capacidadTexto.matches("\\d+")){
                throw new Exception("Capacidad Inválida");
            }

            int capacidad = Integer.parseInt(capacidadTexto);

            if (tipoAlojamiento.equals(TipoAlojamiento.APARTAMENTO) || tipoAlojamiento.equals(TipoAlojamiento.CASA)){

                String precioNocheTexto = txtPrecioNoche.getText();

                if (!precioNocheTexto.matches("\\d+")){
                    throw new Exception("El Precio por noche debe ser un valor numérico");
                }
                double precioNoche = Double.parseDouble(precioNocheTexto);
                String costoAseoTexto = txtCostoAseo.getText();
                String costoMantenimientoTexto = txtCostoMantenimiento.getText();


                if (!costoAseoTexto.matches("\\d+")){
                    throw new Exception("El Costo del aseo debe ser un valor numérico");
                }

                if (!costoMantenimientoTexto.matches("\\d+")){
                    throw new Exception("El Costo del mantenimiento debe ser un valor numérico");
                }

                double costoAseo = Double.parseDouble(costoAseoTexto);
                double costoMantenimiento = Double.parseDouble(costoMantenimientoTexto);

                controladorPrincipal.crearAlojamiento(tipoAlojamiento, ciudad, nombre, descripcion, precioNoche,
                        capacidad, costoAseo, costoMantenimiento, image);
            }else {
                List<Habitacion> habitaciones = habitacionTableView.getItems();

                if (habitaciones ==  null){
                    throw new Exception("Agregue al menos una habitación");
                }
                controladorPrincipal.crearAlojamientoHotel(habitaciones, ciudad, nombre, descripcion, capacidad, image);

            }
            limpiarCampos();
            alojamientoTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.getBookYourStay().getAlojamientos()));
            controladorPrincipal.crearAlerta("El alojamiento se ha creado exitosamente", Alert.AlertType.INFORMATION);


        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void seleccionarImagen(ActionEvent actionEvent){
        controladorPrincipal.abrirFileChooser(imageView);
    }

    public void eliminarHabitacion(ActionEvent actionEvent){
        Habitacion habitacionSeleccionada = habitacionTableView.getSelectionModel().getSelectedItem();
        if (habitacionSeleccionada != null){
            habitacionTableView.getItems().remove(habitacionSeleccionada);
            controladorPrincipal.crearAlerta("La habitación se ha eliminado", Alert.AlertType.INFORMATION);
        }else {
            controladorPrincipal.crearAlerta("Seleccione una habitación", Alert.AlertType.ERROR);
        }
    }


    public void eliminarAlojamiento(ActionEvent actionEvent){
        try {
            Alojamiento alojamiento = alojamientoTableView.getSelectionModel().getSelectedItem();
            if (alojamiento == null){
                throw new Exception("Elija un Alojamiento");
            } else {
                controladorPrincipal.eliminarAlojamiento(alojamiento);
                alojamientoTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.getBookYourStay().getAlojamientos()));
                controladorPrincipal.crearAlerta("El alojamiento se ha eliminado exitosamente", Alert.AlertType.INFORMATION);
            }

        }catch (Exception e){
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipoAlojamientoComboBox.setItems(FXCollections.observableArrayList(TipoAlojamiento.values()));
        ciudadComboBox.setItems(FXCollections.observableArrayList(Ciudad.values()));
        colNumero.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumero())));
        colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
        colCapacidad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCapacidad())));
        cargarDatosAlojamientos();

    }

    public void cargarDatosAlojamientos(){
        colTipoAlojamiento.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            String tipo = switch (alojamiento) {
                case Hotel hotel -> "Hotel";
                case Casa casa -> "Casa";
                case Apartamento apartamento -> "Apartamento";
                case null, default -> "Otro";
            };

            return new SimpleStringProperty(tipo);
        });

        colCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCiudad())));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colCapacidadAlojamiento.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCapacidad())));
        alojamientoTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.getBookYourStay().getAlojamientos()));

        colCostoMantenimiento.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            if (alojamiento instanceof Casa casa) {
                return new SimpleStringProperty(String.format("%.2f", casa.getCostoMantenimiento()));
            }else if (alojamiento instanceof Apartamento apartamento){
                return new SimpleStringProperty(String.format("%.2f", apartamento.getCostoMantenimiento()));
            }
            return new SimpleStringProperty("N/A");
        });

        colCostoAseo.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            if (alojamiento instanceof Casa casa) {
                return new SimpleStringProperty(String.format("%.2f", casa.getCostoAseo()));
            }else if (alojamiento instanceof Apartamento apartamento){
                return new SimpleStringProperty(String.format("%.2f", apartamento.getCostoAseo()));
            }
            return new SimpleStringProperty("N/A");
        });

        colPrecioNoche.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            if (alojamiento instanceof Casa casa) {
                return new SimpleStringProperty(String.format("%.2f", casa.getPrecioNoche()));
            }else if (alojamiento instanceof Apartamento apartamento){
                return new SimpleStringProperty(String.format("%.2f", apartamento.getPrecioNoche()));
            } else if (alojamiento instanceof Hotel) {
                return new SimpleStringProperty("Ver precio habitación");
            }
            return new SimpleStringProperty("N/A");
        });
    }

    public void seleccionarTipoAlojamiento(ActionEvent actionEvent) {
        TipoAlojamiento tipo = tipoAlojamientoComboBox.getValue();

        if(tipo == TipoAlojamiento.HOTEL){
            txtCostoAseo.setVisible(false);
            txtCostoAseo.setManaged(false);
            txtPrecioNoche.setVisible(false);
            txtPrecioNoche.setManaged(false);
            txtCostoMantenimiento.setVisible(false);
            txtCostoMantenimiento.setManaged(false);
            botonHabitacion.setVisible(true);
            botonHabitacion.setManaged(true);
            habitacionTableView.setVisible(true);
            habitacionTableView.setManaged(true);
            botonEliminarHabitacion.setVisible(true);
            botonEliminarHabitacion.setManaged(true);


        }else{
            txtCostoAseo.setVisible(true);
            txtCostoAseo.setManaged(true);
            txtCostoMantenimiento.setVisible(true);
            txtCostoMantenimiento.setManaged(true);
            botonHabitacion.setVisible(false);
            botonHabitacion.setManaged(false);
            habitacionTableView.setVisible(false);
            habitacionTableView.setManaged(false);
            botonEliminarHabitacion.setVisible(false);
            botonEliminarHabitacion.setManaged(false);
            txtPrecioNoche.setVisible(true);
            txtPrecioNoche.setManaged(true);

        }

        botonCrear.setVisible(true);
        botonCrear.setDisable(false);

    }

    public void irCrearHabitacion(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = controladorPrincipal.navegarVentana("/crearHabitacion.fxml", "Crear Habitación");
        CrearHabitacionControlador crearHabitacionControlador = loader.getController();
        crearHabitacionControlador.inicializarObservable(this);
    }

    public void irPanelAdmin(ActionEvent actionEvent)throws Exception{
        controladorPrincipal.navegarVentana("/panelAdmin.fxml", "Panel Admin");
        cerrarVentana();
    }

    public void limpiarCampos(){
        tipoAlojamientoComboBox.getSelectionModel().clearSelection();
        ciudadComboBox.getSelectionModel().clearSelection();
        txtNombre.clear();
        txtDescripcion.clear();
        txtCapacidad.clear();
        txtPrecioNoche.clear();
        txtCostoMantenimiento.clear();
        txtCostoAseo.clear();
        habitacionTableView.setItems(FXCollections.observableArrayList());
        habitacionTableView.setVisible(false);
        txtPrecioNoche.setVisible(false);
        txtCostoAseo.setVisible(false);
        txtCostoMantenimiento.setVisible(false);
        imageView.setImage(null);
        botonCrear.setVisible(false);



    }

    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(tipoAlojamientoComboBox);
    }


    @Override
    public void notificar(List<Habitacion> habitaciones) {
        habitacionTableView.setItems(FXCollections.observableArrayList(habitaciones));
    }
}
