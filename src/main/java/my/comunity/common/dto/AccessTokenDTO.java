package my.comunity.common.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    private final static String client_id="3f503364c60d01b79df1";
    private final static String client_secret="df973aa2cfb62f692222fd9ea8dc9c5fa3142a1e";
    private String code;
    private String redirect_url;
    private String state;
    public String getClient_id() {
        return client_id;
    }
    public String getClient_secret() {
        return client_secret;
    }
}
