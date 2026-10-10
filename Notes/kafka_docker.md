### 在 Kafka 配置目录下执行，如 C:\App\kafka-docker

```bash
# 更新镜像,配合启动kafka
docker compose pull <services>
docker compose up -d <services>

# 启动 Kafka（后台）
docker compose up -d

# 停止并删除容器 + 数据卷（清空数据-v）
docker compose down -v

# 查看运行状态
docker compose ps

# 查看 Kafka 实时日志
docker compose logs -f kafka

```

