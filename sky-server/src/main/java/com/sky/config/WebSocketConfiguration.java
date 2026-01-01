package com.sky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置类，用于注册WebSocket的Bean
 *
 * 因此，WebSocketConfiguration 的作用不是把 WebSocketServer 注册成 Bean，
 * 而是注册一个特殊的 Bean（ServerEndpointExporter），
 * 让 WebSocket 端点能被容器识别和启用。
 *
 * WebSocket 端点（@ServerEndpoint）属于 WebSocket 容器管理，不是 Spring Bean。
 *
 * 配置类里注册的 ServerEndpointExporter 是一个 Spring Bean，
 * 它的作用是 桥接 Spring 和 WebSocket 容器。
 *
 * 所以这里说的“注册 WebSocket 的 Bean”，其实是指 注册 ServerEndpointExporter
 * 这个 Bean，而不是把 WebSocketServer 变成 Bean。
 */
@Configuration
public class WebSocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
