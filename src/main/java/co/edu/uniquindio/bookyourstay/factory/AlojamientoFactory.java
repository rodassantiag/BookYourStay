package co.edu.uniquindio.bookyourstay.factory;

import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import co.edu.uniquindio.bookyourstay.modelo.Habitacion;
import co.edu.uniquindio.bookyourstay.modelo.enums.Ciudad;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlojamientoFactory {

    public Alojamiento crearAlojamiento(TipoAlojamiento tipoAlojamiento, Ciudad ciudad, String nombre,
                                        String descripcion, double precioNoche, int capacidad, double costoAseo,
                                        double contoMantenimiento, Image image) throws Exception{

        if (ciudad == null){
            throw new Exception("Elija una ciudad");
        }

        if (nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }

        if (descripcion.isBlank()){
            throw new Exception("La descripción es obligatoria");
        }

        if (precioNoche <= 0){
            throw new Exception("El precio de la noche debe ser mayor a 0");
        }

        if (capacidad <= 0){
            throw new Exception("La capacidad debe ser mayor a 0");
        }

        if (costoAseo <= 0){
            throw new Exception("El costo del aseo debe ser mayor a 0");
        }

        if (contoMantenimiento <= 0){
            throw new Exception("El costo de mantenimiento debe ser mayor a 0");
        }

        if (image == null){
            throw new Exception("La imagen es obligatoria");
        }

        if(tipoAlojamiento == TipoAlojamiento.APARTAMENTO){
            return Apartamento.builder()
                    .ciudad(ciudad)
                    .nombre(nombre)
                    .descripcion(descripcion)
                    .precioNoche(precioNoche)
                    .capacidad(capacidad)
                    .costoAseo(costoAseo)
                    .costoMantenimiento(contoMantenimiento)
                    .imagen(image)
                    .id(generarId())
                    .resenas(new ArrayList<>())
                    .build();
        }else if(tipoAlojamiento == TipoAlojamiento.CASA){
            return Casa.builder()
                    .ciudad(ciudad)
                    .nombre(nombre)
                    .descripcion(descripcion)
                    .precioNoche(precioNoche)
                    .capacidad(capacidad)
                    .costoAseo(costoAseo)
                    .costoMantenimiento(contoMantenimiento)
                    .imagen(image)
                    .id(generarId())
                    .resenas(new ArrayList<>())
                    .build();
        }

        throw new Exception("No se puede crear otro alojamiento");
    }


    public Alojamiento crearHotel(List<Habitacion> habitaciones, Ciudad ciudad, String nombre,
                                  String descripcion, int capacidad, Image image ) throws Exception{

        if (habitaciones.isEmpty()){
            throw new Exception("No hay habitaciones creadas");
        }

        if (ciudad == null){
            throw new Exception("Elija una ciudad");
        }

        if (nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }

        if (descripcion.isBlank()){
            throw new Exception("La descripción es obligatoria");
        }


        if (capacidad <= 0){
            throw new Exception("La capacidad debe ser mayor a 0");
        }

        if (image == null){
            throw new Exception("La imagen es obligatoria");
        }


        return Hotel.builder()
                .habitaciones(habitaciones)
                .ciudad(ciudad)
                .nombre(nombre)
                .descripcion(descripcion)
                .capacidad(capacidad)
                .imagen(image)
                .id(generarId())
                .resenas(new ArrayList<>())
                .build();
    }

    public String generarId() {
        StringBuilder id = new StringBuilder();


        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int numero = random.nextInt(8);
            id.append(numero);
        }

        return id.toString();
    }

}
