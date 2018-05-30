package com.managed.bean;

import java.util.HashSet;

import com.model.Usuario;

public interface ISessaoDoUsuario {

	public abstract void limpar();

	public abstract boolean isUsuarioLogado();
	
	// VALIDACOES SOBRE PERFIL +++++++++++++++
	public abstract boolean isAdministrador();

	public abstract boolean isColetador();

	public abstract boolean isTecnico();

	public abstract boolean isVisualizador();

	// VALIDACOES SOBRE ACAO CORRENTE +++++++++++++++
	public abstract boolean isListar();

	public abstract boolean isEditar();

	public abstract boolean isCriar();

	public abstract boolean isVisualizar();

	public abstract boolean isAnalisar();

	public abstract boolean isAlterarSenha();

	public abstract boolean isAlterarDadosCadastrais();

	// VALIDACOES SOBRE ACAO +++++++++++++++

	// VALIDACOES SOBRE FUNCAO +++++++++++++++
	public abstract boolean isPodeCadastrarUsuario();

	public abstract boolean isPodeEditarUsuario();

	public abstract boolean isPodeVisualizarUsuario();

	public abstract boolean isPodePesquisarUsuario();

	public abstract boolean isPodeCadastrarCliente();

	public abstract boolean isPodeEditarCliente();

	public abstract boolean isPodeVisualizarCliente();

	public abstract boolean isPodePesquisarCliente();

	public abstract boolean isPodeCadastrarMicrorganismo();

	public abstract boolean isPodeEditarMicrorganismo();

	public abstract boolean isPodeVisualizarMicrorganismo();

	public abstract boolean isPodePesquisarMicrorganismo();

	public abstract boolean isPodeCadastrarSolicitacao();

	public abstract boolean isPodeEditarSolicitacao();

	public abstract boolean isPodeVisualizarSolicitacao();

	public abstract boolean isPodePesquisarSolicitacao();

	public abstract boolean isPodeAnalisarSolicitacao();

	public abstract boolean isPodeDeletarSolicitacao();

	public abstract boolean isPodeCadastrarAmostra();

	public abstract boolean isPodeDeletarAmostra();

	public abstract boolean isPodeEditarAmostra();

	public abstract boolean isPodeVisualizarAmostra();

	public abstract boolean isPodePesquisarAmostra();

	public abstract boolean isPodeAnalisarAmostra();

	public abstract boolean isPodeVisualizarIndicadores();

	public abstract boolean isPodeAlterarConfiguracoes();

	public abstract boolean isPodeConsultarNotificacoes();

	public abstract boolean isPodeVisualizarNotificacao();

	public abstract Usuario getUsuario();

	public abstract void setUsuario(Usuario usuarioLogado);

	public abstract void setAcaoCorrente(String acaoCorrente);

	public abstract HashSet<String> getPerfis();

	public abstract void setPerfis(HashSet<String> perfis);

	public abstract HashSet<String> getFuncoes();

	public abstract void setFuncoes(HashSet<String> funcoes);
	
	public abstract boolean isUsuarioBioqualitas();
	
	public abstract void setUsuarioBioqualitas(boolean flag);
	

}