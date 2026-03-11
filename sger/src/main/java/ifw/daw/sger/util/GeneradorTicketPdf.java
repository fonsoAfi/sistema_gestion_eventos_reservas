package ifw.daw.sger.util;

import java.io.ByteArrayOutputStream;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import ifw.daw.sger.models.Reservas;

public class GeneradorTicketPdf {
	
	public static Image generarQR(String texto) throws Exception {
		
		String formatoImg = "PNG";
		QRCodeWriter codWriter = new QRCodeWriter();
		BitMatrix bitMatrix = codWriter.encode(texto, BarcodeFormat.QR_CODE, 200, 200);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, formatoImg, baos);
		Image qr = Image.getInstance(baos.toByteArray());
		qr.scaleToFit(150, 150);
		
		return qr;	
	}
	
	public static byte[] generarEntrada(Reservas reserva) throws Exception {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		Document document = new Document();
		PdfWriter.getInstance(document, baos);
		
		document.open();
		
	    Font titulo = new Font(Font.HELVETICA, 20, Font.BOLD);
	    Font texto = new Font(Font.HELVETICA, 12);
	    
	    Paragraph title = new Paragraph(reserva.getEvento().getTituloEvento(), titulo);
	    title.setAlignment(Element.ALIGN_CENTER);
	    
	    document.add(title);
	    document.add(new Paragraph(" "));
	    
	    document.add(new Paragraph("Fecha: " + reserva.getEvento().getFechaInicio(), texto));
	    document.add(new Paragraph("Reserva Nº: " + reserva.getIdReserva(), texto));
	    document.add(new Paragraph("Plazas: " + reserva.getPlazasReservadas(), texto));
	    document.add(new Paragraph(" "));
	    
	    Image qr = generarQR("RESERVA-" + reserva.getIdReserva());
	    qr.setAlignment(Element.ALIGN_CENTER);

	    document.add(qr);

	    document.add(new Paragraph(" "));
	    document.add(new Paragraph("Presenta este código en la entrada", texto));

	    document.close();
		
		return baos.toByteArray();
	}
	
}
