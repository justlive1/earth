Vert.x

---

# Vert.x Core
    
- 中英对照表

```
Client：客户端
Server：服务器
Primitive：基本（描述类型）
Writing：编写（有些地方译为开发）
Fluent：流式的
Reactor：反应器，Multi-Reactor即多反应器
Options：配置项，作为参数时候翻译成选项
Context：上下文环境
Undeploy：撤销（反部署，对应部署）
Unregister：注销（反注册，对应注册）
Destroyed：销毁
Handler/Handle：处理器/处理，有些特定处理器未翻译，如Completion Handler等。
Block：阻塞
Out of Box：标准环境（开箱即用）
Timer：计时器
Event Loop Pool：事件轮询线程池，大部分地方未翻译
Worker Pool：工作者线程池，大部分地方未翻译
Sender：发送者
Consumer：消费者
Receiver/Recipient：接收者
Entry：条目（一条key=value的键值对）
Map：动词翻译成 “映射”，名词为数据结构未翻译
Logging：动词翻译成 “记录”，名词翻译成日志器
Trust Store：受信存储
Frame：帧
Event Bus：事件总线
Buffer：缓冲区（一些地方使用的 Vert.x 中的 Buffer 类则不翻译）
Chunk：块（HTTP 数据块，分块传输、分块模式中会用到）
Pump：泵（平滑流式数据读入内存的机制，防止一次性将大量数据读入内存导致内存溢出）
Header：请求/响应头
Body：请求/响应体（有些地方翻译成请求/响应正文）
Pipe：管道
Round-Robin：轮询
Application-Layer Protocol Negotiation：ALPN，应用层协议协商
Wire：报文
Flush：刷新（指将缓冲区中已有的数据一次性压入，用这种方式清空缓冲区，传统上翻译成刷新）
Cipher Suite：密码套件
Datagram：数据报
Socket：套接字（有些地方未翻译，直接用的 Socket）
Multicast：多播（组播）
Concurrent Composition：并发合并
High Availability：高可用性
Multiplexing：多路复用
Fail-Over：故障转移
Hops：跳数（一台路由器/主机到另外一台路由器/主机所经过的路由器的数量，经过路由转发次数越多，跳数越大）
Launcher：启动器
```

- 功能列表
    
```
        编写 TCP 客户端和服务端
        编写支持 WebSocket 的 HTTP 客户端和服务端
        事件总线
        共享数据 —— 本地的Map和分布式集群Map
        周期性、延迟性动作
        部署和撤销 Verticle 实例
        数据报套接字
        DNS客户端
        文件系统访问
        高可用性
        集群
```

- 依赖方式

```
Maven
<dependency>
  <groupId>io.vertx</groupId>
  <artifactId>vertx-core</artifactId>
  <version>${vertx.version}</version>
</dependency>

Gradle
dependencies {
    compile 'io.vertx:vertx-core:$vertxVersion'
}

```

- Reactor 模式和 Multi-Reactor 模式

```
Vert.x 的 API 都是事件驱动的，当有事件时 Vert.x 会将事件传给处理器来处理。
在多数情况下，Vert.x使用被称为 Event Loop 的线程来调用您的处理器。
由于Vert.x或应用程序的代码块中没有阻塞，Event Loop 可以在事件到达时快速地分发到不同的处理器中。
由于没有阻塞，Event Loop 可在短时间内分发大量的事件。例如，一个单独的 Event Loop 可以非常迅速地处理数千个 HTTP 请求。
我们称之为 Reactor 模式。例如 Node.js 实现了这种模式。
在一个标准的反应器实现中，有 一个独立的 Event Loop 会循环执行，处理所有到达的事件并传递给处理器处理。
单一线程的问题在于它在任意时刻只能运行在一个核上。如果您希望单线程反应器应用（如您的 Node.js 应用）扩展到多核服务器上，则需要启动并且管理多个不同的进程。
Vert.x的工作方式有所不同。每个 Vertx 实例维护的是 多个Event Loop 线程。默认情况下，我们会根据机器上可用的核数量来设置 Event Loop 的数量，您亦可自行设置。
这意味着 Vertx 进程能够在您的服务器上扩展，与 Node.js 不同。
我们将这种模式称为 Multi-Reactor 模式（多反应器模式），区别于单线程的 Reactor 模式（反应器模式）。
```

- 黄金法则：不要阻塞Event Loop

