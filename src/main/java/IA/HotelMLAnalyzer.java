package IA;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import models.Reserva;
import services.CrudService;

public class HotelMLAnalyzer {

    public static void main(String[] args) {
        System.out.println("=== Análisis IA de Reservas ===");

        CrudService crud = new CrudService();

        List<Reserva> reservas = new ArrayList<>();

        
        try {
            reservas = crud.consultarIdReserva(1); 
        } catch (Exception e) {
            System.out.println("No se pudo acceder a la base de datos, usando prueba vacía.");
        }

        if (reservas == null || reservas.isEmpty()) {
            System.out.println("No hay reservas registradas. Se generarán datos simulados de prueba...");
            reservas = generarReservasSimuladas();
        }

     
        double totalDias = 0;
        double totalValor = 0;

        for (Reserva r : reservas) {
            long dias = ChronoUnit.DAYS.between(r.getFechaEntrada(), r.getFechaSalida());
            totalDias += dias;
            totalValor += r.getValorReserva();
        }

        double promedioDias = totalDias / reservas.size();
        double promedioValor = totalValor / reservas.size();

        System.out.println("Reservas analizadas: " + reservas.size());
        System.out.println("Promedio de días de estadía: " + promedioDias);
        System.out.println("Promedio de valor de reserva: $" + promedioValor);

      
        if (promedioValor > 300000) {
            System.out.println("Patrón detectado: huéspedes de alto gasto 💎");
        } else {
            System.out.println("Patrón detectado: huéspedes de gasto medio o bajo 🏨");
        }
    }

    private static List<Reserva> generarReservasSimuladas() {
        List<Reserva> lista = new ArrayList<>();
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(10), java.time.LocalDate.now().minusDays(7), 250000, "Tarjeta"));
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(5), java.time.LocalDate.now().minusDays(1), 480000, "Efectivo"));
        lista.add(new Reserva(java.time.LocalDate.now().minusDays(3), java.time.LocalDate.now(), 320000, "Tarjeta"));
        return lista;
    }
}
