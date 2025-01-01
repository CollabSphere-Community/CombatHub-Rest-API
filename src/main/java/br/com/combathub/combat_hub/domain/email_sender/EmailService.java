package br.com.combathub.combat_hub.domain.email_sender;

import br.com.combathub.combat_hub.domain.verification_code.VerificationCodeEntity;
import br.com.combathub.combat_hub.infra.exception.SendingEmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationCode(VerificationCodeEntity entity) {
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message);

            helper.setFrom("noreplay@codenestsolucoes.xyz");
            helper.setTo(entity.getUser().getLogin());
            helper.setSubject("Confirmação de conta");
            String template = getTemplate("verification_email_template.html")
                    .replace("{#verification_code}", entity.getCode());
            helper.setText(template, true);
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            throw new SendingEmailException();
        }
    }

    public String getTemplate(String templateName) throws IOException {
        var template = new ClassPathResource(templateName);
        return new String(template.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

}
