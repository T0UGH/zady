package com.edu.neu.zady.interpretor;

import com.edu.neu.zady.pojo.DTO;
import com.edu.neu.zady.util.DTOFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.File;

@ControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(aClass);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        //如果返回为空
        if (body == null) {
            return DTOFactory.okDTO();
        }

        //如果返回一个DTO，则直接处理
        if (body instanceof DTO) {
            return body;

        //返回文件直接处理
        } else if (body instanceof File) {
            return body;

        //返回String则放到msg上
        } else if(body instanceof String) {
            return DTOFactory.okDTO((String) body, null);

        //返回其他对象就ok
        }else{
            return DTOFactory.okDTO(body);
        }
    }
}
