import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner;

        if (args.length > 0) {
            InputStream inputStream = new FileInputStream(args[0]);
            scanner = new Scanner(inputStream);
        } else {
            scanner = new Scanner(System.in);
        }

        SimpleDB simpleDb = new SimpleDB();
        Transaction transaction = new Transaction(simpleDb);


        String cmd = scanner.nextLine().toLowerCase();
        String[] splited = cmd.split("\\s+");

        while (!splited[0].equals("end")) {
            switch (splited[0]) {
                case "set":
                    if (transaction.inTransaction()) {
                        transaction.insertCmd(cmd);
                    }
                    simpleDb.set(splited[1], Integer.valueOf(splited[2]));

                    break;

                case "unset":
                    if (transaction.inTransaction()) {
                        transaction.insertCmd(cmd);
                    }
                    simpleDb.unset(splited[1]);

                    break;

                case "get":
                    System.out.println(simpleDb.get(splited[1]));
                    break;

                case "numequalto":
                    int ans = simpleDb.numEqualTo(Integer.valueOf(splited[1]));
                    System.out.println(ans);
                    break;

                case "begin":
                    transaction.begin();
                    break;

                case "rollback":
                    String rollback_out = transaction.rollback();
                    if(rollback_out != null) {
                        System.out.println(rollback_out);
                    }
                    break;

                case "commit":
                    String commit_out = transaction.commit();
                    if(commit_out != null) {
                        System.out.println(commit_out);
                    }
                    break;

            }

            cmd = scanner.nextLine().toLowerCase();
            splited = cmd.split("\\s+");
        }


    }
}
