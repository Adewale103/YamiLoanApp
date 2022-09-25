package africa.semicolon.yamiloanapp.dtos.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MailRequest {
    private String receiver;
    private String body;
    private String subject;
}
