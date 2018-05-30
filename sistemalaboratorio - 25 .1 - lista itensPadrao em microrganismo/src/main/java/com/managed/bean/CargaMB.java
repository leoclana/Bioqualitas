package com.managed.bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.Cliente;
import com.model.CondicaoAmostra;
import com.model.Configuracao;
import com.model.FormaPagamento;
import com.model.Funcao;
import com.model.Grupo;
import com.model.ItemGrupo;
import com.model.ItemPadrao;
import com.model.Laudo;
import com.model.Microrganismo;
import com.model.MotivoAnalise;
import com.model.Notificacao;
import com.model.Padrao;
import com.model.Perfil;
import com.model.Usuario;
import com.service.IClienteService;
import com.service.ICondicaoAmostraService;
import com.service.IConfiguracaoService;
import com.service.IFormaService;
import com.service.IFuncaoService;
import com.service.IGrupoService;
import com.service.IItemGrupoService;
import com.service.IItemPadraoService;
import com.service.ILaudoService;
import com.service.IMicrorganismoService;
import com.service.IMotivoService;
import com.service.INotificacaoService;
import com.service.IPadraoService;
import com.service.IPerfilService;
import com.service.IUsuarioService;
import com.util.Criptografia;
import com.util.Messages;
import com.util.enums.Funcoes;
import com.util.enums.Perfis;
import com.util.enums.TipoAnalise;

