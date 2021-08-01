package server;

import bean.RPCRequest;
import bean.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;

/**
 * 这里负责解析得到的request请求，执行服务方法，返回给客户端
 * 1. 从request得到interfaceName 2. 根据interfaceName在serviceProvide Map中获取服务端的实现类
 * 3. 从request中得到方法名，参数， 利用反射执行服务中的方法 4. 得到结果，封装成response，写入socket
 */
@AllArgsConstructor
public class WorkThread implements Runnable{
    private final Socket socket;
    private final ServiceProvider serviceProvide;
    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // 读取客户端传过来的request
            RPCRequest request = (RPCRequest) objectInputStream.readObject();
            // 反射调用服务方法获得返回值
            RPCResponse response = getResponse(request);

            // 写入客户端
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("从Io中读取数据错误");
        }
    }

    private RPCResponse getResponse(RPCRequest request) {
        // 得到服务名
        String interfaceName = request.getInterfaceName();
        // 得到服务端相应服务实现类
        Object service = serviceProvide.getService(interfaceName);
        // 反射调用方法
        Method method;

        try {
            method = service.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("方法执行错误");
            return RPCResponse.fail();
        }
    }
}
