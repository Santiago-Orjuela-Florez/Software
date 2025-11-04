package views;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serial;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

public class Login extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Font FONT_ROBOTO_18 = new Font("Roboto", Font.PLAIN, 18);

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
        JPanel Panel_1 = new JPanel();
        Panel_1.setBackground(new Color(12, 138, 199));
        Panel_1.setBounds(484, 0, 304, 527);
        panel.add(Panel_1);
        Panel_1.setLayout(null);

        JLabel imgHotel = new JLabel("");
        imgHotel.setBounds(0, 0, 304, 538);
        Panel_1.add(imgHotel);
        imgHotel.setIcon(new ImageIcon(Objects.requireNonNull(Login.class.getResource("/imagenes/img-hotel-login-.png"))));

        // Botón salir
        JPanel btnExit = new JPanel();
        btnExit.setBounds(251, 0, 53, 36);
        Panel_1.add(btnExit);
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
                btnExit.setBackground(new Color(12, 138, 199));
                labelExit.setForeground(Color.white);
            }
        });
        btnExit.setBackground(new Color(12, 138, 199));
        btnExit.setLayout(null);
        btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        labelExit = new JLabel("X");
        labelExit.setBounds(0, 0, 53, 36);
        btnExit.add(labelExit);
        labelExit.setForeground(SystemColor.text);
        labelExit.setFont(FONT_ROBOTO_18);
        labelExit.setHorizontalAlignment(SwingConstants.CENTER);

        // Campos de texto
        txtUsuario = new JTextField("");
        txtUsuario.setForeground(Color.GRAY);
        txtUsuario.setFont(FONT_ROBOTO_18);
        txtUsuario.setBorder(BorderFactory.createEmptyBorder());
        txtUsuario.setBounds(65, 256, 324, 32);
        panel.add(txtUsuario);
        txtUsuario.setColumns(10);
        txtUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleUsernamePlaceholder();
            }
        });

        txtContrasena = new JPasswordField("");
        txtContrasena.setForeground(Color.GRAY);
        txtContrasena.setFont(new Font("Roboto", Font.PLAIN, 16));
        txtContrasena.setBorder(BorderFactory.createEmptyBorder());
        txtContrasena.setBounds(65, 353, 324, 32);
        panel.add(txtContrasena);
        txtContrasena.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlePasswordPlaceholder();
            }
        });

        // Separadores
        JSeparator separator = new JSeparator();
        separator.setBackground(new Color(0, 120, 215));
        separator.setBounds(65, 292, 324, 2);
        panel.add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBackground(SystemColor.textHighlight);
        separator_1.setBounds(65, 393, 324, 2);
        panel.add(separator_1);

        // Labels
        JLabel labelTitulo = new JLabel("INICIAR SESIÓN");
        labelTitulo.setForeground(SystemColor.textHighlight);
        labelTitulo.setFont(new Font("Roboto Black", Font.PLAIN, 26));
        labelTitulo.setBounds(65, 149, 202, 26);
        panel.add(labelTitulo);

        JLabel labelUsuario = new JLabel("USUARIO");
        labelUsuario.setForeground(SystemColor.textInactiveText);
        labelUsuario.setFont(new Font("Roboto Black", Font.PLAIN, 20));
        labelUsuario.setBounds(65, 219, 107, 26);
        panel.add(labelUsuario);

        JLabel labelContrasena = new JLabel("CONTRASEÑA");
        labelContrasena.setForeground(SystemColor.textInactiveText);
        labelContrasena.setFont(new Font("Roboto Black", Font.PLAIN, 20));
        labelContrasena.setBounds(65, 316, 140, 26);
        panel.add(labelContrasena);

        // Botón login
        JPanel btnLogin = new JPanel();
        btnLogin.setBackground(SystemColor.textHighlight);
        btnLogin.setBounds(65, 431, 122, 44);
        btnLogin.setLayout(null);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                performLogin();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(0, 156, 223));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(SystemColor.textHighlight);
            }
        });
        panel.add(btnLogin);

        JLabel lblEntrar = new JLabel("ENTRAR");
        lblEntrar.setBounds(0, 0, 122, 44);
        lblEntrar.setForeground(SystemColor.controlLtHighlight);
        lblEntrar.setHorizontalAlignment(SwingConstants.CENTER);
        lblEntrar.setFont(new Font("Roboto", Font.PLAIN, 18));
        btnLogin.add(lblEntrar);

        // Header para mover ventana
        JPanel header = getPanel();
        panel.add(header);
    }

    private @NotNull JPanel getPanel() {
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
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - xMouse, y - yMouse);
            }
        });
        return header;
    }

    // Manejo de placeholders
    private void handleUsernamePlaceholder() {
        if (txtUsuario.getText().equals("Ingrese su nombre de usuario")) {
            txtUsuario.setText("");
            txtUsuario.setForeground(Color.BLACK);
        }
        if (txtContrasena.getPassword().length == 0) {
            txtContrasena.setText("********");
            txtContrasena.setForeground(Color.GRAY);
        }
    }

    private void handlePasswordPlaceholder() {
        if (String.valueOf(txtContrasena.getPassword()).equals("********")) {
            txtContrasena.setText("");
            txtContrasena.setForeground(Color.BLACK);
        }
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setText("Ingrese su nombre de usuario");
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

            if (txtUsuario.getText().equals(envUser) && BCrypt.checkpw(new String(passwordChars), envPassHash)) {
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
