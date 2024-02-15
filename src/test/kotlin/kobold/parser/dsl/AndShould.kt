package kobold.parser.dsl

import kobold.matchers.Accepted
import kobold.matchers.Rejected
import kobold.parser.dsl.support.tokenMatchers
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test

class AndShould {
    @Test
    fun acceptMatchingBoth() {
        val not = parser { "a".oneOrMore() and sequence(tokenMatchers("aaa")) }
        assert(not.parse(tokens("")) is Rejected)
        assert(not.parse(tokens("a")) is Rejected)
        assert(not.parse(tokens("aa")) is Rejected)
        assert(not.parse(tokens("aaa")) is Accepted)
        assert(not.parse(tokens("aaaaaaaaa")) is Accepted)
    }
}