public class CargaMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private IClienteService cS;
	private IUsuarioService uS;
	private IPerfilService pS;
	private IFuncaoService fS;

	private IGrupoService grupoS;
	private IItemGrupoService itemGrupoS;
	private IPadraoService padraoS;
	private IItemPadraoService itemPadraoS;
	private IMicrorganismoService microrganismoS;
	private INotificacaoService ns;
	private IFormaService fps;
	private ICondicaoAmostraService fcs;
	private IConfiguracaoService fconfs;
	private IMotivoService motivoS;
	private ILaudoService laudoS;

	public CargaMB(IClienteService cS, IUsuarioService uS, IPerfilService pS, IFuncaoService fS, 
			IGrupoService grupoS, IItemGrupoService itemGrupoS, IPadraoService padraoS, IItemPadraoService itemPadraoS, IMicrorganismoService microrganismoS,
			INotificacaoService notifService, IFormaService fpS, ICondicaoAmostraService fcas, IConfiguracaoService fconfs,IMotivoService motivoS,ILaudoService laudoS) {
		setcS(cS);
		setfS(fS);
		setpS(pS);
		setuS(uS);

		setGrupoS(grupoS);
		setItemGrupoS(itemGrupoS);
		setPadraoS(padraoS);
		setItemPadraoS(itemPadraoS);
		setMicrorganismoS(microrganismoS);
		setNs(notifService);
		setFps(fpS);
		setFcs(fcas);
		setFconfs(fconfs);
		setMotivoS(motivoS);
		setLaudoS(laudoS);

	}

	// cargas

	public String adicionaFormaPagamento(){

		try{
			List<String> StrFormaPagamentoList = new ArrayList<String>();
			StrFormaPagamentoList.add("Em aberto");
			StrFormaPagamentoList.add("Pago a vista");
			StrFormaPagamentoList.add("Pago via Boleto");
			StrFormaPagamentoList.add("Depósito");

			for (String fpgto : StrFormaPagamentoList) {

				FormaPagamento fPagamento = new FormaPagamento();
				fPagamento.setNome(fpgto);


				List<FormaPagamento>	fpgtoAux = getFps().getByNome(fPagamento);

				if (fpgtoAux != null && (fpgtoAux.isEmpty() || fpgtoAux.size() == 0)) {
					getFps().add(fPagamento);

				}
			}

			Messages.addInfoTextMessage("Adicionado as formas de pagamento");

		}		catch (Exception e) {
			System.out.println(e.getMessage());
			Messages.addErrorTextMessage("ERRO NO adicionaFormaPagamento -->"+ e.getMessage());
		}
		return null;
	}


	public String testaInsert() {

		try{
			//Insere o Grupo de alimentos
			Grupo grupo = new Grupo();
			grupo.setNome("02");
			grupo.setDescricao("DESC");

			System.out.println("Grupo populado");

			if(!grupoS.isExiste(grupo)){
				System.out.println("Grupo nao encontrado");
				grupoS.add(grupo);
			}else{
				System.out.println("Grupo encontrado");
			}

		}catch(Exception e){
			System.out.println("ERRO: " + e.getMessage());
			Messages.addInfoTextMessage("ERRO no teste -->" + e.getMessage());
		}

		return null;
	}

	public void adicionaDocNotificacao() {

		try {
			List<Usuario> usuarios = getuS().getAll();
			for (Usuario usuario : usuarios) {

				System.out.println("Usuario: " + usuario.getNome());

				List<Usuario> users = new ArrayList<Usuario>();
				users.add(usuario);
				Notificacao notificacao = new Notificacao();
				notificacao.setTitulo("Nova análise");
				notificacao.setDescricao("Nova solicitação cadastrada");
				notificacao.setUsuarios(users);
				notificacao.setData("11/02/2015");
				notificacao.setStatus("Solicitacao Cadastrada");
				getNs().salvar(notificacao);

				Notificacao notificacao1 = new Notificacao();
				notificacao1.setTitulo("Solicitação enviada para análise");
				notificacao1.setDescricao("Solicitação enviada para análise");
				notificacao1.setUsuarios(users);
				notificacao1.setData("05/02/2015");
				notificacao1.setStatus("Solicitação enviada para análise");
				getNs().salvar(notificacao1);

				Notificacao notificacao2 = new Notificacao();
				notificacao2.setTitulo("Análise de amostra concluída");
				notificacao2.setDescricao("Análise de amostra concluída");
				notificacao2.setUsuarios(users);
				notificacao2.setData("20/01/2015");
				notificacao2.setStatus("Análise de amostra concluída");
				getNs().salvar(notificacao2);

			}					

			Messages.addInfoTextMessage("!!! NOTIFICAÇÕES ADICIONADAS COM SUCESSO !!!");

		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA CRICAO DA NOTIFICACAO -->"+ e.getMessage());
			e.printStackTrace();
		}
	}


	public String adicionaCondicaoAmostra(){
		try {

			List<String> condicoes = new ArrayList<String>();
			
			//condicoes.add("Íntegra/ Violada/ Parcialmente consumida");
			//condicoes.add("Temperatura apropriada/ inapropriada");
			//condicoes.add("Quantidade suficiente/ insuficiente");
			condicoes.add("Íntegra");
			condicoes.add("Violada");
			condicoes.add("Parcialmente consumida");
			condicoes.add("Temperatura apropriada");
			condicoes.add("Temperatura inapropriada");
			condicoes.add("Quantidade suficiente");
			condicoes.add("Quantidade insuficiente");
			condicoes.add("Outros");

			for (String condicao : condicoes) {
				CondicaoAmostra ca = new CondicaoAmostra();
				ca.setAtivo(true);
				ca.setNome(condicao);
				getFcs().add(ca);
			}

			Messages.addInfoTextMessage("!!! CARGA EXECUTADA COM SUCESSO: CONDICAO AMOSTRA !!!");
		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA CARGA CONDICAO AMOSTRA -->" + e.getMessage());
		}

		return null;
	}

	public String adicionaMotivoAnalise(){
		try {

			List<String> motivoList = new ArrayList<String>();

			motivoList.add("Contraprova");
			motivoList.add("Rotina");
			motivoList.add("Reclamação");
			motivoList.add("Suspeita de Surto");
			motivoList.add("Outros");

			for (String motivo : motivoList) {
				MotivoAnalise ca = new MotivoAnalise();
				ca.setAtivo(true);
				ca.setNome(motivo);
				getMotivoS().add(ca);
			}

			Messages.addInfoTextMessage("!!! CARGA EXECUTADA COM SUCESSO: MOTIVO ANALISE !!!");
		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA CARGA MOTIVO ANALISE -->" + e.getMessage());
		}

		return null;
	}
	
	static final String RESULTADO_A = "A";
	static final String RESULTADO_B = "B";
	static final String RESULTADO_C = "C";
	static final String RESULTADO_D = "D";
	
	
	
	public String adicionaLaudo(){
		try {

			Map<String,Map<TipoAnalise,List<String>>>  mapResult = new HashMap();
			Map<TipoAnalise,List<String>>  mapTipoAnalise = new HashMap<TipoAnalise,List<String>> ();
			
			List<String> listAux = new ArrayList<String>();
			listAux.add("A AMOSTRA APRESENTOU-SE DE ACORDO COM OS PADRÕES LEGAIS VIGENTES.");
			listAux.add("Apesar da amostra ter se apresentado de acordo com os padrões legais vigentes considerando o que é solicitado pela RDC 12, verificamos que o produto apresentou uma contagem total de bactérias em número acima do que costuma ter, indicando que pode estar em inicio de alteração sensorial sem causar comprometimento à saúde.");
			listAux.add("A AMOSTRA APRESENTOU-SE FORA DOS PADRÕES LEGAIS VIGENTES, DEVIDO A ALTERAÇÕES DAS SUAS CARACTERÍSTICAS SENSORIAIS E MICROBIOLÓGICAS. ");
			listAux.add("Apesar da amostra ter se apresentado de acordo com os padrões MICROBIOLÓGICOS legais vigentes considerando o que é solicitado pela RDC 12, OBSERVOU-SE ALTERAÇÕES DAS SUAS CARACTERÍSTICAS SENSORIAIS.");
			listAux.add("A AMOSTRA APRESENTOU-SE FORA DOS PADRÕES LEGAIS VIGENTES, DEVIDO A PRESENÇA DE (INDICAR MICRORGANISMOS ELEVADOS) COLIFORMES TERMOTOLERANTES EM QUANTIDADE SUPERIOR AO LIMITE TOLERADO.");
			listAux.add("A AMOSTRA APRESENTOU-SE DE ACORDO COM OS PADRÕES LEGAIS VIGENTES, NO QUE SE REFEREM AS DETERMINAÇÕES ANALÍTICAS EFETUADAS.");
			listAux.add("A AMOSTRA apresentou-se fora dos padrões legais vigentes, devido a presença de (INDICAR MICRORGANISMOS ELEVADO) em quantidade superior ao limite tolerado, no que se referem as determinações analíticas efetuadas.");
			mapTipoAnalise.put(TipoAnalise.ALIMENTO,listAux);
			mapResult.put(RESULTADO_D,mapTipoAnalise);
			mapTipoAnalise = new HashMap<TipoAnalise,List<String>> ();

			
			listAux = new ArrayList<String>();
			listAux.add("A MÃO DO FUNCIONÁRIO APRESENTOU BOA HIGIENE APÓS A ANTISSEPSIA/DURANTE A TAREFA * de acordo com o registro da amostra * de acordo com o registro da amostra.");
			mapTipoAnalise.put(TipoAnalise.MANIPULADOR,listAux);
			listAux = new ArrayList<String>();
			listAux.add("O RESULTADO INDICOU SUPERFÍCIE COM BOA HIGIENE.");
			mapTipoAnalise.put(TipoAnalise.SUPERFICIE,listAux);
			listAux = new ArrayList<String>();
			listAux.add("A AMOSTRA apresentou-se bacteriologicamente de acordo com os padrões legais vigentes.");
			mapTipoAnalise.put(TipoAnalise.AGUA,listAux);
			listAux = new ArrayList<String>();
			listAux.add("A AMOSTRA APRESENTOU-SE BACTERIOLOGICAMENTE DE ACORDO COM OS PADRÕES VIGENTES.");
			mapTipoAnalise.put(TipoAnalise.AGUAMINERAL,listAux);
			listAux = new ArrayList<String>();
			listAux.add("A AMOSTRA APRESENTOU CONDIÇÕES MICROBIOLÓGICAS satisfatórias no local avaliado.");
			mapTipoAnalise.put(TipoAnalise.AR,listAux);
			mapResult.put(RESULTADO_A,mapTipoAnalise);
			mapTipoAnalise = new HashMap<TipoAnalise,List<String>> ();
			
			listAux = new ArrayList<String>();
			listAux.add("A AMOSTRA apresentou-se bacteriologicamente de acordo com os padrões legais vigentes, considerando a referência da portaria supracitada (em referência) devido à ausência de Escherichia coli. Entretanto, verificou-se a presença elevada de contagem de Bactérias heterotróficas (acima de 500UFC/mL), indicando que há matéria orgânica dispersa na amostra que são resíduos que podem ser usados como nutrientes para as bactérias. É recomendável proceder a limpeza do sistema, EQUIPAMENTO, SUPERFÍCIE DE CONTATO e/ou reservatório, cloração ou troca da unidade filtrante para retirar esta matéria orgânica, ONDE APLICÁVEL.	");
			listAux.add("A AMOSTRA apresentou-se bacteriologicamente de acordo com os padrões legais vigentes, considerando a referência da portaria supracitada (em referência) devido à ausência de Escherichia coli. Entretanto, verificou-se a presença de Bactérias do Grupo Coliforme e elevada contagem de Bactérias heterotróficas. A presença destes microrganismos indica que há matéria orgânica dispersa na amostra que são resíduos que podem ser usados como nutrientes para as bactérias. É recomendável proceder a limpeza do sistema, EQUIPAMENTO, SUPERFÍCIE DE CONTATO e/ou reservatório, cloração ou troca da unidade filtrante para retirar esta matéria orgânica, ONDE APLICÁVEL.	");
			listAux.add("Apesar de não ter apresentado Escherichia coli na amostra analisada, estando dentro dos padrões previstos pela Legislação, verificou-se a presença de Coliformes fecais, Bactérias do Grupo Coliforme e elevada contagem de Bactérias heterotróficas. A presença destes microrganismos indica que há matéria orgânica dispersa na amostra que são resíduos que podem ser usados como nutrientes para as bactérias. É recomendável proceder a limpeza do sistema, EQUIPAMENTO, SUPERFÍCIE DE CONTATO e/ou reservatório, cloração ou troca da unidade filtrante para retirar esta matéria orgânica, ONDE APLICÁVEL.	");
			mapTipoAnalise.put(TipoAnalise.AGUA,listAux);
			mapResult.put(RESULTADO_B,mapTipoAnalise);
			mapTipoAnalise = new HashMap<TipoAnalise,List<String>> ();
			
			listAux = new ArrayList<String>();			
			listAux.add("A MÃO DO FUNCIONÁRIO APRESENTOU HIGIENE INSATISFATÓRIA APÓS A ANTISSEPSIA/DURANTE A TAREFA DEVIDO A PRESENÇA DE (INDICAR MICRORGANISMOS) . * de acordo com o registro da amostra ");
			listAux.add("A mão do funcionário apresentou ausência de Coliformes Termotolerantes e Estafilococos coagulase positiva APÓS A ANTISSEPSIA/DURANTE A TAREFA, porém alertamos para a grande quantidade de coliformes totais encontrados, indicando falhas na higiene.* de acordo com o registro da amostra");
			mapTipoAnalise.put(TipoAnalise.MANIPULADOR,listAux);
			listAux = new ArrayList<String>();	
			listAux.add("O RESULTADO INDICOU SUPERFÍCIE COM HIGIENE INSATISFATÓRIA, DEVIDO A PRESENÇA DE (INDICAR MICRORGANISMOS PRESENTE) E/OU CONTAGEM ACIMA DO LIMITE TOLERADO. *DE ACORDO COM O RESULTADO DE CADA MICROORGANISMO DA AMOSTRA");
			mapTipoAnalise.put(TipoAnalise.SUPERFICIE,listAux);
			listAux = new ArrayList<String>();	
			listAux.add("A amostra APRESENTOU-SE IMPRÓPRIA PARA O CONSUMO DEVIDO À PRESENÇA DE Escherichia coli.");
			listAux.add("Apesar da amostra ter se apresentado de acordo com os padrões bacterioLÓGICOS legais vigentes, OBSERVOU-SE ALTERAÇÕES DAS SUAS CARACTERÍSTICAS SENSORIAIS.");
			listAux.add("A AMOSTRA APRESENTOU-SE FORA DOS PADRÕES LEGAIS VIGENTES, DEVIDO A ALTERAÇÕES DAS SUAS CARACTERÍSTICAS SENSORIAIS E bacterioLÓGICAS.");
			mapTipoAnalise.put(TipoAnalise.AGUA,listAux);
			listAux = new ArrayList<String>();	
			listAux.add("A AMOSTRA APRESENTOU-SE BACTERIOLOGICAMENTE FORA DOS PADRÕES LEGAIS VIGENTES, DEVIDO A PRESENÇA DE (INDICAR O MICROORGANISMO) .");
			listAux.add("Apesar da amostra ter se apresentado de acordo com os padrões bacterioLÓGICOS legais vigentes, OBSERVOU-SE ALTERAÇÕES DAS SUAS CARACTERÍSTICAS SENSORIAIS.");
			listAux.add("A AMOSTRA APRESENTOU-SE FORA DOS PADRÕES LEGAIS VIGENTES, DEVIDO A ALTERAÇÕES DAS SUAS CARACTERÍSTICAS SENSORIAIS E bacterioLÓGICAS.");
			mapTipoAnalise.put(TipoAnalise.AGUAMINERAL,listAux);
			listAux = new ArrayList<String>();	
			listAux.add("A AMOSTRA APRESENTOU CONDIÇÕES MICROBIOLÓGICAS insatisfatórias no local avaliado.");
			mapTipoAnalise.put(TipoAnalise.AR,listAux);
			listAux = new ArrayList<String>();	
			mapResult.put(RESULTADO_C,mapTipoAnalise);
			mapTipoAnalise = new HashMap<TipoAnalise,List<String>> ();

			
			for (String resultado : mapResult.keySet()) {
				Map<TipoAnalise, List<String>> map = mapResult.get(resultado);
				for (TipoAnalise tipoAnalise : map.keySet()) {
					listAux = map.get(tipoAnalise);
					for (String laudo : listAux) {
						Laudo ca = new Laudo();
						ca.setAtivo(true);
						ca.setTipoAnalise(tipoAnalise);
						ca.setNome(laudo);
						ca.setResultado(resultado);
						getLaudoS().add(ca);
					}
				}
			}
			
			Messages.addInfoTextMessage("!!! CARGA EXECUTADA COM SUCESSO: LAUDO !!!");
		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA CARGA LAUDO -->" + e.getMessage());
		}

		return null;
	}

	public String adicionaConfiguracao(){
		try {
			Configuracao config  = getFconfs().getConfiguracao();
			if (config==null)
				config = new Configuracao();

			Calendar year = Calendar.getInstance();
			Integer anoCorrente = new Integer(year.get(Calendar.YEAR));
			String strAnoCorrente = new String(anoCorrente.toString());
			anoCorrente =   Integer.valueOf(strAnoCorrente.substring(2));
			config.setAnoCorrente(anoCorrente);
			config.setAgua(5);
			config.setAguaMineral(5);
			config.setAlimento(5);
			config.setAr(5);
			config.setManipulador(5);
			config.setSuperficie(5);
			config.setEncerramentodd(5);
			config.setEncerramentohh(5);

			if(config.getIdConfiguracao()!=0){
				getFconfs().atualizar(config);
			}else{			
				getFconfs().salvar(config);
			}
			Messages.addInfoTextMessage("!!! CONFIGURAÇÃO CRIADA COM SUCESSO !!!");
		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA CRIAÇÃO DA CONFIGURAÇÃO -->" + e.getMessage());
		}

		return null;
	}

	public String importaTabelaAlimentos() {

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "###";

		try {
			URL resource = getClass().getClassLoader().getResource("tabelaGruposAlimentos2.tsv");
			System.out.println("Found "+resource);	

			//String strResource = getClass().getClassLoader().getResource("tabelaGruposAlimentos.csv");
			//System.out.println("Found "+strResource);

			//** Correcao do ENCODING do arquivo de carga de alimentos para UTF-8.
			//br = new BufferedReader(new InputStreamReader(resource.openStream()));
			br = new BufferedReader(new InputStreamReader(resource.openStream(), "UTF-8"));
			//br = new BufferedReader(new InputStreamReader(resource.openStream(), "ISO-8859-1"));


			line = br.readLine();
			String[] registro = line.split(cvsSplitBy);
			String strPadrao = registro[5];

			System.out.println("GRUPO: " + registro[1] + " / " + registro[2]);
			System.out.println("ALIMENTO: " + registro[4] + " / " + registro[6]);
			System.out.println("PADRAO: " + registro[5]);

			Padrao padrao = new Padrao();
			padrao.setNome("RESOLUÇÃO ANVISA - RDC Nº 12, DE 2 DE JANEIRO DE 2001");
			padrao.setDescricao("Estabelecer os Padrões Microbiológicos Sanitários para Alimentos...");
			padraoS.add(padrao);
			padrao = padraoS.getByNome(padrao);

			//int intCount = 0;

			while (line != null) {

				//Insere o Grupo de alimentos
				Grupo grupo = new Grupo();
				grupo.setNome(registro[0]);
				grupo.setDescricao(registro[1]);

				if(!grupoS.isExiste(grupo)){
					grupoS.add(grupo);
				}

				grupo = grupoS.getByNome(grupo);

				//Insere o Alimento
				ItemGrupo itemGrupo = new ItemGrupo();
				itemGrupo.setGrupo(grupo);
				itemGrupo.setNome(registro[2]);
				itemGrupo.setDescricao(registro[3]);

				if(!itemGrupoS.isExiste(itemGrupo)) {
					itemGrupoS.add(itemGrupo);
				}

				itemGrupo = itemGrupoS.getByNomeIdGrupo(itemGrupo);

				strPadrao = registro[2];

				//Loop de Microrganismos
				while (line != null && strPadrao.equalsIgnoreCase(registro[2])) {

					//Insere o Microrganismo
					System.out.println("MICRORGANISMO: " + registro[4]);
					Microrganismo microrganismo = new Microrganismo();
					microrganismo.setAtivo(true);
					microrganismo.setNome(registro[4].trim());

					if (!microrganismoS.isExiste(microrganismo)){
						microrganismoS.add(microrganismo);
					}

					List<Microrganismo> ml = microrganismoS.getByNome(microrganismo);
					microrganismo = ml.isEmpty() ? null : ml.get(0);

					//Insere o itemPadrao
					System.out.println("ITEM PADRAO: " + registro[4] + " / " + registro[5] + " / " + registro[6] );
					ItemPadrao itemPadrao = new ItemPadrao();
					itemPadrao.setItemGrupo(itemGrupo);
					itemPadrao.setMicrorganismo(microrganismo);
					itemPadrao.setCodigoLegislacao(grupo.getNome() + itemGrupo.getNome());
					itemPadrao.setPadrao(padrao);
					itemPadrao.setTipoAnalise(TipoAnalise.ALIMENTO);

					itemPadrao.setUnidadeMedida(registro[5]);
					itemPadrao.setReferencia(registro[6]);


//					String tx_unid_medida =" "+ Messages.getBundleMessage("carga.unidade_medida")+" ";
//					if(registro[14].contains("?")){
//						itemPadrao.setUnidadeMedida( registro[8].substring(registro[8].length()-3, registro[8].length()-1) + tx_unid_medida + registro[8].substring(registro[8].length()-1) );
//					}else{
//						itemPadrao.setUnidadeMedida(registro[14]);
//					}

					itemPadraoS.add(itemPadrao);


					//if(intCount>48) break;
					//intCount++;

					line = br.readLine();
					if(line!=null){
						registro = line.split(cvsSplitBy);
					}
				}

				//System.out.println("intCount: " + Integer.toString(intCount));
				//if(intCount>48) break;
			}

			//for(int i = 0; i<registro.length; i++){
			//		System.out.println("Coluna " + Integer.toString(i)  + ": " + registro[i]);	
			//}

			Messages.addInfoTextMessage("!!! Microrganismos IMPORTADOS COM SUCESSO !!!");
			System.out.println("FIM");	


		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
			Messages.addInfoTextMessage("ERRO na importação da tabela de alimentos e microrganismos -->" + e.getMessage());
		}

		return null;
	}






	public String atualizaSenhaAposCarga() {

		try {
			List<Usuario> listaUsuarios = getuS().getAll();
			for (Usuario user : listaUsuarios) {
				user.setSenha("adm");
				String password = user.getSenha();
				user.setSenha(Criptografia.encodePassword(password));
				getuS().atualizar(user);
			}

			Messages.addInfoTextMessage("!!! senha atualizada com sucesso !!!");
		} catch (Exception e) {
			Messages.addInfoTextMessage("ERRO NA atualizacao da senha -->" + e.getMessage());
		}

		return null;

	}

	public String cargaAdministradorETodosPerfisEFuncoes() {
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

			Messages.addInfoTextMessage("!!! CARGA EXECUTADA COM SUCESSO !!!");
		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA CARGA -->" + e.getMessage());
		}

		return null;
	}

	private List<Cliente> criarClientes() {

		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		Cliente c = new Cliente();

		c.setAtivo(true);
		c.setNome("BioQualitas");
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
		f = new Funcao();
		f.setNome(Funcoes.DELETARAMOSTRA.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));
		f = new Funcao();
		f.setNome(Funcoes.DELETARSOLICITACAO.toString());
		p.getFuncoes().add(getfS().getByNome(f).get(0));

		getpS().add(p);
		p = getpS().recuperarPorNome(p.getNome());
		perfis.add(p);

		p = new Perfil();

		p.setAtivo(true);
		p.setNome(Perfis.COLETADOR.toString());
		p.setFuncoes(new ArrayList<Funcao>());

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

		//** Somente quando o Usuario for "Contato Principal" da Empresa-Cliente.
		f = new Funcao();
		f.setNome(Funcoes.CADASTRARUSUARIO.toString());
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

	private IClienteService getcS() {
		return cS;
	}

	private void setcS(IClienteService cS) {
		this.cS = cS;
	}

	private IUsuarioService getuS() {
		return uS;
	}

	private void setuS(IUsuarioService uS) {
		this.uS = uS;
	}

	private IPerfilService getpS() {
		return pS;
	}

	private void setpS(IPerfilService pS) {
		this.pS = pS;
	}

	private IFuncaoService getfS() {
		return fS;
	}

	private void setfS(IFuncaoService fS) {
		this.fS = fS;
	}

	public IGrupoService getGrupoS() {
		return grupoS;
	}

	public void setGrupoS(IGrupoService grupoS) {
		this.grupoS = grupoS;
	}

	public IItemGrupoService getItemGrupoS() {
		return itemGrupoS;
	}

	public void setItemGrupoS(IItemGrupoService itemGrupoS) {
		this.itemGrupoS = itemGrupoS;
	}

	public IPadraoService getPadraoS() {
		return padraoS;
	}

	public void setPadraoS(IPadraoService padraoS) {
		this.padraoS = padraoS;
	}

	public IItemPadraoService getItemPadraoS() {
		return itemPadraoS;
	}

	public void setItemPadraoS(IItemPadraoService itemPadraoS) {
		this.itemPadraoS = itemPadraoS;
	}

	public IMicrorganismoService getMicrorganismoS() {
		return microrganismoS;
	}

	public void setMicrorganismoS(IMicrorganismoService microrganismoS) {
		this.microrganismoS = microrganismoS;
	}

	public INotificacaoService getNs() {
		return ns;
	}

	public void setNs(INotificacaoService ns) {
		this.ns = ns;
	}

	public IFormaService getFps() {
		return fps;
	}

	public void setFps(IFormaService fps) {
		this.fps = fps;
	}

	public ICondicaoAmostraService getFcs() {
		return fcs;
	}

	public void setFcs(ICondicaoAmostraService fcs) {
		this.fcs = fcs;
	}

	public IConfiguracaoService getFconfs() {
		return fconfs;
	}

	public void setFconfs(IConfiguracaoService fconfs) {
		this.fconfs = fconfs;
	}

	public IMotivoService getMotivoS() {
		return motivoS;
	}

	public void setMotivoS(IMotivoService motivoS) {
		this.motivoS = motivoS;
	}

	public ILaudoService getLaudoS() {
		return laudoS;
	}

	public void setLaudoS(ILaudoService laudoS) {
		this.laudoS = laudoS;
	}

}