```
Vert.x 的 API 都是非阻塞式的并且不会阻塞 Event Loop，但是这并不能帮您避免在您自己的处理器中阻塞 Event Loop 的情况发生。
如果这样做，该 Event Loop 在被阻塞时就不能做任何事情。如果您阻塞了 Vertx 实例中的所有 Event Loop，那么您的应用就会完全停止！
所以不要这样做！这是一个警告!
这些阻塞做法包括：
    Thead.sleep()
    等待一个锁
    等待一个互斥信号或监视器（例如同步的代码块）
    执行一个长时间数据库操作并等待其结果
    执行一个复杂的计算，占用了可感知的时长
    在循环语句中长时间逗留
如果上述任何一种情况停止了 Event Loop 并占用了 显著执行时间，那您应该去罚站，等待下一步的指示。
所以，什么是 显著执行时间？
您要等多久？它取决于您的应用程序和所需的并发数量。
如果您只有单个 Event Loop，而且您希望每秒处理10000个 HTTP 请求，很明显的是每一个请求处理时间不可以超过0.1毫秒，所以您不能阻塞任何过多（大于0.1毫秒）的时间。
如果您的应用程序没有响应，可能这是一个迹象，表明您在某个地方阻塞了Event Loop。为了帮助您诊断类似问题，若 Vert.x 检测到 Event Loop 有一段时间没有响应，将会自动记录这种警告。若您在日志中看到类似警告，那么您需要检查您的代码。比如：
Thread vertx-eventloop-thread-3 has been blocked for 20458 ms
Vert.x 还将提供堆栈跟踪，以精确定位发生阻塞的位置。
如果想关闭这些警告或更改设置，您可以在创建 Vertx 对象之前在 VertxOptions 中完成此操作。
```

- 运行阻塞式代码

```
可以通过调用 executeBlocking 方法来指定阻塞式代码的执行以及阻塞式代码执行后处理结果的异步回调

vertx.executeBlocking(future -> {
  // 调用一些需要耗费显著执行时间返回结果的阻塞式API
  String result = someAPI.blockingMethod("hello");
  future.complete(result);
}, res -> {
  System.out.println("The result is: " + res.result());
});

默认情况下，如果 executeBlocking 在同一个上下文环境中（如：同一个 Verticle 实例）被调用了多次，那么这些不同的 executeBlocking 代码块会 顺序执行（一个接一个）。
若不需要关心调用 executeBlocking 的顺序，可以将 ordered 参数的值设为 false。这样任何 executeBlocking 都会在 Worker Pool 中并行执行。
另外一种运行阻塞式代码的方法是使用 Worker Verticle。
一个 Worker Verticle 始终会使用 Worker Pool 中的某个线程来执行。
默认的阻塞式代码会在 Vert.x 的 Worker Pool 中执行，通过 setWorkerPoolSize 配置。
可以为不同的用途创建不同的池：

WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-worker-pool");
executor.executeBlocking(future -> {
  // 调用一些需要耗费显著执行时间返回结果的阻塞式API
  String result = someAPI.blockingMethod("hello");
  future.complete(result);
}, res -> {
  System.out.println("The result is: " + res.result());
});

Worker Executor 在不需要的时候必须被关闭：
executor.close();
当使用同一个名字创建了许多 worker 时，它们将共享同一个 pool。当所有的 worker executor 调用了 close 方法被关闭过后，对应的 worker pool 会被销毁。
如果 Worker Executor 在 Verticle 中创建，那么 Verticle 实例销毁的同时 Vert.x 将会自动关闭这个 Worker Executor。
Worker Executor 可以在创建的时候配置：

int poolSize = 10;
// 2分钟
long maxExecuteTime = 120000;
WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-worker-pool", poolSize, maxExecuteTime);

请注意：这个配置信息在 worker pool 创建的时候设置。


```

- 并发合并

