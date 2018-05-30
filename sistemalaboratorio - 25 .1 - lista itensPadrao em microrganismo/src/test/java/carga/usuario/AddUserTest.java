package carga.usuario;

import inicializacao.InicializacaoSpring;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.model.Cliente;
import com.model.Funcao;
import com.model.Perfil;
import com.model.Usuario;
import com.service.IClienteService;
import com.service.IFuncaoService;
import com.service.IPerfilService;
import com.service.IUsuarioService;
import com.util.Messages;
import com.util.enums.Funcoes;
import com.util.enums.Perfis;

public class AddUserTest {
	
	private static IClienteService cS;
	private static IUsuarioService uS;
	private static IPerfilService pS;
	private static IFuncaoService fS;

	@BeforeClass
	public static void setUpClass() {
		InicializacaoSpring.setUpClass();
		setcS( (IClienteService) InicializacaoSpring.ctx.getBean("ClienteService"));
		setuS((IUsuarioService) InicializacaoSpring.ctx
				.getBean("UsuarioService"));
		setpS((IPerfilService) InicializacaoSpring.ctx.getBean("PerfilService"));
		setfS((IFuncaoService) InicializacaoSpring.ctx.getBean("FuncaoService"));
	}

	@BeforeMethod
	public void setUp() {
		System.out.println("================================================");
	}

	@AfterTest
	public void afterTest() {
	}

	@Test
	public void main() {
		cargaAdministradorETodosPerfisEFuncoes();
	}
	
	private void cargaAdministradorETodosPerfisEFuncoes() {
		try {

			List<Funcao> funcoes = criarFuncoes();

			List<Perfil> perfis = criarPerfis();
			Perfil p = perfis.get(0);

			List<Cliente> clientes = criarClientes();
			Cliente c = clientes.get(0);

			Usuario u = new Usuario();

			u.setAtivo(true);
			u.setCliente(c);
			u.setContatoPrincipal(true);
			u.setDddTelCelular("21");
			u.setDddTelComercial("21");
			u.setEmail("adm@bq.com");
			u.setNome("Usuario 1");
			u.setPerfis(new ArrayList<Perfil>());
			u.getPerfis().add(p);
			u.setRamalTelComercial("3333");
			u.setSenha("adm");
			u.setTelCelular("111111111");
			u.setTelComercial("111111111");

			getuS().adicionar(u);

			Messages.addErrorBundleMessage("!!! CARGA EXECUTADA COM SUCESSO !!!");
		} catch (Exception e) {
			Messages.addErrorBundleMessage("ERRO NA CARGA -->" + e.getMessage());
		}
	}

	private List<Cliente> criarClientes() {

		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		Cliente c = new Cliente();

		c.setAtivo(true);
		c.setNome("Cliente 1");
		c.setCpfcnpj("11111111111");
		c.setEmail("a@bq.com");
		c.setEndereco("texto livre");
		c.setAlias("C1");

		getcS().adicionar(c);
		c = getcS().getByCpfCnpj(c);
		clientes.add(c);
		return clientes;
	}

	private List<Perfil> criarPerfis() {
		ArrayList<Perfil> perfis = new ArrayList<Perfil>();
		Funcao f = null;
		Perfil p = new Perfil();

		p.setAtivo(true);
		p.setNome(Perfis.ADMINISTRADOR.toString());

		p.setFuncoes(new ArrayList<Funcao>());
		f = new Funcao();
		f.setNome(Funcoes.ALTERARCONFIGURACOES.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARINDICADORES.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.CADASTRARCLIENTE.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.EDITARCLIENTE.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.PESQUISARCLIENTE.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARCLIENTE.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.CADASTRARUSUARIO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.EDITARUSUARIO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.PESQUISARUSUARIO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARUSUARIO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.CADASTRARMICRORGANISMO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.EDITARMICRORGANISMO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.PESQUISARMICRORGANISMO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARMICRORGANISMO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.PESQUISARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.PESQUISARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.CONSULTARNOTIFICACOES.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARNOTIFICACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));

		getpS().add(p);
		p = getpS().recuperarPorNome(p.getNome());
		perfis.add(p);

		p = new Perfil();

		p.setAtivo(true);
		p.setNome(Perfis.COLETADOR.toString());
		p.setFuncoes(new ArrayList<Funcao>());
		f = new Funcao();
		f.setNome(Funcoes.CADASTRARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.CADASTRARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.CONSULTARNOTIFICACOES.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.EDITARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.EDITARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.PESQUISARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.PESQUISARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARNOTIFICACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));

		getpS().add(p);
		p = getpS().recuperarPorNome(p.getNome());
		perfis.add(p);

		p = new Perfil();

		p.setAtivo(true);
		p.setNome(Perfis.TECNICO.toString());

		p.setFuncoes(new ArrayList<Funcao>());
		f = new Funcao();
		f.setNome(Funcoes.ANALISARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.ANALISARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.CONSULTARNOTIFICACOES.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.PESQUISARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.PESQUISARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARNOTIFICACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));

		getpS().add(p);
		p = getpS().recuperarPorNome(p.getNome());
		perfis.add(p);

		p = new Perfil();

		p.setAtivo(true);
		p.setNome(Perfis.VISUALIZADOR.toString());

		p.setFuncoes(new ArrayList<Funcao>());
		f = new Funcao();
		f.setNome(Funcoes.CONSULTARNOTIFICACOES.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.PESQUISARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.PESQUISARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARNOTIFICACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.VISUALIZARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));

		getpS().add(p);
		p = getpS().recuperarPorNome(p.getNome());
		perfis.add(p);

		return perfis;
	}

	private List<Funcao> criarFuncoes() {
		ArrayList<Funcao> funcoes = new ArrayList<Funcao>();

		for (Funcoes funcao : Funcoes.values()) {
			Funcao f = new Funcao();
			f.setAtivo(true);
			f.setNome(funcao.toString());

			getfS().add(f);
			f = getfS().getByNome(f).get(0);
			funcoes.add(f);
		}

		return funcoes;
	}

	// getters and setters

	private static IClienteService getcS() {
		return cS;
	}

	private static void setcS(IClienteService cS) {
		AddUserTest.cS = cS;
	}

	private static IUsuarioService getuS() {
		return uS;
	}

	private static void setuS(IUsuarioService uS) {
		AddUserTest.uS = uS;
	}

	private static IPerfilService getpS() {
		return pS;
	}

	private static void setpS(IPerfilService pS) {
		AddUserTest.pS = pS;
	}

	private IFuncaoService getfS() {
		return fS;
	}

	private static void setfS(IFuncaoService fS) {
		AddUserTest.fS = fS;
	}
}
