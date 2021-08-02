package client;

import codec.JsonSerializer;
import codec.MyDecode;
import codec.MyEncode;
import codec.ObjectSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 这里使用的还是java 序列化方式， netty的自带的解码编码支持传输这种结构
        // ObjectDecoder 和 ObjectEncoder 可以用来实现 POJO 对象或各种业务对象的编码和解码，
        // 底层使用的仍是Java序列化技术,而Java序列化技术本身效率就不高，存在如下问题
        // 无法跨语言
        // 序列化后的体积太大，是二进制编码的5倍多。
        // 序列化性能太低
        // 自定义
        pipeline.addLast(new MyDecode());
        pipeline.addLast(new MyEncode(new ObjectSerializer()));

        pipeline.addLast(new NettyClientHandler());
    }
}
