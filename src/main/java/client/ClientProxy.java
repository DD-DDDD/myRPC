package client;

import bean.RPCRequest;
import bean.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    // 传入参数 Service 接口的class对象，反射封装成一个request
    private final RPCClient client;

    // jdk 动态代理， 每一次代理对象调用方法
    // 会经过此方法增强（反射获取request对象，socket发送至客户端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // request 构建，使用lombok中builder
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsTypes(method.getParameterTypes()).build();
        // 数据传输
        RPCResponse response = client.sendRequest(request);

        return response.getData();
    }

    <T>T getProxy(Class<T> tClass) {
        // this 就是处理调用方法的InvocationHandler
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, this);
    }
}
