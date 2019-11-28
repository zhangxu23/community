package my.comunity.common.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String accountId;
    private String name;
    private String token;
    private long gmtCreate;
    private long gmtModify;
    private String avatarUrl;
}
