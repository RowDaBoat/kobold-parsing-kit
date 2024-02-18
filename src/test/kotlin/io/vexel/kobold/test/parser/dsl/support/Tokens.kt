package io.vexel.kobold.test.parser.dsl.support

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.TerminalByContent

fun tokens(string: String) = string.map { Token(it.toString()) }

fun tokenMatchers(string: String) = tokens(string).map { TerminalByContent(it) }
