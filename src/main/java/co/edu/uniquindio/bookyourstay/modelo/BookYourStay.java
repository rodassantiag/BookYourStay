package co.edu.uniquindio.bookyourstay.modelo;

import co.edu.uniquindio.bookyourstay.Servicios.ServiciosBookYourStay;
import co.edu.uniquindio.bookyourstay.factory.*;
import co.edu.uniquindio.bookyourstay.modelo.enums.Ciudad;
import co.edu.uniquindio.bookyourstay.modelo.enums.Rol;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.bookyourstay.utils.EnvioEmail;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
public class BookYourStay implements ServiciosBookYourStay {
    private ArrayList<Cliente> clientes;
    private ArrayList<Cliente> admin;
    private ArrayList<Reserva> reservas;
    private ArrayList<Alojamiento> alojamientos;
    private AlojamientoFactory alojamientoFactory;
    private ReservaFactory reservaFactory;


    public BookYourStay() {
        this.clientes = new ArrayList<>();
        this.clientes.add(Cliente.builder()
                .correo("pepito123")
                .contrasena("123")
                .rol(Rol.CLIENTE)
                .primerLogin(false)
                        .billeteraVirtual(BilleteraVirtual.builder()
                                .saldo(500000)
                                .movimientos(new ArrayList<>())
                                .build())
                .build());
        this.admin = new ArrayList<>();
        this.admin.add(Cliente.builder()
                .rol(Rol.ADMIN)
                .nombre("pepito")
                .correo("pepito")
                .contrasena("123")
                .build());

        this.alojamientoFactory = new AlojamientoFactory();
        this.reservaFactory = new ReservaFactory();
        this.alojamientos = new ArrayList<>();
        this.alojamientos.add(Hotel.builder()
                .nombre("veve")
                .ciudad(Ciudad.ARMENIA)
                .build());
        this.reservas = new ArrayList<>();
    }

    @Override
    public Cliente agregarCliente(String cedula, String nombre, String telefono, String correo,
                                  String contrasena) throws Exception {

        if (cedula.isBlank()) {
            throw new Exception("La cédula es obligatoria");
        }

        if (!cedula.matches("\\d+")) {
            throw new Exception("Cédula inválida");
        }

        if (obtenerCliente(cedula) != null) {
            throw new Exception("Ya existe un usuario con la misma cédula");
        }

        if (nombre.isBlank()) {
            throw new Exception("El nombre es obligatorio");
        }

        if (telefono.isEmpty()){
            throw new Exception("El teléfono es obligatorio");
        }

        if (!telefono.matches("\\d+")) {
            throw new Exception("El número de teléfono es inválido");
        }

        if (correo.isEmpty()) {
            throw new Exception("El correo es obligatorio");
        }

        if (obtenerClientePorCorreo(correo) != null){
            throw new Exception("Ya hay un usuario registrado con el mismo correo");
        }

        if (contrasena.length() < 6) {
            throw new Exception("La contraseña debe tener mínimo 6 caracteres");
        }

        Cliente cliente = Cliente.builder()
                .cedula(cedula)
                .nombre(nombre)
                .telefono(telefono)
                .correo(correo)
                .contrasena(contrasena)
                .primerLogin(true)
                .rol(Rol.CLIENTE)
                .id(generarCodigoRegistro())
                .billeteraVirtual(BilleteraVirtual.builder()
                        .saldo(0)
                        .movimientos(new ArrayList<>())
                        .build())
                .build();

        clientes.add(cliente);
        return cliente;
    }

    public String generarCodigoRegistro() {
        StringBuilder codigoRegistro = new StringBuilder();


        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int numero = random.nextInt(8);
            codigoRegistro.append(numero);
        }

