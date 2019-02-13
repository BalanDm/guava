import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static int i = 0;


    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException, ExecutionException {
        String loginOne = "login124!!!";
        String loginTwo = "login4321!!!";
        final String sessT1 = SessionManager.generateSessToken(loginOne);
        final String sessT2 = SessionManager.generateSessToken(loginTwo);
        SessionManager.addSessionToken(sessT1, "1");
        SessionManager.addSessionToken(sessT2, "2");
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        i = 0;
        Callable c1 = new Callable() {
            public Object call() throws Exception {
                System.out.println(SessionManager.getProducts(sessT2));
                System.out.println("Session Manager i = " + SessionManager.getI().incrementAndGet());
                System.out.println("CALL C1");
                return null;
            }
        };

        Callable c2 = new Callable() {
            public Object call() throws Exception {
                System.out.println(SessionManager.getProducts(sessT2));
                System.out.println("Session Manager i = " + SessionManager.getI().incrementAndGet());
                System.out.println("CALL C2");
                return null;
            }
        };

        List<Callable<Void>> callables = new ArrayList<Callable<Void>>();
        callables.add(c1);
        callables.add(c2);
        executorService.invokeAll(callables);
        executorService.shutdown();
    }


}
