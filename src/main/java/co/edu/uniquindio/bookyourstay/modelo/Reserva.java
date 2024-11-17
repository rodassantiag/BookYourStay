package co.edu.uniquindio.bookyourstay.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public abstract class Reserva {
    private Cliente cliente;
    private Alojamiento alojamiento;
    private LocalDate fechaEntrada, fechaSalida;
    private int numeroHuespedes;
    private double totalPagar;

}