        return codigoRegistro.toString();
    }


    @Override
    public void EnviarCodigoRegistro(Cliente cliente){
        String correo = cliente.getCorreo();
        String asunto = "Código de verificación cuenta BookYourStay" ;
        String mensaje = "Hola "+cliente.getNombre()+", Su código de verificación es: "+cliente.getId()+ " gracias por registrarse en nuestra app";
        EnvioEmail envioEmail = new EnvioEmail(correo, asunto, mensaje);
        envioEmail.enviarNotificacion();
    }


    @Override
    public void reenviarCodigoRegistro(Cliente cliente){
        cliente.setId(generarCodigoRegistro());
        String correo = cliente.getCorreo();
        String asunto = "Nuevo código de verificación cuenta BookYourStay" ;
        String mensaje = "Su nuevo código de verificación es: "+cliente.getId()+", gracias por registrarse en nuestra app";
        EnvioEmail envioEmail = new EnvioEmail(correo, asunto, mensaje);
        envioEmail.enviarNotificacion();
    }

    public Cliente obtenerCliente(String cedula){
        for (Cliente cliente : clientes){
            if (cliente.getCedula() != null && cliente.getCedula().equals(cedula) && cliente.getRol() == Rol.CLIENTE){
                return cliente;
            }
        }
        return null;
    }

    public Cliente obtenerClientePorCorreo(String correo){
        for (Cliente cliente : clientes){
            if (cliente.getCorreo().equals(correo)){
                return cliente;
            }
        }
        return null;
    }


    @Override
    public Cliente validarCliente(String correo, String contrasena) throws Exception{
        for (Cliente cliente : clientes){
            if (cliente.getCorreo().equals(correo) && cliente.getContrasena().equals(contrasena)){
                return cliente;
            }
        }

        for (Cliente admin : admin){
            if (admin.getCorreo().equals(correo) && admin.getContrasena().equals(contrasena)){
                return admin;
            }
        }

        throw new Exception("Credenciales incorrectas o usuario no encontrado");
    }

    @Override
    public boolean verificarCodigo(String codigo) throws Exception {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(codigo)) {
                cliente.setPrimerLogin(false);
                return true;
            }
        }
        throw new Exception("El código es incorrecto");
    }

    @Override
    public Alojamiento crearAlojamiento(TipoAlojamiento tipoAlojamiento, Ciudad ciudad, String nombre,
                                 String descripcion, double precioNoche, int capacidad, double costoAseo,
                                 double contoMantenimiento, Image image) throws Exception {

        Alojamiento alojamiento = this.alojamientoFactory.crearAlojamiento(tipoAlojamiento, ciudad, nombre, descripcion, precioNoche, capacidad,
                costoAseo, contoMantenimiento, image);
        alojamientos.add(alojamiento);
        return alojamiento;
    }

    @Override
    public Alojamiento crearAlojamientoHotel(List<Habitacion> habitaciones, Ciudad ciudad, String nombre,
                                             String descripcion, int capacidad, Image image) throws Exception {

        Alojamiento alojamiento = this.alojamientoFactory.crearHotel(habitaciones, ciudad, nombre, descripcion, capacidad, image);
        alojamientos.add(alojamiento);

        return alojamiento;
    }

    @Override
    public void eliminarAlojamiento(Alojamiento alojamiento){
        alojamientos.remove(alojamiento);
    }

    @Override
    public Habitacion crearHabitacion(int numero, double precio, int capacidad, Image image, String descripcion) throws Exception{

        if (numero < 0){
            throw new Exception("El numero de la habitación no puede ser negativo");
        }

        if (precio < 0){
            throw new Exception("El precio de la habitación no puede ser negativo");
        }

        if (capacidad < 0){
            throw new Exception("La capacidad de la habitación no puede ser negativa");
        }

        if (image == null){
            throw new Exception("La imagen es obligatoria");
        }

        if (descripcion.isBlank()){
            throw new Exception("La descripción es obligatoria");
        }

        Habitacion habitacion = Habitacion.builder()
                .numero(numero)
                .precio(precio)
                .capacidad(capacidad)
                .imagen(image)
                .descripcion(descripcion)
                .build();

        return habitacion;

    }



    @Override
    public Reserva crearReservaHotel(Hotel hotel, Habitacion habitacion, Cliente cliente, LocalDate fechaEntrada, LocalDate fechaSalida,
                                     int numeroHuespedes) throws Exception {

        if (obtenerHabitacion(hotel, habitacion, fechaEntrada, fechaSalida) != null){
            throw new Exception("La habitación elegida ya ha sido ocupada");
        }

        if (obtenerReservasCliente(cliente, fechaEntrada, fechaSalida) != null){
            throw new Exception("Ya tiene una reserva activa entre las fechas "
                    + fechaEntrada + " y " + fechaSalida + ". No puede realizar reservas duplicadas.");
        }

        Reserva reserva = this.reservaFactory.crearReservaHotel(hotel, habitacion, cliente, fechaEntrada, fechaSalida, numeroHuespedes);
        reservas.add(reserva);
        return reserva;
    }

    public Reserva obtenerHabitacion(Hotel hotel, Habitacion habitacion, LocalDate fechaEntrada, LocalDate fechaSalida) {

        for (Reserva reserva : reservas) {
            if (reserva.getAlojamiento().equals(hotel)) {
                for (Habitacion h : hotel.getHabitaciones()) {
                    if (h.equals(habitacion)) {
                        if (fechasSeSuperponen(reserva.getFechaEntrada(), reserva.getFechaSalida(), fechaEntrada, fechaSalida)) {
                            return reserva;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Reserva crearReserva(Alojamiento alojamiento, Cliente cliente, LocalDate fechaEntrada, LocalDate fechaSalida, int numeroHuespedes) throws Exception {
        if (obtenerReserva(alojamiento, fechaEntrada, fechaSalida) != null){
            throw new Exception(("El alojamiento seleccionado ya está reservado entre las fechas "
                    + fechaEntrada + " y " + fechaSalida + ". Por favor, elige otras fechas."));
        }

        if (obtenerReservasCliente(cliente, fechaEntrada, fechaSalida) != null){
            throw new Exception("Ya tiene una reserva activa entre las fechas "
                    + fechaEntrada + " y " + fechaSalida + ". No puede realizar reservas duplicadas.");
        }

        Reserva reserva = this.reservaFactory.crearReserva(alojamiento, cliente, fechaEntrada, fechaSalida, numeroHuespedes);
        reservas.add(reserva);
        return reserva;
    }

    public Reserva obtenerReserva(Alojamiento alojamiento, LocalDate fechaEntrada, LocalDate fechaSalida){

        for (Reserva reserva : reservas){
            if (reserva.getAlojamiento().equals(alojamiento) && fechasSeSuperponen(reserva.getFechaEntrada(), reserva.getFechaSalida(), fechaEntrada, fechaSalida )){
                return reserva;
            }
        }
        return null;
    }

    public Reserva obtenerReservasCliente(Cliente cliente, LocalDate fechaEntrada, LocalDate fechaSalida){
        for (Reserva reserva : reservas){
            if (reserva.getCliente().equals(cliente)  && fechasSeSuperponen(reserva.getFechaEntrada(), reserva.getFechaSalida(), fechaEntrada, fechaSalida )){
                return reserva;
            }
        }
        return null;
    }

    private boolean fechasSeSuperponen(LocalDate inicioReserva, LocalDate finReserva, LocalDate inicioParametro, LocalDate finParametro) {
        return !(finParametro.isBefore(inicioReserva) || inicioParametro.isAfter(finReserva));
    }

    @Override
    public List<Alojamiento> obtenerAlojamientosInicio(){

        List<Alojamiento> respuesta = new ArrayList<>();
        Random random = new Random();

        if(alojamientos.size()>= 10) {

            while (respuesta.size() < 10) {
                int pos = random.nextInt(alojamientos.size());
                Alojamiento alojamiento = alojamientos.get(pos);
                if (existeEnLista(alojamiento, respuesta)) {
                    continue;
                }
                respuesta.add(alojamiento);
            }

        }else{
            respuesta = alojamientos;
        }

        return respuesta;

    }



    public boolean existeEnLista(Alojamiento alojamiento, List<Alojamiento> lista){
        for(Alojamiento a: lista){
            if(a.getId().equals(alojamiento.getId())){
                return  true;
            }
        }
        return false;
    }

    @Override
    public List<Reserva> listarReservasCliente(Cliente cliente){
        List<Reserva> reservasCliente = new ArrayList<>();
        for (Reserva reserva : reservas){
            if (reserva.getCliente().equals(cliente)){
                reservasCliente.add(reserva);
            }
        }
        return reservasCliente;
    }

    @Override
    public void eliminarReserva(Reserva reserva) throws Exception {
        if (reserva == null) {
            throw new Exception("Reserva no encontrada.");
        }
        if (!reserva.getFechaEntrada().isAfter(LocalDate.now().plusDays(2))) {
            throw new Exception("La reserva solo puede cancelarse con al menos dos días de antelación.");
        }
        reservas.remove(reserva);
        reserva.getCliente().getBilleteraVirtual().recargar(reserva.getTotalPagar());
    }

    @Override
    public List<Alojamiento> obtenerAlojamientoCiudad(Ciudad ciudad){
        List<Alojamiento> listaAlojamientosCiudad = new ArrayList<>();
        for (Alojamiento alojamiento : alojamientos){
            if (alojamiento.getCiudad().equals(ciudad)){
                listaAlojamientosCiudad.add(alojamiento);
            }
        }
        return listaAlojamientosCiudad;
    }

    @Override
    public List<Alojamiento> obtenerAlojamientosPorTipo(TipoAlojamiento tipoAlojamiento){
        List<Alojamiento> alojamientosTipo =  new ArrayList<>();
        if (tipoAlojamiento.equals(TipoAlojamiento.CASA)){
            for (Alojamiento alojamiento : alojamientos){
                if (alojamiento instanceof Casa){
                    alojamientosTipo.add(alojamiento);
                }
            }
        }

        if (tipoAlojamiento.equals(TipoAlojamiento.APARTAMENTO)){
            for (Alojamiento alojamiento : alojamientos){
                if (alojamiento instanceof Apartamento){
                    alojamientosTipo.add(alojamiento);
                }
            }
        }

        if (tipoAlojamiento.equals(TipoAlojamiento.HOTEL)){
            for (Alojamiento alojamiento : alojamientos){
                if (alojamiento instanceof Hotel){
                    alojamientosTipo.add(alojamiento);
                }
            }
        }
        return alojamientosTipo;
    }

    @Override
    public List<Alojamiento> obtenerAlojamientosRangoPrecio(double precioMin, double precioMax) throws Exception{
        List<Alojamiento> alojamientosRangoPrecio = new ArrayList<>();

        if (precioMin <= 0 || precioMax <= 0){
            throw new Exception("Los precios filtrados deben ser mayores a 0");
        }

        if (precioMin > precioMax){
            throw new Exception("El precio mínimo no puede ser mayor al precio máximo");
        }

        for (Alojamiento alojamiento : alojamientos){
            if (alojamiento instanceof Hotel hotel){
                List<Habitacion> habitaciones = hotel.getHabitaciones();
                if (habitaciones != null){
                    for (Habitacion habitacion : habitaciones){
                        if (estaEnRangoPrecio(precioMin, precioMax, habitacion.getPrecio())){
                            alojamientosRangoPrecio.add(alojamiento);
                        }
                    }
                }
            }else if (alojamiento instanceof Casa casa){
               if (estaEnRangoPrecio(precioMin, precioMax, casa.getPrecioNoche())){
                   alojamientosRangoPrecio.add(alojamiento);
               }
            } else if (alojamiento instanceof Apartamento apartamento) {
                if (estaEnRangoPrecio(precioMin, precioMax, apartamento.getPrecioNoche())){
                    alojamientosRangoPrecio.add(alojamiento);
                }
            }
        }
        return alojamientosRangoPrecio;
    }

    private boolean estaEnRangoPrecio(double precioMin, double precioMax, double precioAlojamiento) {
        if (precioAlojamiento >= precioMin &&  precioAlojamiento <= precioMax){
            return true;
        }
        return false;
    }

    @Override
    public Map<TipoAlojamiento, Double> obtenerPorcentajeMasRentable(){

        double total = 0;
        double totalHotel = 0;
        double totalApartamento = 0;
        double totalCasas = 0;

        Map<TipoAlojamiento, Double> mapa = new HashMap<>();

        for(Reserva r : reservas){
            if(r.getAlojamiento() instanceof Hotel){
                totalHotel += r.getTotalPagar();
            }else if(r.getAlojamiento() instanceof Apartamento){
                totalApartamento += r.getTotalPagar();
            }else {
                totalCasas += r.getTotalPagar();
            }
            total += r.getTotalPagar();
        }

        double porcentajeHotel = total == 0 ? 0 : (totalHotel / total) * 100;
        double porcentajeApartamento = total == 0 ? 0 : (totalApartamento / total) * 100;
        double porcentajeCasas = total == 0 ? 0 : (totalCasas / total) * 100;

        mapa.put(TipoAlojamiento.HOTEL, porcentajeHotel);
        mapa.put(TipoAlojamiento.APARTAMENTO, porcentajeApartamento);
        mapa.put(TipoAlojamiento.CASA, porcentajeCasas);

        return mapa;
    }

    @Override
    public Map<TipoAlojamiento, Integer> obtenerAlojamientoMasPopular(Ciudad ciudad) {
        int totalHotel = 0;
        int totalApartamento = 0;
        int totalCasas = 0;

        Map<TipoAlojamiento, Integer> mapa = new HashMap<>();

        for (Reserva reserva : reservas) {
            if (reserva.getAlojamiento().getCiudad().equals(ciudad)) {
                switch (reserva.getAlojamiento()) {
                    case Hotel hotel -> totalHotel++;
                    case Apartamento apartamento -> totalApartamento++;
                    case Casa casa -> totalCasas++;
                    default -> {
                    }
                }
            }
        }

        mapa.put(TipoAlojamiento.HOTEL, totalHotel);
        mapa.put(TipoAlojamiento.APARTAMENTO, totalApartamento);
        mapa.put(TipoAlojamiento.CASA, totalCasas);

        return mapa;
    }







}
