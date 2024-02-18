package io.vexel.kobold.test.parser.dsl

import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.parser.parser
import io.vexel.kobold.test.parser.dsl.support.tokens
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
