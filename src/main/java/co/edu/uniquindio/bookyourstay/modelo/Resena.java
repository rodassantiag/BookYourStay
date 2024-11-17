package co.edu.uniquindio.bookyourstay.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Resena {
    private int calificacion;
    private String comentario;

}
