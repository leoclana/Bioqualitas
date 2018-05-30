package com.util.mail;

import inicializacao.InicializacaoSpring;

import java.util.LinkedList;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test; 

import com.service.mail.IMailService;
import com.model.Usuario;

public class EnvioEmailTest {

	static IMailService mailSend;

	@BeforeClass
	public static void setUpClass() {
		InicializacaoSpring.setUpClass();
		mailSend = (IMailService) InicializacaoSpring.ctx.getBean("mailApp");
	}

	@BeforeMethod
	public void setUp() {
	}
  
	@AfterTest
	public void afterTest() {
	}

	@Test
	public void main() {
		enviaUmEmailDeTesteParamaisDeUmDestinatario();
	}
	
	 public void enviaUmEmailDeTesteParamaisDeUmDestinatario(){
		  
	        List<Usuario> destinatarios = new LinkedList<Usuario>();
	        Usuario u1 = new Usuario();
	        Usuario u2 = new Usuario();
	        Usuario u3 = new Usuario();
	        Usuario u4 = new Usuario();
	        u1.setNome("Caio");
	       u1.setEmail("ccacsurf@gmail.com");
	        u2.setNome("Paulo Beleza");
	        u2.setEmail("paulo.beleza@gmail.com");
	        u3.setNome("Marcelo");
	        u3.setEmail("celoguimaraes@gmail.com");
	        u4.setNome("ContaFakeE-Mail");
	        u4.setEmail("contafakemail@gmail.com");
	        destinatarios.add(u1);
	        destinatarios.add(u2);
	        destinatarios.add(u3);
	        mailSend.enviaDestinatarios( destinatarios, "TESTE ASSUNTO ENVIO DE E-MAIL","TESTE CORPO DA MENSAGEM ");
	 }
	
	
}
