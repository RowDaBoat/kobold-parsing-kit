package io.vexel.kobold.test.parser.dsl

import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.parser.parser
import io.vexel.kobold.test.parser.dsl.support.tokenMatchers
import io.vexel.kobold.test.parser.dsl.support.tokens
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
