package africa.semicolon.yamiloanapp.services.mail;

import africa.semicolon.yamiloanapp.dtos.requests.MailRequest;
import africa.semicolon.yamiloanapp.dtos.responses.MailResponse;

public interface EmailService {
    MailResponse sendMail(MailRequest mailRequest);
}
