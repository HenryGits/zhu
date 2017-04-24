package vrbaidu.top.netty.buffer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;

/**
 * Created by Administrator on 2017/4/16.
 */
public class NettyClient {

    private String host;
    private int port;

    public void connect(String host, int port) {
        this.host = host;
        this.port = port;

        //work线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ////相当于SocketChannel
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            //是否启用心跳保活机制。
            //在双方TCP套接字建立连接后（即都进入ESTABLISHED状态）并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活。
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    //所有自定义的业务从这开始
                    //加入解码器
                    ch.pipeline().addLast(new MyDecoder(), new DiscardClientHandler());
                }
            });
            ChannelFuture f = b.connect(this.host, this.port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws IOException {
        new NettyClient().connect("127.0.0.1", 8080);
    }
}
