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
        val and = parser { "a".oneOrMore() and sequence(tokenMatchers("aaa")) }
        assertIs<Rejected>(and.parse(tokens("")))
        assertIs<Rejected>(and.parse(tokens("a")))
        assertIs<Rejected>(and.parse(tokens("aa")))
        assertIs<Accepted>(and.parse(tokens("aaa")))
        assertIs<Accepted>(and.parse(tokens("aaaaaaaaa")))
    }
}
