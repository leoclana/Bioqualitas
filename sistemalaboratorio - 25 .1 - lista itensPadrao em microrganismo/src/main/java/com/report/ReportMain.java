package com.report;


import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import com.model.Amostra;
import com.model.Solicitacao;
import com.service.IAmostraService;


public class ReportMain {
	
	private Document document;
	private String texto;
	private PdfWriter writer;
	private static String strTipoRelatorio;
	private static int intTipoAnalise; 

	private Amostra amostra;
	private Solicitacao solicitacao;
	private static IAmostraService amostraService;
	
	private static HttpServletRequest request;
	
	
	public static final String RESOURCE = "/resources/imagens/logo_relatorio155.png";	
	
	public ReportMain(){	
	}

	public void createReport(){
    	
        try {
        	        	        	
			this.document.open();
			
        	TableHeaderFooter event = new TableHeaderFooter();
            writer.setPageEvent(event);

			createHeader();
			createStructure();
			//this.document.add(new Paragraph(this.getTexto()));
			
			this.document.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    private void createHeader(){
    	
        try {
        	if ("ALIMENTO".equalsIgnoreCase(this.getAmostra().getTipoAnalise().toString())){
        		intTipoAnalise = 1;
        	}else{
            	if ("MANIPULADOR".equalsIgnoreCase(this.getAmostra().getTipoAnalise().toString())){
            		intTipoAnalise = 2;
            	}else{
                	if ("SUPERF�CIE".equalsIgnoreCase(this.getAmostra().getTipoAnalise().toString())){
                		intTipoAnalise = 3;
                	}else{
                		
                    	if ("�GUA".equalsIgnoreCase(this.getAmostra().getTipoAnalise().toString())){
                    		intTipoAnalise = 4;
                    	}else{
                    		
                        	if ("�GUA MINERAL".equalsIgnoreCase(this.getAmostra().getTipoAnalise().toString())){
                        		intTipoAnalise = 5;
                        	}else{
                        		
                            	if ("AR".equalsIgnoreCase(this.getAmostra().getTipoAnalise().toString())){
                            		intTipoAnalise = 6;
                            	}else{
                            		
                                	if ("GELO".equalsIgnoreCase(this.getAmostra().getTipoAnalise().toString())){
                                		intTipoAnalise = 7;
                                	}	
                            	}	
                        	}	
                    	}	
                	}	
            	}	
        	}
        	
        	
        	//strTipoAnalise 
        	PdfPTable table = createTableTop("AN�LISE MICROBIOL�GICA DE " + this.getAmostra().getTipoAnalise().toString().toUpperCase());
        	table.completeRow();
            this.document.add(table);
            
            
            String strCliente = this.solicitacao.getCliente().getNome() + "\n" + this.solicitacao.getCliente().getAlias();
            String strNumRegistro = String.format("%05d", this.solicitacao.getIdSolicitacao());
            String strNumAmostra = String.format("%03d", this.amostra.getIdAmostra());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String strDataRegistro = sdf.format(this.solicitacao.getDataSolicitacao());
            
            table = createTableIdentification(strCliente, strNumAmostra, strNumRegistro, strDataRegistro);
        	table.completeRow();
            this.document.add(table);
            
            sdf = new SimpleDateFormat("HH:mm");
            
            String strHoraColeta = "";
            if(this.getAmostra().getHrColeta()!=null){
            	strHoraColeta = sdf.format(this.getAmostra().getHrColeta().getTime()) + " horas";
            }
            
            if (intTipoAnalise!=2) {
            	table = createTableDescription(this.getAmostra().getDescricaoAmostra(),
            			"\n" + "Fab.: ??/??/??  Val.: ??/??/??", 
            			this.getAmostra().getTempAmostraColeta(),
            			this.getAmostra().getCodLegislacao(),
            			this.getAmostra().getLocalColeta(),
            			this.getAmostra().getResponsavelColeta(),
            			strHoraColeta);
            }else{
            	table = createTableDescription(this.getAmostra().getNomeManipulador(),
            			"", 
            			this.getAmostra().getTarefaExecutada(),
            			this.getAmostra().getAntissepsiaDesc(),
            			this.getAmostra().getLocalColeta(),
            			this.getAmostra().getResponsavelColeta(),
            			strHoraColeta);
            }
            
            
            
            
        	table.completeRow();
            this.document.add(table);
    

        } catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    private void createStructure(){
    	
    	try {
    		
//          table = createTableDescription(this.getAmostra().getDescricaoAmostra().toString() + "\n" + "Fab.: ??/??/??  Val.: ??/??/??", 
//          this.getAmostra().getTempAmostraColeta(),
//          this.getAmostra().getCodLegislacao(),
//          this.getAmostra().getLocalColeta(),
//          this.getAmostra().getResponsavelColeta(),
//          sdf.format(this.getAmostra().getHrColeta().getTime()) + " horas");

    		

    		//PdfPTable table = createTableSensorial("?????caracter�stico?????", "?????normal?????", "??????caracter�stico?????", "?????pr�pria?????");
    		PdfPTable table;
    		int int_NumLinhas;
    		String[] strDescricao;
			String[] strResultado;
			String[] strReferencia;
			
    		if (intTipoAnalise!=2 && intTipoAnalise!=3 && intTipoAnalise!=6) {
    			int_NumLinhas = 2;
    			strDescricao = new String[int_NumLinhas];
    			strResultado = new String[int_NumLinhas];
    			strReferencia = new String[int_NumLinhas];

    			strDescricao[0] = "???Odor???";
    			strResultado[0] = "N�o objet�vel";
    			strReferencia[0] = "???N�o objet�vel???";

    			strDescricao[1] = "???pH???";
    			strResultado[1] = "???6,16???";
    			strReferencia[1] = "???6,5 - 9,5???";            
    			
    			String strTituloTabela = "";
    			
    			if (intTipoAnalise==1) {
    				strTituloTabela = "CARACTER�STICAS SENSORIAIS";
    			}else{
    				strTituloTabela = "CARACTER�STICAS GERAIS";
    			}
    			
    			table = createTableDetalhe(strTituloTabela, "PAR�METRO",  strDescricao, strResultado, strReferencia, false);
    			table.completeRow();
    			this.document.add(table);
    		}
    		

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
          
            //System.out.println("Data inicial: " + this.getAmostra().getDataInicioAnalise().toString() + " - Data final: " + this.getAmostra().getDataConclusaoAnalise());
            
            String strDataInicial = "";
            String strDataFinal = "";
            
            if (this.getAmostra().getDataInicioAnalise()!=null){
            	strDataInicial = sdf.format(this.getAmostra().getDataInicioAnalise());
            }
            
            if (this.getAmostra().getDataConclusaoAnalise()!=null){
            	strDataInicial = sdf.format(this.getAmostra().getDataConclusaoAnalise());
            }
            
            table = createTableDataAnalise(strDataInicial, strDataFinal);
        	table.completeRow();
            this.document.add(table);
            
            
            //Monta tabela de Microrganismos
            int_NumLinhas = 3;
            strDescricao = new String[int_NumLinhas];
            strResultado = new String[int_NumLinhas];
            strReferencia = new String[int_NumLinhas];
            
            for(int i=0;i<int_NumLinhas;i++){
            	strDescricao[i] = "Microrganismo " + i;
            	strResultado[i] = "Resultado " + i;
            	strReferencia[i] = "Referencia " + i;
            }
            
            String strTableTitle = "";
            String strColumnTitle = "MICRORGANISMO";
            switch (intTipoAnalise) {
            case 1: case 2: case 3: case 6: 
            	strTableTitle = "PESQUISA DE MICRORGANISMOS";
            case 4: case 5: case 7:
            	strTableTitle = "DETERMINA��ES ANAL�TICAS";
            default:
            	strTableTitle = "PESQUISA DE MICRORGANISMOS";
            }
            
            table = createTableDetalhe(strTableTitle, strColumnTitle,  strDescricao, strResultado, strReferencia, true);
        	table.completeRow();
            this.document.add(table);
            
            table = createTableResultado("Produto em condi��es sanit�rias satisfat�rias", "O produto apresentou-se de acordo com os padr�es legais vigentes.");
        	table.completeRow();
            this.document.add(table);                        
            
//            table = createTableAssinatura();
//        	table.completeRow();
//            this.document.add(table);

        } catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	
    public static PdfPTable createTableTop(String strTipoAnalise) throws DocumentException {
    	
    	float[] widths = {2.4f, 1.6f};
    	PdfPTable table = new PdfPTable(2);
    	table.setWidthPercentage(100);
    	table.setWidths(widths);    	
		
    	try {
    	
			ServletContext servletContext = getRequest().getSession().getServletContext();
			
			URL pictureURL = servletContext.getResource(RESOURCE);
    		Image img = Image.getInstance(pictureURL);
    		
    		//System.out.println("Image width: " + img.getWidth());
    		
    		PdfPCell cell = new PdfPCell(img);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
    		table.addCell(cell);
            
            Font font = FontFactory.getFont("Arial", 20, Font.NORMAL);

            
            Phrase p = new Phrase(strTipoAnalise, font);
            
    		cell = new PdfPCell(p);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
    		cell.setLeading(23f, 0f);
            table.addCell(cell);

    		cell = new PdfPCell();
    		cell.setColspan(2);
    		cell.setBorder(Rectangle.BOTTOM);
    		cell.setFixedHeight(10);
            table.addCell(cell);

            
    		//    	Rectangle r = new Rectangle(PageSize.A4.right(72), PageSize.A4.top(72));
    		//    	table.setWidthPercentage(widths, r);
    		//    	document.add(new Paragraph("We change the percentage using absolute widths:\n\n"));
    		//    	document.add(table);
    		//    	document.add(new Paragraph("We use a locked width:\n\n"));
    		//    	table.setTotalWidth(300);
    		//    	table.setLockedWidth(true);   
            
            
    	} catch (MalformedURLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	
        return table;
    }
    
    
    public static PdfPTable createTableSensorial(String strAspecto, String strTextura, String strOdor, String strColoracao) throws DocumentException {
    	
    	float CONST_DEFAULT_FIXHEIGHT = 13.0f;
    	float CONST_DEFAULT_FONTHEIGHT = 10.5f;
    	float[] widths = {0.5f, 0.8f, 1.1f, 0.2f, 0.8f, 1.1f, 0.5f};
    	PdfPTable table = new PdfPTable(7);
    	table.setWidthPercentage(100);
    	table.setWidths(widths);
		
    	try {

            //C�lula TITULO CARACTER�STICAS SENSORIAIS - Utilizar Properties
    		Font font = FontFactory.getFont("Arial", 11, Font.BOLD);
            Phrase p = new Phrase("CARACTER�STICAS SENSORIAIS", font);
            PdfPCell cell = new PdfPCell(p);
            cell.setColspan(7);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(18);
            table.addCell(cell);
            
            //C�lula LATERAL ESQUERDA (espa�o)
            p = new Phrase("", font);
            cell = new PdfPCell(p);            
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);
            
            //C�lula LABEL ASPECTO - Utilizar Properties
            font = FontFactory.getFont("Arial", CONST_DEFAULT_FONTHEIGHT, Font.BOLD);
            p = new Phrase("Aspecto", font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);
            
            //C�lula ASPECTO
            font = FontFactory.getFont("Arial", CONST_DEFAULT_FONTHEIGHT, Font.NORMAL);
            p = new Phrase(strAspecto, font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);

            //C�lula CENTRAL (espa�o entre blocos)
            p = new Phrase("", font);
            cell = new PdfPCell(p);            
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);

            //C�lula LABEL TEXTURA - Utilizar properties
            font = FontFactory.getFont("Arial", CONST_DEFAULT_FONTHEIGHT, Font.BOLD);
            p = new Phrase("Textura", font);
    		cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);
            
            //C�lula TEXTURA
            font = FontFactory.getFont("Arial", CONST_DEFAULT_FONTHEIGHT, Font.NORMAL);
            p = new Phrase(strTextura, font);
    		cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);

            //C�lula LATERAL DIREITA (espa�o)
            p = new Phrase("", font);
            cell = new PdfPCell(p);            
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);
            
            //C�lula LATERAL ESQUERDA (espa�o)
            p = new Phrase("", font);
            cell = new PdfPCell(p);            
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);
            
            //C�lula LABEL ODOR - Utilizar Properties
            font = FontFactory.getFont("Arial", CONST_DEFAULT_FONTHEIGHT, Font.BOLD);
            p = new Phrase("Odor", font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);

            //C�lula ODOR
            font = FontFactory.getFont("Arial", CONST_DEFAULT_FONTHEIGHT, Font.NORMAL);
            p = new Phrase(strOdor, font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);

            //C�lula CENTRAL (espa�o entre blocos)
            p = new Phrase("", font);
            cell = new PdfPCell(p);            
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);            

