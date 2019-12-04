package my.comunity.common.dto;

import lombok.Data;
import my.comunity.common.exception.CustomizeErrorCode;
import my.comunity.common.exception.CustomizeException;

@Data
public class CommentResultDto {
    private Integer code;
    private String message;
    public static CommentResultDto errorOf(Integer code,String message){
        CommentResultDto resultDto=new CommentResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    public static CommentResultDto errorOf(CustomizeErrorCode noLogin) {
        return errorOf(noLogin.getCode(),noLogin.getMessage());
    }
    public static CommentResultDto okOf(){
        CommentResultDto resultDto=new CommentResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        return resultDto;
    }

    public static CommentResultDto errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }
}
