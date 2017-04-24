package vrbaidu.top.netty.buffer;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import vrbaidu.top.util.DateUtils;

/**
 * Created by Administrator on 2017/4/16.
 */
public class DiscardClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 发送的ByteBuf格式数据
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端加入!!");
        //实例初始化
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer(100);
        String value ="学习ByteBuf";
        buffer.writeBytes(value.getBytes());
        ctx.channel().writeAndFlush(buffer);
    }

    /**
     * 从服务端接受一个32位的整数消息，打印翻译好的时间，最后关闭连接
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            System.out.println("欢迎登陆，现在的时间是：" + DateUtils.getDateFormat(msg.toString()));
        }catch (Exception e) {
           ctx.close();
           ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 当出现Throwable对象才会被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
