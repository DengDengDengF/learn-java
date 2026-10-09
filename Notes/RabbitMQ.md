```bash
# 启动
net start rabbitmq

# 关闭
net stop rabbitmq

# 临时固定内存阈值
rabbitmqctl.bat set_vm_memory_high_watermark absolute "200MB"

# 永久固定内存阈值
C:\Users\XG1001001\AppData\Roaming\RabbitMQ\rabbitmq.conf
vm_memory_high_watermark.absolute = 200MB

# 列出所有队列
rabbitmqctl.bat list_queues name --quiet

# 清理积压队列（内存+磁盘，只要ready状态）
rabbitmqctl.bat purge_queue <queue_name>

# 匹配所有队列，单队列最多 10MB，超出拒收新消息（设一次即持久，重启保留）
rabbitmqctl.bat set_policy max-size "^.*" --% "{""max-length-bytes"":10485760,""overflow"":""reject-publish""}" --apply-to queues

# 查看已有策略
rabbitmqctl.bat list_policies

# 删除策略
rabbitmqctl.bat clear_policy max-size

# 内存分析
rabbitmq-diagnostics.bat memory_breakdown

# 查看监听端口
rabbitmq-diagnostics listeners

http://localhost:15672/#/
交换机/队列，可以通过可视化控制台设置持久化。
队列中的消息，可以通过spring amqp默认持久化。
```

