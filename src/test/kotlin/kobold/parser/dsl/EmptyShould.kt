package kobold.parser.dsl

import kobold.Accepted
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class EmptyShould {
    @Test
    fun acceptAnythingOnEmpty() {
        val empty = parser { empty }
        assertIs<Accepted>(empty.parse(tokens("")))
    }
}

