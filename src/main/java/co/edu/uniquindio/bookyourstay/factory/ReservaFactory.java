package co.edu.uniquindio.bookyourstay.factory;

import co.edu.uniquindio.bookyourstay.modelo.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReservaFactory {


    public Reserva crearReservaHotel(Hotel hotel, Habitacion habitacion, Cliente cliente, LocalDate fechaEntrada, LocalDate fechaSalida,
                                     int numeroHuespedes) throws Exception {

        if (hotel == null || habitacion == null) {
            throw new Exception("Debe seleccionar un hotel y una habitación.");
        }


        if (fechaEntrada == null || fechaSalida == null) {
            throw new Exception("Las fechas de entrada y salida son obligatorias.");
        }

        if (fechaSalida.isBefore(fechaEntrada)) {
            throw new Exception("La fecha de salida no puede ser anterior a la fecha de entrada.");
        }

        if (fechaEntrada.isBefore(LocalDate.now())) {
            throw new Exception("La fecha de entrada debe ser igual o posterior a la fecha actual.");
        }

        if (numeroHuespedes <= 0) {
            throw new Exception("El número de huéspedes debe ser mayor a cero.");
        }

        if (numeroHuespedes > habitacion.getCapacidad()) {
            throw new Exception("El número de huéspedes supera la capacidad máxima de la habitación.");
        }

        long noches = java.time.temporal.ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        if (noches <= 0) {
            throw new Exception("Debe reservar al menos una noche.");
        }
        double totalPagar = noches * habitacion.getPrecio();

        realizarPago(cliente.getBilleteraVirtual(), totalPagar);


        return ReservaHotel.builder()
                .alojamiento(hotel)
                .habitacion(habitacion)
                .cliente(cliente)
                .fechaEntrada(fechaEntrada)
                .fechaSalida(fechaSalida)
                .numeroHuespedes(numeroHuespedes)
                .totalPagar(totalPagar)
                .build();
    }

    public Reserva crearReserva(Alojamiento alojamiento, Cliente cliente, LocalDate fechaEntrada, LocalDate fechaSalida, int numeroHuespedes) throws Exception {

        if (alojamiento == null){
            throw new Exception("Elija el alojamiento");
        }

        if (fechaEntrada == null || fechaSalida == null) {
            throw new Exception("Las fechas de entrada y salida son obligatorias.");
        }

        if (fechaSalida.isBefore(fechaEntrada)) {
            throw new Exception("La fecha de salida no puede ser anterior a la fecha de entrada.");
        }

        if (fechaEntrada.isBefore(LocalDate.now())) {
            throw new Exception("La fecha de entrada debe ser igual o posterior a la fecha actual.");
        }

        if (numeroHuespedes <= 0) {
            throw new Exception("El número de huéspedes debe ser mayor a cero.");
        }

        if (numeroHuespedes > alojamiento.getCapacidad()){
            throw new Exception("El número de huespedes supera la capacidad máxima del alojamiento");
        }

        long noches = java.time.temporal.ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        if (noches <= 0) {
            throw new Exception("Debe reservar al menos una noche.");
        }

        if (alojamiento instanceof Casa casa){
            double totalPagar = noches * casa.getPrecioNoche() + casa.getCostoMantenimiento() + casa.getCostoAseo();
            realizarPago(cliente.getBilleteraVirtual(), totalPagar);

            return ReservaCasa.builder()
                    .alojamiento(alojamiento)
                    .cliente(cliente)
                    .fechaEntrada(fechaEntrada)
                    .fechaSalida(fechaSalida)
                    .numeroHuespedes(numeroHuespedes)
                    .build();

        }else if (alojamiento instanceof Apartamento apartamento){
            double totalPagar = noches * apartamento.getPrecioNoche() + apartamento.getCostoMantenimiento() + apartamento.getCostoAseo();
            realizarPago(cliente.getBilleteraVirtual(), totalPagar);
            return ReservaApartamento.builder()
                    .alojamiento(alojamiento)
                    .cliente(cliente)
                    .fechaEntrada(fechaEntrada)
                    .fechaSalida(fechaSalida)
                    .numeroHuespedes(numeroHuespedes)
                    .totalPagar(totalPagar)
                    .build();


        }

    throw new Exception("No se puede reservar");

    }

    public void realizarPago(BilleteraVirtual billeteraVirtual, double totaPagar) throws Exception{
        if (!billeteraVirtual.pagar(totaPagar)) {
            throw new Exception("Fondos insuficientes para realizar el pago.");
        }
    }

}
