package vrbaidu.top.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import vrbaidu.top.util.DateUtils;

/**
 * Created by Administrator on 2017/4/17.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    /**
     *在数据被接收的时候调用
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf byteBuf = (ByteBuf) msg;
        try {
            System.out.println("接收到客户端发来的消息:" + msg.toString());

//            while (byteBuf.isReadable()) {
//                System.out.println((char) byteBuf.readByte());
//                System.out.flush();
//            }

//            System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
        }catch (Exception e) {
            //ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放。
            ((ByteBuf) msg).release();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }


    /**
     * 对于TIME协议我们需要一个客户端因为人们不能把一个32位的二进制数据翻译成一个日期或者日历
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端已加入,并可用!!");

        final ByteBuf time = PooledByteBufAllocator.DEFAULT.buffer(100);
        String dateTime = DateUtils.getNowLocDate();
        time.writeBytes(dateTime.getBytes());
        final ChannelFuture f = ctx.channel().writeAndFlush(time);
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
             //   ctx.close();
            }
        });
    }

}
