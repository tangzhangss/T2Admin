# my_admin_v2_api(后端)

#### 软件版本
	-springboot2.2.9.RELEASE
	-maven 3.6.3
	-openjdk 11

### 服务器配置
	安装maven
    	 wget https://mirrors.bfsu.edu.cn/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
    	 tar -zxvf  ....
    	 wget https://download.java.net/openjdk/jdk11/ri/openjdk-11+28_linux-x64_bin.tar.gz
    	 tar -zxvf  ....
		 vim(source) /etc/profile
		 -----------------------------------------------	
		 #maven 环境
		 export MAVEN_HOME=/usr/local/apache-maven-3.6.3
		 export PATH=$PATH:$MAVEN_HOME/bin
	 	 #jdk11
		 export JAVA_HOME=/usr/local/jdk-11
		 export CLASSPATH=$JAVA_HOME/lib:$CLASSPATH
		 export PATH=$JAVA_HOME/bin:$PATH
		 -----------------------------------------------
	安装redis
		 docker pull tangzhang/redis_4:custom
		 docker run --name 'redis_4' -p 6379:6379 -d tangzhang/redis_4:custom redis-server /usr/local/redis/redis.conf(default-password:tangzhangss,可使用--requirepass=xxx覆盖) 
			
	安装mysql8.0
		docker pull tangzhang/mysql:8.0
		docker run --name 'mysql_8.0'  -p 3306:3306 -e MYSQL_ROOT_PASSWORD=tangzhangss -v /usr/local/docker/mysql/data:/var/lib/mysql -v /usr/local/docker/mysql/log:/var/log/mysql -d tangzhang/mysql:8.0 --default-authentication-plugin=mysql_native_password

	安装openjdk11容器	
		
		docker pull tangzhang/openjdk:11
		docker run -d -p 10082:10082 --name=***** tangzhang/openjdk:11 java -jar /app.jar
		docker login -u tangzhang -p aa.188632
		
### Gitlab配置
	下载gitlab-runner，注册然后配置即可
	参考：
	http://www.idevops.site/gitlabci/chapter02/01/1-2-gitlabrunner%E5%AE%89%E8%A3%85/

	#/var/run/docker.sock: connect: permission denied
	#给该文件添加777权限或将gitlab-runner用户加入权限组

### 项目部署（gitlab自动部署）
	自行查看项目目录下的.gitlab-ci.yml文件
		  


