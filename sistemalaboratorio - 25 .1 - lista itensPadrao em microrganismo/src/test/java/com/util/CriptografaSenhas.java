package com.util;

import inicializacao.InicializacaoSpring;

import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.model.Usuario;
import com.service.IUsuarioService;
 

public class CriptografaSenhas {

	
	static IUsuarioService us;
	
	@BeforeClass
	public static void setUpClass() {
		InicializacaoSpring.setUpClass();
		us = (IUsuarioService) InicializacaoSpring.ctx.getBean("UsuarioService");
	}
	
	@BeforeMethod
	public void setUp() {
	}

	@AfterTest
	public void afterTest() {
	}

	@Test
	public void main() {
		criptografaSenhas();
	}

	private void criptografaSenhas() {
		List<Usuario> listaUsuarios  = us.getAll();
		for(Usuario user:listaUsuarios){
			user.setSenha("123");
			String password = 	user.getSenha();
			user.setSenha(Criptografia.encodePassword(password));
			us.atualizar(user);
		}
	}
}
