package africa.semicolon.dtos.requests;

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
