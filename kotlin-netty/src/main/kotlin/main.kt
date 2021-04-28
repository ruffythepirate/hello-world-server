package me.johan

fun main(args: Array<String>) {
    val es = HelloWorldServer(8080)
    println("Starting Hello World Server")
    es.start()
}