            //C�lula LABEL COLORACAO - Utilizar properties
            font = FontFactory.getFont("Arial", CONST_DEFAULT_FONTHEIGHT, Font.BOLD);
            p = new Phrase("Colora��o", font);
    		cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);
            
            //C�lula COLORACAO
            font = FontFactory.getFont("Arial", CONST_DEFAULT_FONTHEIGHT, Font.NORMAL);
            p = new Phrase(strColoracao, font);
    		cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);
            
            //C�lula LATERAL DIREITA (espa�o)
            p = new Phrase("", font);
            cell = new PdfPCell(p);            
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            table.addCell(cell);
            
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
        return table;
    }
    
    public static PdfPTable createTableDataAnalise(String strDataInicial, String strDataFinal) throws DocumentException {
    	
    	float[] widths = {0.5f, 0.5f};
    	PdfPTable table = new PdfPTable(2);
    	table.setWidthPercentage(100);
    	table.setWidths(widths);
		
    	try {
            //C�lula DATA INICIAL DA ANALISE - Utilizar Properties
    		Font font = FontFactory.getFont("Arial", 11, Font.NORMAL);
            Phrase p = new Phrase("Data Inicial da An�lise: " + strDataInicial, font);
            PdfPCell cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(50);
            table.addCell(cell);

            //C�lula DATA FINAL DA ANALISE - Utilizar Properties
    		font = FontFactory.getFont("Arial", 11, Font.NORMAL);
            p = new Phrase("Data Final da An�lise: " + strDataFinal, font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(50);
            table.addCell(cell);
            
//            //C�lula DATA FINAL DA ANALISE - Utilizar Properties
//    		font = FontFactory.getFont("Arial", 11, Font.NORMAL);
//            p = new Phrase("", font);
//            cell = new PdfPCell(p);
//            cell.setColspan(2);
//    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//    		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
//    		cell.setBorder(Rectangle.NO_BORDER);
//    		cell.setFixedHeight(12);
//            table.addCell(cell);            
            
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
        return table;
    }
    
    public static PdfPTable createTableDetalhe(String strTitulo, String strColunaDescricao, String[] strDescricao, String[] strResultado, String[] strReferencia, Boolean bLegenda) throws DocumentException {
    	
    	float[] widths = {1.4f, 0.5f, 1.1f};
    	PdfPTable table = new PdfPTable(3);
    	table.setWidthPercentage(100);
    	table.setWidths(widths);
		
    	try {
			Font font = FontFactory.getFont("Arial", 11, Font.BOLD);
			Phrase p = new Phrase(strTitulo, font);
			PdfPCell cell = new PdfPCell(p);
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(20);
			table.addCell(cell);

    		
    		font = FontFactory.getFont("Arial", 10, Font.NORMAL);
            p = new Phrase(strColunaDescricao, font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorder(Rectangle.BOX);
    		cell.setFixedHeight(20);
            table.addCell(cell);

            //C�lula RESULTADO - Utilizar Properties
    		font = FontFactory.getFont("Arial", 10, Font.NORMAL);
            p = new Phrase("RESULTADO", font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorder(Rectangle.BOX);
            table.addCell(cell);
            
            //C�lula REFERENCIA - Utilizar Properties
    		font = FontFactory.getFont("Arial", 10, Font.NORMAL);
            p = new Phrase("REFER�NCIA", font);
            cell = new PdfPCell(p);

            //ADICIONAR TEXTO ("Resolu��o RDC n12 - 02/01/01 ANVISA", font);

    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorder(Rectangle.BOX);
            table.addCell(cell);
            

            for(int i=0;i<strDescricao.length;i++) {
            
	            //C�lula MICRORGANISMO - Utilizar Properties
	    		font = FontFactory.getFont("Arial", 10, Font.NORMAL);
	            p = new Phrase(strDescricao[i], font);
	            cell = new PdfPCell(p);
	    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    		cell.setBorder(Rectangle.BOX);
	    		cell.setUseAscender(true);
	    		cell.setUseDescender(false);
	    		cell.setFixedHeight(14);
	            table.addCell(cell);
	
	            //C�lula RESULTADO - Utilizar Properties
	    		font = FontFactory.getFont("Arial", 10, Font.NORMAL);
	            p = new Phrase(strResultado[i], font);
	            cell = new PdfPCell(p);
	    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    		cell.setBorder(Rectangle.BOX);
	    		cell.setUseAscender(true);
	    		cell.setUseDescender(false);
	            table.addCell(cell);
	            
	            //C�lula REFERENCIA - Utilizar Properties
	    		font = FontFactory.getFont("Arial", 10, Font.NORMAL);
	            p = new Phrase(strReferencia[i], font);
	            cell = new PdfPCell(p);
	    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    		cell.setBorder(Rectangle.BOX);
	    		cell.setUseAscender(true);
	    		cell.setUseDescender(false);
	            table.addCell(cell);
            }            

            if (bLegenda) {
            	String strMetodologia = "";
            	if (intTipoAnalise==1 || intTipoAnalise==4 || intTipoAnalise==5 || intTipoAnalise==7 ) {
            		if (intTipoAnalise==1) {
            			if ("1".equals(strTipoRelatorio)){
            				strMetodologia = "Metodologia: Instru��o Normativa n� 62 de 26 de agosto de 2003 - Minist�rio da Agricultura";
            			}
            		}else{
            			if (intTipoAnalise==4 || intTipoAnalise==5 || intTipoAnalise==7 ) {
            				strMetodologia = "Metodologia Utilizada: American Public Health of Water and Wastewater; Standard Methods for the Examination of Water and Wastewater - 21� ed., Baltimore, Maryland, USA: APHA, 2005.";
            			}

            			//C�lula METODOLOGIA - Utilizar Properties
            			font = FontFactory.getFont("Arial", 10, Font.NORMAL);
            			p = new Phrase(strMetodologia, font);
            			cell = new PdfPCell(p);
            			cell.setColspan(3);
            			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            			cell.setBorder(Rectangle.NO_BORDER);
            			table.addCell(cell);



            			if (intTipoAnalise==1){
            				//C�lula LEGENDA - Utilizar Properties
            				font = FontFactory.getFont("Arial", 10, Font.NORMAL);
            				p = new Phrase("LEGENDA: UFC - Unidades Formadoras de Col�nia / NMP - N�mero Mais Prov�vel", font);
            				cell = new PdfPCell(p);
            				cell.setColspan(3);
            				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            				cell.setBorder(Rectangle.NO_BORDER);
            				table.addCell(cell);
            			}

            			if (intTipoAnalise == 7){
            				//C�lula LEGENDA - Utilizar Properties
            				font = FontFactory.getFont("Arial", 10, Font.NORMAL);
            				p = new Phrase("* Os crit�rios microbiol�gicos s�o os da Portaria 2914 de 12/12/11 - MS.", font);
            				cell = new PdfPCell(p);
            				cell.setColspan(3);
            				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            				cell.setBorder(Rectangle.NO_BORDER);
            				table.addCell(cell);
            			}				

            			if (intTipoAnalise == 5 || intTipoAnalise == 7) {
            				//C�lula LEGENDA - Utilizar Properties
            				font = FontFactory.getFont("Arial", 7, Font.NORMAL);
            				p = new Phrase("*Art.28 - � 3� 'altera��es bruscas ou acima do usual na contagem de bact�rias heterotr�ficas devem ser investigadas para identifica��o  de irregularidade e provid�ncias devem ser adotadas para o restabelecimento da integridade do sistema de distribui��o (reservat�rio e rede), recomenda-se que n�o se ultrapasse o limite de 500 UFC/mL'.", font);
            				cell = new PdfPCell(p);
            				cell.setColspan(3);
            				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            				cell.setBorder(Rectangle.NO_BORDER);
            				table.addCell(cell);
            			}
            		}
            	}
            }
			
            
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
        return table;
    }   
    
    
    public static PdfPTable createTableAssinatura() throws DocumentException {
    	
    	PdfPTable table = new PdfPTable(1);
    	table.setWidthPercentage(100);
		
    	try {
			//C�lula Declaracao do laudo - Utilizar Properties
			Font font = FontFactory.getFont("Arial", 10, Font.NORMAL);
			Phrase p = new Phrase("O presente laudo refere-se exclusivamente � amostra analisada.", font);
			PdfPCell cell = new PdfPCell(p);
			//cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(30);
			table.addCell(cell);

            //C�lula Local e Data - Utilizar Properties
    		font = FontFactory.getFont("Arial", 10, Font.NORMAL);
    		Date d = new Date();
    		Locale PT_BR = new Locale("pt", "BR");
    		DateFormat dfmt = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", PT_BR);     		
            p = new Phrase("Rio de Janeiro, " + dfmt.format(d), font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(30);
            table.addCell(cell);

            //C�lula Assinatura - Utilizar Properties
    		font = FontFactory.getFont("Arial", 10.5f, Font.BOLD);
            p = new Phrase("Brigitte M.A. Bertin", font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            
            //C�lula CRF - Utilizar Properties
    		font = FontFactory.getFont("Arial", 10, Font.BOLD);
            p = new Phrase("CRF 74467", font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(18);
            table.addCell(cell);            
               
            
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
        return table;
    }    
    
    
    public static PdfPTable createTableResultado(String strResultado, String strResultadoDesc) throws DocumentException {
    	
    	float[] widths = {0.7f, 1.3f};
    	PdfPTable table = new PdfPTable(2);
    	table.setWidthPercentage(100);
    	table.setWidths(widths);
		
    	try {
    		
			//C�lula Resultado - Utilizar Properties
			Font font = FontFactory.getFont("Arial", 10, Font.BOLD);
			Phrase p = new Phrase("", font);
			PdfPCell cell = new PdfPCell(p);			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.NO_BORDER);
    		cell.setFixedHeight(15);
			cell.setColspan(2);

			table.addCell(cell);
			
			//C�lula Resultado - Utilizar Properties
			font = FontFactory.getFont("Arial", 10, Font.BOLD);
			p = new Phrase("RESULTADO: ", font);
			cell = new PdfPCell(p);			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(15);
			table.addCell(cell);

            //C�lula RESULTADO
    		font = FontFactory.getFont("Arial", 10, Font.BOLD);
            p = new Phrase(strResultado, font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorder(Rectangle.BOX);
    		cell.setFixedHeight(20);
            table.addCell(cell);

            //C�lula ESPACO
    		font = FontFactory.getFont("Arial", 11, Font.BOLD);
            p = new Phrase("", font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setFixedHeight(12);
    		cell.setColspan(2);
    		cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);
            
            //C�lula ESPACO
    		font = FontFactory.getFont("Arial", 11, Font.BOLD);
            p = new Phrase("", font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setFixedHeight(12);
    		cell.setColspan(2);
    		cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            
            //C�lula RESULTADO DESCRICAO
    		font = FontFactory.getFont("Arial", 11, Font.NORMAL);
            p = new Phrase(strResultadoDesc, font);
            cell = new PdfPCell(p);
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setColspan(2);
    		cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            
            
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
        return table;
    }     

    public static PdfPTable createTableFooter() throws DocumentException {
    	
    	//float[] widths = {0.7f, 1.3f};
    	PdfPTable table = new PdfPTable(1);
    	table.setWidthPercentage(100);
    	//table.setWidths(widths);
		
    	try {
    		
			//C�lula BIOQUALITAS - Utilizar Properties
			Font font = FontFactory.getFont("Arial", 10, Font.BOLD);
			Phrase p = new Phrase("BIOQUALITAS AN�LISES DE ALIMENTOS E TREINAMENTOS CONT�NUOS LTDA.", font);
			PdfPCell cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.TOP);
    		//cell.setFixedHeight(15);
			//cell.setColspan(2);
			table.addCell(cell);
			
			//C�lula ENDERECO - Utilizar Properties
			font = FontFactory.getFont("Arial", 10, Font.NORMAL);
			p = new Phrase("Avenida Presidente Vargas, 534 / 2� andar - Centro - Rio de Janeiro - CEP 20071-000", font);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.NO_BORDER);
    		//cell.setFixedHeight(15);
			//cell.setColspan(2);
			table.addCell(cell);
			
			//C�lula ENDERECO - Utilizar Properties
			font = FontFactory.getFont("Arial", 10, Font.NORMAL);
			p = new Phrase("Tel.: 21 2518-1095  bioqualitas@bioqualitas.com.br  CNPJ 03404827/0001-59  CRQ 5935", font);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.NO_BORDER);			
			table.addCell(cell);			

			
            
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
        return table;
    }     

    
    public static PdfPTable createTableDescription(String strDescricao, String strFabricacao, String strTemperaturaAmostra, String strCodLegislacao, String strLocalColeta, String strRespColeta, String strHoraColeta) throws DocumentException {
    	
    	float CONST_DEFAULT_FIXHEIGHT = 13.0f;
    	float[] widths = {2.0f, 4.0f};
    	PdfPTable table = new PdfPTable(2);
    	table.setWidthPercentage(100);
    	table.setWidths(widths);
		
    	try {

            Font font = FontFactory.getFont("Arial", 11, Font.NORMAL);
    		
            Phrase p = new Phrase("", font);
            PdfPCell cell = new PdfPCell(p);
            
            String strLabelDescricao = "";
            String strLabelTemperatura = "";
            String strLabelCodigoLegislacao = "";
            
            if (intTipoAnalise!=2) {
            	strLabelDescricao = "Descri��o da Amostra:";
            	strLabelTemperatura = "Temperatura da Amostra:";
            	strLabelCodigoLegislacao = "C�digo da Legisla��o:";
            }else{
            	strLabelDescricao = "Nome do Manipulador:";
            	strLabelTemperatura = "Tarefa executada:";
            	strLabelCodigoLegislacao = "Antissepsia:";
            }
            	
            //C�lula LABEL DESCRICAO AMOSTRA - Utilizar Properties
            if (strDescricao!=null) {
            	p = new Phrase(strLabelDescricao, font);
            	cell = new PdfPCell(p);
            	cell.setBorder(0);
            	cell.setFixedHeight(25);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	cell.setUseAscender(true);
            	cell.setUseDescender(false);
            	table.addCell(cell);

            	//C�lula Descricao da Amostra
            	p = new Phrase(strDescricao+strFabricacao, font);
            	cell = new PdfPCell(p);
            	cell.setBorder(0);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	cell.setUseAscender(true);
            	cell.setUseDescender(false);
            	table.addCell(cell);
            }

            //C�lula LABEL TEMPERATURA DA AMOSTRA - Utilizar properties
            if (strTemperaturaAmostra!=null) {
	            p = new Phrase(strLabelTemperatura, font);
	    		cell = new PdfPCell(p);
	    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    		cell.setBorder(0);
	    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
	    		cell.setUseAscender(true);
	    		cell.setUseDescender(false);
	            table.addCell(cell);
	            
	            //C�lula TEMPERATURA DA AMOSTRA
	            p = new Phrase(strTemperaturaAmostra, font);
	    		cell = new PdfPCell(p);
	    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    		cell.setBorder(0);
	    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
	    		cell.setUseAscender(true);
	    		cell.setUseDescender(false);
	            table.addCell(cell);
            }

            //C�lula LABEL CODIGO LEGISLACAO - Utilizar properties
            if (strCodLegislacao!=null) {
	            p = new Phrase(strLabelCodigoLegislacao, font);
	    		cell = new PdfPCell(p);
	    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    		cell.setBorder(0);
	    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
	    		cell.setUseAscender(true);
	    		cell.setUseDescender(false);
	            table.addCell(cell);            
	
	            //C�lula CODIGO LEGISLACAO
	            p = new Phrase(strCodLegislacao, font);
	    		cell = new PdfPCell(p);
	    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    		cell.setBorder(0);
	    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
	    		cell.setUseAscender(true);
	    		cell.setUseDescender(false);
	            table.addCell(cell);
            }

            
            //C�lula LABEL LOCAL DA COLETA - Utilizar properties
            if (strLocalColeta!=null) {
	            p = new Phrase("Local da Coleta:", font);
	    		cell = new PdfPCell(p);
	    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    		cell.setBorder(0);
	    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
	    		cell.setUseAscender(true);
	    		cell.setUseDescender(false);
	            table.addCell(cell);            
	
	            //C�lula LOCAL DA COLETA
	            p = new Phrase(strLocalColeta, font);
	    		cell = new PdfPCell(p);
	    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    		cell.setBorder(0);
	    		cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
	    		cell.setUseAscender(true);
	    		cell.setUseDescender(false);
	            table.addCell(cell);
            }
            
            
            //C�lula LABEL RESPONSAVEL PELA COLETA - Utilizar properties
            if (strRespColeta!=null) {
            	p = new Phrase("Respons�vel pela Coleta:", font);
            	cell = new PdfPCell(p);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	cell.setBorder(0);
            	cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            	cell.setUseAscender(true);
            	cell.setUseDescender(false);
            	table.addCell(cell);

            	//C�lula RESPONSAVEL PELA COLETA
            	p = new Phrase(strRespColeta, font);
            	cell = new PdfPCell(p);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	cell.setBorder(0);
            	cell.setFixedHeight(CONST_DEFAULT_FIXHEIGHT);
            	cell.setUseAscender(true);
            	cell.setUseDescender(false);
            	table.addCell(cell);        
            }
            
            
            //C�lula LABEL HORARIO DA COLETA - Utilizar properties
            if(strHoraColeta!=null){
	            p = new Phrase("Hor�rio da Coleta:", font);
	    		cell = new PdfPCell(p);
	    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		cell.setVerticalAlignment(Element.ALIGN_TOP);
	    		cell.setBorder(0);
	    		cell.setFixedHeight(30);
	    		cell.setUseAscender(true);
	    		cell.setUseDescender(false);
	            table.addCell(cell);            
	
	            //C�lula HORA DA COLETA
	            p = new Phrase(strHoraColeta, font);
	    		cell = new PdfPCell(p);
	    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		cell.setVerticalAlignment(Element.ALIGN_TOP);
	    		cell.setBorder(0);
	    		cell.setUseAscender(true);
	    		cell.setUseDescender(false);
	            table.addCell(cell);
            }
            
            
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
        return table;
    }    
    
    public static PdfPTable createTableIdentification(String strCliente, String strNumAmostra, String strNumRegistro, String strDataRegistro) throws DocumentException {
    	
    	float[] widths = {0.5f, 3.7f, 1.8f};
    	PdfPTable table = new PdfPTable(3);
    	table.setWidthPercentage(100);
    	table.setWidths(widths);
		
    	try {

            Font font = FontFactory.getFont("Arial", 11, Font.NORMAL);
    		
            
            //C�lula LABEL CLIENTE - Utilizar Properties
            Phrase p = new Phrase("Cliente:", font);
            PdfPCell cell = new PdfPCell(p);
    		cell.setFixedHeight(33);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cell);

            //C�lula CLIENTE
            p = new Phrase(strCliente, font);
            cell = new PdfPCell(p);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cell);

            //C�lula REGISTRO - Utilizar properties            
            p = new Phrase("Registro: " + strNumRegistro, font);
    		cell = new PdfPCell(p);
    		cell.setBorder(Rectangle.NO_BORDER);
    		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    		cell.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cell);
            
            
            //C�lula NUMERO AMOSTRA - Utilizar properties
            p = new Phrase("Numero da Amostra: " + strNumAmostra, font);
    		cell = new PdfPCell(p);
    		cell.setColspan(2);
    		cell.setFixedHeight(30);
    		cell.setBorder(Rectangle.BOTTOM);
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            
            //C�lula DATA REGISTRO - Utilizar properties
            p = new Phrase("Data de Registro: " + strDataRegistro, font);
    		cell = new PdfPCell(p);
    		cell.setBorder(Rectangle.BOTTOM);
    		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
        return table;
    }
	
	
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public static HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setWriter(PdfWriter writer) {
		// TODO Auto-generated method stub
		this.writer = writer;
		
	}


	public void setStrTipoRelatorio(String strTipoRelatorio) {
		// TODO Auto-generated method stub
		this.strTipoRelatorio = strTipoRelatorio;
		
	}


	public String getStrTipoRelatorio() {
		return strTipoRelatorio;
	}


	public Amostra getAmostra() {
		return amostra;
	}


	public void setAmostra(Amostra amostra) {
		this.amostra = amostra;
	}


	public Solicitacao getSolicitacao() {
		return solicitacao;
	}


	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}


	public static IAmostraService getAmostraService() {
		return amostraService;
	}


	public void setAmostraService(IAmostraService amostraService) {
		ReportMain.amostraService = amostraService;
	}
	

	
    class TableHeaderFooter extends PdfPageEventHelper {
     	
        /**
         * Allows us to change the content of the header.
         * @param header The new header String
         */
        public void setHeader(String header) {
            //this.header = header;
        }
 
        /**
         * Creates the PdfTemplate that will hold the total number of pages.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document) {
            //total = writer.getDirectContent().createTemplate(30, 16);
        }
 
        /**
         * Adds a header to every page
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable table;
            try {
            	table = createTableAssinatura();
            	table.completeRow();
            	table.setTotalWidth(500);
            	table.writeSelectedRows(0, -1, 0, -1, 50, 170, writer.getDirectContent());
            	table = createTableFooter();
            	table.setTotalWidth(500);
            	table.completeRow();
            	table.writeSelectedRows(0, -1, 0, -1, 50, 70, writer.getDirectContent());
            }
            catch(DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
 
        /**
         * Fills out the total number of pages before the document is closed.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onCloseDocument(PdfWriter writer, Document document) {
        	//ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
        }
    }
		
}