```
CompositeFuture.all 方法接受多个 Future 对象作为参数（最多6个，或者传入 List）。当所有的 Future 都成功完成，该方法将返回一个 成功的 Future；当任一个 Future 执行失败，则返回一个 失败的 Future：

Future<HttpServer> httpServerFuture = Future.future();
httpServer.listen(httpServerFuture.completer());

Future<NetServer> netServerFuture = Future.future();
netServer.listen(netServerFuture.completer());

CompositeFuture.all(httpServerFuture, netServerFuture).setHandler(ar -> {
  if (ar.succeeded()) {
    // 所有服务器启动完成
  } else {
    // 有一个服务器启动失败
  }
});

所有被合并的 Future 中的操作同时运行。当组合的处理操作完成时，该方法返回的 Future 上绑定的处理器（Handler）会被调用。当一个操作失败（其中的某一个 Future 的状态被标记成失败），则返回的 Future 会被标记为失败。当所有的操作都成功时，返回的 Future 将会成功完成。

您可以传入一个 Future 列表（可能为空）：

CompositeFuture.all(Arrays.asList(future1, future2, future3));

不同于 all 方法的合并会等待所有的 Future 成功执行（或任一失败），any 方法的合并会等待第一个成功执行的Future。CompositeFuture.any 方法接受多个 Future 作为参数（最多6个，或传入 List）。当任意一个 Future 成功得到结果，则该 Future 成功；当所有的 Future 都执行失败，则该 Future 失败

CompositeFuture.any(future1, future2).setHandler(ar -> {
  if (ar.succeeded()) {
    // 至少一个成功
  } else {
    // 所有的都失败
  }
});

CompositeFuture.any(Arrays.asList(f1, f2, f3));

join 方法的合并会等待所有的 Future 完成，无论成败。CompositeFuture.join 方法接受多个 Future 作为参数（最多6个），并将结果归并成一个 Future 。当全部 Future 成功执行完成，得到的 Future 是成功状态的；当至少一个 Future 执行失败时，得到的 Future 是失败状态的。

CompositeFuture.join(future1, future2, future3).setHandler(ar -> {
  if (ar.succeeded()) {
    // 所有都成功
  } else {
    // 至少一个失败
  }
});

CompositeFuture.join(Arrays.asList(future1, future2, future3));

```

- 顺序合并

```
和 all 以及 any 实现的并发组合不同，compose 方法作用于顺序组合 Future。

FileSystem fs = vertx.fileSystem();
Future<Void> startFuture = Future.future();

Future<Void> fut1 = Future.future();
fs.createFile("/foo", fut1.completer());

fut1.compose(v -> {
  // fut1中文件创建完成后执行
  Future<Void> fut2 = Future.future();
  fs.writeFile("/foo", Buffer.buffer(), fut2.completer());
  return fut2;
}).compose(v -> {
  // fut2文件写入完成后执行
  fs.move("/foo", "/bar", startFuture.completer());
},
  // 如果任何一步失败，将startFuture标记成failed
  startFuture);

这里例子中，有三个操作被串起来了：
    一个文件被创建（fut1）
    一些东西被写入到文件（fut2）
    文件被移走（startFuture）
如果这三个步骤全部成功，则最终的 Future（startFuture）会是成功的；其中任何一步失败，则最终 Future 就是失败的。

例子中使用了：
compose(mapper)：当前 Future 完成时，执行相关代码，并返回 Future。当返回的 Future 完成时，组合完成。
compose(handler, next)：当前 Future 完成时，执行相关代码，并完成下一个 Future 的处理。
对于第二个例子，处理器需要完成 next future，以此来汇报处理成功或者失败。

您可以使用 completer 方法来串起一个带操作结果的或失败的 Future ，它可使您避免用传统方式编写代码：如果成功则完成 Future，否则就标记为失败。（译者注：3.4.0 以后不需要再使用 completer 方法）

```

- Verticle

