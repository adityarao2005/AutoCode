package com.raos.autocode.math.parsing;

import com.raos.autocode.math.Expression;

/*
Constant: {<constant name>} | (0-9)+
Variable: x,y,z
Addition: <left expression> + <right expression>
Subtraction: <left expression> - <right expression>
Multiplication: <left expression> * <right expression>
Division: <left expression> / <right expression>
Exponentiation: <base> ^ <exponent>
Logarithm: {ln(<value>)}, {log10(<value>)}, {log(<power>, <base>)}
Trig: {sin(<value>)}, {cos(<value>)}, {tan(<value>)}
Function: {<function name>(<params>...)}
Brackets: (<expression>)
Negate: -(<expression>)

 */
public class NaturalExpressionParser extends InfixExpressionParser {

	@Override
	public Expression parse(String str) {
		// Last expression
		// Last Operation
		
		
		return null;
	}

}
