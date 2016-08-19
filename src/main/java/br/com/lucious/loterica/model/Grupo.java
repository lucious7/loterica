package br.com.lucious.loterica.model;

import java.time.LocalDate;
import java.time.Month;

public class Grupo {

	private Integer id;
	private Month mes;
	private Integer ano;
	private LocalDate dataInicio;
	private Integer sorteioInicial;
	private Integer sorteioFinal;

	public Grupo() {
	}

	public Grupo(Month mes, Integer ano, LocalDate dataInicio, Integer sorteioInicial, Integer sorteioFinal) {
		this.mes = mes;
		this.ano = ano;
		this.dataInicio = dataInicio;
		this.sorteioInicial = sorteioInicial;
		this.sorteioFinal = sorteioFinal;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}


	public Month getMes() {
		return mes;
	}

	public void setMes(Month mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Integer getSorteioInicial() {
		return sorteioInicial;
	}

	public void setSorteioInicial(Integer sorteioInicial) {
		this.sorteioInicial = sorteioInicial;
	}

	public Integer getSorteioFinal() {
		return sorteioFinal;
	}

	public void setSorteioFinal(Integer sorteioFinal) {
		this.sorteioFinal = sorteioFinal;
	}

	@Override
	public String toString() {
		return getMes() + "/" + getAno() + " (" + getSorteioInicial() + " - " + getSorteioFinal() + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		Grupo other = (Grupo) obj;
		return this.getId() == other.getId();
	}

}
