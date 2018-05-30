package com.servlet;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.jsf.FacesContextUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import com.filter.IndicadorFilter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.*;
import com.managed.bean.AmostraMB;
import com.managed.bean.ISessaoDoUsuario;
import com.managed.bean.SolicitacaoMB;
import com.model.Amostra;
import com.model.Cliente;
import com.model.FormaPagamento;
import com.model.MotivoAnalise;
import com.model.Solicitacao;
import com.report.ReportMain;
import com.service.IAmostraService;
import com.service.IClienteService;
import com.service.IFormaService;
import com.service.IMicrorganismoService;
import com.service.IResultadoService;
import com.service.ISolicitacaoService;

/**
 * Servlet implementation class getPdf
 */
public class getPdf extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public getPdf() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
		try {
			
			
            // Get the text that will be added to the PDF
            String strTipoRelatorio = request.getParameter("tp");

            if (strTipoRelatorio == null || strTipoRelatorio.trim().length() == 0) {
            	strTipoRelatorio = "Tipo de relatório inválido.";
            }
            
            // Get the text that will be added to the PDF
            String strIdAnalise = request.getParameter("id");
            if (strIdAnalise == null || strIdAnalise.trim().length() == 0) {
            	strIdAnalise = "ID da análise inválido.";
            }

            
            
            Enumeration<?> e = request.getSession(true).getAttributeNames();
            while (e.hasMoreElements()){
            	System.out.println((String) e.nextElement());
            }
            
            Amostra amostra = (Amostra)request.getSession(true).getAttribute("amostra");
            
            if (amostra!=null){
            	System.out.println("CLASSE: " + amostra.getClass().getName());
            	System.out.println("DESCRICAO: " + amostra.getDescricaoAmostra());
            	//System.out.println("ALIMENTO: " + amostra.getAlimento().getDescricao());
            	System.out.println("TIPO ANALISE: " + amostra.getTipoAnalise().name());
            }
            

            Solicitacao solicitacao = (Solicitacao)request.getSession(true).getAttribute("solicitacao");
            
            if (amostra!=null){
            	System.out.println("CLASSE: " + solicitacao.getClass().getName());
            	System.out.println("DATA SOLICITACAO: " + solicitacao.getDataSolicitacao());
            	System.out.println("ID SOLICITACAO: " + solicitacao.getIdSolicitacao());
            	System.out.println("STATUS: " + solicitacao.getStatus());
            }
            
//
//            IResultadoService resultadoService = (IResultadoService)request.getSession(true).getAttribute("resultadoService");
//            
//            if (resultadoService==null){
//            	System.out.println("RESULTADO SERVICE NULL");
//            }else{
//            	System.out.println("RESULTADO SERVICE ALIVE");
//            	System.out.println(resultadoService.getByAmostra(amostra).get(0).getMicrorganismo().getNome());
//            }
//            
            

            
            IAmostraService amostraService = (IAmostraService)request.getSession(true).getAttribute("amostraService");
            if (amostraService!=null){
            	System.out.println("CLASSE SERVICE: " + amostraService.getClass().getName());
            	
//            	amostra = amostraService.getAllBySolicitacao(solicitacao).get(0);
//            	
//                if (amostra!=null){
//                	System.out.println("CLASSE: " + amostra.getClass().getName());
//                	System.out.println("DESCRICAO: " + amostra.getDescricaoAmostra());
//                	//System.out.println("ALIMENTO: " + amostra.getAlimento().getDescricao());
//                	System.out.println("TIPO ANALISE: " + amostra.getTipoAnalise().name());
//                }
            	
//                amostra = amostraService.getAllBySolicitacao(solicitacao).get(1);
//            	
//                if (amostra!=null){
//                	System.out.println("CLASSE: " + amostra.getClass().getName());
//                	System.out.println("DESCRICAO: " + amostra.getDescricaoAmostra());
//                	//System.out.println("ALIMENTO: " + amostra.getAlimento().getDescricao());
//                	System.out.println("TIPO ANALISE: " + amostra.getTipoAnalise().name());
//                }
            	            	
            }
            
            
            
            
            
			
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            
            ReportMain rpt = new ReportMain();
            rpt.setRequest(request);
            rpt.setWriter(writer);
            document.setPageSize(PageSize.A4);
            rpt.setDocument(document);
            
            rpt.setTexto("Tipo de relatório: " + strTipoRelatorio);
            rpt.setStrTipoRelatorio(strTipoRelatorio);
            if (amostra==null){
            	System.out.println("AMOSTRA IS NULL");
            }
        	rpt.setAmostra(amostra);
        	rpt.setSolicitacao(solicitacao);
        	rpt.setAmostraService(amostraService);


            rpt.createReport();
            
            
            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");

            // the contentlength
            response.setContentLength(baos.size());
            
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            
            // free OutputStream
            os.flush();
            os.close();
            
            
        }
        catch(DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }
	

}
