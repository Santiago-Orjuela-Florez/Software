package views;

import com.toedter.calendar.JDateChooser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservasViewWhiteBoxTest {

    private ReservasView vista;

    @BeforeEach
    void setUp() {
        vista = new ReservasView();
        if (ReservasView.txtFechaEntrada == null) {
            ReservasView.txtFechaEntrada = new JDateChooser();
        }
        if (ReservasView.txtFechaSalida == null) {
            ReservasView.txtFechaSalida = new JDateChooser();
        }
    }

    private Date date(int y, int m, int d) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(y, m - 1, d, 0, 0, 0);
        return c.getTime();
    }

    @Test
    void cajaBlanca_1_calculoDiasPositivos() {
        ReservasView.txtFechaEntrada.setDate(date(2025, 10, 1));
        ReservasView.txtFechaSalida.setDate(date(2025, 10, 4));
        int valor = vista.valorReserva();
        assertEquals(60, valor); // 3 días * 20
    }

    @Test
    void cajaBlanca_2_mismaFecha_diasIgualCero() {
        ReservasView.txtFechaEntrada.setDate(date(2025, 10, 1));
        ReservasView.txtFechaSalida.setDate(date(2025, 10, 1));
        int valor = vista.valorReserva();
        assertEquals(0, valor);
    }

    @Test
    void cajaBlanca_3_salidaAntesDeEntrada_diasNegativos() {
        ReservasView.txtFechaEntrada.setDate(date(2025, 10, 5));
        ReservasView.txtFechaSalida.setDate(date(2025, 10, 3));
        int valor = vista.valorReserva();
        assertEquals(-40, valor); // (-2) * 20
    }

    @Test
    void cajaBlanca_4_nulos_lanzaExcepcion() {
        ReservasView.txtFechaEntrada.setDate(null);
        ReservasView.txtFechaSalida.setDate(null);
        assertThrows(NullPointerException.class, () -> vista.valorReserva());
    }
}
