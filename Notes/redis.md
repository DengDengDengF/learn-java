```bash
# 看所有当前生效配置
redis-cli CONFIG GET *
# 启动
net start Redis
# 关闭
net stop Redis
# 临时修改最大内存
redis-cli CONFIG SET maxmemory 10mb
# 临时修改内存满了后的淘汰策略（allkeys-lru：从所有键中淘汰最近最少使用的）
redis-cli CONFIG SET maxmemory-policy allkeys-lru
# 把当前运行时配置写回配置文件，和 CONFIG SET 配合使用
redis-cli CONFIG REWRITE
# 内存占用
redis-cli INFO memory
# 清空所有库
redis-cli FLUSHALL
```

