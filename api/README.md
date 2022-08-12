# my_admin_v2_api(后端)

#### 软件版本
	-springboot2.2.9.RELEASE
	-maven 3.6.3
	-openjdk 11
	-mysql8.0(主键生成策略使用的是百度的雪花算法，worker_node表使用的mysql自增Id)
	

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
		 docker run --restart=always --name 'redis_4' -p 6379:6379 -d tangzhang/redis_4:custom redis-server /usr/local/redis/redis.conf(default-password:tangzhangss,可使用--requirepass=xxx覆盖) 
			
	安装mysql8.0
		docker pull tangzhang/mysql:8.0
		docker run --restart=always --name 'mysql_8.0'  -p 3306:3306 -e MYSQL_ROOT_PASSWORD=tangzhangss -v /usr/local/docker/mysql/data:/var/lib/mysql -v /usr/local/docker/mysql/log:/var/log/mysql -d tangzhang/mysql:8.0 --default-authentication-plugin=mysql_native_password

	安装openjdk11容器	
		
		docker pull tangzhang/openjdk:11
		docker run --restart=always -d -p 10082:10082 --name=***** tangzhang/openjdk:11 java -jar /app.jar
		docker login -u tangzhang -p aa.188632
		
### Gitlab配置
	下载gitlab-runner，注册然后配置即可
	参考：
	http://www.idevops.site/gitlabci/chapter02/01/1-2-gitlabrunner%E5%AE%89%E8%A3%85/

	#/var/run/docker.sock: connect: permission denied
	#给该文件添加777权限或将gitlab-runner用户加入权限组

### 项目部署（gitlab自动部署）
	自行查看项目目录下的.gitlab-ci.yml文件
	
### nacos配置
安装配置
   
    -下载:https://github.com/alibaba/nacos/releases/tag/2.0.0-bugfix
    -将\nacos\conf\nacos-mysql.sql文件初始化到数据库
    -配置\nacos\conf\application.properties
    -启动 -m standalone(默认集群模式，单机模式启动使用)
    -文件路径不要含有中文！！！
    
项目中配置

    -server.port
    --------------------------------------------------
     (application-common.propertise中默认)
    -spring.cloud.nacos.discovery.server-addr=${NACOS-SERVER-ADDR:127.0.0.1:8848}
    -spring.cloud.nacos.discovery.ip=${SPRING-APPLICATION-IP:127.0.0.1}
    -------------------------------------------------- 
如果需要注册服务需要配置属性(如果不需要就不用写)

    -spring.application.name=""    
		  
### fegin配置

接口编写和配置自行搜索，一下是封装之后的调用实例(所有使用fegin调用的接口返回对象需要common-utils包Result结构!!!!);


       FeginRemoteCall call = ()->serviceTestFeginInterface.getData("a","b",null);
       Result result = FeginConfig.apply(call,true);

将外部服务的结构原样返回，成功的结果类似,如：
        
    {
        "code": 404,
        "message": "Not Found",
        "data": "访问的页面不存在"
    }

如果apply的第二个参数为true,将再次包裹Result直接抛出异常全局捕获事务回滚处理等，异常信息如下:

    {
        "code": 602,
        "message": "远程调用失败",
        "data": "{\"result\":{\"code\":404,\"data\":\"访问的页面不存在\",\"message\":\"Not Found\"},\"service\":\"COMMON-DATA\"}"
    }

#其他配置
    可以通过创建配置文件覆盖原有配置
    application-datasource.propertise
    application-common.propertise
    application-redis.propertise
    -也可利用spring配置加载规则在classpath目录创建config/application.propertise(或者更高加载顺序)更改配置等【大多数时候没法覆盖include的配置】
    可以自己新建一个application-custom.propertise 并且 spring.profiles.include=common,custom（custom放最后面）


    
    
