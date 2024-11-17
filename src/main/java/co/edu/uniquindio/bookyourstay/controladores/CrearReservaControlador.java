package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.factory.Apartamento;
import co.edu.uniquindio.bookyourstay.factory.Casa;
import co.edu.uniquindio.bookyourstay.factory.Hotel;
import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import co.edu.uniquindio.bookyourstay.modelo.Habitacion;
import co.edu.uniquindio.bookyourstay.modelo.Reserva;
import co.edu.uniquindio.bookyourstay.modelo.enums.Ciudad;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class CrearReservaControlador implements Initializable {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    public Label lblFiltrar;
    @FXML
    public Button botonFiltroPrecio;
    private Cliente cliente;
    @FXML
    private TextField saldo, txtNombrePrecioAlojamiento, txtPrecioMax;
    @FXML
    private ComboBox<TipoAlojamiento> tipoAlojamientoComboBox;
    @FXML
    private ComboBox<Ciudad> ciudadComboBox;
    @FXML
    private ComboBox<String> filtroComboBox;
    @FXML
    private DatePicker fechaEntradaDp;
    @FXML
    private DatePicker fechaSalidaDp;
    @FXML
    private TextField txtNumeroHuespedes;
    @FXML
    private TableView<Alojamiento> alojamientoTableView;
    @FXML
    private TableColumn<Alojamiento, String> colTipoAlojamiento, colNombre, colCapacidadAlojamiento, colCostoMantenimiento, colCostoAseo, colPrecioNoche;
    @FXML
    private TableView<Habitacion> habitacionTableView;
    @FXML
    private TableColumn<Habitacion, String> colNumero;
    @FXML
    private TableColumn<Habitacion, String> colPrecio;
    @FXML
    private TableColumn<Habitacion, String> colCapacidad;
    @FXML
    private TableColumn<Habitacion, String> colDescripcion;

    public CrearReservaControlador(){
        this.cliente = controladorPrincipal.obtenerSesion();
    }


    public void crearReserva(ActionEvent actionEvent){
        try {
            Alojamiento alojamiento = alojamientoTableView.getSelectionModel().getSelectedItem();
            LocalDate fechaEntrada = fechaEntradaDp.getValue();
            LocalDate fechaSalida = fechaSalidaDp.getValue();
            String nHuespedesTexto = txtNumeroHuespedes.getText();

            if (!nHuespedesTexto.matches("\\d+")){
                throw new Exception("Número de huespedes inválido");
            }

            int numeroHuespedes = Integer.parseInt(nHuespedesTexto);

            if (alojamiento instanceof Hotel hotel){
                Habitacion habitacion = habitacionTableView.getSelectionModel().getSelectedItem();
                controladorPrincipal.crearReservaHotel(hotel, habitacion, cliente, fechaEntrada, fechaSalida, numeroHuespedes);
                controladorPrincipal.crearAlerta("La reserva se ha creado exitosamente", Alert.AlertType.INFORMATION);
            }else {
                controladorPrincipal.crearReserva(alojamiento, cliente, fechaEntrada, fechaSalida, numeroHuespedes);
                controladorPrincipal.crearAlerta("La reserva se ha creado exitosamente", Alert.AlertType.INFORMATION);

            }
            actualizarSaldo();
            limpiarCampos();

        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actualizarSaldo();
        ciudadComboBox.setItems(FXCollections.observableArrayList(Ciudad.values()));
        tipoAlojamientoComboBox.setItems(FXCollections.observableArrayList(TipoAlojamiento.values()));
        filtroComboBox.setItems(FXCollections.observableArrayList(List.of("Ciudad", "Nombre", "Precio", "Tipo" )));

        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
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


        colCapacidadAlojamiento.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCapacidad())));

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

        alojamientoTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof Hotel hotel) {
                habitacionTableView.setManaged(true);
                habitacionTableView.setVisible(true);
                cargarHabitaciones(hotel);
            } else {

                habitacionTableView.setVisible(false);
                habitacionTableView.setManaged(false);

                fechaEntradaDp.setManaged(true);
                fechaEntradaDp.setVisible(true);

                fechaSalidaDp.setManaged(true);
                fechaSalidaDp.setVisible(true);

                txtNumeroHuespedes.setManaged(true);
                txtNumeroHuespedes.setVisible(true);

                habitacionTableView.setItems(FXCollections.observableArrayList());
            }

        });

        habitacionTableView.setVisible(false);
        habitacionTableView.setManaged(false);

        configurarFiltroPorNombre();


    }

    public void limpiarCampos(){
        ciudadComboBox.setValue(null);
        fechaEntradaDp.setValue(null);
        fechaSalidaDp.setValue(null);
        txtNumeroHuespedes.clear();
    }

    private void cargarHabitaciones(Hotel hotel) {
        List<Habitacion> habitaciones = hotel.getHabitaciones();
        habitacionTableView.setItems(FXCollections.observableArrayList(habitaciones));
        colNumero.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumero())));
        colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
        colCapacidad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCapacidad())));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));


    }



    public void actualizarSaldo() {
        if (cliente != null && cliente.getBilleteraVirtual() != null) {
            saldo.setText(String.format("%.2f", cliente.getBilleteraVirtual().getSaldo()));
        }
    }

    public void irPanelCliente(ActionEvent actionEvent) throws Exception {
        controladorPrincipal.navegarVentana("/panelCliente.fxml", "Panel cliente");
        controladorPrincipal.cerrarVentana(saldo);
    }

    public void cargarFiltros(ActionEvent actionEvent) {
        String filtro = filtroComboBox.getValue();

        if (filtro.equals("Ciudad")){
            ciudadComboBox.setVisible(true);
            ciudadComboBox.setManaged(true);
            tipoAlojamientoComboBox.setManaged(false);
            tipoAlojamientoComboBox.setVisible(false);
            txtNombrePrecioAlojamiento.setVisible(false);
            txtNombrePrecioAlojamiento.setManaged(false);
            txtPrecioMax.setVisible(false);
            txtPrecioMax.setManaged(false);
            botonFiltroPrecio.setVisible(false);
            botonFiltroPrecio.setManaged(false);
        }else if (filtro.equals("Tipo")){
            ciudadComboBox.setVisible(false);
            ciudadComboBox.setManaged(false);
            tipoAlojamientoComboBox.setManaged(true);
            tipoAlojamientoComboBox.setVisible(true);
            txtNombrePrecioAlojamiento.setVisible(false);
            txtNombrePrecioAlojamiento.setManaged(false);
            txtPrecioMax.setVisible(false);
            txtPrecioMax.setManaged(false);
            botonFiltroPrecio.setVisible(false);
            botonFiltroPrecio.setManaged(false);
        } else if (filtro.equals("Precio")) {
            ciudadComboBox.setVisible(false);
            ciudadComboBox.setManaged(false);
            tipoAlojamientoComboBox.setManaged(false);
            tipoAlojamientoComboBox.setVisible(false);
            txtNombrePrecioAlojamiento.setVisible(true);
            txtNombrePrecioAlojamiento.setManaged(true);
            txtNombrePrecioAlojamiento.setPromptText("Precio Mínimo");
            txtPrecioMax.setVisible(true);
            txtPrecioMax.setManaged(true);
            botonFiltroPrecio.setVisible(true);
            botonFiltroPrecio.setManaged(true);
        } else {
            ciudadComboBox.setVisible(false);
            ciudadComboBox.setManaged(false);
            tipoAlojamientoComboBox.setManaged(false);
            tipoAlojamientoComboBox.setVisible(false);
            txtNombrePrecioAlojamiento.setVisible(true);
            txtNombrePrecioAlojamiento.setManaged(true);
            txtNombrePrecioAlojamiento.setPromptText("Nombre");
            txtPrecioMax.setVisible(false);
            txtPrecioMax.setManaged(false);
            botonFiltroPrecio.setVisible(false);
            botonFiltroPrecio.setManaged(false);
        }
    }


    public void actualizarTablaCiudad(ActionEvent actionEvent) {
        try {
            Ciudad ciudad = ciudadComboBox.getValue();

            if (ciudad != null){
                alojamientoTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.obtenerAlojamientoCiudad(ciudad)));
            }
        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void actualizarTablaTipo(ActionEvent actionEvent) {
        try {
            TipoAlojamiento tipoAlojamiento = tipoAlojamientoComboBox.getValue();

            if (tipoAlojamiento != null){
                alojamientoTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.obtenerAlojamientosPorTipo(tipoAlojamiento)));
            }
        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void actualizarTablaPrecios(ActionEvent actionEvent) {
        try {
            String precioMinTxt = txtNombrePrecioAlojamiento.getText();
            String precioMaxTxt = txtPrecioMax.getText();
            if (!precioMinTxt.matches("\\d+")){
                throw new Exception("Precio mínimo inválido");
            }

            if (!precioMaxTxt.matches("\\d+")){
                throw new Exception("Precio máximo inválido");
            }

            double precioMin = Double.parseDouble(precioMinTxt);
            double precioMax = Double.parseDouble(precioMaxTxt);
            List<Alojamiento> alojamientos = controladorPrincipal.obtenerAlojamientosRangoPrecio(precioMin, precioMax);

            if (alojamientos.isEmpty()){
                throw new Exception("No hay Alojamientos en ese rango de precios");
            }

            alojamientoTableView.setItems(FXCollections.observableArrayList(alojamientos));

        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void configurarFiltroPorNombre() {
        txtNombrePrecioAlojamiento.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null && !newValue.trim().isEmpty()) {
                    String textoBusqueda = newValue.toLowerCase();
                    List<Alojamiento> alojamientosFiltrados = controladorPrincipal.getBookYourStay().getAlojamientos().stream()
                            .filter(alojamiento -> alojamiento.getNombre().toLowerCase().contains(textoBusqueda))
                            .toList();

                    alojamientoTableView.setItems(FXCollections.observableArrayList(alojamientosFiltrados));
                } else {
                    alojamientoTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.getBookYourStay().getAlojamientos()));
                }
            } catch (Exception e) {
                controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

}
