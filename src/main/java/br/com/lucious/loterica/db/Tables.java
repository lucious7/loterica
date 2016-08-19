package br.com.lucious.loterica.db;

import java.util.HashMap;
import java.util.Map;

public class Tables {

	public static Map<String, String> getScripts() {
		HashMap<String, String> tables = new HashMap<String, String>();

		tables.put("GRUPO", getCreateGrupo());
		tables.put("JOGO", getCreateJogo());
		tables.put("SORTEIO", getCreateSorteio());

		return tables;
	}

	private static String getCreateGrupo() {
		StringBuilder builder = new StringBuilder();

		builder.append("CREATE TABLE GRUPO ");
		builder.append("( ");
		builder.append("  id integer NOT NULL GENERATED ALWAYS AS IDENTITY "
                + "(START WITH 1, INCREMENT BY 1), ");
		builder.append("  mes integer NOT NULL, ");
		builder.append("  ano integer NOT NULL, ");
		builder.append("  dtInicio date NOT NULL, ");
		builder.append("  sorteioInicial integer NOT NULL, ");
		builder.append("  sorteioFinal integer NOT NULL, ");
		builder.append("  CONSTRAINT PK_GRUPO PRIMARY KEY (id) ");
		builder.append(") ");

		return builder.toString();
	}

	private static String getCreateJogo() {
		StringBuilder builder = new StringBuilder();

		builder.append(" CREATE TABLE JOGO ");
		builder.append(" ( ");
		builder.append("  id integer NOT NULL GENERATED ALWAYS AS IDENTITY "
                + "(START WITH 1, INCREMENT BY 1), ");
		builder.append("  grupo_id integer NOT NULL REFERENCES GRUPO(id), ");
		builder.append("  nome_jogador varchar(100) NOT NULL, ");
		builder.append("  dezena01 integer NOT NULL, ");
		builder.append("  dezena02 integer NOT NULL, ");
		builder.append("  dezena03 integer NOT NULL, ");
		builder.append("  dezena04 integer NOT NULL, ");
		builder.append("  dezena05 integer NOT NULL, ");
		builder.append("  dezena06 integer NOT NULL, ");
		builder.append("  dezena07 integer NOT NULL, ");
		builder.append("  dezena08 integer NOT NULL, ");
		builder.append("  dezena09 integer NOT NULL, ");
		builder.append("  dezena10 integer NOT NULL, ");
		builder.append("  CONSTRAINT PK_JOGO PRIMARY KEY (id) ");
		builder.append(" ) ");

		return builder.toString();
	}

	private static String getCreateSorteio() {
		StringBuilder builder = new StringBuilder();

		builder.append(" CREATE TABLE SORTEIO ");
		builder.append(" ( ");
		builder.append("  id integer NOT NULL GENERATED ALWAYS AS IDENTITY "
                + "(START WITH 1, INCREMENT BY 1), ");
		builder.append("   numero integer NOT NULL, ");
		builder.append("   dezena01 integer NOT NULL, ");
		builder.append("   dezena02 integer NOT NULL, ");
		builder.append("   dezena03 integer NOT NULL, ");
		builder.append("   dezena04 integer NOT NULL, ");
		builder.append("   dezena05 integer NOT NULL, ");
		builder.append("   dezena06 integer NOT NULL, ");
		builder.append("   dezena07 integer NOT NULL, ");
		builder.append("   dezena08 integer NOT NULL, ");
		builder.append("   dezena09 integer NOT NULL, ");
		builder.append("   dezena10 integer NOT NULL,  ");
		builder.append("   CONSTRAINT PK_SORTEIO PRIMARY KEY (id) ");
		builder.append(" ) ");

		return builder.toString();
	}

}
