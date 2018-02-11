RPC

---

    远程过程调用，是一个分布式系统间通信的必备技术。最核心要解决的问题就是在分布式系统间，如何执行另外一个地址空间上的函数、方法，就仿佛在本地调用一样。
    
# 核心概念和技术

- 传输(Transport)
    - TCP协议是RPC的基石，一般来说通信的建立在TCP协议之上的，而且RPC往往需要可靠的通信，因此不采用UDP
    - TCP的关键词：面向连接的，全双工，可靠传输(按序、不重、不丢、容错)，流量控制(滑动窗口)
    - RPC中的嵌套header+body，协议栈每一层都要包含下一层协议的全部数据，只不过包了一个头
    - RPC传输的message也就是TCP body中的数据，这个message也同样可以包含header+body。body也经常叫做payload
    - TCP就是可靠地把数据在不同地址空间上搬运
    - TCP协议栈存在端口的概念，端口是进程获取数据的渠道
    
- I/O模型(I/O Model)
    - 做一个高性能可伸缩的RPC需要满足
        - 服务端尽可能多的处理并发请求
        - 同时尽可能短的处理完毕
    - CPU和I/O之间存在着差异，网络传输的延时不可控，例如：如果有线程调用I/O没有响应，CPU只能选择挂起，线程也被I/O阻塞住
    - 传统的阻塞I/O(Blocking I/O)
    - 非阻塞I/O(Non-blocking I/O)
    - I/O多路复用(I/O multiplexing)
        - 基于内核，建立在epoll或者kqueue上实现，I/O多路复用最大的优势是用户可以在一个线程内同时处理多个Socket的I/O请求。用户可以订阅事件，包括文件描述符或者I/O可读、可写、可连接事件等
        - 通过一个线程监听全部的TCP连接，有任何事件发生就通知用户态处理即可，这么做的目的就是假设I/O是慢的，CPU是快的，那么让用户态尽可能的忙碌起来，最大化CPU利用率，避免传统的I/O阻塞
    - 异步I/O(Asynchronous I/O)
        - 用户线程发起I/O请求直接退出，当内核I/O操作完成后通知用户线程来调用其回调函数

- 进程/线程模式(Thread/Process Model)
    -  可伸缩I/O
        - Read -> Decode -> Compute -> Encode -> Send
        - 使用传统的阻塞I/O+线程池方案(Multitasks)会遇到C10k问题
        - 但是业界很多实现都是这个方式，Tomcat/Jetty默认配置就采用这个方法，可以工作得很好
        - 从I/O模型可以看出它会让工作线程卡在I/O上，而一个系统可使用的线程数量是有限的，所有才有了多路复用和异步I/O
        - I/O多路复用往往对应Reactor模式，异步I/O往往对应Proactor
        - Reactor一般使用epoll+事件驱动的经典模式，通过分治手段，把耗时的网络连接、安全认证、编码等工作交给专门的线程完成，然后再去调用真正的核心业务逻辑层，这在*nix系统中被广泛使用
        - 著名的Redis、Nginx、Node.js的Socket I/O都用的这个，java的NIO框架Netty也是，Spark2.0RPC所依赖的同样采用Reactor模式
        - Proactor在*nix中没有很好的实现，但是在windows上大放异彩(例如IOCP模型)
        
- Schema和序列化(Schema & Data Serialization)
    - 序列化框架
        - Encoding format是human readable还是binary
        - Schema declaration，也叫契约声明，基于IDL，比如Protocol Buffers/Thrift，还是自描述，比如JSON、XML。另外还需要看是否是强类型
        - 语言平台的中立性。比如Java的Native Serialization就只能自己玩，而Protobuf可以跨各种语言和平台
        - 新老契约的兼容性。比如IDL加了一个字段，老数据是否还可以反序列化成功
        - 和压缩算法的契合度。跑benchmark和实际应用都会结合各种压缩算法，例如gzip、snappy
        - 性能。这是最重要的，序列化、反序列化的时间，序列化后数据的字节大小是考察重点
    - 序列化方式
        - Protobuf
        - Avro
        - Thrift
        - XML
        - JSON
        - MessagePack
        - Kyro
        - Hessian
        - Protostuff
        - Java Native Serialize
        - FST
    - protobuf
        - 紧凑高效
        - 使用字段的序号作为标识，使用varint和zigzag对整型做特殊处理
        - 可以跨语言，前提是使用IDL编写描述文件，然后codegen工具生成各种语言的代码

- 协议结构(Wire Protocol)
    - Socket范畴里讨论的包叫做Frame、Packet、Segment都没错，但是一般把这些分别映射为数据链路层、IP层和TCP层的数据包
    - TCP只是binary stream通道，是binary数据的可靠搬运工，它不懂RPC里面包装的是什么。而在一个通道上传输message势必涉及message的识别
    - TCP粘包和半包
        - 发送端发送 ABC + DEF + GHI 分3个message
        - 接收端接收到4个Frame AB + CDEFG + H + I
        - AB、H、I称作半包，CDEFG称作粘包
        - 虽然顺序是对的，但是分组完全和之前对不上
    - 采用方式
        - 分隔符
        - 换行符，比如memcache由客户端发送的命令使用的是文本行\r\n作为message的分隔符
        - 固定长度，RPC经常采用这种方式，使用header+payload的方式
            - 比如HTTP协议，建立在TCP之上最广泛使用的RPC,HTTP头肯定有一个content-length告知应用层如何去读懂一个message，做HTTP包的识别
    
    
- 可靠性(Reliability)
    - 保持长连接心跳
    - 网络闪断
    - 重连、重传

- 易用性(Ease of use)
    - 优雅的启动停止一个server
    - 注入endpoint
    - 客户端连接
    - 重试调用
    - 超时控制
    - 同步异步调用
    - SDK是否需要交换
    
- 业界RPC框架
    - Dubbo：阿里巴巴
    - Motan：新浪微博
    - Dubbox：当当基于dubbo的
    - rpcx：基于Golang的
    - Thrift：Facebook
    - Avro：hadoop
    - Finagle：Twitter
    - gRPC：Google
    - Hessian：cuacho
    - Coral Service：amazon(没有开源)