```
Vert.x 通过开箱即用的方式提供了一个简单便捷的、可扩展的、类似 Actor Model 的部署和并发模型机制。您可以用此模型机制来保管您自己的代码组件。
这个模型是可选的，如果您不想这样做，Vert.x 不会强迫您用这种方式创建您的应用程序。
这个模型不能说是严格的 Actor 模式的实现，但它确实有相似之处，特别是在并发、扩展性和部署等方面。
要使用该模型，您需要将您的代码组织成一系列的 Verticle。
Verticle 是由 Vert.x 部署和运行的代码块。默认情况一个 Vert.x 实例维护了N（默认情况下N = CPU核数 x 2）个 Event Loop 线程。Verticle 实例可使用任意 Vert.x 支持的编程语言编写，而且一个简单的应用程序也可以包含多种语言编写的 Verticle。
可以将 Verticle 想成 Actor Model 中的 Actor。
一个应用程序通常是由在同一个 Vert.x 实例中同时运行的许多 Verticle 实例组合而成。不同的 Verticle 实例通过向 Event Bus 上发送消息来相互通信。

Verticle 的实现类必须实现 Verticle 接口。
如果您喜欢的话，可以直接实现该接口，但是通常直接从抽象类 AbstractVerticle 继承更简单。

public class MyVerticle extends AbstractVerticle {

  // Called when verticle is deployed
  // Verticle部署时调用
  public void start() {
  }

  // Optional - called when verticle is undeployed
  // 可选 - Verticle撤销时调用
  public void stop() {
  }

}

通常您需要像上边例子一样重写 start 方法。
当 Vert.x 部署 Verticle 时，它的 start 方法将被调用，这个方法执行完成后 Verticle 就变成已启动状态。
同样可以重写 stop 方法，当Vert.x 撤销一个 Verticle 时它会被调用，这个方法执行完成后 Verticle 就变成已停止状态了。

有些时候您的 Verticle 启动会耗费一些时间，您想要在这个过程做一些事，并且您做的这些事并不想等到Verticle部署完成过后再发生。如：您想在 start 方法中部署其他的 Verticle。
您不能在您的 start 方法中阻塞等待其他的 Verticle 部署完成，这样做会破坏 黄金法则。
您可以实现 异步版本 的 start 方法来做这个事。这个版本的方法会以一个 Future 作参数被调用。方法执行完时，Verticle 实例并没有部署好（状态不是 deployed）。稍后，您完成了所有您需要做的事（如：启动其他Verticle），您就可以调用 Future 的 complete（或 fail ）方法来标记启动完成或失败了。

public class MyVerticle extends AbstractVerticle {

  public void start(Future<Void> startFuture) {
    // 现在部署其他的一些verticle
    vertx.deployVerticle("com.foo.OtherVerticle", res -> {
      if (res.succeeded()) {
        startFuture.complete();
      } else {
        startFuture.fail(res.cause());
      }
    });
  }
  
  public void stop(Future<Void> stopFuture) {
    obj.doSomethingThatTakesTime(res -> {
      if (res.succeeded()) {
        stopFuture.complete();
      } else {
        stopFuture.fail();
      }
    });
  }
}

请注意：您不需要在一个 Verticle 的 stop 方法中手工去撤销启动时部署的子 Verticle，当父 Verticle 在撤销时 Vert.x 会自动撤销任何子 Verticle。

这儿有三种不同类型的 Verticle：

    Stardand Verticle：这是最常用的一类 Verticle —— 它们永远运行在 Event Loop 线程上。稍后的章节我们会讨论更多。
    Worker Verticle：这类 Verticle 会运行在 Worker Pool 中的线程上。一个实例绝对不会被多个线程同时执行。
    Multi-Threaded Worker Verticle：这类 Verticle 也会运行在 Worker Pool 中的线程上。一个实例可以由多个线程同时执行（注：因此需要开发者自己确保线程安全）。

Standard Verticle
    当 Standard Verticle 被创建时，它会被分派给一个 Event Loop 线程，并在这个 Event Loop 中执行它的 start 方法。当您在一个 Event Loop 上调用了 Core API 中的方法并传入了处理器时，Vert.x 将保证用与调用该方法时相同的 Event Loop 来执行这些处理器。
    这意味着我们可以保证您的 Verticle 实例中 所有的代码都是在相同Event Loop中执行（只要您不创建自己的线程并调用它！）
    同样意味着您可以将您的应用中的所有代码用单线程方式编写，让 Vert.x 去考虑线程和扩展问题。您不用再考虑 synchronized 和 volatile 的问题，也可以避免传统的多线程应用经常会遇到的竞态条件和死锁的问题。
    
Worker Verticle
    Worker Verticle 和 Standard Verticle 很像，但它并不是由一个 Event Loop 来执行，而是由Vert.x中的 Worker Pool 中的线程执行。
    Worker Verticle 被设计来调用阻塞式代码，它不会阻塞任何 Event Loop。
    如果您不想使用 Worker Verticle 来运行阻塞式代码，您还可以在一个Event Loop中直接使用 内联阻塞式代码。
    若您想要将 Verticle 部署成一个 Worker Verticle，您可以通过 setWorker 方法来设置：
    
    DeploymentOptions options = new DeploymentOptions().setWorker(true);
    vertx.deployVerticle("com.mycompany.MyOrderProcessorVerticle", options);

    Worker Verticle 实例绝对不会在 Vert.x 中被多个线程同时执行，但它可以在不同时间由不同线程执行
    
Multi-threaded Worker Verticle
    一个 Multi-threaded Worker Verticle 近似于普通的 Worker Verticle，但是它可以由不同的线程同时执行。
    警告：Multi-threaded Worker Verticle 是一个高级功能，大部分应用程序不会需要它。由于这些 Verticle 是并发的，您必须小心地使用标准的Java多线程技术来保持 Verticle 的状态一致性。
    
    
可以指定一个 Verticle 名称或传入您已经创建好的 Verticle 实例，使用任意一个 deployVerticle 方法来部署Verticle。

请注意：通过 Verticle 实例 来部署 Verticle 仅限Java语言。

Verticle myVerticle = new MyVerticle();
vertx.deployVerticle(myVerticle);

同样可以指定 Verticle 的 名称 来部署它。

这个 Verticle 的名称会用于查找实例化 Verticle 的特定 VerticleFactory。

不同的 Verticle Factory 可用于实例化不同语言的 Verticle，也可用于其他目的，例如加载服务、运行时从Maven中获取Verticle实例等。

这允许部署用任何使用Vert.x支持的语言编写的Verticle实例。

vertx.deployVerticle("com.mycompany.MyOrderProcessorVerticle");
// 部署JavaScript的Verticle
vertx.deployVerticle("verticles/myverticle.js");
// 部署Ruby的Verticle
vertx.deployVerticle("verticles/my_verticle.rb");

Verticle名称到Factory的映射规则
当使用名称部署Verticle时，会通过名称来选择一个用于实例化 Verticle 的 Verticle Factory。
Verticle 名称可以有一个前缀 —— 使用字符串紧跟着一个冒号，它用于查找存在的Factory，参考例子。

js:foo.js // 使用JavaScript的Factory
groovy:com.mycompany.SomeGroovyCompiledVerticle // 用Groovy的Factory
service:com.mycompany:myorderservice // 用Service的Factory

如果不指定前缀，Vert.x将根据提供名字后缀来查找对应Factory，如：

foo.js // 将使用JavaScript的Factory
SomeScript.groovy // 将使用Groovy的Factory

若前缀后缀都没指定，Vert.x将假定这个名字是一个Java 全限定类名（FQCN）然后尝试实例化它。

如何定位Verticle Factory？
大部分Verticle Factory会从 classpath 中加载，并在 Vert.x 启动时注册。

您同样可以使用编程的方式去注册或注销Verticle Factory：通过 registerVerticleFactory 方法和 unregisterVerticleFactory 方法。


```

