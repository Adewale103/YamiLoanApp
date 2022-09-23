package africa.semicolon.yamiloanapp.services.mail;

import africa.semicolon.dtos.requests.MailRequest;
import africa.semicolon.dtos.responses.MailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JavaMailService implements EmailService{
    private final JavaMailSender javaMailSender;
    @Override
    public MailResponse sendMail(MailRequest mailRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("adeyinkawale13@gmail.com");
        simpleMailMessage.setTo(mailRequest.getReceiver());
        simpleMailMessage.setSubject(mailRequest.getSubject());
        simpleMailMessage.setText(mailRequest.getBody());

        javaMailSender.send(simpleMailMessage);
        log.info("Mail successfully sent!");
        return MailResponse.builder().
                message("kindly check your mail, we are awaiting your response to the mail we sent").
                build();
    }
}
