consul

---

# 安装consul

- 下载安装包
    - macOS tool/consul_1.0.3_darwin_amd64.zip
    - linux tool/consul_1.0.3_linux_amd64.zip
    - windows tool/consul_1.0.3_windows_amd64.zip

- 配置环境变量
    - macOs、linux export PATH=$PATH:/path/to/dir 
    - windows 环境变量配置PATH c:\path;c:\path2
    
- 验证安装完成
    - 控制台输入 consul 命令 出现如下界面
        
        usage: consul [--version] [--help] <command> [<args>]
        Available commands are:
        agent          Runs a Consul agent
        event          Fire a new event
        
# 启动consul agent

    代理可以运行服务器和客户端模式，每个数据中心必须至少一个服务，集群推荐3或5个服务。不推荐单机部署，故障情况下会丢失数据

- 启动consul
    - 启动开发模式 consul agent -dev
   
    ==> Starting Consul agent...
    ==> Consul agent running!
           Version: 'v1.0.3'
           Node ID: 'e1678894-52d1-44c6-656d-5d69dd8d98e3'
         Node name: 'cloud'
        Datacenter: 'dc1' (Segment: '<all>')
            Server: true (Bootstrap: false)
       Client Addr: [127.0.0.1] (HTTP: 8500, HTTPS: -1, DNS: 8600)
      Cluster Addr: 127.0.0.1 (LAN: 8301, WAN: 8302)
           Encrypt: Gossip: false, TLS-Outgoing: false, TLS-Incoming: false
        2018/02/07 14:34:04 [DEBUG] Using random ID "e1678894-52d1-44c6-656d-5d69dd8d98e3" as node ID
        2018/02/07 14:34:04 [INFO] raft: Initial configuration (index=1): [{Suffrage:Voter ID:e1678894-52d1-44c6-656d-5d69dd8d98e3 Address:127.0.0.1:8300}]
        2018/02/07 14:34:04 [INFO] raft: Node at 127.0.0.1:8300 [Follower] entering Follower state (Leader: "")
        2018/02/07 14:34:04 [INFO] serf: EventMemberJoin: cloud.dc1 127.0.0.1
        2018/02/07 14:34:04 [INFO] serf: EventMemberJoin: cloud 127.0.0.1
        2018/02/07 14:34:04 [INFO] consul: Adding LAN server cloud (Addr: tcp/127.0.0.1:8300) (DC: dc1)
        2018/02/07 14:34:04 [INFO] consul: Handled member-join event for server "cloud.dc1" in area "wan"
        2018/02/07 14:34:04 [INFO] agent: Started DNS server 127.0.0.1:8600 (udp)
        2018/02/07 14:34:04 [INFO] agent: Started DNS server 127.0.0.1:8600 (tcp)
        2018/02/07 14:34:04 [INFO] agent: Started HTTP server on 127.0.0.1:8500 (tcp)
        2018/02/07 14:34:04 [INFO] agent: started state syncer
        2018/02/07 14:34:04 [WARN] raft: Heartbeat timeout from "" reached, starting election
        2018/02/07 14:34:04 [INFO] raft: Node at 127.0.0.1:8300 [Candidate] entering Candidate state in term 2
        2018/02/07 14:34:04 [DEBUG] raft: Votes needed: 1
        2018/02/07 14:34:04 [DEBUG] raft: Vote granted from e1678894-52d1-44c6-656d-5d69dd8d98e3 in term 2. Tally: 1
        2018/02/07 14:34:04 [INFO] raft: Election won. Tally: 1
        2018/02/07 14:34:04 [INFO] raft: Node at 127.0.0.1:8300 [Leader] entering Leader state
        2018/02/07 14:34:04 [INFO] consul: cluster leadership acquired
        2018/02/07 14:34:04 [INFO] consul: New leader elected: cloud
        2018/02/07 14:34:04 [DEBUG] consul: Skipping self join check for "cloud" since the cluster is too small
        2018/02/07 14:34:04 [INFO] consul: member 'cloud' joined, marking health alive
        2018/02/07 14:34:04 [DEBUG] Skipping remote check "serfHealth" since it is managed automatically
        2018/02/07 14:34:04 [INFO] agent: Synced node info
        2018/02/07 14:34:04 [DEBUG] agent: Node info in sync
        2018/02/07 14:34:06 [DEBUG] Skipping remote check "serfHealth" since it is managed automatically
        2018/02/07 14:34:06 [DEBUG] agent: Node info in sync

- 集群成员
    - consul members可以查看集群中成员
    - 增加 -detailed 命令可以查看详情
        
        Node   Address         Status  Type    Build  Protocol  DC   Segment
        cloud  127.0.0.1:8301  alive   server  1.0.3  2         dc1  <all>
    
        Node   Address         Status  Tags
        cloud  127.0.0.1:8301  alive   build=1.0.3:48f3dd56,dc=dc1,id=e1678894-52d1-44c6-656d-5d69dd8d98e3,port=8300,raft_vsn=3,role=consul,segment=<all>,vsn=2,vsn_max=3,vsn_min=2,wan_join_port=8302
    
- 停止服务
    - consul leave -option
        - ca-file=<value>
        - ca-path=<value>
        - client-cert=<value> 
        - client-key=<value> 
        - http-addr=<addr>
        - tls-server-name=<value>
        - token=<value>