package kobold.parser.dsl

import kobold.Accepted
import kobold.Rejected
import kobold.parser.dsl.support.tokenMatchers
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class NotShould {
    @Test
    fun acceptAnythingNotMatchingSequence() {
        val not = parser { not("a") then any() }
        assertIs<Accepted>(not.parse(tokens("b")))
    }

    @Test
    fun rejectAnythingMatchingSequence() {
        val not = parser { not("a") then("b") }
        assertIs<Rejected>(not.parse(tokens("a")))
        assertIs<Rejected>(not.parse(tokens("aa")))
        assertIs<Accepted>(not.parse(tokens("b")))
    }

    @Test
    fun rejectMatchingExactSequence() {
        val not = parser {
            val abc = anyOf("a", "b", "c")
            not(sequence(tokenMatchers("aaa"))) then sequence(abc, abc, abc)
        }

        assertIs<Accepted>(not.parse(tokens("aab")))
        assertIs<Accepted>(not.parse(tokens("cac")))
        assertIs<Rejected>(not.parse(tokens("aaa")))
    }
}
