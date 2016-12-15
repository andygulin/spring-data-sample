#### Spring Data Cassandra Sample

##### 下载
[http://archive.apache.org/dist/cassandra/2.1.7/apache-cassandra-2.1.7-bin.tar.gz](http://archive.apache.org/dist/cassandra/2.1.7/apache-cassandra-2.1.7-bin.tar.gz)

##### 启动
	cd bin
	./cassandra -f
	加-f为前台启动，默认后台启动
	
##### 修改配置
	cd bin
	vim cassandra.yaml
	
	listen_address 127.0.0.1

	rpc_address 0.0.0.0

	去掉broadcast_rpc_address的注释
	
##### 创建KeySpace
	CREATE KEYSPACE test WITH replication = {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': 1 };

##### 显示所有KeySpace
	SELECT * FROM system.schema_keyspaces;
	
##### 进入KeySpace
	use test;
	
##### 创建Table
	CREATE TABLE user(
		id text,
		name text,
		age int,
		address text,
		created_at timestamp,
		PRIMARY KEY(id));

##### 创建Index
	CREATE INDEX idx_name ON user (name);
	CREATE INDEX idx_age ON user (age);
	
##### 添加认证
	vim cassandra.yaml
	
	authenticator: AllowAllAuthenticator
	改成
	authenticator: PasswordAuthenticator
	
	关闭重启cassandra
	
	用户默认帐号登录
	./cqlsh -ucassandra -pcassandra
	
	创建用户
	CREATE USER admin WITH PASSWORD 'admin' SUPERUSER;
	
	关闭重启cassandra
	
	删除默认用户
	DROP USER cassandra;
	
