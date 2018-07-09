package blog

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("cm")
class BlogProperties {
    lateinit var title: String
    val banner = Banner()

    class Banner {
        var title: String? = null
        lateinit var content: String
    }
}