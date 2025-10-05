package TestUnitarios;

import models.Reserva;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestReserveUnit {

    @Test
    void testToStringReserva() {
        // Arrange: crear un objeto Reserva de prueba
        Reserva reserva = new Reserva();
        reserva.setIdReserva(2422);
        reserva.setFechaEntrada(LocalDate.parse("2025-10-01"));
        reserva.setFechaSalida(LocalDate.parse("2025-10-05"));
        reserva.setValorReserva(350000.5);
        reserva.setFormaDePago("Tarjeta");

        // Act: obtener la representación en texto
        String result = reserva.toString();

        // Assert: validar que contiene la información esperada
        assertTrue(result.contains("id=2422"));
        assertTrue(result.contains("fechaEntrada=2025-10-01"));
        assertTrue(result.contains("fechaSalida=2025-10-05"));
        assertTrue(result.contains("valorReserva=350000.5"));
        assertTrue(result.contains("formaDePago=Tarjeta"));

        System.out.printf("%nTest 1 Exitoso");
    }

    @Test
    void testToStringExacto() {
        // Arrange
        Reserva reserva = new Reserva();
        reserva.setIdReserva(1001);
        reserva.setFechaEntrada(LocalDate.parse("2025-12-01"));
        reserva.setFechaSalida(LocalDate.parse("2025-12-10"));
        reserva.setValorReserva(500000.2);
        reserva.setFormaDePago("Efectivo");

        // Act
        String result = reserva.toString();

        // Assert: comparación exacta (puede romperse si cambias el formato del toString)
        String esperado = "Reserva [id=1001, fechaEntrada=2025-12-01, fechaSalida=2025-12-10, valorReserva=500000.2, formaDePago=Efectivo]";
        assertEquals(esperado, result);
        System.out.printf("%nTest 2 Exitoso");
    }
}
