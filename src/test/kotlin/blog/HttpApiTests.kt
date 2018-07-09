package blog

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest
class HttpApiTests(@Autowired
                   val mockkMvc: MockMvc) {
    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var articleRepository: ArticleRepository

    @MockBean
    private lateinit var markDownConverter: MarkDownConverter

    @Test
    fun `List articles`() {
        val juergen = User("springjuergen",
                "Juergen",
                "Hoeller")
        val spring5Article = Article("Spring Framework 5.0 goes GA",
                "Dear Spring community ...",
                "Lorem ipsum",
                juergen,
                1)
        val spring43Article = Article("Spring Framework 4.3 goes GA",
                "Dear Spring community ...",
                "Lorem ipsum",
                juergen,
                2)
        whenever(articleRepository.findAllByOrderByAddedAtDesc()).thenReturn(listOf(spring5Article,
                spring43Article))
        whenever(markDownConverter.invoke(any())).thenAnswer { it.arguments[0] }

        mockkMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.[0].author.login").value(juergen.login))
                .andExpect(jsonPath("\$.[0].id").value(spring5Article.id!!))
                .andExpect(jsonPath("\$.[1].author.login").value(juergen.login))
                .andExpect((jsonPath("\$.[1].id").value(spring43Article.id!!)))
    }

    @Test
    internal fun `List Users`() {
        val i = User("ramanjeemishra",
                "hello",
                "who")
        val you = User("anotheruser",
                "another fname",
                "another lname")
        whenever(userRepository.findAll()).thenReturn(listOf(i,
                you))
        mockkMvc.perform(get("/api/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.[0].login").value(i.login))
                .andExpect(jsonPath("\$.[1].login").value(you.login))
    }
}