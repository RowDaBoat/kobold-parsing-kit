package io.vexel.kobold.test.parser.dsl

import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.Token
import io.vexel.kobold.parser.parser
import io.vexel.kobold.parser.terminal
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class TerminalShould {
    class SomeThing : Token("")
    class OtherThing : Token("")

    @Test
    fun `accept a terminal symbol by type`() {
        val parser = parser {
            val some = terminal<SomeThing>()
            val other = terminal<OtherThing>()
            some then other then some
        }

        val result = parser.parse(listOf(SomeThing(), OtherThing(), SomeThing()))
        println(result)
        assertIs<Accepted>(result)
    }

    @Test
    fun `reject a terminal not matching type`() {
        val parser = parser { terminal<SomeThing>() }
        val result = parser.parse(listOf(OtherThing()))

        assertIs<Rejected>(result)
    }
}