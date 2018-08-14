package pro.savichev.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["pro.savichev"])
open class LogConfiguration {

    companion object {
        const val TAG = "PROFESSOR_FARNSWORTH_BOT"
    }

    @Bean
    open fun getLogger(): Logger {
        return LoggerFactory.getLogger(TAG)
    }
}