package persistenciaTest;

import models.*;
import persistencia.PersistenceManager;

public class TestReserva {

    public static void main(String[] args) {
        PersistenceManager manager = new PersistenceManager();
        Reserva reserva = manager.getReserva(2422);

        if (reserva != null) {
            System.out.println(reserva);
            System.out.println("Reserva encontrada.");
        } else {
            System.out.println("No se encontró ninguna reserva con ID 1 en la base de datos.");
            System.out.println("Por favor, verifica que existan registros en la tabla 'reserva'.");
        }
    }
}