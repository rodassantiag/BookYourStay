package co.edu.uniquindio.bookyourstay.modelo;

import co.edu.uniquindio.bookyourstay.modelo.enums.Ciudad;
import javafx.scene.image.Image;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Alojamiento {
    private String id;
    private Ciudad ciudad;
    private String nombre, descripcion;
    private int capacidad;
    private Image imagen;
    private List<Resena> resenas;


}
