package com.edu.neu.zady.util;

import com.edu.neu.zady.pojo.DTO;

public class DTOFactory {
    public static DTO okDTO(Object body){
        DTO dto = new DTO();
        dto.setCode(202);
        dto.setMsg("请求成功");
        dto.setBody(body);
        return dto;
    }
    public static DTO okDTO(){
        DTO dto = new DTO();
        dto.setCode(202);
        dto.setMsg("请求成功");
        return dto;
    }
    public static DTO okDTO(String msg, Object body){
        DTO dto = new DTO();
        dto.setCode(202);
        dto.setMsg(msg);
        dto.setBody(body);
        return dto;
    }

    public static DTO unKnownErrorDTO(String msg){
        DTO dto = new DTO();
        dto.setCode(503);
        dto.setMsg(msg);
        return dto;
    }
}
