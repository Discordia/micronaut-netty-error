This is a reproduce of a exception that happens now and then in Micronaut 4.7.5.

*How to reproduce*
1. Start the service `netty-error-service`. It will start on port 8081.
2. Start the proxy service `netty-error-proxy-client`. It will start on port 8080.
3. Bomb with requests on POST http://localhost:8080/service/payload with the json body thats in the file large64k.json.

For 3 I used Insomnia (https://insomnia.rest/). There you can just click send again and again as fast as possible to start new request before the previous one is done.

After a few (30-50) requets, I started to see the exception being thrown in the proxy:
```
16:00:11.777 [default-nioEventLoopGroup-1-2] WARN  i.n.util.concurrent.DefaultPromise - An exception was thrown by io.micronaut.http.client.netty.StreamWriter$$Lambda/0x0000000801523510.operationComplete()
java.lang.NullPointerException: Cannot invoke "io.micronaut.http.netty.body.BufferConsumer$Upstream.onBytesConsumed(long)" because "this.upstream" is null
	at io.micronaut.http.client.netty.StreamWriter.lambda$add0$1(StreamWriter.java:112)
	at io.netty.util.concurrent.DefaultPromise.notifyListener0(DefaultPromise.java:590)
	at io.netty.util.concurrent.DefaultPromise.notifyListenersNow(DefaultPromise.java:557)
	at io.netty.util.concurrent.DefaultPromise.notifyListeners(DefaultPromise.java:492)
	at io.netty.util.concurrent.DefaultPromise.addListener(DefaultPromise.java:185)
	at io.netty.channel.DefaultChannelPromise.addListener(DefaultChannelPromise.java:95)
	at io.netty.channel.DefaultChannelPromise.addListener(DefaultChannelPromise.java:30)
	at io.micronaut.http.client.netty.StreamWriter.add0(StreamWriter.java:108)
	at io.micronaut.http.client.netty.StreamWriter.add(StreamWriter.java:96)
	at io.micronaut.http.netty.body.StreamingNettyByteBody$SharedBuffer.subscribe0(StreamingNettyByteBody.java:348)
	at io.micronaut.http.netty.body.StreamingNettyByteBody$SharedBuffer.subscribe(StreamingNettyByteBody.java:326)
	at io.micronaut.http.netty.body.StreamingNettyByteBody.primary(StreamingNettyByteBody.java:92)
	at io.micronaut.http.client.netty.StreamWriter.startWriting(StreamWriter.java:64)
	at io.micronaut.http.client.netty.DefaultHttpClient.lambda$sendRawRequest$48(DefaultHttpClient.java:1779)
	at reactor.core.publisher.MonoCreate.subscribe(MonoCreate.java:61)
	at reactor.core.publisher.Mono.subscribe(Mono.java:4576)
	at reactor.core.publisher.MonoSubscribeOn$SubscribeOnSubscriber.run(MonoSubscribeOn.java:126)
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:84)
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:37)
	at io.netty.util.concurrent.PromiseTask.runTask(PromiseTask.java:96)
	at io.netty.util.concurrent.PromiseTask.run(PromiseTask.java:106)
	at io.netty.util.concurrent.AbstractEventExecutor.runTask(AbstractEventExecutor.java:173)
	at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:166)
	at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:472)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:566)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:1583)
```
