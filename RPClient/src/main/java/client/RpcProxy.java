package client;

import core.RpcRequest;
import core.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class RpcProxy implements InvocationHandler {

    private String serverAddress;

    private ServiceDiscovery discovery;

    public RpcProxy(ServiceDiscovery discovery) {
        this.discovery = discovery;
        this.serverAddress = discovery.discover();
    }

    public <T> T getService(Class<T> serviceInterface) {
        if (serviceInterface == null) {
            throw new IllegalArgumentException("This interface can't be null");
        }
        Object result = Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                new Class[]{serviceInterface}, this);
        return serviceInterface.cast(result);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest request = new RpcRequest();
        request.setId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);

        String[] strings = serverAddress.split(":");
        Rpclient client = new Rpclient(strings[0], Integer.parseInt(strings[1]));
        RpcResponse response = client.send(request);
        return response.getResult();
    }
}
