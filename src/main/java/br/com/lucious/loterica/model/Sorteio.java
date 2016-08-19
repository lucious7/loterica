package br.com.lucious.loterica.model;

import java.util.Vector;

public class Sorteio {

	private Integer id;
	private Integer numero;
	private Vector<Integer> dezenas;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Vector<Integer> getDezenas() {
		return dezenas;
	}

	public void setDezenas(Vector<Integer> dezenas) {
		this.dezenas = dezenas;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Sorteio other = (Sorteio) obj;
		return this.getId() == other.getId();
	}
}