- Verticle 隔离组

```
默认情况，当Vert.x部署Verticle时它会调用当前类加载器来加载类，而不会创建一个新的。大多数情况下，这是最简单、最清晰和最干净。

但是在某些情况下，您可能需要部署一个Verticle，它包含的类要与应用程序中其他类隔离开来。比如您想要在一个Vert.x实例中部署两个同名不同版本的Verticle，或者不同的Verticle使用了同一个jar包的不同版本。

当使用隔离组时，您需要用 setIsolatedClassed 方法来提供一个您想隔离的类名列表。列表项可以是一个Java 限定类全名，如 com.mycompany.myproject.engine.MyClass；也可以是包含通配符的可匹配某个包或子包的任何类，例如 com.mycompany.myproject.* 将会匹配所有 com.mycompany.myproject 包或任意子包中的任意类名。

请注意仅仅只有匹配的类会被隔离，其他任意类会被当前类加载器加载。

若您想要加载的类和资源不存在于主类路径（main classpath），您可使用 setExtraClasspath 方法将额外的类路径添加到这里。

警告：谨慎使用此功能，类加载器可能会导致您的应用难于调试，变得一团乱麻（can of worms）。

DeploymentOptions options = new DeploymentOptions().setIsolationGroup("mygroup");
options.setIsolatedClasses(Arrays.asList("com.mycompany.myverticle.*",
                   "com.mycompany.somepkg.SomeClass", "org.somelibrary.*"));
vertx.deployVerticle("com.mycompany.myverticle.VerticleClass", options);

```

- 从命令行运行Verticle

