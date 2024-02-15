package kobold.parser.dsl

import kobold.parser.dsl.support.tokens
import kobold.matchers.Accepted
import kobold.matchers.Rejected
import kobold.parser.parser
import org.junit.jupiter.api.Test

class AnyShould {
    @Test
    fun acceptAnySymbol() {
        val any = parser { any().oneOrMore() }
        assert(any.parse(tokens("aoierbvnaeorbnaietrbae")) is Accepted)
    }

    @Test
    fun rejectAnEmptyList() {
        val any = parser { any() }
        assert(any.parse(tokens("")) is Rejected)
    }
}
