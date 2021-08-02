package server;

import codec.MyDecode;
import codec.MyEncode;
import codec.ObjectSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private final ServiceProvider serviceProvider;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 这里使用的还是java 序列化方式， netty的自带的解码编码支持传输这种结构
        // 自定义
        pipeline.addLast(new MyDecode());
        pipeline.addLast(new MyEncode(new ObjectSerializer()));

        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));
    }
}
