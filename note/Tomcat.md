Tomcat

---

# Tomcat 8.5 基于 Apache Portable Runtime（APR）库性能优化

    Tomcat可以使用Apache Portable Runtime来提供卓越的性能及可扩展性，更好地与本地服务器技术的集成。Apache Portable Runtime是一个高度可移植的库，位于Apache HTTP Server 2.x的核心。APR有许多用途，包括访问高级IO功能（如sendfile，epoll和OpenSSL），操作系统级功能（随机数生成，系统状态等）以及本地进程处理（共享内存，NT管道和Unix套接字）
    
- 官方要求

```
APR 1.2+ development headers (libapr1-dev package)
OpenSSL 1.0.2+ development headers (libssl-dev package)
JNI headers from Java compatible JDK 1.4+
GNU development environment (gcc, make)
```

- 生产环境

```
CentOS Linux release 7.3.1611 (Core) x86 64
Server version: Apache Tomcat/8.5.16
java version “1.8.0_131”
```

- 安装相关依赖包

```
# yum -y install gcc gcc-c++ libtool* autoconf automake expat-devel perl perl-devel
```

- 下载安装包

```
# cd /tmp/
# wget http://mirror.bit.edu.cn/apache/apr/apr-1.6.2.tar.gz
# wget http://mirror.bit.edu.cn/apache/apr/apr-iconv-1.2.1.tar.gz
# wget http://mirror.bit.edu.cn/apache/apr/apr-util-1.6.0.tar.gz
# wget https://www.openssl.org/source/openssl-1.1.0f.tar.gz
```
- 安装APR

```
# tar zxvf apr-1.6.2.tar.gz
# cd apr-1.6.2
# vim configure
默认值:

RM='$RM'
修改为:

RM='$RM -f'
# ./configure --prefix=/usr/local/apr
# make && make install
```

- 安装apr-iconv

```
# tar zxvf apr-iconv-1.2.1.tar.gz
# cd apr-iconv-1.2.1
# ./configure --prefix=/usr/local/apr-iconv --with-apr=/usr/local/apr
# make && make install
```

- 安装apr-util

```
# tar zxvf apr-util-1.6.0.tar.gz
# cd apr-util-1.6.0
# ./configure --prefix=/usr/local/apr-util --with-apr=/usr/local/apr --with-apr-iconv=/usr/local/apr-iconv/bin/apriconv
# make && make install
```

- 安装OpenSSL

```
# tar zxvf openssl-1.1.0f.tar.gz
# cd openssl-1.1.0f
# ./config --prefix=/usr/local/openssl
# make -j 4 && make install
```

- 安装tomcat-native

```
# cd /usr/local/tomcat/bin/
# tar zxvf tomcat-native.tar.gz
# cd tomcat-native-1.2.12-src/native
# ./configure --with-ssl=/usr/local/openssl --with-apr=/usr/bin/apr-1-config --with-java-home=/usr/java/jdk1.8.0_131
# make && make install
注意:如果以上 configure 失败，可以执行 make distclean 清除
```

- 添加变量内容

```
# vim /etc/profile.d/jdk.sh
export LD_LIBRARY_PATH=/usr/local/apr/lib:$LD_LIBRARY_PATH
# source /etc/profile.d/jdk.sh
至此APR安装成功。
```

- 接下来需要修改tomcat配置文件中的APR运行模式，并测试是否安装成功。

```
# vim /usr/local/tomcat/conf/server.xml
默认值：

<Connector port="8080" protocol="HTTP/1.1"
 connectionTimeout="20000"
 redirectPort="8443" />
修改为：

<Connector port="8080" protocol="org.apache.coyote.http11.Http11AprProtocol"
 connectionTimeout="20000"
 redirectPort="8443" />
默认值：

<Connector port="8009" protocol="AJP/1.3" redirectPort="8443" />
修改为：

<Connector port="8009" protocol="org.apache.coyote.ajp.AjpAprProtocol" redirectPort="8443" />
```

- 现在重启tomcat服务,并查看启动日志

```
# systemctl restart tomcat
# cat /usr/local/tomcat/logs/catalina.out
...
INFO [main] org.apache.catalina.core.AprLifecycleListener.lifecycleEvent The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib]
INFO [main] org.apache.coyote.AbstractProtocol.init Initializing ProtocolHandler ["http-nio-8080"]
INFO [main] org.apache.tomcat.util.net.NioSelectorPool.getSharedSelector Using a shared selector for servlet write/read
INFO [main] org.apache.coyote.AbstractProtocol.init Initializing ProtocolHandler ["ajp-nio-8009"]
INFO [main] org.apache.tomcat.util.net.NioSelectorPool.getSharedSelector Using a shared selector for servlet write/read
...
注意:可以看到红色部分，提示找不到基于APR的Apache Tomcat Native库，因此无法使用APR模式启动。

解决方案：

# cp -R /usr/local/apr/lib/* /usr/lib64
# cp -R /usr/local/apr/lib/* /usr/lib
再次重启tomcat，并查看启动日志

# cat /usr/local/tomcat/logs/catalina.out
...
INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["http-apr-8080"]
INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["http-apr-8009"]
...
```