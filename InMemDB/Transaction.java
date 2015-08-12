import java.util.Stack;

public class Transaction {

    private Stack<String> stack = new Stack<>();
    private SimpleDB simpleDb;

    public Transaction(SimpleDB simpleDb) {
        this.simpleDb = simpleDb;
    }

    public void begin() {
        stack.push("begin");
    }

    public void insertCmd(String cmd) {
        String[] splited = cmd.split("\\s+");
        switch (splited[0]) {
            case "set":
                stack.push(simpleDb.findInvertOf(cmd));
                break;

            case "unset":
                if (simpleDb.findInvertOf(cmd) != null) {
                    stack.push(simpleDb.findInvertOf(cmd));
                }
                break;
        }
    }


    public String rollback() {
        if (!inTransaction()) {
            return "NO TRANSACTION";
        }


        String cmd = stack.pop();
        String[] splited = cmd.split("\\s+");
        while (!splited[0].equals("begin")) {
            switch (splited[0]) {
                case "set":
                    simpleDb.set(splited[1], Integer.valueOf(splited[2]));
                    break;

                case "unset":
                    simpleDb.unset(splited[1]);
                    break;
            }

            cmd = stack.pop();
            splited = cmd.split("\\s+");
        }

        return null;
    }

    public String commit() {
        if (!inTransaction()) {
            return "NO TRANSACTION";
        }

        stack = new Stack<>();
        return null;
    }

    public boolean inTransaction() {
        return !stack.isEmpty();
    }
}
