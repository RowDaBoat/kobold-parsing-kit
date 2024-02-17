package kobold.parser.dsl

import kobold.Accepted
import kobold.Rejected
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class OrShould {
    @Test
    fun acceptAnythingMatchingEither() {
        val either = parser { "a" or "b" }
        assertIs<Accepted>(either.parse(tokens("a")))
        assertIs<Accepted>(either.parse(tokens("b")))
    }

    @Test
    fun rejectAnythingNotMatchingEither() {
        val either = parser { "a" or "b" }
        assertIs<Rejected>(either.parse(tokens("c")))
    }
}

