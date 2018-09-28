# RPC Framework

**轻量级远程服务注册, 调用, 发现框架。**

## 简介

RPC基于TCP, 通过序列化等机制, 将调用请求发送到具有此服务的主机上, 其中由服务发现框架进行服务发现, 降低耦合, 然后服务主机处理请求, 返回序列化后的调用结果

为了实现RPC, 使用了如下模块:

- Spring: 作为Bean管理容器
- Nettey: 实现RPC客户端和服务端
- Zookeeper: 提供服务发现的功能, 天生具有集群高可用性
- ProtoStuff: 基于ProtoBuf的序列化反序列化工具, 拥有比Java底层序列化机制更高的字节压缩率, 在网络传输中提高效率

以这些作为支持组成了三个Module:

- RPCient: 通过Zookeeper进行服务发现, 获得具有此服务的主机地址, 利用Netty进行请求, Spring管理
- RPCore: 提供序列化工具类以及所有服务的接口包, 作为RPCient和RPCServer的依赖
- RPCServer: 利用Spring管理, Nettey作为服务器, 启动时注册地址到Zookeeper, 发布部分或全部RPCore的服务接口的具体实现


## 具体使用

### 服务注册

在RPCore包下编写服务的接口(可以利用JMX)：

```java
public interface ILookupService {
    /**
     * Look up by key
     * @param key key to look up
     * @return value
     */
    String lookup(String key);
}
```

在RPCServer中发布服务

```java
/**
* 通过 @RpcService 注册到Zookeeper中维护一个服务映射表
* ILookUpService是服务接口
*/
@RpcService(ILookupService.class)
public class LookUpService implements ILookupService{

    Map<String, String> resultMap = new HashMap<>();

    public LookUpService() {
        initData();
    }

    private void initData() {
        resultMap.put("A", "Hello Stranger");
        resultMap.put("B", "This is some time");
        resultMap.put("C", "And a better life");
        resultMap.put("D", "Just some test data");
    }

    @Override
    public String lookup(String key) {
        return resultMap.getOrDefault(key, "null String");
    }

}
```

在RPClient中调用服务, 服务发现对它来说透明, 它不需要考虑哪个主机上有哪些服务

```java
/**
* Spring管理
*/
public class RPCTest extends BaseTest{

    /**
    * 调用代理, 利用Java Proxy实现
    */
    @Autowired
    RpcProxy proxy;

    @Test
    public void main() {
        // 根据接口获取服务
        ILookupService service = proxy.getService(ILookupService.class);
        // 调用
        Object A = service.lookup("A");
        Object B = service.lookup("B");
        Object C = service.lookup("C");
        Object D = service.lookup("D");
        // 验证
        Assert.assertEquals(A, "Hello Stranger");
        Assert.assertEquals(B, "This is some time");
        Assert.assertEquals(C, "And a better life");
        Assert.assertEquals(D, "Just some test data");
    }
}
```

输出：

绿条



## TODO

- [x] 透明查找
- [x] 异步高并发
- [ ] 支持长连接
- [ ] JMX支持
- [ ] Token check/refresh
- [ ] Setting
- [ ] 规范、完善的测试

[badge-state]: https://img.shields.io/pypi/status/zhihu_oauth.svg
[badge-license]: https://img.shields.io/pypi/l/zhihu_oauth.svg
