package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import models.Huesped;
import models.Reserva;
import services.CrudService;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	
	private CrudService consultar;
	private List<Reserva> reservaEncontrada;
	private Huesped owner;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.llenarTabla();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		tbReservas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        
		    }
		});
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				addRows();
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		lblEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int tabIndex = panel.getSelectedIndex(); // panel es el JTabbedPane que contiene las tablas
				if (tabIndex == 0) {
					editarHuesped(tbReservas);
				} else if (tabIndex == 1) {
					editarHuesped(tbHuespedes);
				 }
			}
			});
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eliminar();
				}
			});
	}
	
	private boolean verificarTexto() {
		String texto = txtBuscar.getText();
		Pattern patron = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).+$");
		Matcher matcher = patron.matcher(texto);
		if (matcher.matches()) {
			JOptionPane.showMessageDialog(null, "la busqueda contiene alfanumericos, por favor ingrese de nuevo");
			txtBuscar.setText(null);
			return false;
		} else {
		return true;
		}
	}
	
	private List<Reserva> buscar() {
		List<Reserva> reservaB= new ArrayList<>();
		consultar = new CrudService();
		if(verificarTexto()==true) {
			try {
			    int id = Integer.parseInt(txtBuscar.getText());
			    reservaB=consultar.consultarIdReserva(id);
			    JOptionPane.showMessageDialog(null, "consultó el id: "+id);
			} catch (NumberFormatException e) {
			    String apellido = txtBuscar.getText();
			    reservaB=consultar.consultarApellido(apellido);
			    JOptionPane.showMessageDialog(null, "consultó el apellido: "+apellido);
			}
			return reservaB;
		}else {
			return null;
		}
	}
	
	private void addRows() {
		modelo.setRowCount(0);
		modeloHuesped.setRowCount(0);
		reservaEncontrada = buscar();
		if(reservaEncontrada.isEmpty()) {
			JOptionPane.showMessageDialog(null, "no se encontraron coincidencias en la base de datos, intente de nuevo ");
		}
		for(Reserva encontrada: reservaEncontrada) {
		owner = encontrada.getHuesped();
		Object[] row1 = {encontrada.getIdReserva(),encontrada.getFechaEntrada(),encontrada.getFechaSalida(),encontrada.getValorReserva(),encontrada.getFormaDePago()};
		modelo.addRow(row1);
		Object[] huesped1 = {owner.getId(),owner.getNombre(),owner.getApellido(),owner.getFechaNacimiento(),owner.getNacionalidad(),owner.getTelefono(),owner.getReservas().getIdReserva()};
		modeloHuesped.addRow(huesped1);
		}
	}
	
	private int obtenerValorTb(JTable tabla, int column) {
		int selectedRow = tabla.getSelectedRow();
		if (selectedRow != -1) {
		    Object id = tabla.getValueAt(selectedRow, column);
		    int ide =(Integer)id;
		    return ide;
		} else {
		    return -1;
		}	
	}
	
	private void confirmar(JTable tabla,String opcion) {
		consultar = new CrudService();
		int resultado = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas continuar?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
		if (resultado == JOptionPane.OK_OPTION) {
			consultar.eliminar(obtenerValorTb(tabla,0),opcion);
			JOptionPane.showMessageDialog(null, "se ha eliminado con exito");
		} else {
			tbHuespedes.clearSelection();
			tbReservas.clearSelection();
		}
	}
	
	private void eliminar() {
		if(tbHuespedes.getSelectedRow()!=-1 && tbReservas.getSelectedRow()!=-1) {
			tbHuespedes.clearSelection();
			tbReservas.clearSelection();
			JOptionPane.showMessageDialog(null, "por favor seleccione un solo registro");
		}else if(tbHuespedes.getSelectedRow()==-1 && tbReservas.getSelectedRow()==-1) {
			JOptionPane.showMessageDialog(null, "por favor seleccione un registro para eliminar");
		}else if(tbHuespedes.getSelectedRow()!=-1 && tbReservas.getSelectedRow()==-1){
			tbReservas.clearSelection();
			confirmar(tbHuespedes,"huesped");
		}else if(tbHuespedes.getSelectedRow()==-1 && tbReservas.getSelectedRow()!=-1){
			tbHuespedes.clearSelection();
			confirmar(tbReservas,"reserva");
		}
		llenarTabla();
	}
	
	public void llenarTabla() {
		modelo.setRowCount(0);
		modeloHuesped.setRowCount(0);
		List<Huesped> huesped = new ArrayList<>();
		consultar = new CrudService();
		huesped = consultar.consultarTodos();
		for(Huesped nuevo: huesped) {
			
			Object[] row1 = {nuevo.getReservas().getIdReserva(),nuevo.getReservas().getFechaEntrada(),nuevo.getReservas().getFechaSalida(),nuevo.getReservas().getValorReserva(),nuevo.getReservas().getFormaDePago()};
			modelo.addRow(row1);
			Object[] huesped1 = {nuevo.getId(),nuevo.getNombre(),nuevo.getApellido(),nuevo.getFechaNacimiento(),nuevo.getNacionalidad(),nuevo.getTelefono(),nuevo.getReservas().getIdReserva()};
			modeloHuesped.addRow(huesped1);
			}
	}
	
	private void editarHuesped(JTable tabla) {
		Object respuesta = JOptionPane.showInputDialog(null, "Ingrese el nuevo dato:");
		if(respuesta != null) {
			editor(tabla,respuesta);
			llenarTabla();
	    } else {
			JOptionPane.showMessageDialog(null, "no se puede actualizar, por favor ingrese un dato");
		}
	}
	
	private void editor(JTable tabla, Object respuesta) {
		consultar = new CrudService();
		Huesped huesped;  
		int column = tabla.getSelectedColumn();
   		if(tabla==tbHuespedes) {
   			huesped = consultar.consultarId(obtenerValorTb(tabla, 0));
   			switch(column) {
   				case 0:
   					JOptionPane.showMessageDialog(null, "el id del huesped no es un campo editable, seleccione otro");
   					tbHuespedes.clearSelection();
   					break;
   				case 1:
   					String nombre =(String)respuesta;
   					huesped.setNombre(nombre);
   					consultar.actualizar(huesped);
					break;
				case 2:
					String apellido=(String)respuesta;
					huesped.setApellido(apellido);
					consultar.actualizar(huesped);
					break;
				case 3:
					String entrada =(String)respuesta;
   					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   					LocalDate fecha = LocalDate.parse(entrada, formatter);
					huesped.setFechaNacimiento(fecha);
					consultar.actualizar(huesped);
					break;
				case 4:
					String nacionalidad = (String)respuesta;
					huesped.setNacionalidad(nacionalidad);
					consultar.actualizar(huesped);
					break;
				case 5:
					String telefono = (String)respuesta;
					huesped.setTelefono(telefono);
					consultar.actualizar(huesped);
					break;
				case 6:
					JOptionPane.showMessageDialog(null, "el id de reserva no es un campo editable, seleccione otro");
   					tbHuespedes.clearSelection();
					break;
   				}
   			}else if(tabla==tbReservas) {
   				huesped = consultar.consultarHuespedPorReserva(obtenerValorTb(tabla, 0));
   				switch(column) {
   				case 0:
   					JOptionPane.showMessageDialog(null, "el id de reserva no es un campo editable, seleccione otro");
   					tbReservas.clearSelection();
   					break;
   				case 1:
   					String entrada =(String)respuesta;
   					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   					LocalDate fecha = LocalDate.parse(entrada, formatter);
   					huesped.getReservas().setFechaEntrada(fecha);
   					consultar.actualizar(huesped);
					break;
				case 2:
					String salida =(String)respuesta;
   					DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   					LocalDate fechasalida = LocalDate.parse(salida, formato);
					huesped.getReservas().setFechaSalida(fechasalida);
					consultar.actualizar(huesped);
					break;
				case 3:
					int valor =Integer.parseInt((String)respuesta);
					huesped.getReservas().setValorReserva(valor);
					consultar.actualizar(huesped);
					break;
				case 4:
					String formaPago = (String)respuesta;
					huesped.getReservas().setFormaDePago(formaPago);
					consultar.actualizar(huesped);
					break;
   			       }
   				}
	}
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
