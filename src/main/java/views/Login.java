package views;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serial;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

public class Login extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;

    // Constantes
    private static final Font FONT_ROBOTO_18 = new Font("Roboto", Font.PLAIN, 18);
    private static final Font FONT_ROBOTO_BLACK_20 = new Font("Roboto Black", Font.PLAIN, 20);
    private static final Font FONT_ROBOTO_BLACK_26 = new Font("Roboto Black", Font.PLAIN, 26);
    private static final Color COLOR_PANEL_LATERAL = new Color(12, 138, 199);
    private static final Color COLOR_HOVER_BTN = new Color(0, 156, 223);
    private static final Color COLOR_SEPARATOR_1 = SystemColor.textHighlight;
    private static final Color COLOR_SEPARATOR_0 = new Color(0, 120, 215);

    private final JTextField txtUsuario;
    private final JPasswordField txtContrasena;
    private int xMouse;
    private int yMouse;
    private final JLabel labelExit;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación: " + e.getMessage());
            }
        });
    }

    public Login() {
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 788, 527);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 788, 527);
        panel.setBackground(Color.WHITE);
        contentPane.add(panel);
        panel.setLayout(null);

        // Panel lateral azul
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(COLOR_PANEL_LATERAL);
        panelLateral.setBounds(484, 0, 304, 527);
        panelLateral.setLayout(null);
        panel.add(panelLateral);

        JLabel imgHotel = new JLabel("");
        imgHotel.setBounds(0, 0, 304, 538);
        panelLateral.add(imgHotel);
        imgHotel.setIcon(new ImageIcon(Objects.requireNonNull(Login.class.getResource("/imagenes/img-hotel-login-.png"))));

        // Botón salir
        JPanel btnExit = new JPanel();
        btnExit.setBounds(251, 0, 53, 36);
        panelLateral.add(btnExit);
        btnExit.setBackground(COLOR_PANEL_LATERAL);
        btnExit.setLayout(null);
        btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        labelExit = new JLabel("X");
        labelExit.setBounds(0, 0, 53, 36);
        labelExit.setForeground(SystemColor.text);
        labelExit.setFont(FONT_ROBOTO_18);
        labelExit.setHorizontalAlignment(SwingConstants.CENTER);
        btnExit.add(labelExit);

        btnExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { System.exit(0); }
            @Override
            public void mouseEntered(MouseEvent e) {
                btnExit.setBackground(Color.red);
                labelExit.setForeground(Color.white);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnExit.setBackground(COLOR_PANEL_LATERAL);
                labelExit.setForeground(Color.white);
            }
        });

        // Campos de texto
        txtUsuario = createTextField(panel);
        txtContrasena = createPasswordField(panel);

        // Separadores
        panel.add(createSeparator(COLOR_SEPARATOR_0, 292));
        panel.add(createSeparator(COLOR_SEPARATOR_1, 393));

        // Labels
        JLabel labelTitulo = new JLabel("INICIAR SESIÓN");
        labelTitulo.setForeground(SystemColor.textHighlight);
        labelTitulo.setFont(FONT_ROBOTO_BLACK_26);
        labelTitulo.setBounds(65, 149, 202, 26);
        panel.add(labelTitulo);

        panel.add(createLabel("USUARIO", 219, 107));
        panel.add(createLabel("CONTRASEÑA", 316, 140));

        // Botón login
        JPanel btnLogin = new JPanel();
        btnLogin.setBackground(SystemColor.textHighlight);
        btnLogin.setBounds(65, 431, 122, 44);
        btnLogin.setLayout(null);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(btnLogin);

        JLabel labelEntrar = new JLabel("ENTRAR");
        labelEntrar.setBounds(0, 0, 122, 44);
        labelEntrar.setForeground(SystemColor.controlLtHighlight);
        labelEntrar.setHorizontalAlignment(SwingConstants.CENTER);
        labelEntrar.setFont(FONT_ROBOTO_18);
        btnLogin.add(labelEntrar);

        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { performLogin(); }
            @Override
            public void mouseEntered(MouseEvent e) { btnLogin.setBackground(COLOR_HOVER_BTN); }
            @Override
            public void mouseExited(MouseEvent e) { btnLogin.setBackground(SystemColor.textHighlight); }
        });

        // Header para mover ventana
        panel.add(createHeader());
    }

    // Métodos helper
    private @NotNull JTextField createTextField(JPanel parent) {
        JTextField field = new JTextField("");
        field.setForeground(Color.GRAY);
        field.setFont(FONT_ROBOTO_18);
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setBounds(65, 256, 324, 32);
        parent.add(field);
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { handleUsernamePlaceholder(); }
        });
        return field;
    }

    private @NotNull JPasswordField createPasswordField(JPanel parent) {
        JPasswordField field = new JPasswordField("");
        field.setForeground(Color.GRAY);
        field.setFont(FONT_ROBOTO_18);
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setBounds(65, 353, 324, 32);
        parent.add(field);
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { handlePasswordPlaceholder(); }
        });
        return field;
    }

    private @NotNull JSeparator createSeparator(Color color, int y) {
        JSeparator sep = new JSeparator();
        sep.setBackground(color);
        sep.setBounds(65, y, 324, 2);
        return sep;
    }

    private @NotNull JLabel createLabel(String text, int y, int width) {
        JLabel label = new JLabel(text);
        label.setBounds(65, y, width, 26);
        label.setFont(Login.FONT_ROBOTO_BLACK_20);
        label.setForeground(SystemColor.textInactiveText);
        return label;
    }

    private @NotNull JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(SystemColor.window);
        header.setBounds(0, 0, 784, 36);
        header.setLayout(null);
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xMouse = e.getX();
                yMouse = e.getY();
            }
        });
        header.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - xMouse, e.getYOnScreen() - yMouse);
            }
        });
        return header;
    }

    // Placeholder handlers
    private void handleUsernamePlaceholder() {
        if (txtUsuario.getText().equals("Ingrese su nombre")) {
            txtUsuario.setText("");
            txtUsuario.setForeground(Color.BLACK);
        }
        if (txtContrasena.getPassword().length == 0) {
            txtContrasena.setText("*******");
            txtContrasena.setForeground(Color.GRAY);
        }
    }

    private void handlePasswordPlaceholder() {
        if (String.valueOf(txtContrasena.getPassword()).equals("********")) {
            txtContrasena.setText("");
            txtContrasena.setForeground(Color.BLACK);
        }
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setText("Ingrese su nombre de usuari");
            txtUsuario.setForeground(Color.GRAY);
        }
    }

    // Cargar config
    private Properties loadConfig() throws IOException {
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream("C:/Secrets/config.properties")) {
            p.load(fis);
        }
        return p;
    }

    // Login seguro
    private void performLogin() {
        Properties config;
        try {
            config = loadConfig();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se pudo leer la configuración: " + e.getMessage());
            return;
        }

        String envUser = config.getProperty("app.user");
        String envPassHash = config.getProperty("app.pass.hash");
        char[] passwordChars = txtContrasena.getPassword();

        try {
            if (envUser == null || envPassHash == null) {
                JOptionPane.showMessageDialog(this, "Credenciales no configuradas.");
                return;
            }

            if (txtUsuario.getText().equals(envUser) &&
                    BCrypt.checkpw(new String(passwordChars), envPassHash)) {
                MenuUsuario menu = new MenuUsuario();
                menu.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o Contraseña no válidos");
            }
        } finally {
            Arrays.fill(passwordChars, '0'); // limpiar password de memoria
        }
    }
}
