package persistenciaTest;

import models.Huesped;
import persistencia.PersistenceManager;

public class TestHuesped {

    public static void main(String[] args) {
        PersistenceManager manager = new PersistenceManager();
        Huesped huesped = manager.getHuesped(1);

        if (huesped != null) {
            System.out.println(huesped);
            System.out.println("Reserva encontrada.");
        } else {
            System.out.println("No se encontró ninguna reserva con ID 1 en la base de datos.");
            System.out.println("Por favor, verifica que existan registros en la tabla 'reserva'.");
        }
    }
}