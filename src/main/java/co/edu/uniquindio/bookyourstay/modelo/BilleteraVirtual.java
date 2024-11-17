package co.edu.uniquindio.bookyourstay.modelo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BilleteraVirtual {
    private double saldo;
    private List<Movimiento> movimientos;


    public void recargar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        this.saldo += monto;
        movimientos.add(Movimiento.builder()
                        .fecha(LocalDateTime.now())
                        .descripcion("Recarga")
                        .monto(monto)
                .build());
    }

    public boolean pagar(double monto) {
        if (monto > saldo) {
            return false;
        }
        this.saldo -= monto;
        movimientos.add(Movimiento.builder()
                        .fecha(LocalDateTime.now())
                        .monto(monto)
                        .descripcion("Pago")
                .build());
        return true;
    }


}
