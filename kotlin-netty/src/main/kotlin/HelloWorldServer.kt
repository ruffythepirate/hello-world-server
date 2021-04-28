package me.johan

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.ChannelPipeline
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.http.HttpRequestDecoder
import io.netty.handler.codec.http.HttpResponseEncoder

class HelloWorldServer(val port: Int) {
    val bossGroup = NioEventLoopGroup()
    val workerGroup = NioEventLoopGroup()

    fun start() {
        try {
            val b = ServerBootstrap()
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .option(ChannelOption.SO_BACKLOG, 100)
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel?) {
                        val p: ChannelPipeline = ch!!.pipeline()
                        p.addLast(HttpRequestDecoder())
                        p.addLast(HttpResponseEncoder())
                        p.addLast(HelloWorldHandler())
                    }
                })

            // start the server
            val f = b.bind(port).sync()

            // Wait until server is done
            f.channel().closeFuture().sync()
        } finally {
            println("sayonara")
        }
    }
}
