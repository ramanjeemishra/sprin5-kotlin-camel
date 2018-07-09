package blog

import com.samskivert.mustache.Mustache
import org.apache.camel.LoggingLevel
import org.apache.camel.builder.RouteBuilder
import org.springframework.boot.Banner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableConfigurationProperties(BlogProperties::class)
class BlogAppApplication {

    @Bean
    fun mustacheCompiler(loader: Mustache.TemplateLoader?) = Mustache.compiler().escapeHTML(false).withLoader(loader)

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            articleRepository: ArticleRepository) = CommandLineRunner {
        val i = User("i",
                "i-fn",
                "i-ln")
        userRepository.save(i)
        articleRepository.save(Article("T1",
                "H1",
                "Sample C1",
                i,
                1))
        articleRepository.save(Article("T1",
                "H2",
                "Sample C2",
                i,
                2))
    }

    @Component
    class MessageRoute : RouteBuilder() {
        override fun configure() {
            from("timer://foo?fixedRate=true")
                    .log(LoggingLevel.INFO, "sample message").routeId("TimeRoute")

            from("{{route.from}}").to("json-validator:myschema.json").routeId("JMSRoute")

            from("activemq:foo")
                    .to("log:sample").routeId("AMQReceiver")

            from("timer:bar")
                    .setBody(constant("Hello from Camel"))
                    .to("activemq:foo", "{{route.from}}").routeId("AMQPublisher")
        }

    }
}

fun main(args: Array<String>) {
    runApplication<BlogAppApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}