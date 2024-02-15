package kobold.parser.dsl

import kobold.parser.dsl.support.tokens
import kobold.matchers.Accepted
import kobold.matchers.Rejected
import kobold.parser.parser
import org.junit.jupiter.api.Test

class OrShould {
    @Test
    fun acceptAnythingMatchingEither() {
        val either = parser { "a" or "b" }
        assert(either.parse(tokens("a")) is Accepted)
        assert(either.parse(tokens("b")) is Accepted)
    }

    @Test
    fun rejectAnythingNotMatchingEither() {
        val either = parser { "a" or "b" }
        assert(either.parse(tokens("c")) is Rejected)
    }
}

