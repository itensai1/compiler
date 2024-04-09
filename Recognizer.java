public class Recognizer {

    private String token;

    public Recognizer(String tok) {
        this.token = tok;
    }

    public Boolean end_with_ab() {
        int head = 0, state = 0;
        char ch;
        do {
            ch = token.charAt(head);
            switch (state) {
                case 0:
                    if (ch == 'a') {
                        state = 1;
                    } else if (ch == 'b') {
                        state = 0;
                    } else {
                        return false; // To handle if the token contains a character from outside the alphabet
                    }
                    break;

                case 1:
                    if (ch == 'a') {
                        state = 1;
                    } else if (ch == 'b') {
                        state = 2;
                    } else {
                        return false;
                    }

                    break;
                case 2:
                    if (ch == 'a') {
                        state = 1;
                    } else if (ch == 'b') {
                        state = 0;
                    } else {
                        return false;
                    }
                    break;
            }
            head++;
        } while (head < token.length());

        return (state == 2);
    }

    public static void main(String[] args) {
        String token = "abbab";

        Recognizer Rec = new Recognizer(token);

        System.out.println(Rec.end_with_ab());
    }

}
