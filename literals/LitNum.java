public class LitNum {

    private int head = 0;
    private String msg;

    public LitNum(String msg) {
        this.msg = msg;
    }

    public enum TokenType {
        Dec, Bin, Oct, Hex, None
    }

    public static class Token {
        String val = "";
        TokenType type;

        public Token() {
        }

        public Token(String v, TokenType t) {
            this.val = v;
            this.type = t;
        }

        public void add(char c) {
            this.val += c;
        }

        public String show() {
            return "Value: " + this.val + "\tType: " + this.type.toString();
        }
    }

    public Token nextToken() {
        if (head >= msg.length())
            return null;

        Token token = new Token();
        int state = 0;
        char ch;
        do {
            ch = msg.charAt(head);
            if (ch == ' ' && !token.val.isEmpty()) {
                head++;
                break;
            } else if (ch == ' ' && token.val.isEmpty()) {
                head++;
                continue;
            }
            token.add(ch);
            switch (state) {
                case 0:
                    if (ch == '0') {
                        token.type = TokenType.Dec;
                        state = 2;
                    } else if (ch >= '1' && ch <= '9') {
                        token.type = TokenType.Dec;
                        state = 1;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 1:
                    if (Character.isDigit(ch)) {
                        token.type = TokenType.Dec;
                        state = 1;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 2:
                    if (ch == 'b') {
                        token.type = TokenType.None;
                        state = 3;
                    } else if (ch >= '0' && ch <= '7') {
                        token.type = TokenType.Oct;
                        state = 5;
                    } else if (ch == 'x') {
                        token.type = TokenType.None;
                        state = 6;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 3:
                    if (ch == '0' || ch == '1') {
                        token.type = TokenType.Bin;
                        state = 4;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 4:
                    if (ch == '0' || ch == '1') {
                        token.type = TokenType.Bin;
                        state = 4;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 5:
                    if (ch >= '0' && ch <= '7') {
                        token.type = TokenType.Oct;
                        state = 5;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 6:
                    if (Character.isDigit(ch)
                            || (Character.toLowerCase(ch) >= 'a' && Character.toLowerCase(ch) <= 'f')) {
                        token.type = TokenType.Hex;
                        state = 7;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 7:
                    if (Character.isDigit(ch)
                            || (Character.toLowerCase(ch) >= 'a' && Character.toLowerCase(ch) <= 'f')) {
                        token.type = TokenType.Hex;
                        state = 7;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 99:
                    break;
                default:
                    break;
            }
            head++;
        } while (head < msg.length());

        if (token.val.isEmpty())
            return null;
        return token;
    }

    public static void main(String[] args) {
        String text = "0 012 0b12 135 0x789F 0897 0xfGF  ... abc 0b010    ";
        LitNum num = new LitNum(text);

        Token token = new Token();

        while ((token = num.nextToken()) != null) {
            System.out.println(token.show());
        }
    }
}