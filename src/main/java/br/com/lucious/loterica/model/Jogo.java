package br.com.lucious.loterica.model;

import java.util.Vector;

public class Jogo {
	
	public static final Integer PRECO_JOGO = 24;

	private Integer id;
	private Grupo grupo;
	private String nomeJogador;
	private Vector<Integer> dezenas;

	public Jogo() {
	}

	public Jogo(Grupo grupo, String nomeJogador, Vector<Integer> dezenas) {
		this.grupo = grupo;
		this.nomeJogador = nomeJogador;
		this.dezenas = dezenas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
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
		Jogo other = (Jogo) obj;
		return this.getId() == other.getId();
	}

}
