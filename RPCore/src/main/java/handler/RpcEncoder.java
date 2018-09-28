package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import util.Serialization;

/**
 * @author jony
 */
public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> clazz;

    public RpcEncoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
        if (clazz.isInstance(msg)) {
            byte[] bytes = Serialization.serialize(msg);
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        } else {
            // do sth
        }
    }
}
