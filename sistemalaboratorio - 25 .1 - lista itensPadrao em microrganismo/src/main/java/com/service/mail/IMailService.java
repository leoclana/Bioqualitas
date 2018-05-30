package com.service.mail;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;

import com.model.Usuario;

public interface IMailService {

	public abstract void  enviaCadastroUsuario(Usuario userMail, String passwd);
	public abstract void  resetaSenha(Usuario userMail, String passwd);
	public abstract void  enviaDestinatarios(List<Usuario> sendTo,String assunto, String corpo);
	public abstract void  enviarParaAnalise(List<Usuario> userListTecnico,String idSolicitacao);
	public abstract void  enviarParaAprovacao(List<Usuario> userAdministradores, String idSolicitacao);
	public abstract void invalidarAmostra(List<Usuario> users,	String idAmostra,String justificativa);
	public abstract void  liberarParaCliente(List<Usuario> userSolicitante,	String idSolicitacao);
	public abstract void liberarParaCliente(List<Usuario> userSolicitante,
			String idSolicitacao, ByteArrayResource[] anexo);
	

}