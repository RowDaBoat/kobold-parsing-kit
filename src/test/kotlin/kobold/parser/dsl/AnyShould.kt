package kobold.parser.dsl

import kobold.Accepted
import kobold.Rejected
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class AnyShould {
    @Test
    fun acceptAnySymbol() {
        val any = parser { any().oneOrMore() }
        assertIs<Accepted>(any.parse(tokens("aoierbvnaeorbnaietrbae")))
    }

    @Test
    fun rejectAnEmptyList() {
        val any = parser { any() }
        assertIs<Rejected>(any.parse(tokens("")))
    }
}
