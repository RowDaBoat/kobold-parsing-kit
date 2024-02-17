package kobold.parser.dsl

import kobold.Accepted
import kobold.Rejected
import kobold.parser.dsl.support.tokenMatchers
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class AndShould {
    @Test
    fun acceptMatchingBoth() {
        val not = parser { "a".oneOrMore() and sequence(tokenMatchers("aaa")) }
        assertIs<Rejected>(not.parse(tokens("")))
        assertIs<Rejected>(not.parse(tokens("a")))
        assertIs<Rejected>(not.parse(tokens("aa")))
        assertIs<Accepted>(not.parse(tokens("aaa")))
        assertIs<Accepted>(not.parse(tokens("aaaaaaaaa")))
    }
}
