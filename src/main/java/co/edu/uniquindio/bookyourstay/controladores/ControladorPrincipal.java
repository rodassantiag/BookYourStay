package co.edu.uniquindio.bookyourstay.controladores;

import co.edu.uniquindio.bookyourstay.Servicios.ServiciosBookYourStay;
import co.edu.uniquindio.bookyourstay.factory.Hotel;
import co.edu.uniquindio.bookyourstay.modelo.*;
import co.edu.uniquindio.bookyourstay.modelo.enums.Ciudad;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.bookyourstay.utils.Sesion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
public class ControladorPrincipal implements ServiciosBookYourStay {

    private final BookYourStay bookYourStay;

    public static ControladorPrincipal INSTANCIA;

    public ControladorPrincipal(){bookYourStay = new BookYourStay();}

    public static ControladorPrincipal getInstancia(){
        if(INSTANCIA == null){
            INSTANCIA = new ControladorPrincipal();
        }
        return INSTANCIA;
    }

    public FXMLLoader navegarVentana(String nombreArchivoFxml, String tituloVentana) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.setTitle(tituloVentana);

        stage.show();

        return loader;
    }

    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    public void cerrarVentana(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void abrirFileChooser(ImageView imageView) {

        // Crear un FileChooser para seleccionar la imagen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");

        // Filtrar los archivos que se pueden seleccionar
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        // Mostrar el FileChooser y obtener la imagen seleccionada
        File imagen = fileChooser.showOpenDialog(null);

        // Mostrar la imagen seleccionada en la interfaz gráfica
        if (imagen != null) {
            imageView.setImage(new javafx.scene.image.Image(imagen.toURI().toString()));
        }

    }

    @Override
    public Cliente agregarCliente(String cedula, String nombre, String telefono, String correo, String contrasena) throws Exception {
        return bookYourStay.agregarCliente(cedula, nombre, telefono, correo, contrasena);
    }

    @Override
    public Reserva crearReserva(Alojamiento alojamiento, Cliente cliente, LocalDate fechaEntrada, LocalDate fechaSalida, int numeroHuespedes) throws Exception {
        return bookYourStay.crearReserva(alojamiento, cliente, fechaEntrada, fechaSalida, numeroHuespedes);
    }

    @Override
    public Reserva crearReservaHotel(Hotel hotel, Habitacion habitacion, Cliente cliente, LocalDate fechaEntrada, LocalDate fechaSalida, int numeroHuespedes) throws Exception {
        return bookYourStay.crearReservaHotel(hotel, habitacion, cliente, fechaEntrada, fechaSalida, numeroHuespedes);
    }

    @Override
    public void EnviarCodigoRegistro(Cliente cliente) {
        bookYourStay.EnviarCodigoRegistro(cliente);
    }

    @Override
    public void reenviarCodigoRegistro(Cliente cliente) {
        bookYourStay.reenviarCodigoRegistro(cliente);
    }

    @Override
    public Cliente validarCliente(String correo, String contrasena) throws Exception {
        return bookYourStay.validarCliente(correo, contrasena);
    }

    @Override
    public boolean verificarCodigo(String codigo) throws Exception {
        return bookYourStay.verificarCodigo(codigo);
    }

    @Override
    public Alojamiento crearAlojamiento(TipoAlojamiento tipoAlojamiento, Ciudad ciudad, String nombre, String descripcion, double precioNoche, int capacidad, double costoAseo, double contoMantenimiento, Image image) throws Exception {
        return bookYourStay.crearAlojamiento(tipoAlojamiento, ciudad, nombre, descripcion, precioNoche, capacidad, costoAseo, contoMantenimiento, image);

    }

    @Override
    public Alojamiento crearAlojamientoHotel(List<Habitacion> habitaciones, Ciudad ciudad, String nombre, String descripcion, int capacidad, Image image) throws Exception {
        return bookYourStay.crearAlojamientoHotel(habitaciones, ciudad, nombre, descripcion, capacidad, image);

    }

    @Override
    public void eliminarAlojamiento(Alojamiento alojamiento) {
        bookYourStay.eliminarAlojamiento(alojamiento);
    }

    @Override
    public Habitacion crearHabitacion(int numero, double precio, int capacidad, Image image, String descripcion) throws Exception {
        return bookYourStay.crearHabitacion(numero, precio, capacidad, image, descripcion);
    }

    @Override
    public List<Alojamiento> obtenerAlojamientosInicio() {
        return bookYourStay.obtenerAlojamientosInicio();
    }

    @Override
    public List<Reserva> listarReservasCliente(Cliente cliente) {
        return bookYourStay.listarReservasCliente(cliente);
    }

    @Override
    public void eliminarReserva(Reserva reserva) throws Exception{
        bookYourStay.eliminarReserva(reserva);
    }

    @Override
    public List<Alojamiento> obtenerAlojamientoCiudad(Ciudad ciudad) {
        return bookYourStay.obtenerAlojamientoCiudad(ciudad);
    }

    @Override
    public List<Alojamiento> obtenerAlojamientosPorTipo(TipoAlojamiento tipoAlojamiento) {
        return bookYourStay.obtenerAlojamientosPorTipo(tipoAlojamiento);
    }

    @Override
    public List<Alojamiento> obtenerAlojamientosRangoPrecio(double precioMin, double precioMax) throws Exception {
        return bookYourStay.obtenerAlojamientosRangoPrecio(precioMin, precioMax);
    }

    @Override
    public Map<TipoAlojamiento, Double> obtenerPorcentajeMasRentable() {
        return bookYourStay.obtenerPorcentajeMasRentable();
    }

    @Override
    public Map<TipoAlojamiento, Integer> obtenerAlojamientoMasPopular(Ciudad ciudad) {
        return bookYourStay.obtenerAlojamientoMasPopular(ciudad);
    }

    public void guardarSesion(Cliente cliente){
        Sesion sesion = Sesion.getInstancia();
        sesion.asignarCliente(cliente);
    }

    public Cliente obtenerSesion(){
        return Sesion.getInstancia().obtenerCliente();
    }

    public void eliminarSesion(){
        Sesion.getInstancia().asignarCliente(null);
    }

}