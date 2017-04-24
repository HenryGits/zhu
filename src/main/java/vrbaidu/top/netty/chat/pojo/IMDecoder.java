package vrbaidu.top.netty.chat.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.msgpack.MessagePack;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IM协议解码器，输入，客户端请求过来以后，要解码
 *
 * @author Tom
 */
public class IMDecoder extends ByteToMessageDecoder {

    //正则解析协议内容
    private Pattern pattern = Pattern.compile("^\\[(.*)\\](\\s\\-\\s(.*))?");

    //实现反序列化
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            final byte[] array = new byte[in.readableBytes()];
            String content = new String(array, in.readerIndex(), in.readableBytes());
            if (!(null == content || "".equals(content.trim()))) {
                //如果拿到的字符串内容不是自定义协议，那么忽略
                if (!IMP.isIMP(content)) {
                    ctx.channel().pipeline().remove(this);
                    return;
                }
            }
            //将缓冲区中的数据清掉，避免下层去解码
            in.getBytes(in.readerIndex(), array, 0, in.readableBytes());
            out.add(new MessagePack().read(array, IMMessage.class));
            in.clear();
        } catch (Exception e) {
            //告诉下面的其他解码器，我无法解析，你去解析吧
            ctx.channel().pipeline().remove(this);
        }

    }


    /**
     * 把IMP协议字符串解析成一个IMMessage的对象
     * 这样显得就有种神秘感
     * 显得高端大气上档次
     *
     * @param msg
     * @return
     */
    public IMMessage decode(String msg) {
        if (null == msg || "".equals(msg.trim())) {
            return null;
        }
        try {
            Matcher m = pattern.matcher(msg);
            String header = ""; //消息头
            String content = ""; //消息体
            if (m.matches()) {
                header = m.group(1);
                content = m.group(3);
            }
            String[] headers = header.split("\\]\\[");
            //获取命令发送时间
            long time = Long.parseLong(headers[1]);
            //获取(‧‧)nnn昵称
            String nickName = headers[2];
            nickName = nickName.length() < 10 ? nickName : nickName.substring(0, 9);
            String cmd = headers[0];
            //封装IMMessage对象
            if (IMP.LOGIN.getName().equals(cmd) ||
                    IMP.LOGOUT.getName().equals(cmd) ||
                    IMP.FLOWER.getName().equals(cmd)) {
                return new IMMessage(cmd, time, nickName);
            } else if (IMP.CHAT.getName().equals(cmd) ||
                    IMP.SYSTEM.getName().equals(cmd)) {
                return new IMMessage(cmd, time, nickName, content);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
