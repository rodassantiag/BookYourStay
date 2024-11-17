package co.edu.uniquindio.bookyourstay.utils;

import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Sesion {


    private Cliente cliente;
    private static Sesion INSTANCIA;

    private Sesion() {
    }

    public static Sesion getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Sesion();
        }
        return INSTANCIA;
    }

    public void asignarCliente(Cliente cliente) {
        INSTANCIA.setCliente(cliente);
    }

    public Cliente obtenerCliente() {
        return INSTANCIA.getCliente();
    }
}