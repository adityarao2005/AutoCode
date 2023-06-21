package com.raos.autocode.math.parsing;

import com.raos.autocode.math.Expression;

// Parsing occurs as so
/*
Constant -> 
	\const{value="0"}
		- value - double value
	\const{name="[name of constant]"} // all lowercase
		- name - name of the constant i.e. pi, e
Variable -> \var{name="[name]"}
Addition -> \add{ [left_expression], [right_expression] }
Subtract -> \subtract{ [left_expression], [right_expression] }
Negate -> \negate{ [expression] }
Multiplication -> \multiply{ [left_expression], [right_expression] }
Division -> \divide{ [left_expression], [right_expression] }
Exponentiation -> \exp{ [base_expression], [power_expression] }
Functions ->
	\func{ name="[name of function]", params={ [value], ... } }
	 - Params will be requesting the number of arguments for function
Logarithm ->
	\func{ name="log", params={ [value] }
		- returns ln(value)
	\func{ name="log", params={ [base], [value] } }
		- returns ln(value)/ln(base)
Sine ->	\func{ name="sin", params={ [angle] } }
Cosine -> \func{ name="cos", params={ [angle] } }
Tangent -> \func{ name="tan", params={ [angle] }
Brackets -> \brackets{ [expression] }

Characters used - '\', [a-zA-Z0-9], '{', '}', '=', '"'

 */
public class StructuredExpressionParser extends ExpressionParserBase {

//	private static final Predicate<Character> VALID_PARSING = w -> {
//		// Handles a-z && A-Z && 0-9
//		if (Character.isLetterOrDigit(w)) {
//			return true;
//		}
//
//		// Handles brackets
//		if (w == '{' || w == '}')
//			return true;
//
//		// Handle backslash
//		if (w == '\\')
//			return true;
//
//		// Handle equals
//		if (w == '=')
//			return true;
//
//		// Quote
//		if (w == '\"')
//			return true;
//		return false;
//	};

	@Override
	public Expression parse(String str) {

		// Create a queue

		// Test function: f(x) = 1 - 2 * 3 + (4 / (x sin (5 * x))) * x^5
		// Compiled Form: f(x) = \add{ \subtract{ \const{value = "1"}, \multiply{
		// \const{value = "2"}, \const{value = "3"}}}, \multiply{ \brackets{ \divide{
		// \const{value="4"}, \bracket{ \multiply{ \var{name="x"}, \func{name="sin",
		// params={ \multiply{ \const{value="5"}, \var{name="x"}}}}}}, \exp{ \var{name =
		// "x"}, \const{value = "5"}}}}}}

		parse(str, 0);

		// Return the main expression
		return null;
	}

	public Expression parse(String str, int index) {
		// If we are at the first character, skip it, it will be a '\' character
		if (index == 0)
			// Parse the next values
			return parse(str, 1);
		// If we reach an open bracket, parse using ExpressionBuilder
		else if (str.charAt(index) == '{') {
			
			return null;

		} else {
			// Parse the next values
			return parse(str, index + 1);
		}

	}

}