```

您可以在 Maven 或 Gradle 项目中以正常方式添加 Vert.x Core 为依赖，在项目中直接使用 Vert.x。

但是，您也可以从命令行直接运行 Vert.x 的 Verticle。

为此，您需要下载并安装 Vert.x 的发行版，并且将安装的 bin 目录添加到您的 PATH 环境变量中，还要确保您的 PATH 中设置了Java 8的JDK环境。

请注意：JDK需要支持Java代码的运行时编译（on the fly compilation）。

现在您可以使用 vertx run 命令运行Verticle了，这儿是一些例子：

# 运行JavaScript的Verticle
vertx run my_verticle.js

# 运行Ruby的Verticle
vertx run a_n_other_verticle.rb

# 使用集群模式运行Groovy的Verticle
vertx run FooVerticle.groovy -cluster
您甚至可以不必编译 Java 源代码，直接运行它：

vertx run SomeJavaSourceFile.java
Vert.x 将在运行它之前对 Java 源代码文件执行运行时编译，这对于快速原型制作和演示很有用。不需要设置 Maven 或 Gradle 就能跑起来！

```

- Context 对象

```
当 Vert.x 传递一个事件给处理器或者调用 Verticle 的 start 或 stop 方法时，它会关联一个 Context 对象来执行。通常来说这个 Context 会是一个 Event Loop Context，它绑定到了一个特定的 Event Loop 线程上。所以在该 Context 上执行的操作总是在同一个 Event Loop 线程中。对于运行内联的阻塞代码的 Worker Verticle 来说，会关联一个 Worker Context，并且所有的操作运都会运行在 Worker 线程池的线程上。

每个 Verticle 在部署的时候都会被分配一个 Context（根据配置不同，可以是Event Loop Context 或者 Worker Context），之后此 Verticle 上所有的普通代码都会在此 Context 上执行（即对应的 Event Loop 或Worker 线程）。一个 Context 对应一个 Event Loop 线程（或 Worker 线程），但一个 Event Loop 可能对应多个 Context。

Context context = vertx.getOrCreateContext();

若已经有一个 Context 和当前线程关联，那么它直接重用这个 Context 对象，如果没有则创建一个新的。您可以检查获取的 Context 的类型：

Context context = vertx.getOrCreateContext();
if (context.isEventLoopContext()) {
  System.out.println("Context attached to Event Loop");
} else if (context.isWorkerContext()) {
  System.out.println("Context attached to Worker Thread");
} else if (context.isMultiThreadedWorkerContext()) {
  System.out.println("Context attached to Worker Thread - multi threaded worker");
} else if (! Context.isOnVertxThread()) {
  System.out.println("Context not attached to a thread managed by vert.x");
}

当您获取了这个 Context 对象，您就可以在 Context 中异步执行代码了。换句话说，您提交的任务将会在同一个 Context 中运行：

vertx.getOrCreateContext().runOnContext(v -> {
  System.out.println("This will be executed asynchronously in the same context");
})

当在同一个 Context 中运行了多个处理函数时，可能需要在它们之间共享数据。 Context 对象提供了存储和读取共享数据的方法。举例来说，它允许您将数据传递到 runOnContext 方法运行的某些操作中：

final Context context = vertx.getOrCreateContext();
context.put("data", "hello");
context.runOnContext((v) -> {
  String hello = context.get("data");
});

```

- 执行周期性/延迟性操作

```

在 Vert.x 中，想要延迟之后执行或定期执行操作很常见。

在 Standard Verticle 中您不能直接让线程休眠以引入延迟，因为它会阻塞 Event Loop 线程。取而代之是使用 Vert.x 定时器。定时器可以是一次性或周期性的

一次性计时器
    一次性计时器会在一定延迟后调用一个 Event Handler，以毫秒为单位计时。
    可以通过 setTimer 方法传递延迟时间和一个处理器来设置计时器的触发。
    
    long timerID = vertx.setTimer(1000, id -> {
      System.out.println("And one second later this is printed");
    });
    System.out.println("First this is printed");

    返回值是一个唯一的计时器id，该id可用于之后取消该计时器，这个计时器id会传入给处理器。
    
周期性计时器
    您同样可以使用 setPeriodic 方法设置一个周期性触发的计时器。第一次触发之前同样会有一段设置的延时时间。

    setPeriodic 方法的返回值也是一个唯一的计时器id，若之后该计时器需要取消则使用该id。传给处理器的参数也是这个唯一的计时器id。
    
    请记住这个计时器将会定期触发。如果您的定时任务会花费大量的时间，则您的计时器事件可能会连续执行甚至发生更坏的情况：重叠。这种情况，您应考虑使用 setTimer 方法，当任务执行完成时设置下一个计时器
    
    long timerID = vertx.setPeriodic(1000, id -> {
      System.out.println("And every second this is printed");
    });
    
    System.out.println("First this is printed");

取消计时器
    指定一个计时器id并调用 cancelTimer 方法来取消一个周期性计时器
    
    vertx.cancelTimer(timerID);
    
    如果您在 Verticle 中创建了计时器，当这个 Verticle 被撤销时这个计时器会被自动关闭。    
    
```

