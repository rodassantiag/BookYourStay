package co.edu.uniquindio.bookyourstay.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Movimiento {
    private LocalDateTime fecha;
    private String descripcion;
    private double monto;

}
