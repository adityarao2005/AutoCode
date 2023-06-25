package com.raos.autocode.math.parsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.operation.function.Functions;
import com.raos.autocode.math.operation.Operations;
import com.raos.autocode.math.operation.function.FunctionalExpression;
import com.raos.autocode.math.variable.VariableExpression;

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
// Expression Parser - Compiled/Structured expression parser
// Parses the expression from left to right form - Heavily depends on regex
public class StructuredExpressionParser extends PrefixExpressionParser {
	// Regex Patterns
	private static final String EXPRESSION_PARSING_REGEX = "^\\\\([\\w]+)\\{(.*)\\}$";
	private static final String VARIABLE_DETAILS_REGEX = "^name[\\s]?=[\\s]?\\\"([a-z])\\\"$";
	private static final String CONSTANT_DETAILS_REGEX = "^(?:(name)[\\s]?=[\\s]?\\\"([a-z])\\\")|(?:(value)[\\s]?=[\\s]?\\\"([0-9]+[.]?[0-9]*)\\\")$";
	private static final String FUNCTION_DETAILS_REGEX = "^name=\\\"(\\w+)\\\"\\sparams=\\{(.+)\\}$";

	// Parses expression
	@Override
	public Expression parse(String str) {

		// Create a queue

		// Test function: f(x) = 1 - 2 * 3 + (4 / (x sin (5 * x))) * x^5
		// Compiled Form: f(x) = \add{ \subtract{ \const{value = "1"}, \multiply{
		// \const{value = "2"}, \const{value = "3"}}}, \multiply{ \brackets{ \divide{
		// \const{value="4"}, \bracket{ \multiply{ \var{name="x"}, \func{name="sin",
		// params={ \multiply{ \const{value="5"}, \var{name="x"}}}}}}, \exp{ \var{name =
		// "x"}, \const{value = "5"}}}}}}

		// Regex api to match string
		Pattern pattern = Pattern.compile(EXPRESSION_PARSING_REGEX);
		Matcher matcher = pattern.matcher(str.trim());
		// Get the regex params
		if (matcher.find()) {

			// Internal matching
			String type = matcher.group(1);
			String internalArgs = matcher.group(2);

			// Return the expression
			return parseInternal(type, internalArgs);

			// Throw error
		} else {
			throw new ExpressionParsingException("Invalid expression type");
		}

	}

	// Parses the expression internally
	private Expression parseInternal(String type, String internalArgs) {
		// Create the expression
		Expression expression = null;

		Pattern pattern;
		Matcher matcher;

		// Go through all its types
		switch (type) {
		case "var": {
			// get the name of the expression
			pattern = Pattern.compile(VARIABLE_DETAILS_REGEX);
			matcher = pattern.matcher(internalArgs.trim());

			// Find the pattern
			if (matcher.find()) {

				// Return the variable expression
				expression = new VariableExpression(matcher.group(1));

			} else {
				// Throw error
				throw new ExpressionParsingException(
						"For this parser, the syntax for variables must be followed: \\var{ name = \"<name of variable, one character only>\" }");
			}

			break;
		}
		case "const": {
			// Parse the expression
			pattern = Pattern.compile(CONSTANT_DETAILS_REGEX);
			matcher = pattern.matcher(internalArgs.trim());

			// Find the pattern
			if (matcher.find()) {

				// If there is no named constant
				if (matcher.group(1) == null || matcher.group(1).trim().isEmpty()) {

					// Get the value
					expression = Expression.ofConstant(Double.parseDouble(matcher.group(4)));
				} else {

					// Get the value at name
					expression = Expression.getConstant(matcher.group(2));

				}
			} else {
				// Throw error
				throw new ExpressionParsingException(
						"For this parser, the syntax for variables must be followed: \\const{ name = \"<name of constant>\"} or \\const{ value = \"<double or int value>\" }");
			}

			break;
		}
		case "add":
		case "subtract":
		case "multiply":
		case "divide":
		case "exp": {
			// Perform balancing algorithm
			String[] split = balancingSplit(internalArgs, '{', '}', ',');

			// Substring to get the left expression
			String left = split[0];
			String right = split[1];

			// Return expressions
			if (type.equals("add")) {
				expression = Operations.add(parse(left), parse(right));

			} else if (type.equals("subtract")) {
				expression = Operations.subtract(parse(left), parse(right));

			} else if (type.equals("multiply")) {
				expression = Operations.multiply(parse(left), parse(right));

			} else if (type.equals("divide")) {
				expression = Operations.divide(parse(left), parse(right));

			} else if (type.equals("exp")) {
				expression = Operations.power(parse(left), parse(right));

			}

			break;
		}
		case "negate":
		case "bracket":

			// If negate, then negate the expression. otherwise just parse what ever is in
			// there (although it shouldn't be needed)
			if (type.equals("negate"))
				expression = Expression.negate(parse(internalArgs));
			else
				expression = parse(internalArgs);
			break;
		case "func": {

			// Create the pattern
			pattern = Pattern.compile(FUNCTION_DETAILS_REGEX);
			matcher = pattern.matcher(internalArgs);

			// Create the matcher
			if (matcher.find()) {
				// Get the names and params as string
				String name = matcher.group(1);
				String paramsStr = matcher.group(2);

				// Get functions
				Function<Expression[], FunctionalExpression> function = Functions.getFunction(name);

				// Create the array
				String[] params = balancingSplit(paramsStr, '{', '}', ',');

				// Create the expression
				expression = function.apply(Arrays.stream(params).map(this::parse).toArray(Expression[]::new));

			} else {
				// Throw error
				throw new ExpressionParsingException(
						"For this parser, the syntax for variables must be followed: \\const{ name = \"<name of constant>\"} or \\const{ value = \"<double or int value>\" }");
			}

			break;
		}
		// Throw error
		default:
			throw new IllegalArgumentException(String.format("%s is not a valid type", type));
		}

		// Return the expression
		return expression;
	}

	// Balancing split
	private String[] balancingSplit(String str, char open, char closed, char split) {
		// return the array
		List<String> values = new ArrayList<>();

		// Balancing algorithm
		int balance = 0;
		int startExpr = 0;

		// loop over all the characters in the loop
		for (int i = 0; i < str.length(); i++) {
			// if the character is an open brace, then increase the balance
			if (str.charAt(i) == open) {
				balance++;
			} else {
				// If the character is a closed brace
				if (str.charAt(i) == closed) {
					// if the balance is greater than 0, then decrease the balance
					if (balance > 0)
						balance--;
					// Otherwise throw an error
					else {
						throw new ExpressionParsingException(String.format("Invalid character found at index: %d", i));
					}

					// If the balance is 0, then grab the start to the end
					if (balance == 0) {
						// Reached the end
						values.add(str.substring(startExpr, i).trim());

						// Set the start expr
						startExpr = str.substring(i).indexOf(split) + 1;

					}

				}
			}
		}

		// Return the array
		return values.toArray(String[]::new);
	}
}
