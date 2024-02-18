package io.vexel.kobold.test.parser.dsl

import io.vexel.kobold.Accepted
import io.vexel.kobold.parser.parser
import io.vexel.kobold.test.parser.dsl.support.tokenMatchers
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class OptionalShould {
    @Test
    fun acceptNotMatching() {
        val either = parser { optional("a") then sequence(tokenMatchers("bbb")) }
        assertIs<Accepted>(either.parse(tokens("bbb")))
    }

    @Test
    fun acceptAndConsumeMatching() {
        val either = parser { optional("a") then sequence(tokenMatchers("bbb")) }
        assertIs<Accepted>(either.parse(tokens("abbb")))
    }
}
