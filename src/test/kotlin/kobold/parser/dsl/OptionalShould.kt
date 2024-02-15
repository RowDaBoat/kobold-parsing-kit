package kobold.parser.dsl

import kobold.parser.dsl.support.tokenMatchers
import kobold.parser.dsl.support.tokens
import kobold.matchers.Accepted
import kobold.parser.parser
import org.junit.jupiter.api.Test

class OptionalShould {
    @Test
    fun acceptNotMatching() {
        val either = parser { optional("a") then sequence(tokenMatchers("bbb")) }
        assert(either.parse(tokens("bbb")) is Accepted)
    }

    @Test
    fun acceptAndConsumeMatching() {
        val either = parser { optional("a") then sequence(tokenMatchers("bbb")) }
        assert(either.parse(tokens("abbb")) is Accepted)
    }
}
