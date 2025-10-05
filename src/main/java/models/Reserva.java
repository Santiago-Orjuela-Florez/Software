package models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="reserva")
public class Reserva {
	
	@Id
	private int idReserva;
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	private double valorReserva;
	private String formaDePago;
	@OneToOne
	@JoinColumn(name = "huesped_id")
	private Huesped reservo;
	
	public Reserva() {}

	public Reserva(LocalDate fechaEntrada, LocalDate fechaSalida, double valorReserva, String formaDePago) {
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valorReserva = valorReserva;
		this.formaDePago = formaDePago;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + idReserva + ", fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida
				+ ", valorReserva=" + valorReserva + ", formaDePago=" + formaDePago + "]";
	}
	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	

	public LocalDate getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDate fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public LocalDate getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(LocalDate fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public double getValorReserva() {
		return valorReserva;
	}

	public void setValorReserva(double valorReserva) {
		this.valorReserva = valorReserva;
	}

	public String getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}

	public Huesped getHuesped() {
		return this.reservo;
	}
	public void setHuesped(Huesped huesped) {
		this.reservo=huesped;
	}
}
