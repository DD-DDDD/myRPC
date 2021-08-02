package register;

import java.net.InetSocketAddress;

public interface ServiceRegister {
    void register(String serviceName, InetSocketAddress socketAddress);
    InetSocketAddress serviceDiscovery(String serviceName);
}
