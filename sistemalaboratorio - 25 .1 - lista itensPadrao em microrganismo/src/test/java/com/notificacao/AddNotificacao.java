package com.notificacao;

import inicializacao.InicializacaoSpring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.model.Notificacao;
import com.model.Usuario;
import com.service.INotificacaoService;
import com.service.IUsuarioService;
import com.util.Messages;

public class AddNotificacao {

	private static INotificacaoService notificacaoService;
	private static IUsuarioService usuarioService;

	@BeforeClass
	public static void setUpClass() {
		InicializacaoSpring.setUpClass();
		setNotificacaoService((INotificacaoService) InicializacaoSpring.ctx.getBean("NotificacaoService"));
		setUsuarioService((IUsuarioService) InicializacaoSpring.ctx.getBean("UsuarioService"));
	}

	@BeforeMethod
	public void setUp() {
	}

	@AfterTest
	public void afterTest() {
	}

	@Test
	public void main() {
		adicionaDocNotificacao();
		listarNotificacoes();
		//adicionaUsuariosArquivados();
	}

	private void listarNotificacoes() {
		List<Usuario> usuarios = getUsuarioService().getAll();
		for (Usuario u : usuarios) {
			List<Notificacao> notificacaoList = new ArrayList();
			notificacaoList = getNotificacaoService().getAll(u);
			for (Notificacao notificacao : notificacaoList) {
				System.out.println("Notificacao titulo: " +notificacao.getTitulo());
				System.out.println("Notificacao descri��o: " + notificacao.getDescricao());
			}
		}
	}

	private void adicionaDocNotificacao() {

		try {
			List<Usuario> usuarios = getUsuarioService().getAll();
			for (Usuario usuario : usuarios) {
				//		if(usuario.getIdUsuario().equals(1)){
				for(int intCount=1;intCount<=3;intCount++){

					List<Usuario> users = new ArrayList<Usuario>();
					users.add(usuario);
					Notificacao notificacao = new Notificacao();
					notificacao.setTitulo("titulo teste " + intCount);
					notificacao.setDescricao("descri��o teste " + intCount);
					notificacao.setUsuarios(users);
					getNotificacaoService().salvar(notificacao);
				}
				//	}
			}

		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA CRIA��O DA NOTIFICACAO -->"+ e.getMessage());
		}
	}

	/*	private void adicionaUsuariosArquivados() {
		try {
			List<Usuario> userList = getUsuarioService().getAll();
			List<Usuario> uArquivados = new ArrayList<Usuario>();
			List<Notificacao> ns = getNotificacaoService()
					.getAll(userList.get(0));
			Notificacao n = ns.get(0);
			for (Usuario usuario : userList) {
				if (usuario.getIdUsuario().equals(2)
						|| usuario.getIdUsuario().equals(4)) {
					uArquivados.add(usuario);
				}
			}

		///	n.setUsuariosArquivados(uArquivados);

			Notificacao notificacao = new Notificacao();
			getNotificacaoService().atualizar(notificacao);

		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA CRIA��O DA NOTIFICACAO -->"
					+ e.getMessage());
		}
	}
	 */

	public static INotificacaoService getNotificacaoService() {
		return notificacaoService;
	}

	public static void setNotificacaoService(
			INotificacaoService notificacaoService) {
		AddNotificacao.notificacaoService = notificacaoService;
	}

	public static IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public static void setUsuarioService(IUsuarioService usuarioService) {
		AddNotificacao.usuarioService = usuarioService;
	}

}
