import java.util.Hashtable;

public class DeclarationLexer {
    private int head = 0;
    private String msg;
    private static Hashtable<String, Boolean> ht = new Hashtable<>();

    private void fill_ht() {
        ht.put("boolean", true);
        ht.put("byte", true);
        ht.put("char", true);
        ht.put("double", true);
        ht.put("float", true);
        ht.put("int", true);
        ht.put("long", true);
        ht.put("short", true);
        ht.put("String", true);
    }

    public DeclarationLexer(String msg) {
        this.msg = msg;
        fill_ht();
    }

    public enum TokenType {
        DT, ID, EQ, NUM, FNUM, SEM, None
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
            } else if (ch == '=' && token.val.isEmpty()) {
                token.add(ch);
                token.type = TokenType.EQ;
                head++;
                break;
            } else if (ch == '=' && !token.val.isEmpty()) {
                break;
            } else if (ch == ';' && token.val.isEmpty()) {
                token.add(ch);
                token.type = TokenType.SEM;
                head++;
                break;
            } else if (ch == ';' && !token.val.isEmpty()) {
                break;
            }
            token.add(ch);

            switch (state) {
                case 0:
                    if (Character.isLetter(ch)) {
                        token.type = TokenType.ID;
                        state = 1;
                    } else if (ch == '_') {
                        token.type = TokenType.None;
                        state = 2;
                    } else if (ch == '0') {
                        token.type = TokenType.NUM;
                        state = 3;
                    } else if (ch >= '1' && ch <= '9') {
                        token.type = TokenType.NUM;
                        state = 4;
                    } else if (ch == '.') {
                        token.type = TokenType.None;
                        state = 6;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 1:
                    if (Character.isLetterOrDigit(ch) || ch == '_') {
                        token.type = TokenType.ID;
                        state = 1;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 2:
                    if (Character.isLetterOrDigit(ch) || ch == '_') {
                        token.type = TokenType.ID;
                        state = 1;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 3:
                    if (ch == '.') {
                        token.type = TokenType.FNUM;
                        state = 5;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 4:
                    if (Character.isDigit(ch)) {
                        token.type = TokenType.NUM;
                        state = 4;
                    } else if (ch == '.') {
                        token.type = TokenType.FNUM;
                        state = 5;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 5:
                    if (Character.isDigit(ch)) {
                        token.type = TokenType.FNUM;
                        state = 5;
                    } else {
                        token.type = TokenType.None;
                        state = 99;
                    }
                    break;
                case 6:
                    if (Character.isDigit(ch)) {
                        token.type = TokenType.FNUM;
                        state = 5;
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

        if (token.type == TokenType.ID) {
            if (ht.get(token.val) != null)
                token.type = TokenType.DT;
        }

        return token;
    }

    public static void main(String[] args) {

        String text = "int x = 5 ; double fnum = 0.5  ;   ";
        DeclarationLexer line = new DeclarationLexer(text);

        Token token = new Token();

        while ((token = line.nextToken()) != null) {
            System.out.println(token.show());
        }
    }

}