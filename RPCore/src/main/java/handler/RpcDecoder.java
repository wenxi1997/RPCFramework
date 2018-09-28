package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import util.Serialization;

import java.util.List;

/**
 * @author jony
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;

    public RpcDecoder(Class<?> tClass) {
        this.clazz = tClass;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object obj = Serialization.deserialize(data, clazz);
        out.add(obj);
    }
}
