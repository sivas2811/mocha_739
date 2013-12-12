import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
 
public class webserver {

        static String master_address; 
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool
                        (10, new ThreadFactory(){
                                public Thread newThread(Runnable runnable){
                                        Thread thread = new MyThreadFactory().newThread(runnable);
                                        return thread;
                                }
                        });
        System.out.println("Webserver is running");
    
        ServerSocket listen_from_master = new ServerSocket(8084);
        Socket master;
        master = listen_from_master.accept();
        BufferedReader mas =  new BufferedReader(new InputStreamReader(master.getInputStream()));
        master_address = mas.readLine();
        System.out.println("Master IP address is "+master_address);


         ServerSocket Listener = new ServerSocket(9090);
        Socket socket ;
        while(true){
        socket = Listener.accept();
                 BufferedReader in = 
                                         new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 String input = in.readLine();
                 System.out.println("readLine:"+ input);
            Runnable worker = new WorkerThread(input);
            executor.execute(worker);  
            
        }
    }
 
}
/* TODO
 *  *  * 1. SEnd request to appropriate data node
 *   *   * 2. Store client id and return with the reply message
 *    *    * 
 *     *     */

