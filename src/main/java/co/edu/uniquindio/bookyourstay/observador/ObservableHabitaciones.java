package co.edu.uniquindio.bookyourstay.observador;

import co.edu.uniquindio.bookyourstay.modelo.Habitacion;

import java.util.List;

public interface ObservableHabitaciones {
    void notificar(List<Habitacion> habitaciones);
}
