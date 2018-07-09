package blog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class RepositoriesTests(
        @Autowired
        val em: TestEntityManager,
        @Autowired
        val userRepository: UserRepository,
        @Autowired
        val articleRepository: ArticleRepository) {
    @Test
    fun `When findById then return Article`() {
        val i = User("rj",
                "fn",
                "ln")
        em.persist(i)
        val article = Article("Kotlin Program",
                "Testing",
                "Lots of details",
                i)
        em.persist(article)
        em.flush()
        val found = articleRepository.findById(article.id!!)
        assertThat(found.get()).isEqualTo(article)
    }

    @Test
    fun `When findById then return User`() {
        val i = User("rj",
                "fn",
                "ln")
        em.persist(i)
        em.flush()
        val found = userRepository.findById(i.login)
        assertThat(found.get()).isEqualTo(i)
    }
}