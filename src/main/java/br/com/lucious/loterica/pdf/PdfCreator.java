package br.com.lucious.loterica.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.lucious.loterica.model.Grupo;
import br.com.lucious.loterica.model.Jogo;
import br.com.lucious.loterica.model.Resultado;

public class PdfCreator {

	private static Document createDocument(Grupo grupo, Integer numeroJogos) {
		if (grupo == null) {
			throw new RuntimeException("Grupo não informado.");
		}
		Document doc = new Document();
		try {
			FileOutputStream fos = new FileOutputStream(filename(grupo));
			PdfWriter.getInstance(doc, fos);
			doc.open();

			PdfPTable header = new PdfPTable(1);
			header.addCell(
					new CellBuilder("QUINA MENSAL ENTRE AMIGOS").alignHorizontally(Element.ALIGN_CENTER).build());

			StringBuilder texto = new StringBuilder("PARTICIPANTES DO CONCURSO ").append(grupo.getSorteioInicial())
					.append(" AO ").append(grupo.getSorteioFinal()).append(" DO MÊS DE ").append(grupo.getMes().name())
					.append(" DE ").append(grupo.getAno());

			header.addCell(new CellBuilder(texto.toString()).alignHorizontally(Element.ALIGN_CENTER)
					.border(Rectangle.NO_BORDER).build());

			texto = new StringBuilder(" INICIANDO EM ")
					.append(grupo.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
					.append(" VALOR DO PRÊMIO: R$").append(numeroJogos * Jogo.PRECO_JOGO);

			header.addCell(new CellBuilder(texto.toString()).alignHorizontally(Element.ALIGN_CENTER)
					.border(Rectangle.NO_BORDER).build());

			header.setSpacingAfter(10f);
			header.setWidthPercentage(100f);
			doc.add(header);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static void create(Grupo grupo, List<Jogo> jogos) {
		Document doc = createDocument(grupo, jogos.size());
		PdfPTable tabelaJogos = createTableJogos(jogos);
		try {
			doc.add(tabelaJogos);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		doc.close();
	}

	public static void create(Set<Integer> dezenasSorteadas, Grupo grupo, List<Resultado> resultados) {
		Document doc = createDocument(grupo, resultados.size());
		PdfPTable tabelaResultados = createTableResultados(resultados, dezenasSorteadas);
		try {
			doc.add(tabelaResultados);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		doc.close();
	}

	private static PdfPTable createTableJogos(List<Jogo> jogos) {
		float[] widths = { 1f, 10f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f };
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(100f);
		Integer index = 1;

		table.addCell(new CellBuilder("#").alignHorizontally(Element.ALIGN_CENTER).build());
		table.addCell(new CellBuilder("Nome do Jogador").alignHorizontally(Element.ALIGN_CENTER).build());
		table.addCell(new CellBuilder("Números").span(10).alignHorizontally(Element.ALIGN_CENTER).build());

		for (Jogo jogo : jogos) {
			table.addCell(new CellBuilder(index++).alignHorizontally(Element.ALIGN_CENTER).build());
			table.addCell(jogo.getNomeJogador());
			for (Integer dezena : jogo.getDezenas()) {
				table.addCell("" + dezena);
			}
		}

		return table;
	}

	private static PdfPTable createTableResultados(List<Resultado> resultados, Set<Integer> dezenasSorteadas) {
		float[] widths = { 1f, 10f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f };
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(100f);
		Integer index = 1;

		table.addCell(new CellBuilder("#").alignHorizontally(Element.ALIGN_CENTER).build());
		table.addCell(new CellBuilder("Nome do Jogador").alignHorizontally(Element.ALIGN_CENTER).build());
		table.addCell(new CellBuilder("Números").span(10).alignHorizontally(Element.ALIGN_CENTER).build());
		table.addCell(new CellBuilder("Act").alignHorizontally(Element.ALIGN_CENTER).build());

		for (Resultado resultado : resultados) {
			table.addCell(new CellBuilder(index++).alignHorizontally(Element.ALIGN_CENTER).build());
			table.addCell(resultado.getJogo().getNomeJogador());
			for (Integer dezena : resultado.getJogo().getDezenas()) {
				table.addCell(new CellBuilder(dezena).highlight(dezenasSorteadas.contains(dezena)).build());
			}
			table.addCell(new CellBuilder(resultado.getAcertos()).alignHorizontally(Element.ALIGN_CENTER).build());
		}

		return table;
	}

	public static String filename(Grupo grupo) {
		String base = grupo.toString().replaceAll("[\\s/]+", "");
		return base + ".pdf";
	}

}
