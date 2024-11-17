package co.edu.uniquindio.bookyourstay.Servicios;

import co.edu.uniquindio.bookyourstay.factory.Hotel;
import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import co.edu.uniquindio.bookyourstay.modelo.Habitacion;
import co.edu.uniquindio.bookyourstay.modelo.Reserva;
import co.edu.uniquindio.bookyourstay.modelo.enums.Ciudad;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ServiciosBookYourStay {

    Cliente agregarCliente(String cedula, String nombre, String telefono, String correo, String contrasena) throws Exception;

    Reserva crearReserva(Alojamiento alojamiento, Cliente cliente, LocalDate fechaEntrada, LocalDate fechaSalida,
                              int numeroHuespedes) throws Exception;

    Reserva crearReservaHotel(Hotel hotel, Habitacion habitacion, Cliente cliente, LocalDate fechaEntrada, LocalDate fechaSalida,
                              int numeroHuespedes) throws Exception;

    void EnviarCodigoRegistro(Cliente cliente);

    void reenviarCodigoRegistro(Cliente cliente);

    Cliente validarCliente(String correo, String contrasena) throws Exception;

    boolean verificarCodigo(String codigo) throws Exception;

    Alojamiento crearAlojamiento(TipoAlojamiento tipoAlojamiento, Ciudad ciudad, String nombre,
                          String descripcion, double precioNoche, int capacidad, double costoAseo,
                          double contoMantenimiento, Image image) throws Exception;

    Alojamiento crearAlojamientoHotel(List<Habitacion> habitaciones, Ciudad ciudad, String nombre,
                                      String descripcion, int capacidad, Image image) throws Exception;

    void eliminarAlojamiento(Alojamiento alojamiento);

    Habitacion crearHabitacion(int numero, double precio, int capacidad, Image image, String descripcion) throws Exception;

    List<Alojamiento> obtenerAlojamientosInicio();

    List<Reserva> listarReservasCliente(Cliente cliente);

    void eliminarReserva(Reserva reserva) throws Exception;

    List<Alojamiento> obtenerAlojamientoCiudad(Ciudad ciudad);

    List<Alojamiento> obtenerAlojamientosPorTipo(TipoAlojamiento tipoAlojamiento);

    List<Alojamiento> obtenerAlojamientosRangoPrecio(double precioMin, double precioMax) throws Exception;

    Map<TipoAlojamiento, Double> obtenerPorcentajeMasRentable();

    Map<TipoAlojamiento, Integer> obtenerAlojamientoMasPopular(Ciudad ciudad);
}
