package kobold.parser.dsl

import kobold.Accepted
import kobold.parser.dsl.support.tokenMatchers
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class OptionalShould {
    @Test
    fun acceptNotMatching() {
        val either = parser { optional("a") then sequence(tokenMatchers("bbb")) }
        assertIs<Accepted>(either.parse(tokens("bbb")))
    }

    @Test
    fun acceptAndConsumeMatching() {
        val either = parser { optional("a") then sequence(tokenMatchers("bbb")) }
        assertIs<Accepted>(either.parse(tokens("abbb")))
    }
}
