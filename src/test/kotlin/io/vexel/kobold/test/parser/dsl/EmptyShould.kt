package io.vexel.kobold.test.parser.dsl

import io.vexel.kobold.Accepted
import io.vexel.kobold.parser.parser
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class EmptyShould {
    @Test
    fun acceptAnythingOnEmpty() {
        val empty = parser { empty }
        assertIs<Accepted>(empty.parse(tokens("")))
    }
}

