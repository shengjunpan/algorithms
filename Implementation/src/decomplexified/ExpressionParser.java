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
    /* sanity check before adding a new token. Valid adjacent tokens:
     number and ) can be followed by +, -,  or )
     start  and ( can be followed by number or (
     */
    private void safeAdd(Token token) {
//end{check}
        
        Token last = tokens.empty() ? null : tokens.peek();        
        boolean safe = true;
        if (last !=null && (last.isNumber() || last.isOperator(')'))) {
            safe = token.isOperator('+') || token.isOperator('-') ||
                    token.isOperator(')');
        } else {
            safe = token.isNumber() || token.isOperator('(');
        }
        
        if (!safe) {
            System.err.println("Can't add token '" + token + " to stack " + tokens);
            System.exit(1);
        }
        tokens.add(token);
    }

//begin{reduce-paren}    
    // take the top 3 tokens and perform parentheses operation.
    // need tokens: (, number, )
    private boolean reduceParen() {
//end{reduce-paren}    
        if (tokens.size() < 3) {
            System.out.println("  --> (no paren)");
            return false;            
        }
        Token rightParen = tokens.pop();
        Token operand = tokens.pop();
        Token leftParen = tokens.pop();
        
        if (!operand.isNumber() ||
                !leftParen.isOperator('(') ||
                !rightParen.isOperator(')')) {
            tokens.add(leftParen);
            tokens.add(operand);
            tokens.add(rightParen);
            System.out.println("  --> (no paren)");
            return false;            
        }
        tokens.add(operand);
        System.out.println("  --> " + tokens);                
        return true;
    }

//begin{reduce-binary}    
    // take the top 3 tokens and perform binary operation 
    // need tokens: number, +/-, number
    private boolean reduceBinary() {
//end{reduce-binary}        
        if (tokens.size() < 3) {
            System.out.println("  --> (no binary)");
            return false;
        }
        
        Token operand2 = tokens.pop();
        Token operator = tokens.pop();
        Token operand1 = tokens.pop();

        if (!operator.isOperator('+') && !operator.isOperator('-') ||
                !operand2.isNumber() || !operand1.isNumber()) {
            tokens.add(operand1);
            tokens.add(operator);
            tokens.add(operand2);
            System.out.println("  --> (no binary)");
            return false;
        }
        
        int num1 = operand1.operand;
        int num2 = operand2.operand;
        char op = operator.operator;        
        int result = op == '+' ? (num1 + num2) : (num1 - num2);
        tokens.add(new Token(result));
        System.out.println("  --> " + tokens);
        return true;
    }
    
//begin{parse}    
    public Integer parse(String expression) {
        // we don't parse an empty expression
        if (expression.isEmpty()) { return null; }
        
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
                safeAdd(new Token(operand));
            } // if
            
            System.out.println();
            if (i < expression.length()) {
                System.out.println("Next operator: " + c);
            }
            System.out.println("      " + tokens);
            
            // perform reduction. Both must be called, and reduceParen()
            // must be done before reduceBinary()
            reduceParen();
            reduceBinary();

            if (i < expression.length()) {
                safeAdd(new Token(c));
            }
        } // for
        
        System.out.println("\nFinal:");
        System.out.println("      " + tokens);
        reduceParen();
        reduceBinary();

        if (tokens.size() > 1 || !tokens.peek().isNumber()) {
            return null;
        }
        return tokens.pop().operand;
    }
//end{parse}    

    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        String expression = "20+34+(12+34)-(56-(78+4))-(6-7)+11";
        Integer result = parser.parse(expression);
        System.err.println();
        if (result == null) {
            System.err.println("Incomplete expression.");
        }
        System.out.println(expression + " = " + result);
    }
}
