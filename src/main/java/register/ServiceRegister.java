package register;

import bean.RPCRequest;

import java.net.InetSocketAddress;

public interface ServiceRegister {
    void register(String serviceName, InetSocketAddress socketAddress);
    InetSocketAddress serviceDiscovery(String serviceName, RPCRequest request);
}
