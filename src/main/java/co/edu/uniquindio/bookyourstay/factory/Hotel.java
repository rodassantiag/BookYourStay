package co.edu.uniquindio.bookyourstay.factory;

import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import co.edu.uniquindio.bookyourstay.modelo.Habitacion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
@ToString
@SuperBuilder
@Getter
@Setter
public class Hotel extends Alojamiento {

    private List<Habitacion> habitaciones;
}
