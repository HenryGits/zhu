package vrbaidu.top.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import vrbaidu.top.netty.buffer.DiscardServerHandler;
import vrbaidu.top.netty.buffer.MyDecoder;

import java.io.IOException;

/**
 * Created by Administrator on 2017/4/16.
 */
public class NettyServer {

    private int port = 8080;

    public void start() {
        //Netty的工作机制boss线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //work线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //相当于ServerSocketChannel
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    //标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。
                    // 如果未设置或所设置的值小于1，Java将使用默认值50。
                    //设置1024个工作线程
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //每次都new一个工作流
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            //pipeline:工作流、流水线
                            //加入解码器
                            ch.pipeline().addLast(new MyDecoder(), new DiscardServerHandler());
                        }
                    });
            //同步方式
            ChannelFuture f = b.bind(this.port).sync();
            System.out.println("服务已启动,监听端口" + this.port);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws IOException {
        new NettyServer().start();
    }

}
