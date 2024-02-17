package kobold.parser.dsl

import kobold.Accepted
import kobold.Rejected
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class AnyOfShould {
    @Test
    fun acceptAnythingMatchingOptions() {
        val anyOf = parser { anyOf("a", "b", "c", "d") }
        assertIs<Accepted>(anyOf.parse(tokens("a")))
        assertIs<Accepted>(anyOf.parse(tokens("b")))
        assertIs<Accepted>(anyOf.parse(tokens("c")))
        assertIs<Accepted>(anyOf.parse(tokens("d")))
    }

    @Test
    fun rejectEverythingNotMatchingOptions() {
        val anyOf = parser { anyOf("a", "b", "c", "d") }
        assertIs<Rejected>(anyOf.parse(tokens("e")))
    }
}
