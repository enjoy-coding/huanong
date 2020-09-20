接入微信公众平台开发，开发者需要按照如下步骤完成：
1、填写服务器配置(填写服务器地址（URL）、Token和EncodingAESKey，其中URL是开发者用来接收微信消息和事件的接口URL。Token可由开发者可以任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）。EncodingAESKey由开发者手动填写或随机生成，将用作消息体加解密密钥。)
2、开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。
3、依据接口文档实现业务逻辑


调用的时候配置：
1.在配置文件中增加配置参数：WX_MSG_REQUEST_URL(微信请求地址，对应地址部署在对的微信服务器中,值类似http://localhost:8082/wx/sendMsg)


海康平台事件调用配置
1.在HikvisionApplicationRunner类中serverConfig.getUrl()确保这个地址是外网的ip，内网是无法做到事件回调的