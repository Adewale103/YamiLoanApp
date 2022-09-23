package africa.semicolon.yamiloanapp.services.mail;

import africa.semicolon.dtos.requests.MailRequest;
import africa.semicolon.dtos.responses.MailResponse;

public interface EmailService {
    MailResponse sendMail(MailRequest mailRequest);
}
