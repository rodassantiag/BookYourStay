package co.edu.uniquindio.bookyourstay.modelo;

import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Habitacion {
    private int numero;
    private double precio;
    private int capacidad;
    private Image imagen;
    private String descripcion;
}
