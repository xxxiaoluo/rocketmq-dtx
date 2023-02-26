package learn.seata.dbinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
public class DbInitApplication {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(DbInitApplication.class, args);
    }

    /**
     * 将sql文件在项目启动时就执行
     */
    @PostConstruct
    public void init() throws SQLException {
        exec("sql/account.sql");
        exec("sql/order.sql");
        exec("sql/seata-server.sql");
        exec("sql/storage.sql");
    }

    //将sql文件放到下面的方法执行，但executeSqlScript第二个参数是接收文件资源对象
    private void exec(String sql) throws SQLException {
        ClassPathResource cpr = new ClassPathResource(sql, DbInitApplication.class.getClassLoader());//相当于加载当前类的根路径(target文件下)(加载sql)
        EncodedResource encodedResource = new EncodedResource(cpr, "UTF-8"); //设置文件中的编码(因为里面有中文注释)

        //使用spring jdbc提供的一个工具来执行sql脚本文件
        ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource); //只能传文件资源对象
    }

}
