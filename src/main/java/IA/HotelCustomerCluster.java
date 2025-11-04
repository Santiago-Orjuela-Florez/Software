package IA;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import models.Reserva;
import services.CrudService;

public class HotelCustomerCluster {

    public static void main(String[] args) {
        System.out.println("=== Agrupamiento de Huéspedes por Comportamiento ===");

        CrudService crud = new CrudService();
        List<Reserva> reservas;

        try {
            // Intenta obtener datos reales de la base de datos
            reservas = crud.consultarIdReserva(1);
        } catch (Exception e) {
            System.out.println("No hay conexión a base de datos. Usando datos simulados...");
            reservas = generarReservasSimuladas();
        }

        // Si no hay datos reales, usar datos simulados
        if (reservas == null || reservas.isEmpty()) {
            reservas = generarReservasSimuladas();
        }

        // Calcular la duración promedio de estadía
        double totalDias = 0;
        for (Reserva r : reservas) {
            totalDias += ChronoUnit.DAYS.between(r.getFechaEntrada(), r.getFechaSalida());
        }
        double promedio = totalDias / reservas.size();

        // Crear listas de agrupamiento (clustering)
        List<Reserva> clientesCortos = new ArrayList<>();
        List<Reserva> clientesLargos = new ArrayList<>();

        for (Reserva r : reservas) {
            long dias = ChronoUnit.DAYS.between(r.getFechaEntrada(), r.getFechaSalida());
            if (dias >= promedio) {
                clientesLargos.add(r);
            } else {
                clientesCortos.add(r);
            }
        }

        // Mostrar resultados
        System.out.println("Duración promedio: " + promedio + " días");

        System.out.println("\nClientes de estadía larga (" + clientesLargos.size() + "):");
        for (Reserva r : clientesLargos) {
            System.out.printf(" - ID %d: %d días, $%.2f%n",
                    r.getIdReserva(),
                    ChronoUnit.DAYS.between(r.getFechaEntrada(), r.getFechaSalida()),
                    r.getValorReserva());
        }

        System.out.println("\nClientes de estadía corta (" + clientesCortos.size() + "):");
        for (Reserva r : clientesCortos) {
            System.out.printf(" - ID %d: %d días, $%.2f%n",
                    r.getIdReserva(),
                    ChronoUnit.DAYS.between(r.getFechaEntrada(), r.getFechaSalida()),
                    r.getValorReserva());
        }

        System.out.println("\n=== Fin del agrupamiento ===");
    }

    /**
     * Genera una lista de reservas de ejemplo para simular el comportamiento
     * del sistema cuando no hay conexión a la base de datos.
     */
    private static List<Reserva> generarReservasSimuladas() {
        List<Reserva> lista = new ArrayList<>();
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(10), java.time.LocalDate.now().minusDays(7), 250000, "Tarjeta"));
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(6), java.time.LocalDate.now().minusDays(1), 500000, "Efectivo"));
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(3), java.time.LocalDate.now(), 300000, "Tarjeta"));
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(15), java.time.LocalDate.now().minusDays(8), 700000, "Efectivo"));
        return lista;
    }
}
