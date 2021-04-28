package me.johan

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.http.HttpServerRequest

class HelloVerticle(val port: Int) : AbstractVerticle() {
    override fun start(promise: Promise<Void>) {
        vertx.createHttpServer()
            .requestHandler { r: HttpServerRequest ->
                r.response().end("Hello World")
            }
            .listen(port) {
                if (it.succeeded()) {
                    promise.complete()
                } else {
                    promise.fail(it.cause())
                }
            }
        println("ok, let's go.")
        super.start()
    }
}