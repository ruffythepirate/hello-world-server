package me.johan

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.codec.http.*
import io.netty.util.CharsetUtil

class HelloWorldHandler: ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        if( msg is LastHttpContent) {
            println("Request Received")
            val responseBuffer = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            val httpResponse = DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, responseBuffer)
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
            httpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,
                httpResponse.content().readableBytes());
            ctx?.writeAndFlush(httpResponse);
        } else {
            super.channelRead(ctx, msg)
        }
    }
}
