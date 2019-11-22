package my.comunity.common.dto;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
