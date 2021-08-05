/*
 * 프로그램에 대한 저작권을 포함한 지적재산권은 (주)씨알에스큐브에 있으며, (주)씨알에스큐브가 명시적으로 허용하지 않은
 * 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며, (주)씨알에스큐브의 지적 재산권 침해에 해당됩니다.
 * Copyright ⓒ 2021. CRScube Co., Ltd. All Rights Reserved| Confidential)
 */

package io.taesu.demo.webfluxcoroutine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.util.StreamUtils
import java.nio.charset.Charset

/**
 * Created by itaesu on 2021/04/21.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
@Configuration(proxyBeanMethods = false)
class DatabaseInitializationConfiguration {
    /**
     * https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-initialize-a-database-using-r2dbc
     * R2dbc를 사용하는 경우 auto-configuration이 off 됨
     */
    @Autowired
    fun initializeDatabase(r2dbcEntityTemplate: R2dbcEntityTemplate) {
        ready(r2dbcEntityTemplate)
    }

    fun ready(r2dbcEntityTemplate: R2dbcEntityTemplate) {
        val schema = StreamUtils.copyToString(ClassPathResource("schema.sql").inputStream, Charset.defaultCharset())
        r2dbcEntityTemplate.databaseClient.sql(schema).fetch().rowsUpdated().block()
    }
}