- Verticle Worker Pool

```

Verticle 使用 Vert.x 中的 Worker Pool 来执行阻塞式行为，例如 executeBlocking 或 Worker Verticle。
可以在部署配置项中指定不同的Worker 线程池

vertx.deployVerticle("the-verticle", new DeploymentOptions().setWorkerPoolName("the-specific-pool"));


```

- Event Bus

```

Event Bus 是 Vert.x 的神经系统。

    每一个 Vert.x 实例都有一个单独的 Event Bus 实例。您可以通过 Vertx 实例的 eventBus 方法来获得对应的 EventBus 实例。
    
    您的应用中的不同部分通过 Event Bus 相互通信，无论它们使用哪一种语言实现，无论它们在同一个 Vert.x 实例中或在不同的 Vert.x 实例中。
    
    甚至可以通过桥接的方式允许在浏览器中运行的客户端JavaScript在相同的Event Bus上相互通信。
    
    Event Bus可形成跨越多个服务器节点和多个浏览器的点对点的分布式消息系统。
    
    Event Bus支持发布/订阅、点对点、请求/响应的消息通信方式。
    
    Event Bus的API很简单。基本上只涉及注册处理器、撤销处理器和发送和发布消息。

寻址
    消息会被 Event Bus 发送到一个 地址(address)。
    
    同任何花哨的寻址方案相比，Vert.x的地址格式并不麻烦。Vert.x中的地址是一个简单的字符串，任意字符串都合法。当然，使用某种模式来命名仍然是明智的。如：使用点号来划分命名空间。
    
    一些合法的地址形如：europe.news.feed1、acme.games.pacman、sausages和X。


处理器
    消息在处理器（Handler）中被接收。您可以在某个地址上注册一个处理器来接收消息。
    
    同一个地址可以注册许多不同的处理器，一个处理器也可以注册在多个不同的地址上。
    
发布/订阅消息
    Event Bus支持 发布消息 功能。
    
    消息将被发布到一个地址中，发布意味着会将信息传递给 所有 注册在该地址上的处理器。这和 发布/订阅模式 很类似。

点对点模式/请求-响应模式
    Event Bus也支持 点对点消息模式。
    
    消息将被发送到一个地址中，Vert.x将会把消息分发到某个注册在该地址上的处理器。若这个地址上有不止一个注册过的处理器，它将使用 不严格的轮询算法 选择其中一个。
    
    点对点消息传递模式下，可在消息发送的时候指定一个应答处理器（可选）。
    
    当接收者收到消息并且已经被处理时，它可以选择性决定回复该消息，若选择回复则绑定的应答处理器将会被调用。当发送者收到回复消息时，它也可以回复，这个过程可以不断重复。通过这种方式可以允许在两个不同的 Verticle 之间设置一个对话窗口。这种消息模式被称作 请求-响应 模式。
    
尽力传输
    Vert.x会尽它最大努力去传递消息，并且不会主动丢弃消息。这种方式称为 尽力传输(Best-effort delivery)。
    
    但是，当 Event Bus 中的全部或部分发生故障时，则可能会丢失消息。
    
    若您的应用关心丢失的消息，您应该编写具有幂等性的处理器，并且您的发送者可以在恢复后重试。
    
    RPC通信通常情况下有三种语义：at least once、at most once 和 exactly once。不同语义情况下要考虑的情况不同。
    
消息类型
    Vert.x 默认允许任何基本/简单类型、String 或 Buffer 作为消息发送。不过在 Vert.x 中的通常做法是使用 JSON 格式来发送消息。
    
    JSON 对于 Vert.x 支持的所有语言都是非常容易创建、读取和解析的，因此它已经成为了Vert.x中的通用语(lingua franca)。但是若您不想用 JSON，我们并不强制您使用它。
    
    Event Bus 非常灵活，它支持在 Event Bus 中发送任意对象。您可以通过为您想要发送的对象自定义一个 MessageCodec 来实现。

```

