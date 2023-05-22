package utils;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
public class AuthRequest {

    private static String email;
    private static String password;
    public JSONObject requestBody;

    public AuthRequest(final String email, final String password){
        this.email = email;
        this.password = password;
        createMeinBody();
    }

    private void createMeinBody(){
        JSONObject data = new JSONObject();
        data.put("email", email);
        data.put("password", password);
        requestBody = data;
    }

    public void validateResponseStatusCode(final int statusCode){
        Assertions.assertThat(statusCode)
                .as("Invalid status code!")
                .isEqualTo(200);
    }
}
