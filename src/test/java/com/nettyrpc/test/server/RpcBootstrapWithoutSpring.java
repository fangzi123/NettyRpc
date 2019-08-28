package com.nettyrpc.test.server;

import com.nettyrpc.server.RpcServer;
import com.nettyrpc.server.RpcServerOptions;
import com.nettyrpc.test.client.HelloService;
import com.nettyrpc.utils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RpcBootstrapWithoutSpring {
    private static final Logger logger = LoggerFactory.getLogger(RpcBootstrapWithoutSpring.class);

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 18866;
        //ServiceRegistry serviceRegistry = new ServiceRegistry("127.0.0.1:2181");
        RpcServerOptions options = new RpcServerOptions();
        options.setNamingServiceUrl("zookeeper://127.0.0.1:2181");
//        options.setNamingServiceUrl(PropertyUtils.get("server"));
        RpcServer rpcServer = new RpcServer(host,port,options);
        HelloService helloService = new HelloServiceImpl();
        rpcServer.addService("com.nettyrpc.test.client.HelloService", helloService);
        try {
            rpcServer.start();
        } catch (Exception ex) {
            logger.error("Exception: {}", ex);
        }
    }
}
