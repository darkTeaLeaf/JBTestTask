# Task description
Язык описания операций над целочисленными массивами задан следующей грамматикой:

```
<digit>   ::= “0” | “1" | “2” | “3" | “4” | “5" | “6” | “7" | “8” | “9"
<number> ::= <digit> | <digit> <number>
<operation> ::= “+” | “-” | “*” | “>” | “<” | “=” | “&” | “|”
<constant-expression> ::= “-” <number> | <number>
<binary-expression> ::= “(” <expression> <operation> <expression> “)”
<expression> ::= “element” | <constant-expression> | <binary-expression>
<map-call> ::= “map{” <expression> “}”
<filter-call> ::= “filter{” <expression> “}”
<call> ::= <map-call> | <filter-call>
<call-chain> ::= <call> | <call> “%>%” <call-chain>
```
Арифметические операции имеют стандартную семантику. Операция “&” это логическое “и”, операция “|” --- логическое “или“. Бинарные выражения с операторам “&”, “|” , “=”, “>”, “<” имеют булевый тип, а с операторами “+”, “-”, “*” --- арифметический. Операнды арифметических операций должны иметь целочисленный тип, а операнды логических --- булевый. Вызов функции map заменяет каждый элемент массива на результат вычисления переданного арифметического выражения, в котором вместо element подставляется значение текущего элемента. Вызов функции filter оставляет в массиве только элементы, для которых переданное выражение истинно.

Последовательность вызовов применяется к массиву по очереди, слева направо.

Необходимо написать преобразователь выражений описываемых правилом <call-chain> в выражения вида <filter-call> “%>%” <map-call>, эквивалентные исходному. Решение должно принимать на стандартный поток ввода одну строку --- выражение описываемое правилом <call-chain> и выводить строку с преобразованным выражением. В случае наличия синтаксической ошибки, решение должно вывести SYNTAX ERROR, а если тип выражения не совпадает c ожидаемым TYPE ERROR.

Дополнительно, к решению предъявляются следующие требования:

* решение должно быть выполнено на языке Java или Kotlin;
* решение должно быть оформлено в виде публичного git репозитория;
* код должен быть хорошо структурирован;
* решение должно включать в себя тесты;
Бонус:

* решение производит упрощение выражений;

Примеры ниже содержат один из корректных вариантов преобразования, ваш вариант не обязан совпадать с предложенным. Примеры:

```
In:
filter{(element>10)}%>%filter{(element<20)}
Out:
filter{(element>10)&(element<20)}%>%map{element}
```
```
In:
map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}
Out:
filter{((element+10)>10)}%>%map{((element+10)*(element+10))}
```
```
In:
map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}
Out:
filter{(element>0)}%>%map{((element*element)+((element*20)+100))}
```
```
In:
filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)
Out:
filter{(1=0)}%>%map{element}
```