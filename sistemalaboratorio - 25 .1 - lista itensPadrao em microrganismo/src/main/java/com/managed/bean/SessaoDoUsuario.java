package com.managed.bean;

import java.io.Serializable;
import java.util.HashSet;

import com.model.Usuario;
import com.util.enums.Acoes;
import com.util.enums.Funcoes;
import com.util.enums.Perfis;

public class SessaoDoUsuario implements Serializable, ISessaoDoUsuario {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private boolean usuarioBioqualitas;
	private HashSet<String> perfis;
	private HashSet<String> funcoes;
	private String acaoCorrente;


	public SessaoDoUsuario() {
		limpar();
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#limpar()
	 */
	@Override
	public void limpar() {
		setUsuario(null);
		setUsuarioBioqualitas(false);
		setPerfis(null);
		setFuncoes(null);
		setAcaoCorrente(null);
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isUsuarioLogado()
	 */
	@Override
	public boolean isUsuarioLogado() {
		return usuario != null ? true : false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isUsuarioBioqualitas(boolean)
	 */
	@Override
	public boolean isUsuarioBioqualitas() {
		return this.usuarioBioqualitas;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#setUsuarioBioqualitas(boolean)
	 */
	@Override
	public void setUsuarioBioqualitas(boolean flag) {
		this.usuarioBioqualitas=flag;		
	}

	// VALIDACOES SOBRE PERFIL +++++++++++++++
	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isAdministrador()
	 */
	@Override
	public boolean isAdministrador() {
		if (getPerfis() != null)
			return getPerfis().contains(Perfis.ADMINISTRADOR.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isColetador()
	 */
	@Override
	public boolean isColetador() {
		if (getPerfis() != null)
			return getPerfis().contains(Perfis.COLETADOR.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isTecnico()
	 */
	@Override
	public boolean isTecnico() {
		if (getPerfis() != null)
			return getPerfis().contains(Perfis.TECNICO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isVisualizador()
	 */
	@Override
	public boolean isVisualizador() {
		if (getPerfis() != null)
			return getPerfis().contains(Perfis.VISUALIZADOR.toString());
		else
			return false;
	}

	// VALIDACOES SOBRE PERFIL +++++++++++++++

	// VALIDACOES SOBRE ACAO CORRENTE +++++++++++++++
	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isListar()
	 */
	@Override
	public boolean isListar() {
		return Acoes.LISTAR.toString().equals(getAcaoCorrente());
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isEditar()
	 */
	@Override
	public boolean isEditar() {
		return Acoes.EDITAR.toString().equals(getAcaoCorrente());
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isCriar()
	 */
	@Override
	public boolean isCriar() {
		return Acoes.CRIAR.toString().equals(getAcaoCorrente());
	}
	
	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isVisualizar()
	 */
	@Override
	public boolean isVisualizar() {
		return Acoes.VISUALIZAR.toString().equals(getAcaoCorrente());
	}
	
	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isAnalisar()
	 */
	@Override
	public boolean isAnalisar() {
		return Acoes.ANALISAR.toString().equals(getAcaoCorrente());
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isAlterarSenha()
	 */
	@Override
	public boolean isAlterarSenha() {
		return Acoes.ALTERARSENHA.toString().equals(getAcaoCorrente());
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isAlterarDadosCadastrais()
	 */
	@Override
	public boolean isAlterarDadosCadastrais() {
		return Acoes.ALTERARDADOSCADASTRAIS.toString().equals(getAcaoCorrente());
	}
	// VALIDACOES SOBRE ACAO +++++++++++++++


	// VALIDACOES SOBRE FUNCAO +++++++++++++++
	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeCadastrarUsuario()
	 */
	@Override
	public boolean isPodeCadastrarUsuario() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.CADASTRARUSUARIO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeEditarUsuario()
	 */
	@Override
	public boolean isPodeEditarUsuario() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.EDITARUSUARIO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeVisualizarUsuario()
	 */
	@Override
	public boolean isPodeVisualizarUsuario() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.VISUALIZARUSUARIO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodePesquisarUsuario()
	 */
	@Override
	public boolean isPodePesquisarUsuario() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.PESQUISARUSUARIO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeCadastrarCliente()
	 */
	@Override
	public boolean isPodeCadastrarCliente() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.CADASTRARCLIENTE.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeEditarCliente()
	 */
	@Override
	public boolean isPodeEditarCliente() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.EDITARCLIENTE.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeVisualizarCliente()
	 */
	@Override
	public boolean isPodeVisualizarCliente() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.VISUALIZARCLIENTE.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodePesquisarCliente()
	 */
	@Override
	public boolean isPodePesquisarCliente() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.PESQUISARCLIENTE.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeCadastrarMicrorganismo()
	 */
	@Override
	public boolean isPodeCadastrarMicrorganismo() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.CADASTRARMICRORGANISMO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeEditarMicrorganismo()
	 */
	@Override
	public boolean isPodeEditarMicrorganismo() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.EDITARMICRORGANISMO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeVisualizarMicrorganismo()
	 */
	@Override
	public boolean isPodeVisualizarMicrorganismo() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.VISUALIZARMICRORGANISMO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodePesquisarMicrorganismo()
	 */
	@Override
	public boolean isPodePesquisarMicrorganismo() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.PESQUISARMICRORGANISMO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeCadastrarSolicitacao()
	 */
	@Override
	public boolean isPodeCadastrarSolicitacao() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.CADASTRARSOLICITACAO.toString());
		else
			return false;
	}
	

	@Override
	public boolean isPodeDeletarSolicitacao() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.DELETARSOLICITACAO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeEditarSolicitacao()
	 */
	@Override
	public boolean isPodeEditarSolicitacao() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.EDITARSOLICITACAO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeVisualizarSolicitacao()
	 */
	@Override
	public boolean isPodeVisualizarSolicitacao() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.VISUALIZARSOLICITACAO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodePesquisarSolicitacao()
	 */
	@Override
	public boolean isPodePesquisarSolicitacao() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.PESQUISARSOLICITACAO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeAnalisarSolicitacao()
	 */
	@Override
	public boolean isPodeAnalisarSolicitacao() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.ANALISARSOLICITACAO.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeCadastrarAmostra()
	 */
	@Override
	public boolean isPodeCadastrarAmostra() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.CADASTRARAMOSTRA.toString());
		else
			return false;
	}
	
	@Override
	public boolean isPodeDeletarAmostra() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.DELETARAMOSTRA.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeEditarAmostra()
	 */
	@Override
	public boolean isPodeEditarAmostra() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.EDITARAMOSTRA.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeVisualizarAmostra()
	 */
	@Override
	public boolean isPodeVisualizarAmostra() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.VISUALIZARAMOSTRA.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodePesquisarAmostra()
	 */
	@Override
	public boolean isPodePesquisarAmostra() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.PESQUISARAMOSTRA.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeAnalisarAmostra()
	 */
	@Override
	public boolean isPodeAnalisarAmostra() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.ANALISARAMOSTRA.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeVisualizarIndicadores()
	 */
	@Override
	public boolean isPodeVisualizarIndicadores() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.VISUALIZARINDICADORES.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeAlterarConfiguracoes()
	 */
	@Override
	public boolean isPodeAlterarConfiguracoes() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.ALTERARCONFIGURACOES.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeConsultarNotificacoes()
	 */
	@Override
	public boolean isPodeConsultarNotificacoes() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.CONSULTARNOTIFICACOES.toString());
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#isPodeVisualizarNotificacao()
	 */
	@Override
	public boolean isPodeVisualizarNotificacao() {
		if (getFuncoes() != null)
			return getFuncoes().contains(Funcoes.VISUALIZARNOTIFICACAO.toString());
		else
			return false;
	}

	// VALIDACOES SOBRE FUNCAO +++++++++++++++


	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#getUsuario()
	 */
	@Override
	public Usuario getUsuario() {
		return usuario;
	}
	
	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#setUsuario(com.model.Usuario)
	 */
	@Override
	public void setUsuario(Usuario usuarioLogado) {
		this.usuario = usuarioLogado;
	}

	private String getAcaoCorrente() {
		return acaoCorrente;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#setAcaoCorrente(java.lang.String)
	 */
	@Override
	public void setAcaoCorrente(String acaoCorrente) {
		this.acaoCorrente = acaoCorrente;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#getPerfis()
	 */
	@Override
	public HashSet<String> getPerfis() {
		return perfis;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#setPerfis(java.util.HashSet)
	 */
	@Override
	public void setPerfis(HashSet<String> perfis) {
		this.perfis = perfis;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#getFuncoes()
	 */
	@Override
	public HashSet<String> getFuncoes() {
		return funcoes;
	}

	/* (non-Javadoc)
	 * @see com.managed.bean.ISessaoDoUsuario#setFuncoes(java.util.HashSet)
	 */
	@Override
	public void setFuncoes(HashSet<String> funcoes) {
		this.funcoes = funcoes;
	}

}
