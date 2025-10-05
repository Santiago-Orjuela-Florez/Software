package views;

import javax.swing.*;
import java.awt.*;

public class VentanaBase extends JFrame{
    public VentanaBase(String titulo) {
        super(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(910, 537);          // Tamaño fijo de tu ventana
        setLocationRelativeTo(null); // Centrada en pantalla
        setResizable(true);          // Permite maximizar
    }
}
