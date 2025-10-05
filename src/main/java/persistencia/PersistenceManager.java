package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import models.Huesped;
import models.Reserva;

public class PersistenceManager {
	
	private EntityManagerFactory fabrica;
	private EntityManager em;
	
	public PersistenceManager() {
		this.fabrica = Persistence.createEntityManagerFactory("hotelDb");
		this.em = fabrica.createEntityManager();	
	}
	
	
	public Reserva getReserva(int id) {
		Reserva reserva = em.find(Reserva.class, id);
		return reserva;
	}
	
	public Huesped getHuesped(int id) {
		Huesped huesped = em.find(Huesped.class, id);
		return huesped;
	}
	
	public List<Huesped> getHuespedApellido(String apellido) {
		String query = "SELECT h FROM Huesped h WHERE h.apellido = :apellido";
		@SuppressWarnings("unchecked")
		List<Huesped> huesped = (List<Huesped>) em.createQuery(query).setParameter("apellido", apellido).getResultList();
		
		return huesped;
	}
	public List<Huesped> getTodos() {
		String query = "SELECT h FROM Huesped h";
		@SuppressWarnings("unchecked")
		List<Huesped> huesped = (List<Huesped>) em.createQuery(query).getResultList();
		
		return huesped;
	}
	

	
	public List<Reserva> getReservaId(int idReserva) {
		String query = "SELECT r FROM Reserva r WHERE r.idReserva = :idres";
		@SuppressWarnings("unchecked")
		List<Reserva> huesped = (List<Reserva>) em.createQuery(query).setParameter("idres", idReserva).getResultList();
		
		return huesped;
	}
	
	public boolean insertarHuesped(Huesped huesped) {
		this.em = fabrica.createEntityManager();
		EntityTransaction transaccion = em.getTransaction();//aqui obtenemos una nueva transaccion para manterner las operaciones unificadas
		transaccion.begin();
		em.persist(huesped);//el metodo persist crea un nuevo registro en la bd
		try {
			transaccion.commit();//el metodo commit indica al bdms que haga permanente este registro dentro de la db
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean actualizarHuesped(Huesped huesped) {
		this.em = fabrica.createEntityManager();
		EntityTransaction transaccion = em.getTransaction();//
		transaccion.begin();
		try{
			Huesped merge = em.merge(huesped);//el metodo merge unifica el registro que le envio con uno exisistente en la bd
			//de manera que ya no hará una insersion sino un update
			em.persist(merge);//al enviarle el objeto merge, este ya ha sido reconocido por la db lo que le permite saber que se hará un update y no un insert
			transaccion.commit();
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean eliminarHuesped(Huesped huesped) {
		this.em = fabrica.createEntityManager();
		EntityTransaction transaccion = em.getTransaction();//
		transaccion.begin();
		try{
			Huesped merge = em.merge(huesped);//el metodo merge unifica el registro que le envio con uno exisistente en la bd
			//de manera que ya no hará una insersion sino un update
			em.remove(merge);//al enviarle el objeto merge, este ya ha sido reconocido por la db lo que le permite saber cual registro eliminar
			transaccion.commit();
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public void cerrar() {
		em.close();
		fabrica.close();
	}

}
