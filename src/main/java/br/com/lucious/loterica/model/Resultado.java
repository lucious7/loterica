package br.com.lucious.loterica.model;

public class Resultado {

	private Jogo jogo;
	private Integer acertos;
	
	public Resultado(Jogo jogo){
		this.jogo = jogo;
	}
	
	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public Integer getAcertos() {
		return acertos;
	}

	public void setAcertos(Integer acertos) {
		this.acertos = acertos;
	}

}
