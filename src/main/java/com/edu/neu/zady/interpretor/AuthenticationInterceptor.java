package com.edu.neu.zady.interpretor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NoAuthException;
import com.edu.neu.zady.pojo.Role;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public class AuthenticationInterceptor implements HandlerInterceptor {

    //RequestContextHandler
    //todo: 各种异常的处理
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        // 从 http 请求头中取出 token
        String token = request.getHeader("token");

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        //通过反射拿到这个方法的元数据
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();

        //如果这个方法被Auth修饰，则进入判断，否则直接返回true放行
        if (method.isAnnotationPresent(Auth.class)) {
            Auth auth =method.getAnnotation(Auth.class);

            //如果需要登陆，则继续验证
            if(auth.needLogin()){

                //没有token直接报错
                if (token == null) {
                    throw new NoAuthException("无token，请重新登录");
                }

                //取userId时无法解码直接报错
                String userId;
                try {
                    userId = JWT.decode(token).getClaim("userId").asString();
                } catch (JWTDecodeException j) {
                    throw new DefaultException("无法解析userId");
                }



                if(userId == null){
                    throw new NoAuthException("无法解析userId");
                }

                //将取得的userId存到requestHeader
                request.setAttribute("userId", userId);

                //如果需要是同一个user则执行验证
                if(auth.sameUser()){
                    String paramUserId = request.getParameter("userId");
                    if(!userId.equals(paramUserId)){
                        throw new NoAuthException("您无权操作其他用户");
                    }
                }

                //如果还需要携带projectId，则继续验证
                if(auth.needProject()){

                    //取projectId时无法解码直接报错
                    String projectId;

                    try {
                        projectId = JWT.decode(token).getClaim("projectId").asString();
                    } catch (JWTDecodeException j) {
                        throw new DefaultException("无法解析projectId");
                    }

                    if(projectId == null){
                        throw new NoAuthException("无法解析projectId");
                    }

                    //将取得的projectId存到requestHeader
                    request.setAttribute("projectId", projectId);

                    //验证是否需要同一项目
                    if(auth.sameProject()){
                        String paramProjectId = request.getParameter("projectId");
                        if(!projectId.equals(paramProjectId)){
                            throw new NoAuthException("您无权操作其他项目");
                        }
                    }

                    //继续验证Role的信息
                    Role.RoleEnum[] configRoles = auth.role();

                    //如果配置了超过0个Role，则继续验证
                    if(configRoles.length != 0){

                        //从token中拿到role字符串
                        String role;
                        try {
                            role = JWT.decode(token).getClaim("role").asString();
                        } catch (JWTDecodeException j) {
                            throw new DefaultException("无法解析role");
                        }

                        if(role == null){
                            throw new NoAuthException("无法解析role");
                        }

                        //遍历配置的每个Role，来鉴权，如果包含任意一个就返回true
                        for (Role.RoleEnum configRole:configRoles) {
                            if(role.contains(configRole.name())){
                                return true;
                            }
                        }
                        throw new NoAuthException("您的智力过低，请终止此操作或者联系master授权");

                     //如果未配置Role，默认这个project的所有用户都可以访问
                    }else{
                        return true;
                    }
                 //如果不需要携带projectId，则直接返回true
                }else{
                    return true;
                }

             //如果不需要登陆，则直接返回true
            }else{
                return true;
            }
        }
        return true;
    }



}
