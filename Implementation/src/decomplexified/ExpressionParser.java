package decomplexified;

import java.util.Stack;

/**
 * @author Alan
 *
 */
public class ExpressionParser {
//begin{token}    
    // class representing an operator or an integral operand
    private static class Token {
        public Character operator;
        public Integer operand;
//end{token}
        
        public Token(char c) {
            operator = c;
            operand = null;
        }
        
        public Token(int x) {
            operator = null;
            operand = x;
        }
        
        public boolean isNumber() { return operand != null; }
        
        public boolean isOperator(char c) {
            return !isNumber() && operator == c;
        }
        
        public String toString() {
            if (isNumber()) {
                return operand.toString();
            } else {
                return operator.toString();
            }
        }
    }
    
    // stack to store tokens parsed so far
    private Stack<Token> tokens = new Stack<>();
    
//begin{check}    
    // sanity check before adding a new token. Valid adjacent tokens:
    //   * number and ) can be followed by +, -,  or )
    //   * start  and ( can be followed by number or (
    // return false if not safe.
    private boolean safeAdd(Token token) {
//end{check}
        
        Token last = tokens.empty() ? null : tokens.peek();        
        boolean safe = true;
        if (last !=null && (last.isNumber() || last.isOperator(')'))) {
            safe = token.isOperator('+') || token.isOperator('-') ||
                    token.isOperator(')');
        } else {
            safe = token.isNumber() || token.isOperator('(');
        }
        
        if (safe) {
            tokens.add(token);
        } else {
            System.out.println(token + " can't follow " + tokens.peek());
        }
        return safe;
    }

//begin{reduce-binary}    
    // take the top 3 tokens and perform binary operation 
    // need tokens: number, +/-, number
    private void reduceBinary() {
//end{reduce-binary}
        // not enough tokens
        if (tokens.size() < 3) { return; }
        
        // last token must be a number
        if (!tokens.peek().isNumber()) { return; }        
        Token operand2 = tokens.pop();
        
        // second last token must be an operator
        if (tokens.peek().isNumber()) {
            tokens.add(operand2);
            return;
        }
        Token operator = tokens.pop();
        
        // third last token must be a number
        if (!tokens.peek().isNumber()) {
            tokens.add(operator);
            tokens.add(operand2);
            return;
        }        
        Token operand1 = tokens.pop();

        int num1 = operand1.operand;
        int num2 = operand2.operand;
        char op = operator.operator;        
        int result = op == '+' ? (num1 + num2) : (num1 - num2);
        tokens.add(new Token(result));
    }
    
  //begin{reduce-paren}    
    // take the top 2 tokens and perform parenthesis operation.
    // need tokens: (, number
    // return false if parentheses mismatch
    private boolean reduceParen() {
//end{reduce-paren}
        
        // after previous reductions the stack must have ( and a number on top.       
        if(tokens.size() < 2) {
            System.out.println("parentheses mismatch.");
            return false;
        }
        
        Token operand = tokens.pop();
        Token leftParen = tokens.pop();
        
        // after previous reductions the stack must have ( and a number on top.       
        if (!operand.isNumber() || !leftParen.isOperator('(')) {
            System.out.println("parentheses mismatch.");
            return false;            
        }

        tokens.add(operand);
        return true;
    }

//begin{parse}
    // return null if parse fails
    public Integer parse(String expression) {
        // we don't parse an empty expression
        if (expression.isEmpty()) {
            System.out.println("Expression is empty.");
            return null;
        }
        
        // initialization
        tokens.clear();
        
        for (int i=0; i<expression.length(); ++i) {
            char c = expression.charAt(i);
            
            // If the current character is a digit, scan for all
            // following digits to form an operand
            if (c >= '0' && c <= '9') {
                int operand = c - '0';
                for( i++; i < expression.length(); ++i) {
                    c = expression.charAt(i);
                    if (c < '0' || c > '9') { break; }
                    operand = operand * 10 + (c - '0');
                } // for
                if (!safeAdd(new Token(operand))) {
                    return null;
                }
            } // if
            
            // perform binary reduction
            reduceBinary();

            if (i < expression.length()) {
                // do parenthesis reduction if the new operator is right parenthesis,
                if (c == ')') {
                    if(!reduceParen()) { return null; }
                // otherwise try to push it to the stack.
                } else if (!safeAdd(new Token(c))) {
                    return null;
                }
            }
        } // for
        
        // it may be necessary to perform one last binary operation if the
        // expression ends with a number
        reduceBinary();

        if (tokens.size() > 1 || !tokens.peek().isNumber()) {
            System.out.println("Incomplete expression.");
            return null;
        }
        return tokens.pop().operand;
    }
//end{parse}    

    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        
        String[] expressions = {
                "",
                "1",
                "20+(34-((1-2)-3))+56",
                "20+34+(12+34)-(56-(78+4))-(6-7)+11",
                "20+34+(12+34)-(56-)(78+4))-(6-7)+11",
                "20+34+(12+34)-(56(-78+4))-(6-7)+11",
                "20+34+(12+34))-(56-(78+4))-(6-7)+11",
                "20+34+(12+34)-((56-(78+4))-(6-7)+11"
        };

        for (int i=0; i < expressions.length; ++i) {
            String expression = expressions[i];
            System.out.print(expression + " = ");
            Integer result = parser.parse(expression);
            if (result != null) {
                System.out.println(result);
            }
        }
    }
}
