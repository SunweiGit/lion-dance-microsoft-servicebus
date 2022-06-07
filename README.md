# 注意事项

#### 获取服务总线命名空间连接字符串

    大多数示例使用为该服务总线命名空间生成的连接字符串对服务总线进行授权。连接字符串值可以通过以下方式获得：

* 转到 Azure 门户中的服务总线命名空间。
* 转到您的服务总线命名空间的“共享访问策略”。
* 单击“RootManageSharedAccessKey”策略。
* 从策略的属性中复制连接字符串

#### 参考地址

* [官网地址](https://azure.microsoft.com/zh-cn/services/service-bus/#getting-started)
* [文档地址](https://docs.microsoft.com/zh-cn/azure/service-bus-messaging)
* [参考代码](https://github.com/Azure/azure-sdk-for-java/tree/main/sdk/servicebus/azure-messaging-servicebus/src/samples)

#### schedule Message

* 1 、offsetDateTime < now() 消息会立即处理,延时失效
* 2 、发送延时消息需要 OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(8));
* 3 、消息生存时间不影响延时的消息(比如消息生存时间设置为5分钟,在2022-06-07 15:00:00 发送一条延时十分钟的消息。十分钟后可以正常接收)
* .....

#### senderClient

* 1、 spring boot 创建的senderClient bean 一旦关闭就无法在使用。这里要注意！！！
