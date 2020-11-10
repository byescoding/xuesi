package com.bai.guli.infrastructure.apigatway.filter;

import com.bai.guli.common.base.util.JwtUtils;
import com.google.gson.JsonObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthGlobalFilter implements GlobalFilter , Ordered {

    //进行过滤配置
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
         //获取请求
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();//获取访问路径

        //进行接口认证管理
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        //对于需要认证的路径进行一些判断
        if (antPathMatcher.match("/api/**/auth/**",path)){
            List<String> list = request.getHeaders().get("token");

            //如果token为空的话做出一些响应处理
            if (list == null){
                ServerHttpResponse response = exchange.getResponse();
                return out(response);
            }
            //token 校验失败
            boolean res = JwtUtils.checkJwtTToken(list.get(0));
            if (!res){
                ServerHttpResponse response = exchange.getResponse();
                return out(response);
            }
        }
        return chain.filter(exchange);
    }

    //定义当前过滤器的等级  值越小 优先级 越高
    @Override
    public int getOrder() {
        return 0;
    }

    //响应处理方法
    private Mono<Void> out (ServerHttpResponse response){
        JsonObject msg = new JsonObject();
        msg.addProperty("success", false);
        msg.addProperty("code", 28004);
        msg.addProperty("data", "");
        msg.addProperty("message", "鉴权失败");
        byte[] bytes = msg.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
       //输出响应
        return  response.writeWith(Mono.just(wrap));
    }
}
