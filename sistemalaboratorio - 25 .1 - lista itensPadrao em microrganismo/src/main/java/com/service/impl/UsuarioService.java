package com.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dao.IUsuarioDao;
import com.model.Cliente;
import com.model.Perfil;
import com.model.Usuario;
import com.service.IPerfilService;
import com.service.IUsuarioService;
import com.service.mail.IMailService;
import com.util.Criptografia;
import com.util.PassPhrase;
import com.util.enums.AcoesLog;
import com.util.enums.Perfis;

@Transactional(readOnly = true)
public class UsuarioService extends Service implements Serializable,
		IUsuarioService {

	private static final long serialVersionUID = 1L;

	private IUsuarioDao usuarioDAO;
	private IPerfilService perfilService;
	private IMailService mailSend;

	public UsuarioService() {
	}

	@Override
	@Transactional(readOnly = false)
	public void adicionar(Usuario usuario) {
		if (!isExisteUsuario(usuario)) {

			final String novaSenha = PassPhrase.getNext();
			usuario.setSenha(Criptografia.encodePassword(novaSenha));
			getUserDAO().adicionar(usuario);
			getMailSend().enviaCadastroUsuario(usuario, novaSenha);

			logar(AcoesLog.adicionou, usuario);
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void adicionarClientePf(Cliente cliente) {
		
		Usuario usuario = new Usuario();
		usuario.setAtivo(true);
		usuario.setCliente(cliente);
		usuario.setNome(cliente.getNome());
		usuario.setContatoPrincipal(true);
		usuario.setEmail(cliente.getEmail());
		//usuario.setDddTelCelular(cliente.getDddtelefone());
		//usuario.setTelCelular(cliente.getTelefone());
		usuario.setDddTelComercial(cliente.getDddtelefone());
		usuario.setTelComercial(cliente.getTelefone());
		Perfil p = new Perfil();
		p.setNome(Perfis.VISUALIZADOR.toString());
		Perfil perfil = getPerfilService().recuperarPorNome(p.getNome());
		ArrayList<Perfil> perfis = new ArrayList<Perfil>();
		if (perfil != null) {
			perfis.add(perfil);
			usuario.setPerfis(perfis);
		}
		
		if (!isExisteUsuario(usuario)) {

			final String novaSenha = PassPhrase.getNext();
			usuario.setSenha(Criptografia.encodePassword(novaSenha));
			getUserDAO().adicionar(usuario);
			getMailSend().enviaCadastroUsuario(usuario, novaSenha);

			logar(AcoesLog.adicionou, usuario);
		}
	}
	
	

	@Override
	@Transactional(readOnly = false)
	public void delete(Usuario usuario) {
		Usuario usuarioPersistido = getById(usuario);
		usuarioPersistido.setAtivo(false);
		getUserDAO().atualizar(usuarioPersistido);

		logar(AcoesLog.deletou, usuario);
	}

	@Override
	@Transactional(readOnly = false)
	public void atualizar(Usuario usuario) {
		if (usuario.getSenha() == null || usuario.getPerfis() == null) {
			Usuario u = getById(usuario);

			if (usuario.getSenha() == null) {
				usuario.setSenha(u.getSenha());
			}
			if (usuario.getPerfis() == null) {
				usuario.setPerfis(u.getPerfis());
			}
		}
		getUserDAO().atualizar(usuario);

		logar(AcoesLog.atualizou, usuario);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void atualizarClientePf(Cliente cliente) {
		Usuario usuarioPorAmostra = buscaPorEmail(cliente.getEmail());
		if(usuarioPorAmostra!=null){
			usuarioPorAmostra.setCliente(cliente);
			usuarioPorAmostra.setNome(cliente.getNome());
			usuarioPorAmostra.setEmail(cliente.getEmail());
			usuarioPorAmostra.setDddTelCelular(cliente.getTelefone());
			usuarioPorAmostra.setDddTelComercial(cliente.getTelefone());
			
			getUserDAO().atualizar(usuarioPorAmostra);
			logar(AcoesLog.atualizou, usuarioPorAmostra);
		}
	}
	
	@Override
	public boolean isExisteUsuario(Usuario usuario) {
		Usuario usuarioPorAmostra = new Usuario();
		usuarioPorAmostra.setEmail(usuario.getEmail());
		//** Mesmo que seja desativado, nao poderar cadastrar 2 e-mails iguais
		usuarioPorAmostra.setAtivo(null);

		List<Usuario> resultado = null;
		try {
			resultado = getUserDAO().recuperarPorAtributoPreenchido(usuarioPorAmostra);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultado != null && !resultado.isEmpty();
	}

	@Override
	public Usuario buscaPorClienteContatoPrincipal(Cliente cliente) {
		Usuario usuarioPorAmostra = new Usuario();
		usuarioPorAmostra.setCliente(cliente);
		usuarioPorAmostra.setContatoPrincipal(true);
		usuarioPorAmostra.setAtivo(true);

		Usuario usuarioEncontrado = null;

		List<Usuario> retorno = null;
		try {
			retorno = getUserDAO().recuperarPorAtributoPreenchido(
					usuarioPorAmostra);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (retorno != null && retorno.size() == 1) {
			usuarioEncontrado = retorno.get(0);
		}

		return usuarioEncontrado;
	}

	@Override
	public List<Usuario> buscaPorCliente(Cliente cliente) {
		Usuario usuarioPorAmostra = new Usuario();
		usuarioPorAmostra.setCliente(cliente);
		//usuarioPorAmostra.setContatoPrincipal(true);
		usuarioPorAmostra.setAtivo(true);

		Usuario usuarioEncontrado = null;

		List<Usuario> retorno = null;
		try {
			retorno = getUserDAO().recuperarPorAtributoPreenchido(usuarioPorAmostra);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public boolean clientePossuiContatoPrincipal(Cliente cliente) {
		final boolean resultado = buscaPorClienteContatoPrincipal(cliente) != null;
		return resultado;
	}

	@Override
	@Transactional(readOnly = false)
	public void resetSenha(Usuario usuario) {

		Usuario userPersistido = getById(usuario);
		final String novaSenha = PassPhrase.getNext();
		userPersistido.setSenha(Criptografia.encodePassword(novaSenha));
		getUserDAO().atualizar(userPersistido);
		getMailSend().resetaSenha(userPersistido, novaSenha);
		

		logar(AcoesLog.resetouSenha, userPersistido);
	}

	@Override
	public Usuario getById(Usuario usuario) {
		return getUserDAO().recuperar(usuario.getClass(),
				usuario.getIdUsuario());
	}
	
	@Override
	public Usuario buscaPorEmail(String email) {
		
		Usuario usuarioPorAmostra = new Usuario();
		usuarioPorAmostra.setAtivo(true);
		usuarioPorAmostra.setEmail(email);
		
		Usuario usuarioEncontrado = null;

		List<Usuario> retorno = null;
		try {
			retorno = getUserDAO().recuperarPorAtributoPreenchido(
					usuarioPorAmostra);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (retorno != null && retorno.size() == 1) {
			usuarioEncontrado = retorno.get(0);
		}

		return usuarioEncontrado;
		
		
	
	}


	@Override
	public Usuario getByIdComLista(Usuario usuario) {
		return getUserDAO().recuperarComLista(usuario.getIdUsuario());
	}

	@Override
	public List<Usuario> getAll() {
		Usuario usuarioPorAmostra = new Usuario();
		usuarioPorAmostra.setAtivo(true);
		try {
			return getUserDAO().recuperarPorAtributoPreenchido(usuarioPorAmostra);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Usuario buscaPorLogin(String login, String senha) {

		Usuario usuarioPorAmostra = new Usuario();
		usuarioPorAmostra.setEmail(login);
		usuarioPorAmostra.setSenha(Criptografia.encodePassword(senha));
		usuarioPorAmostra.setAtivo(true);

		List<Usuario> retornoBanco = null;
		try {
			retornoBanco = getUserDAO().recuperarPorAtributoPreenchido(
					usuarioPorAmostra);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Usuario userPersistido = null;
		if (retornoBanco != null && retornoBanco.size() == 1) {
			userPersistido = retornoBanco.get(0);
		}
		return userPersistido;
	}

	@Override
	public List<Usuario> getColetadores() {
		Perfil p = new Perfil();
		p.setNome(Perfis.COLETADOR.toString());
		Perfil perfil = getPerfilService().recuperarPorNome(p.getNome());
		List<Usuario> userListColetador = new ArrayList<Usuario>();
		ArrayList<Perfil> perfis = new ArrayList<Perfil>();
		if (perfil != null) {
			perfis.add(perfil);
			userListColetador = getUserDAO().recuperarPorPerfil(perfis);
		}
		return userListColetador;
	}

	@Override
	public List<Usuario> getTecnicos() {
		Perfil p = new Perfil();
		p.setNome(Perfis.TECNICO.toString());
		Perfil perfil = getPerfilService().recuperarPorNome(p.getNome());
		List<Usuario> userListTecnico = new ArrayList<Usuario>();
		ArrayList<Perfil> perfis = new ArrayList<Perfil>();
		if (perfil != null) {
			perfis.add(perfil);
			userListTecnico = getUserDAO().recuperarPorPerfil(perfis);
		}
		return userListTecnico;
	}

	@Override
	public List<Usuario> getAdministradores() {
		Perfil p = new Perfil();
		p.setNome(Perfis.ADMINISTRADOR.toString());
		Perfil perfil = getPerfilService().recuperarPorNome(p.getNome());
		List<Usuario> userListAdministradores = new ArrayList<Usuario>();
		ArrayList<Perfil> perfis = new ArrayList<Perfil>();
		if (perfil != null) {
			perfis.add(perfil);
			userListAdministradores = getUserDAO().recuperarPorPerfil(perfis);
		}
		return userListAdministradores;
	}

	@Override
	public Usuario recuperar(Integer id) {
		return getUserDAO().recuperar(Usuario.class, id);
	}

	private IUsuarioDao getUserDAO() {
		return usuarioDAO;
	}

	public void setUserDAO(IUsuarioDao userDAO) {
		this.usuarioDAO = userDAO;
	}

	private IMailService getMailSend() {
		return mailSend;
	}

	public void setMailSend(IMailService mailSend) {
		this.mailSend = mailSend;
	}

	private IPerfilService getPerfilService() {
		return perfilService;
	}

	public void setPerfilService(IPerfilService perfilService) {
		this.perfilService = perfilService;
	}

}
