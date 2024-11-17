package co.edu.uniquindio.bookyourstay.factory;

import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
@SuperBuilder
@Getter
@Setter
public class Apartamento extends Alojamiento {
    private double costoAseo;
    private double costoMantenimiento;
    private double precioNoche;
}
