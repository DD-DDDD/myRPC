import server.RPCServer;
import server.ServiceProvider;
import server.ThreadPoolPRPCServer;
import service.BlogService;
import service.UserService;
import service.impl.BlogServiceImpl;
import service.impl.UserServiceImpl;

public class TestServer {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer rpcServer = new ThreadPoolPRPCServer(serviceProvider);
        rpcServer.start(8898);
    }
}
