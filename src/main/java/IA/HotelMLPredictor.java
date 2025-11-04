package IA;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import models.Reserva;
import services.CrudService;

public class HotelMLPredictor {

    public static void main(String[] args) {
        System.out.println("=== Predicción de Valor de Reserva con IA ===");

        CrudService crud = new CrudService();
        List<Reserva> reservas;

        try {
            reservas = crud.consultarIdReserva(1); 
        } catch (Exception e) {
            System.out.println("No se pudo acceder a la base de datos, usando datos simulados...");
            reservas = generarReservasSimuladas();
        }

        if (reservas.isEmpty()) {
            reservas = generarReservasSimuladas();
        }

        // Entrenar modelo lineal: valor = a * días + b
        double sumX = 0, sumY = 0, sumXY = 0, sumXX = 0;
        for (Reserva r : reservas) {
            double x = ChronoUnit.DAYS.between(r.getFechaEntrada(), r.getFechaSalida());
            double y = r.getValorReserva();
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumXX += x * x;
        }

        int n = reservas.size();
        double a = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        double b = (sumY - a * sumX) / n;

        System.out.println("Modelo entrenado: valor = " + a + " * días + " + b);

        // Prueba de predicción
        double diasEstadia = 4;
        double prediccion = a * diasEstadia + b;
        System.out.println("Predicción para una estadía de " + diasEstadia + " días: $" + prediccion);
    }

    private static List<Reserva> generarReservasSimuladas() {
        List<Reserva> lista = new ArrayList<>();
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(10), java.time.LocalDate.now().minusDays(7), 250000, "Tarjeta"));
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(5), java.time.LocalDate.now().minusDays(1), 480000, "Efectivo"));
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(3), java.time.LocalDate.now(), 320000, "Tarjeta"));
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(15), java.time.LocalDate.now().minusDays(10), 410000, "Efectivo"));
        return lista;
    }
}

