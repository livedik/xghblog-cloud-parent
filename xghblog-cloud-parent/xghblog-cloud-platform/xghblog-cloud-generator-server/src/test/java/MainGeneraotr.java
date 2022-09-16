import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.xhgblog.common.mybatis.entity.AbstractGeneralFieldEntity;

import java.io.File;
import java.util.Collections;

/***
 *  代码生成器
 */

public class MainGeneraotr {

    public static void main(String[] args) {
        String outputDir = System.getProperty("user.dir") + File.separator + "generator";

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/blog?serverTimezone=GMT%2B8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("xgh") // 设置作者
                            .enableSwagger()// 开启 swagger 模式
                            .disableOpenDir() //完事后不打开目录  碍事
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(outputDir);// 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xghblog.base.server") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapper, outputDir)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("base_role_authority")
                            // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")
                            .entityBuilder() //实体类 Entity 配置
                            .enableLombok()// 开启 Lombok
//                            .superClass(AbstractGeneralFieldEntity.class)// Entity 父类
//                            .addIgnoreColumns("gmt_create","gmt_modify","create_id",
//                                    "modify_id","create_name","modify_name","is_del","seq_id") // 忽略公共字段
                            .serviceBuilder()//Service 配置
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImp")
                            .controllerBuilder().enableRestStyle(); // 设置过滤表前缀
                }).execute();
        // 使用Freemarker引擎模板，默认的是Velocity引擎模板
    }
}
