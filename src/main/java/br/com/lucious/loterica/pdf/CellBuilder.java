package br.com.lucious.loterica.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

public class CellBuilder {

	private PdfPCell cell;

	public PdfPCell build() {
		return cell;
	}

	public CellBuilder() {
		cell = new PdfPCell();
	}

	public CellBuilder(String text) {
		cell = new PdfPCell(new Phrase(text));
	}

	public CellBuilder(Integer number) {
		cell = new PdfPCell(new Phrase(String.valueOf(number)));
	}
	
	public CellBuilder alignHorizontally(int alignment){
		cell.setHorizontalAlignment(alignment);
		return this;
	}
	
	public CellBuilder alignVertically(int alignment){
		cell.setVerticalAlignment(alignment);
		return this;
	}
	
	public CellBuilder span(int colspan){
		cell.setColspan(colspan);
		return this;
	}
	
	public CellBuilder border(int border){
		cell.setBorder(border);
		return this;
	}
	
	public CellBuilder background(BaseColor bgColor){
		cell.setBackgroundColor(bgColor);
		return this;
	}
	
	public CellBuilder highlight(boolean hg){
		if(hg){
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		}
		return this;
	}

}
