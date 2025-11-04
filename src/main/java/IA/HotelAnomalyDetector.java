package IA;

import java.util.ArrayList;
import java.util.List;

import models.Reserva;
import services.CrudService;

public class HotelAnomalyDetector {

    public static void main(String[] args) {
        System.out.println("=== Detección de Anomalías en Reservas ===");

        CrudService crud = new CrudService();
        List<Reserva> reservas;

        try {
            reservas = crud.consultarIdReserva(1);
        } catch (Exception e) {
            System.out.println("No hay conexión a base de datos. Generando datos simulados...");
            reservas = generarReservasSimuladas();
        }

        if (reservas == null || reservas.isEmpty()) {
            reservas = generarReservasSimuladas();
        }


        double suma = 0;
        for (Reserva r : reservas) {
            suma += r.getValorReserva();
        }
        double promedio = suma / reservas.size();

        double sumaDesv = 0;
        for (Reserva r : reservas) {
            sumaDesv += Math.pow(r.getValorReserva() - promedio, 2);
        }
        double desviacion = Math.sqrt(sumaDesv / reservas.size());


        System.out.printf("Promedio de valor: %.2f | Desviación estándar: %.2f%n", promedio, desviacion);
        System.out.println("Reservas sospechosas detectadas:");
        for (Reserva r : reservas) {
            double valor = r.getValorReserva();
            if (valor > promedio + 2 * desviacion || valor < promedio - 2 * desviacion) {
                System.out.printf(" - Reserva ID %d con valor anómalo: %.2f%n", r.getIdReserva(), valor);
            }
        }

        System.out.println("=== Fin del análisis de anomalías ===");
    }

    private static List<Reserva> generarReservasSimuladas() {
        List<Reserva> lista = new ArrayList<>();
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(10), java.time.LocalDate.now().minusDays(7), 250000, "Tarjeta"));
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(5), java.time.LocalDate.now().minusDays(1), 480000, "Efectivo"));
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(3), java.time.LocalDate.now(), 900000, "Tarjeta")); // anómala
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(8), java.time.LocalDate.now().minusDays(4), 100000, "Efectivo")); // anómala
        return lista;
    }
}
