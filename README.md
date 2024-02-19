# Kobold Parsing Kit

[![Test and Publish](https://github.com/RowDaBoat/kobold-parsing-kit/actions/workflows/ci.yml/badge.svg)](https://github.com/RowDaBoat/kobold-parsing-kit/actions/workflows/ci.yml)

The **Kobold Parsing Kit** is a set of tools designed to create parsers using grammars expressed in a simple domain specific language. No regular expressions, just an easy to learn DSL.

The tools rely on packrat parsing for matching expressions, and resolve multiple left recursive grammars, both direct and indirect, by implementing the solution proposed by Umeda and Maeda in their [excellent contribution](https://www.jstage.jst.go.jp/article/ipsjjip/29/0/29_174/_pdf).

## Kobold Lexer

The kit offers a lexer to transform strings into a list of `Token`s (also called "lexemes") by using rules.

The following rules detect numbers composed by numeric characters, variable names composed of alphabetic characters, and the plus operator, while ignoring spaces.

```kotlin
class Variable(name: String): Token(name)
class Number(value: String): Token(value)
class PlusOperator: Token("+")

val lexer = lexer {
    ('0' to '9').oneOrMore() with { Number(it) }
    (('a' to 'z') or ('A' to 'Z')).oneOrMore() with { Variable(it) }
    "+" with { PlusOperator() }
    ignore(" ")
}
```

The lexer can then be used by calling `tokenize()`

```kotlin
val tokens = lexer.tokenize("some + other + 100")
```

Which yields the token list:

```
[Variable(text="some", line=1, column=1), PlusOperator(text="+", line=1, column=6), Variable(text="other", line=1, column=8), PlusOperator(text="+", line=1, column=14), Number(text="100", line=1, column=16)]
```

## Kobold Parser

The kit also offers a parser to transform a list of tokens into a syntax tree using a grammar declared by rules.

### Defining a grammar

The following parses simple algebraic sum expressions.

```kotlin
class Sum(override val children: List<Symbol>) : Symbol

val parser = parser {
    val operator = terminal<PlusOperator>()
    val number = terminal<Number>()
    val variable = terminal<Variable>()

    val expression = nonTerminal()
    val sum = nonTerminal { Sum(it) }
    val value = nonTerminal()

    expression from anyOf(sum, value)
    sum from sequence(value, operator, expression)
    value from (number or variable)

    expression
}
```

### Parsing input

The parser can then be used by calling `parse()`, the result is `Accepted` when the parsing succeeds.
The resulting symbol tree can be shown using `show()` on it.

```kotlin
val tokens = lexer.tokenize("a + 10 + a + b")
val result = parser.parse(tokens)

when (result) {
    is Accepted -> println(result.tree?.show())
    else -> println("Syntax error.")
}
```

which outputs:

```
Sum
  Variable(text="some", line=1, column=1)
  PlusOperator(text="+", line=1, column=6)
  Sum
    Variable(text="other", line=1, column=8)
    PlusOperator(text="+", line=1, column=14)
    Number(text="20", line=1, column=17)
```

The symbol tree can be walked through using its `.children` property.

### Treating errors

When the parsing fails, the result is `Rejected`, line and column numbers, and a `Reason` object are provided.

```kotlin
val tokens = lexer.tokenize("a + 10 + a +")
val result = parser.parse(tokens)

when (val result = parser.parse(tokens)) {
    is Accepted -> println(result.tree?.show())
    is Rejected -> println(":${result.line}:${result.column}: error: " + result.reason.show())
}
```

which outputs:

```
:1:12: error: Input cannot be completely parsed, trailing input remains: +.
```

The `Reason` object provides useful error information:

```kotlin
val reason = result.reason

val errorMessage = when (reason) {
    is UnexpectedToken -> "Unexpected token ${reason.detected}. Expected: ${reason.expected.joinToString(", ") { it.text }}."
    is NoRemainingTokens -> "Reached an unexpected end of input."
    is TrailingTokens -> "Some input remains after parsed expression."
    is UnsupportedLeftRecursion -> "The grammar has an unsupported left recursive case. This is a bug."
}
```