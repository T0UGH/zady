package com.edu.neu.zady.util;

import com.edu.neu.zady.pojo.DTO;
import org.springframework.http.HttpStatus;

public class DTOFactory {
    public static DTO okDTO(Object body){
        DTO dto = new DTO();
        dto.setCode(HttpStatus.OK.value());
        dto.setMsg("请求成功");
        dto.setBody(body);
        return dto;
    }
    public static DTO okDTO(){
        DTO dto = new DTO();
        dto.setCode(HttpStatus.OK.value());
        dto.setMsg("请求成功");
        return dto;
    }
    public static DTO okDTO(String msg, Object body){
        DTO dto = new DTO();
        dto.setCode(HttpStatus.OK.value());
        dto.setMsg(msg);
        dto.setBody(body);
        return dto;
    }

    public static DTO unKnownErrorDTO(String msg){
        DTO dto = new DTO();
        dto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        dto.setMsg(msg);
        return dto;
    }

    public static DTO unauthorizedDTO(String msg){
        DTO dto = new DTO();
        dto.setCode(HttpStatus.UNAUTHORIZED.value());
        dto.setMsg(msg);
        return dto;
    }

    public static DTO notFoundDTO(String msg){
        DTO dto = new DTO();
        dto.setCode(HttpStatus.NOT_FOUND.value());
        dto.setMsg(msg);
        return dto;
    }

    public static DTO DefaultErrorDTO(String msg){
        DTO dto = new DTO();
        dto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        dto.setMsg(msg);
        return dto;
    }

    public static DTO forbiddenDTO(String msg){
        DTO dto = new DTO();
        dto.setCode(HttpStatus.FORBIDDEN.value());
        dto.setMsg(msg);
        return dto;
    }
}
