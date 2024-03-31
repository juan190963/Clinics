package com.cristianRuizBlog.aplicacion.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) throws MessagingException {
        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, false, "utf-8");
        mensaje.setContent(cuerpo, "text/html");
        helper.setTo(destinatario);
        helper.setSubject(asunto);

        try {
            javaMailSender.send(mensaje);
        } catch (MailException e) {
            throw new MailException("Error al enviar el correo") {
			};
        }
    }
}
