package com.moran.controller.open;

import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.moran.conf.bean.ResponseBean;
import com.moran.controller.open.vo.MybatisRespVO;
import com.moran.utils.FileUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Types;

/**
 * 开放api
 */
@RestController
@RequestMapping("/open")
public class OpenController {
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${spring.profiles.active}")
    private String active;
    /**
     * 生成文件
     */
    @PostMapping("/table")
    @SaIgnore
    public ResponseBean<Boolean> generateTable(@RequestBody @Valid MybatisRespVO vo) {
        if (!"local".equals(active)) {
            return ResponseBean.fail("其他环境不允许生成");
        }
        Path path = Paths.get(System.getProperty("user.dir"));
        FastAutoGenerator.create(dbUrl, dbUsername, dbPassword)
                .globalConfig(builder -> builder
                        .author("MyBatis-Plus Generator")
                        .outputDir(path + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                        .disableOpenDir()
                )
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.TINYINT) {
                                // 自定义类型转换
                                return DbColumnType.BOOLEAN;
                            }else if (typeCode == Types.SMALLINT) {
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder -> builder
                        .parent("com.moran")
                        .entity("model")
                        .mapper("mapper")
                        .service("service")
                        // .serviceImpl("service.impl")
                        // .pathInfo(Collections.singletonMap(OutputFile.xml, path +  "/src/main/resources/mappers"))  设置xml 生成路径
                )
                .strategyConfig(builder -> {
                    builder.addInclude(vo.getTableNames()) // 设置需要生成的表名
                            .entityBuilder()
                            .enableLombok() // 启用 Lombok
                            .logicDeleteColumnName("deleted")
                            .logicDeletePropertyName("deleted")
                            .addTableFills(
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE),
                                    new Column("creator", FieldFill.INSERT),
                                    new Column("updater", FieldFill.INSERT_UPDATE)
                            )
                            .controllerBuilder()
                            .disable() // 禁止生成controller
                            .serviceBuilder()
                            .disableServiceImpl()
                            .formatServiceFileName("%sService")
                            .serviceTemplate("/templates/service.vm")
                            // .serviceImplTemplate("/templates/serviceImpl.vm")
                            // .entityBuilder()
                            // .disable()
                            .mapperBuilder()
                            .disableMapperXml();
                })
                .execute();
        return ResponseBean.ok(true);
    }

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @SaIgnore
    public ResponseBean<String> upload(@RequestParam("file") MultipartFile file) {
        String write = FileUtil.write("D:\\java\\moran\\files", file);
        return ResponseBean.ok(write);
    }
}
