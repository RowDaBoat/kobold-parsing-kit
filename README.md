# Kobold Parsing Kit

The **Kobold Parsing Kit** is a set of tools designed to create parsers using grammars expressed in a simple domain specific language. No regular expressions, just an easy to learn DSL.

The tools rely on packrat parsing for matching expressions, and resolve multiple left recursive grammars, direct and indirect, by implementing the solution proposed by Umeda and Maeda in their [excellent contribution](https://www.jstage.jst.go.jp/article/ipsjjip/29/0/29_174/_pdf).

## Kobold Lexer

The kit offers a lexer to transform strings into a list of Tokens (also called "lexemes") using rules to produce tokens.

The following detects numbers composed by numeric characters, variables as alphanumerical names starting with a letter, and the plus operator, while ignoring spaces.

```kotlin
class Variable(name: String): Token(name)
class Number(value: String): Token(value)
class PlusOperator: Token("+")

val lexer = lexer {
    ('0' to '9').oneOrMore() with { Number(it) }
    ('a' to 'z') or ('A' to 'Z') with { Variable(it) }
    "+" with { PlusOperator() }
    ignore(" ")
}
```

The lexer can then be used by calling `tokenize()`

```kotlin
val tokens = lexer.tokenize("some + other + 100")
```

Which yields the token list:

```kotlin
[Variable(name="some"), Variable(name="other"), Number(value="100")]
```

## Kobold Parser

The kit also offers a parser to transform a list of Tokens into a Syntax Tree using a grammar declared by rules.

The following parses simple algebraic sum expressions.

```kotlin
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

The parser can then be used by calling `parse()`

```kotlin
val tokens = lexer.tokenize("a + 10 + a + b")
val result = parser.parse(tokens)
```

Which yields the syntax tree:

```kotlin
Sum
  Variable(a)
  PlusOperator(+)
  Sum
    Number(10)
    PlusOperator(+)
    Sum
      Variable(a)
      PlusOperator(+)
      Variable(b)
```
