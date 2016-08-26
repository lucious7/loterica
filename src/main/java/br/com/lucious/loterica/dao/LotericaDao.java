package br.com.lucious.loterica.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Month;
import java.util.Vector;

import br.com.lucious.loterica.db.ConnectionManager;
import br.com.lucious.loterica.model.Grupo;
import br.com.lucious.loterica.model.Jogo;
import br.com.lucious.loterica.model.Sorteio;

public class LotericaDao {

	////////////////////////
	// Operações em Jogos //
	////////////////////////
	public void adicionarJogo(Jogo jogo) {
		try {
			PreparedStatement incluirQuery = ConnectionManager.getConnection().prepareStatement(
					"INSERT INTO JOGO (grupo_id, nome_jogador, dezena01, dezena02, dezena03, dezena04, dezena05, "
							+ "dezena06, dezena07, dezena08, dezena09, dezena10) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
			incluirQuery.setInt(1, jogo.getGrupo().getId());
			incluirQuery.setString(2, jogo.getNomeJogador());
			incluirQuery.setInt(3, jogo.getDezenas().get(0));
			incluirQuery.setInt(4, jogo.getDezenas().get(1));
			incluirQuery.setInt(5, jogo.getDezenas().get(2));
			incluirQuery.setInt(6, jogo.getDezenas().get(3));
			incluirQuery.setInt(7, jogo.getDezenas().get(4));
			incluirQuery.setInt(8, jogo.getDezenas().get(5));
			incluirQuery.setInt(9, jogo.getDezenas().get(6));
			incluirQuery.setInt(10, jogo.getDezenas().get(7));
			incluirQuery.setInt(11, jogo.getDezenas().get(8));
			incluirQuery.setInt(12, jogo.getDezenas().get(9));

			incluirQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void alterarJogo(Jogo jogo) {
		try {
			PreparedStatement atualizarQuery = ConnectionManager.getConnection().prepareStatement(
					"UPDATE JOGO SET nome_jogador = ?, dezena01 = ?, dezena02 = ?, dezena03 = ?, dezena04 = ?, dezena05 = ?, "
							+ "dezena06 = ?, dezena07 = ?, dezena08 = ?, dezena09 = ?, dezena10 = ? WHERE id = ?");
			atualizarQuery.setString(1, jogo.getNomeJogador());
			atualizarQuery.setInt(2, jogo.getDezenas().get(0));
			atualizarQuery.setInt(3, jogo.getDezenas().get(1));
			atualizarQuery.setInt(4, jogo.getDezenas().get(2));
			atualizarQuery.setInt(5, jogo.getDezenas().get(3));
			atualizarQuery.setInt(6, jogo.getDezenas().get(4));
			atualizarQuery.setInt(7, jogo.getDezenas().get(5));
			atualizarQuery.setInt(8, jogo.getDezenas().get(6));
			atualizarQuery.setInt(9, jogo.getDezenas().get(7));
			atualizarQuery.setInt(10, jogo.getDezenas().get(8));
			atualizarQuery.setInt(11, jogo.getDezenas().get(9));
			atualizarQuery.setInt(12, jogo.getId());

			atualizarQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<Jogo> listarJogosPorGrupo(int grupoId) {
		Vector<Jogo> jogos = new Vector<Jogo>();
		try {
			PreparedStatement listaQuery = ConnectionManager.getConnection().prepareStatement(
					"SELECT id, grupo_id, nome_jogador, dezena01, dezena02, dezena03, dezena04, dezena05, "
							+ "dezena06, dezena07, dezena08, dezena09, dezena10 FROM JOGO WHERE grupo_id = ? "
							+ "ORDER BY nome_jogador");

			listaQuery.setInt(1, grupoId);
			listaQuery.execute();

			ResultSet rs = listaQuery.getResultSet();
			while (rs.next()) {
				Jogo jogo = new Jogo();
				jogo.setId(rs.getInt("id"));
				Grupo grupo = new Grupo();
				grupo.setId(rs.getInt("grupo_id"));
				jogo.setGrupo(grupo);
				jogo.setNomeJogador(rs.getString("nome_jogador"));
				Vector<Integer> dezenas = new Vector<Integer>();
				dezenas.add(rs.getInt("dezena01"));
				dezenas.add(rs.getInt("dezena02"));
				dezenas.add(rs.getInt("dezena03"));
				dezenas.add(rs.getInt("dezena04"));
				dezenas.add(rs.getInt("dezena05"));
				dezenas.add(rs.getInt("dezena06"));
				dezenas.add(rs.getInt("dezena07"));
				dezenas.add(rs.getInt("dezena08"));
				dezenas.add(rs.getInt("dezena09"));
				dezenas.add(rs.getInt("dezena10"));
				jogo.setDezenas(dezenas);

				jogos.add(jogo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jogos;
	}

	public void removerJogo(int id) {
		try {
			PreparedStatement deletarQuery = ConnectionManager.getConnection()
					.prepareStatement("DELETE FROM JOGO WHERE id = ?");
			deletarQuery.setInt(1, id);
			deletarQuery.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/////////////////////////
	// Operações em Grupos //
	/////////////////////////
	public void adicionarGrupo(Grupo grupo) {
		try {
			PreparedStatement incluirQuery = ConnectionManager.getConnection().prepareStatement(
					"INSERT INTO GRUPO (mes, ano, dtInicio, sorteioInicial, sorteioFinal) VALUES (?, ?, ?, ?, ?) ");
			incluirQuery.setInt(1, grupo.getMes().getValue());
			incluirQuery.setInt(2, grupo.getAno());
			incluirQuery.setDate(3, Date.valueOf(grupo.getDataInicio()));
			incluirQuery.setInt(4, grupo.getSorteioInicial());
			incluirQuery.setInt(5, grupo.getSorteioFinal());

			incluirQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void alterarGrupo(Grupo grupo) {
		try {
			PreparedStatement atualizarQuery = ConnectionManager.getConnection().prepareStatement(
					"UPDATE GRUPO SET mes=?, ano=?, dtInicio=?, sorteioInicial=?, sorteioFinal=? WHERE id = ? ");
			atualizarQuery.setInt(1, grupo.getMes().getValue());
			atualizarQuery.setInt(2, grupo.getAno());
			atualizarQuery.setDate(3, Date.valueOf(grupo.getDataInicio()));
			atualizarQuery.setInt(4, grupo.getSorteioInicial());
			atualizarQuery.setInt(5, grupo.getSorteioFinal());
			atualizarQuery.setInt(6, grupo.getId());

			atualizarQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<Grupo> listarGrupos() {
		Vector<Grupo> grupos = new Vector<Grupo>();
		try {
			Statement listaQuery = ConnectionManager.getConnection().createStatement();
			listaQuery.execute("SELECT id, mes, ano, dtInicio, sorteioInicial, sorteioFinal FROM GRUPO");
			ResultSet rs = listaQuery.getResultSet();
			while (rs.next()) {
				Grupo grupo = new Grupo();
				grupo.setId(rs.getInt("id"));
				grupo.setMes(Month.of(rs.getInt("mes")));
				grupo.setAno(rs.getInt("ano"));
				grupo.setDataInicio(rs.getDate("dtInicio").toLocalDate());
				grupo.setSorteioInicial(rs.getInt("sorteioInicial"));
				grupo.setSorteioFinal(rs.getInt("sorteioFinal"));

				grupos.add(grupo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return grupos;
	}

	public void removerGrupo(int id) {
		try {
			PreparedStatement deletarQuery = ConnectionManager.getConnection()
					.prepareStatement("DELETE FROM GRUPO WHERE id = ?");
			deletarQuery.setInt(1, id);
			deletarQuery.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////
	// Operações em Sorteios //
	///////////////////////////
	public void adicionarSorteio(Sorteio sorteio) {
		try {
			PreparedStatement incluirQuery = ConnectionManager.getConnection()
					.prepareStatement("INSERT INTO SORTEIO (numero, dezena01, dezena02, dezena03, dezena04, dezena05, "
							+ "dezena06, dezena07, dezena08, dezena09, dezena10) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
			incluirQuery.setInt(1, sorteio.getNumero());
			incluirQuery.setInt(2, sorteio.getDezenas().get(0));
			incluirQuery.setInt(3, sorteio.getDezenas().get(1));
			incluirQuery.setInt(4, sorteio.getDezenas().get(2));
			incluirQuery.setInt(5, sorteio.getDezenas().get(3));
			incluirQuery.setInt(6, sorteio.getDezenas().get(4));
			incluirQuery.setInt(7, sorteio.getDezenas().get(5));
			incluirQuery.setInt(8, sorteio.getDezenas().get(6));
			incluirQuery.setInt(9, sorteio.getDezenas().get(7));
			incluirQuery.setInt(10, sorteio.getDezenas().get(8));
			incluirQuery.setInt(11, sorteio.getDezenas().get(9));

			incluirQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void alterarSorteio(Sorteio sorteio) {
		try {
			PreparedStatement atualizarQuery = ConnectionManager.getConnection().prepareStatement(
					"UPDATE SORTEIO SET numero = ?, dezena01 = ?, dezena02 = ?, dezena03 = ?, dezena04 = ?, dezena05 = ?, "
							+ "dezena06 = ?, dezena07 = ?, dezena08 = ?, dezena09 = ?, dezena10 = ? WHERE id = ?");
			atualizarQuery.setInt(1, sorteio.getNumero());
			atualizarQuery.setInt(2, sorteio.getDezenas().get(0));
			atualizarQuery.setInt(3, sorteio.getDezenas().get(1));
			atualizarQuery.setInt(4, sorteio.getDezenas().get(2));
			atualizarQuery.setInt(5, sorteio.getDezenas().get(3));
			atualizarQuery.setInt(6, sorteio.getDezenas().get(4));
			atualizarQuery.setInt(7, sorteio.getDezenas().get(5));
			atualizarQuery.setInt(8, sorteio.getDezenas().get(6));
			atualizarQuery.setInt(9, sorteio.getDezenas().get(7));
			atualizarQuery.setInt(10, sorteio.getDezenas().get(8));
			atualizarQuery.setInt(11, sorteio.getDezenas().get(9));
			atualizarQuery.setInt(12, sorteio.getId());

			atualizarQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<Sorteio> listarSorteiosPorGrupo(Grupo grupo) {
		Vector<Sorteio> sorteios = new Vector<Sorteio>();
		try {
			PreparedStatement listaQuery = ConnectionManager.getConnection()
					.prepareStatement("SELECT id, numero, dezena01, dezena02, dezena03, dezena04, dezena05, "
							+ "dezena06, dezena07, dezena08, dezena09, dezena10 FROM SORTEIO WHERE numero >= ? AND numero <= ?");

			listaQuery.setInt(1, grupo.getSorteioInicial());
			listaQuery.setInt(2, grupo.getSorteioFinal());
			listaQuery.execute();

			ResultSet rs = listaQuery.getResultSet();
			while (rs.next()) {
				Sorteio sorteio = new Sorteio();
				sorteio.setId(rs.getInt("id"));
				sorteio.setNumero(rs.getInt("numero"));
				Vector<Integer> dezenas = new Vector<Integer>();
				dezenas.add(rs.getInt("dezena01"));
				dezenas.add(rs.getInt("dezena02"));
				dezenas.add(rs.getInt("dezena03"));
				dezenas.add(rs.getInt("dezena04"));
				dezenas.add(rs.getInt("dezena05"));
				dezenas.add(rs.getInt("dezena06"));
				dezenas.add(rs.getInt("dezena07"));
				dezenas.add(rs.getInt("dezena08"));
				dezenas.add(rs.getInt("dezena09"));
				dezenas.add(rs.getInt("dezena10"));
				sorteio.setDezenas(dezenas);

				sorteios.add(sorteio);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sorteios;
	}

	public Vector<Sorteio> listarSorteios() {
		Vector<Sorteio> sorteios = new Vector<Sorteio>();
		try {
			PreparedStatement listaQuery = ConnectionManager.getConnection()
					.prepareStatement("SELECT id, numero, dezena01, dezena02, dezena03, dezena04, dezena05, "
							+ "dezena06, dezena07, dezena08, dezena09, dezena10 FROM SORTEIO");

			listaQuery.execute();

			ResultSet rs = listaQuery.getResultSet();
			while (rs.next()) {
				Sorteio sorteio = new Sorteio();
				sorteio.setId(rs.getInt("id"));
				sorteio.setNumero(rs.getInt("numero"));
				Vector<Integer> dezenas = new Vector<Integer>();
				dezenas.add(rs.getInt("dezena01"));
				dezenas.add(rs.getInt("dezena02"));
				dezenas.add(rs.getInt("dezena03"));
				dezenas.add(rs.getInt("dezena04"));
				dezenas.add(rs.getInt("dezena05"));
				dezenas.add(rs.getInt("dezena06"));
				dezenas.add(rs.getInt("dezena07"));
				dezenas.add(rs.getInt("dezena08"));
				dezenas.add(rs.getInt("dezena09"));
				dezenas.add(rs.getInt("dezena10"));
				sorteio.setDezenas(dezenas);

				sorteios.add(sorteio);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sorteios;
	}
	
	public void removerSorteio(int id) {
		try {
			PreparedStatement deletarQuery = ConnectionManager.getConnection()
					.prepareStatement("DELETE FROM SORTEIO WHERE id = ?");
			deletarQuery.setInt(1, id);
			deletarQuery.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
