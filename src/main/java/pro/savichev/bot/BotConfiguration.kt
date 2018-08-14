package pro.savichev.bot

import com.google.gson.Gson
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import pro.savichev.Config
import pro.savichev.bot.impl.TelegramBot


@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = ["pro.savichev"])
open class BotConfiguration {

    @Bean
    open fun getGson(): Gson {
        return Gson()
    }

    @Bean
    open fun getTelegramBot(): TelegramBot {
        return TelegramBot(Config.BOT_TOKEN)
    }
}
