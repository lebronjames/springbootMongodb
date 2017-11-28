SpringBoot跟Mongodb集成
一、下载Mongodb tgz包
wget https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-3.4.0.tgz

二、解压
tar -zxvf mongodb-linux-x86_64-3.4.0.tgz 
mv mongodb-linux-x86_64-3.4.0 mongodb

三、移到/usr/local
mv mongodb /usr/local

四、进入Mongodb目录，创建db和日志目录
cd mongodb
mkdir -p /usr/local/mongodb/data
mkdir -p /usr/local/mongodb/data/db
mkdir -p /usr/local/mongodb/data/logs

五、logs目录下创建mongodb.log文件
touch mongodb.log

六、在mongodb/data目中创建mongodb.conf
cd data
vi mongodb.conf
/** 加入相关配置 **/
#端口号
port = 27017
#数据目录
dbpath = /opt/mongodb/data/db
#日志目录
logpath = /opt/mongodb/data/logs/mongodb.log
#设置后台运行
fork = true
#日志输出方式
logappend = true
#开启认证
#auth = true

七、运行
cd /usr/local/mongodb/bin
./mongod --config /usr/local/mongodb/data/mongodb.conf

八、安装好后在admin中创建用户
cd /usr/local/mongodb/bin
./mongo
use admin
创建用户为migu_mongo 密码为123456的用户
针对数据库创建用户
db.createUser({user: "migu_mongo",pwd: "123456",roles: [{ role: "userAdminAnyDatabase", db: "admin" }]})
在admin数据库认证
db.auth('migu_mongo','123456')

九、pom.xml增加mongodb的依赖spring-boot-starter-data-mongodb

十、UserRepository集成MongoRepository实现
@Component
public interface UserRepository extends MongoRepository<User, Long> {
	User findUserByUsername(String name);
}

十一、注入UserRepository，直接调取相应方法
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/add")
	public void add() {
		userRepository.save(new User(1L, "didi", 30));
		userRepository.save(new User(2L, "mama", 40));
		userRepository.save(new User(3L, "kaka", 50));
		userRepository.save(new User(4L, "hehe", 70));
	}
	
	@GetMapping("/deleteAll")
	public void deleteAll() {
		userRepository.deleteAll();
	}
	
	@GetMapping("/find")
	public User find(@RequestParam("id") String id) {
		return userRepository.findOne(Long.valueOf(id));
	}
	
	@GetMapping("/delete")
	public void delete(@RequestParam("id") String id) {
		userRepository.delete(Long.valueOf(id));
	}
	
	@GetMapping("/findAll")
	public List<User> findAll(){
		return userRepository.findAll();
	}
}

十二、配置文件配置链接信息
spring:
  data:
    mongodb:
      uri: mongodb://migu_mongo:123456@10.5.2.242:27017/admin

十三、测试
http://localhost:8080/user/add
http://localhost:8080/user/findAll
http://localhost:8080/user/find?id=1
http://localhost:8080/user/delete?id=3