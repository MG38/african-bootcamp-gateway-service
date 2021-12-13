package community.andela.com.AfricanBootcampGatewayService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class Error {
    private String message;
    private HttpStatus code;
}
