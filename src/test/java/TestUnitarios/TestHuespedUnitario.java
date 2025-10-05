package TestUnitarios;

import models.Huesped;
import models.Reserva;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestHuespedUnit {


    @Test
    void testToStringHuespedConReserva() {
        // Arrange
        Huesped huesped = new Huesped(
                "Ana",
                "Pérez",
                "Mexicana",
                LocalDate.of(1999, 1, 1),
                "555999888"
        );

        Reserva reserva = new Reserva();
        reserva.setIdReserva(8888);
        reserva.setFechaEntrada(LocalDate.parse("2025-12-01"));
        reserva.setFechaSalida(LocalDate.parse("2025-12-10"));
        reserva.setValorReserva(500000.0F);
        reserva.setFormaDePago("Efectivo");

        huesped.setReservas(reserva);

        // Act
        String result = huesped.toString();
        System.out.println(result);
    }

    @Test
    void testToStringHuespedSinReserva() {
        // Arrange
        Huesped huesped = new Huesped(
                "Santiago",
                "Orjuela",
                "Colombiana",
                LocalDate.of(2006, 5, 20),
                "3001234567"
        );

        // Act
        String result = huesped.toString();

        // Assert: validamos los campos
        assertTrue(result.contains("nombre=Santiago"));
        assertTrue(result.contains("apellido=Orjuela"));
        assertTrue(result.contains("nacionalidad=Colombiana"));
        assertTrue(result.contains("fechaNacimiento=2006-05-20"));
        assertTrue(result.contains("telefono=3001234567"));
        assertTrue(result.contains("reservas=null")); // aún no asignamos reserva

        // Mensaje final
        System.out.println("Test Reserva Huesped exitoso");
    }

}