package com.service.mail;

import java.util.LinkedList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.model.Usuario;
import com.util.Messages;

public class JavaMailService implements IMailService {

	private MailSender mailSender;
	private JavaMailSenderImpl javaMailSenderImpl;
	private boolean desenv;

	private static final String REMETENTE_SISTEMA = Messages
			.getBundleMessage("mail.remetente.sistema");

	public JavaMailService(MailSender mailSender, boolean desenv) {
		this.mailSender = mailSender;
		this.javaMailSenderImpl = (JavaMailSenderImpl) mailSender;
		this.desenv = false;
	}

	@Override
	public void enviaCadastroUsuario(final Usuario userMail, final String passwd) {
		final String assunto = Messages
				.getBundleMessage("mail.adicionausuario.subject");
		final String corpo = Messages.getBundleMessage(
				"mail.adicionausuario.corpo", passwd);
		final List<String> destinatarios = new LinkedList<String>();
		destinatarios.add(userMail.getEmail());
		sendMail(REMETENTE_SISTEMA, destinatarios, assunto, corpo);
	}

	@Override
	public void resetaSenha(final Usuario userMail, final String passwd) {
		final String assunto = Messages
				.getBundleMessage("mail.resetaSenha.subject");
		final String corpo = Messages.getBundleMessage(
				"mail.resetaSenha.corpo", passwd);
		final List<String> destinatarios = new LinkedList<String>();
		destinatarios.add(userMail.getEmail());
		sendMail(REMETENTE_SISTEMA, destinatarios, assunto, corpo);
	}

	@Override
	public void enviaDestinatarios(List<Usuario> sendTo, String assunto,
			String corpo) {
		final List<String> destinatarios = new LinkedList<String>();
		destinatarios.addAll(formata(sendTo));
		sendMail(REMETENTE_SISTEMA, destinatarios, assunto, corpo);
	}

	@Override
	public void enviarParaAnalise(List<Usuario> userListTecnico,
			String idSolicitacao) {
		final String assunto = Messages
				.getBundleMessage("mail.envioAnalise.subject");
		final String corpo = Messages.getBundleMessage(
				"mail.envioAnalise.corpo", idSolicitacao);
		final List<String> destinatarios = new LinkedList<String>();
		destinatarios.addAll(formata(userListTecnico));
		sendMail(REMETENTE_SISTEMA, destinatarios, assunto, corpo);
	}

	@Override
	public void enviarParaAprovacao(List<Usuario> userListColetador,
			String idSolicitacao) {
		final String assunto = Messages
				.getBundleMessage("mail.envioAprovacao.subject");
		final String corpo = Messages.getBundleMessage(
				"mail.envioAprovacao.corpo", idSolicitacao);
		final List<String> destinatarios = new LinkedList<String>();
		destinatarios.addAll(formata(userListColetador));
		sendMail(REMETENTE_SISTEMA, destinatarios, assunto, corpo);
	}

	@Override
	public void liberarParaCliente(List<Usuario> userSolicitante,
			String idSolicitacao) {
		liberarParaCliente(userSolicitante, idSolicitacao, null);
	}

	@Override
	public void liberarParaCliente(List<Usuario> userSolicitante,
			String idSolicitacao, ByteArrayResource[] anexo) {
		final String assunto = Messages
				.getBundleMessage("mail.solicitacaoliberada.subject");
		final String corpo = Messages.getBundleMessage(
				"mail.solicitacaoliberada.corpo", idSolicitacao);
		final List<String> destinatarios = new LinkedList<String>();
		destinatarios.addAll(formata(userSolicitante));

		if (anexo == null) {
			sendMail(REMETENTE_SISTEMA, destinatarios, assunto, corpo);
		} else {
			sendMail(REMETENTE_SISTEMA, destinatarios, assunto, corpo, anexo);
		}
	}

	@Override
	public void invalidarAmostra(List<Usuario> users, String idAmostra,
			String justificativa) {
		final String assunto = Messages
				.getBundleMessage("mail.amostrainvalida.subject");
		final String corpo = Messages.getBundleMessage(
				"mail.amostrainvalida.corpo", idAmostra, justificativa);
		final List<String> destinatarios = new LinkedList<String>();
		destinatarios.addAll(formata(users));
		sendMail(REMETENTE_SISTEMA, destinatarios, assunto, corpo);

	}

	private String formata(String nome, String email) {
		StringBuilder endereco = new StringBuilder(nome);
		endereco.append("<");
		endereco.append(email);
		endereco.append(">");
		return endereco.toString();
	}

	private String formata(Usuario user) {
		return formata(user.getNome(), user.getEmail());
	}

	private List<String> formata(final List<Usuario> users) {
		final List<String> destinatarios = new LinkedList<String>();
		if (users == null) {
			return destinatarios;
		}
		for (Usuario u : users) {
			destinatarios.add(formata(u));
		}
		return destinatarios;
	}

	private List<String> retornaListaFormatada(Usuario... users) {
		final List<Usuario> resultado = new LinkedList<Usuario>();
		for (Usuario u : users) {
			resultado.add(u);
		}
		return formata(resultado);
	}

	public void sendMail(final String remetente,
			final List<String> destinatarios, final String subject,
			final String body) {
		sendMail(remetente, destinatarios, subject, body, null);
	}

	public void sendMail(final String remetente,
			final List<String> destinatarios, final String subject,
			final String body, final ByteArrayResource[] attachments) {

		Runnable mailThread = new Runnable() {
			@Override
			public void run() {
				try {

					if (attachments == null) {
						SendSimpleEmail(remetente, destinatarios, subject, body);
					} else {
						sendEmailWithAttachment(remetente, destinatarios,
								subject, body, attachments);
					}

				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			private void SendSimpleEmail(final String remetente,
					final List<String> destinatarios, final String subject,
					final String body) {
				
				SimpleMailMessage message = new SimpleMailMessage();

				String[] toUser = new String[destinatarios.size()];
				int index = 0;
				for (String destinatario : destinatarios) {
					toUser[index++] = destinatario;
				}

				message.setFrom(remetente);
				message.setTo(toUser);
				message.setSubject(subject);
				message.setText(body);

				// previne de ficar enviando email enquanto em
				// desenvolvimento
				// para comecar a enviar email, acesse o arquivo
				// config.properties
				// dentro de WEB-INF/config
				// e coloque a variavel app.environment.desenv como
				// false
				if (!desenv) {
					mailSender.send((SimpleMailMessage) message);
				}
			}

			private void sendEmailWithAttachment(final String remetente,
					final List<String> destinatarios, final String subject,
					final String body, final ByteArrayResource[] attachments) {
				
				MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
					@Override
					public void prepare(MimeMessage message) throws Exception {
						MimeMessageHelper helper = new MimeMessageHelper(
								message);
						helper.setFrom(remetente);
						helper.setTo((String[]) destinatarios.toArray());
						helper.setSubject(subject);
						helper.setText(body);
						for (ByteArrayResource byteArrayResource : attachments) {
							helper.addAttachment(
									byteArrayResource.getDescription(),
									byteArrayResource);
						}
					}
				};

				// previne de ficar enviando email enquanto em
				// desenvolvimento
				// para comecar a enviar email, acesse o arquivo
				// config.properties
				// dentro de WEB-INF/config
				// e coloque a variavel app.environment.desenv como
				// false
				if (!desenv) {
					javaMailSenderImpl.send(messagePreparator);
				}
			}
		};

		new Thread(mailThread).start();
	}

}
