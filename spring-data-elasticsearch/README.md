# spring-data-elasticsearch

### 启动
	./elasticsearch -d -Des.insecure.allow.root=true
	
### 允许外部访问
	vi elasticsearch.yml
	network.host: 0.0.0.0
	
### 版本信息
	curl http://192.168.209.128:9200
	
### 插件安装
	查看插件
	./plugin list
	
	安装elasticsearch-head
	./plugin install mobz/elasticsearch-head
	安装完成后查看
	http://192.168.209.128:9200/_plugin/head/
	
### 集群配置
	集群名称，同一网段下如果名称相同则自动组成集群
	cluster.name: elasticsearch-cluster
	
	节点名称，可使用IP尾段
	node.name: elasticsearch-node-1
	
	是否充当主节点
	node.master: true
	是否充当数据节点
	node.data: true
	
	节点列表
	discovery.zen.ping.unicast.hosts : ["192.168.209.128","192.168.209.132"]
	最少的主节点数量
	discovery.zen.minimum_master_nodes: 2
	