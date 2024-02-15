package kobold.parser.dsl

import kobold.parser.dsl.support.tokens
import kobold.matchers.Accepted
import kobold.matchers.Rejected
import kobold.parser.parser
import org.junit.jupiter.api.Test

class AnyOfShould {
    @Test
    fun acceptAnythingMatchingOptions() {
        val anyOf = parser { anyOf("a", "b", "c", "d") }
        assert(anyOf.parse(tokens("a")) is Accepted)
        assert(anyOf.parse(tokens("b")) is Accepted)
        assert(anyOf.parse(tokens("c")) is Accepted)
        assert(anyOf.parse(tokens("d")) is Accepted)
    }

    @Test
    fun rejectEverythingNotMatchingOptions() {
        val anyOf = parser { anyOf("a", "b", "c", "d") }
        assert(anyOf.parse(tokens("e")) is Rejected)
    }
}
