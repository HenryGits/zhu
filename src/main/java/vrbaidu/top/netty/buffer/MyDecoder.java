package vrbaidu.top.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */
public class MyDecoder extends ByteToMessageDecoder {
    /**
     * 数据转换
     * @throws Exception is thrown if an error accour
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);
        String body = new String(req,"UTF-8");
//        final int length = in.readableBytes();
//        final byte[] array = new byte[length];
//        String content = new String(array, in.readerIndex(), length, "UTF-8");
        out.add(body);
    }
}
