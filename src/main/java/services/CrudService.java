package services;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import models.Huesped;
import models.Reserva;
import persistencia.PersistenceManager;

public class CrudService {
	private PersistenceManager manager;
	
	public  void guardarDatos(Huesped huesped) {
		manager = new PersistenceManager();
		manager.insertarHuesped(huesped);
		manager.cerrar();
	}
	
	public Huesped consultarId(int id) {
		manager = new PersistenceManager();
		Huesped huesped = manager.getHuesped(id);
		return huesped;
	}
	
	public Huesped consultarHuespedPorReserva(int id) {
		manager = new PersistenceManager();
		Huesped huesped = manager.getReserva(id).getHuesped();
		return huesped;
	}
	
	
	public List<Reserva> consultarApellido(String apellido) {
		List<Reserva> reserva = new ArrayList<Reserva>();
		manager = new PersistenceManager();
		List<Huesped>consultado = manager.getHuespedApellido(apellido);
		 for(Huesped act: consultado) {
			 reserva.add(act.getReservas());
		 }
		manager.cerrar();
		return reserva;
	}
	
	public List<Reserva> consultarIdReserva(int id) {
		manager = new PersistenceManager();
		List<Reserva> reservaId = manager.getReservaId(id);
		manager.cerrar();
		return reservaId;
	}
	
	
	public void eliminar(int id, String opcion) {
		manager = new PersistenceManager();
		Huesped eliminado=null;
		if(opcion.equals("reserva")) {
			eliminado = manager.getReserva(id).getHuesped();
		}else if(opcion.equals("huesped")) {
			eliminado = manager.getHuesped(id);
		}
		manager.eliminarHuesped(eliminado);
		manager.cerrar();
	}
	
	public List<Huesped> consultarTodos(){
		List<Huesped> huesped = new ArrayList<>();
		manager = new PersistenceManager();
		huesped = manager.getTodos();
		manager.cerrar();
		return huesped;
	}
	
	public void actualizar(Huesped huesped) {
		manager = new PersistenceManager();
		manager.actualizarHuesped(huesped);
	    manager.cerrar();	
	    JOptionPane.showMessageDialog(null, "se ha actualizado con exito");
	}
	

}
