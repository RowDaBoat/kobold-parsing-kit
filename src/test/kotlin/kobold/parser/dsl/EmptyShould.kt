package kobold.parser.dsl

import kobold.parser.dsl.support.tokens
import kobold.matchers.Accepted
import kobold.parser.parser
import org.junit.jupiter.api.Test

class EmptyShould {
    @Test
    fun acceptAnythingOnEmpty() {
        val empty = parser { empty }
        assert(empty.parse(tokens("")) is Accepted)
    }
}

