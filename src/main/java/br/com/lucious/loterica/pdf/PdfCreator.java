package br.com.lucious.loterica.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Set;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.lucious.loterica.model.Grupo;
import br.com.lucious.loterica.model.Jogo;
import br.com.lucious.loterica.model.Resultado;

public class PdfCreator {

	private static Document createDocument(Grupo grupo) {
		if(grupo == null){
			throw new RuntimeException("Grupo não informado.");
		}
		Document doc = new Document();
		try {
			FileOutputStream fos = new FileOutputStream(filename(grupo));
			PdfWriter.getInstance(doc, fos);
			doc.open();
			doc.add(new Paragraph("QUINA MENSAL ENTRE AMIGOS"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static void createComJogos(Grupo grupo, List<Jogo> jogos) {
		Document doc = createDocument(grupo);
		doc.close();
	}

	public static void createComResultados(Set<Integer> dezenasSorteadas, Grupo grupo, List<Resultado> resultados) {
		Document doc = createDocument(grupo);
		doc.close();
	}

	public static String filename(Grupo grupo) {
		String base = grupo.toString().replaceAll("[\\s/]+","");
		return base + ".pdf";
	}

}
