import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class WorkerThread implements Runnable {
     
    private String input;
    private String portNumber;
     
    public WorkerThread(String input){
        this.input=input;
    }
    /** Helper function for calculating hash
     *  
     * @param data byte array to hash
     * @param length length of the array to hash
     * @return 64 bit hash of the given string
     */
    public static long hash64( final byte[] data, int length, int seed) {
                final long m = 0xc6a4a7935bd1e995L;
                final int r = 47;
                long h = (seed&0xffffffffl)^(length*m);
                int length8 = length/8;
                for (int i=0; i<length8; i++) {
                        final int i8 = i*8;
                        long k =  ((long)data[i8+0]&0xff)      +(((long)data[i8+1]&0xff)<<8)
                                        +(((long)data[i8+2]&0xff)<<16) +(((long)data[i8+3]&0xff)<<24)
                                        +(((long)data[i8+4]&0xff)<<32) +(((long)data[i8+5]&0xff)<<40)
                                        +(((long)data[i8+6]&0xff)<<48) +(((long)data[i8+7]&0xff)<<56);
   
                        k *= m;
                        k ^= k >>> r;
                        k *= m;
   
                        h ^= k;
                        h *= m; 
                }
   
                switch (length%8) {
                case 7: h ^= (long)(data[(length&~7)+6]&0xff) << 48;
                case 6: h ^= (long)(data[(length&~7)+5]&0xff) << 40;
                case 5: h ^= (long)(data[(length&~7)+4]&0xff) << 32;
                case 4: h ^= (long)(data[(length&~7)+3]&0xff) << 24;
                case 3: h ^= (long)(data[(length&~7)+2]&0xff) << 16;
                case 2: h ^= (long)(data[(length&~7)+1]&0xff) << 8;
                case 1: h ^= (long)(data[length&~7]&0xff);
                        h *= m;
                };
         
                h ^= h >>> r;
                h *= m;
                h ^= h >>> r;
                return h;
        }
   
        /** Generates 64 bit hash from byte array with default seed value.
         * 
         * @param data byte array to hash
         * @param length length of the array to hash
         * @return 64 bit hash of the given string
         */
        public static long hash64( final byte[] data, int length) {
                return hash64( data, length, 0xe17a1465);
        }
        /** Generates 64 bit hash from a string.
         * 
         * @param text string to hash
         * @return 64 bit hash of the given string
         */
        public static long hash64( final String text) {
                final byte[] bytes = text.getBytes(); 
                return hash64( bytes, bytes.length);
        }
  // s;tc;topic;content (success), s;up;user;password(success), s;ut;user;topic (success)
  // r;up;user;password(authenticated), r;tc;topic(content), r;ut;user(topic)
    @Override
    public void run() {
      //  System.out.println(Thread.currentThread().getName()+" Start. Command = "+input);
        try {
                        processCommand(Thread.currentThread().getName());
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
      //  System.out.println(Thread.currentThread().getName()+" End.");
    }
 
    private void processCommand(String port) throws UnknownHostException, IOException {
         String[] splits = this.input.split(";");
         String key = splits[2];
         long hashval = hash64(key);
         String serverAddress =  webserver.master_address;//"192.168.0.110";
         System.out.println("Trying to send the input "+input+ "to server" +serverAddress);
         Socket s = new Socket(serverAddress,9898);
         System.out.println("Created socket");
         PrintWriter out = new PrintWriter(s.getOutputStream(), true);
         out.println(hashval+":"+port);
         s.close();
         System.out.println("Socket closed");
          
         ServerSocket Listener = new ServerSocket(Integer.parseInt(port));
         Socket socket = Listener.accept();
                 BufferedReader in = 
                                         new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 String  datanodeAddress = in.readLine();
                 System.out.println("Returned value for:" + key+" is" + datanodeAddress );
                 socket.close();
                 Listener.close();
                 
                 //Sending to datanode
                Socket datanode_socket = new Socket(datanodeAddress, 8000);
                 out = new PrintWriter(datanode_socket.getOutputStream(), true);
                 out.println(this.input+";"+port);
                 datanode_socket.close();                

                 Listener = new ServerSocket(Integer.parseInt(port));
                 socket = Listener.accept();
                 System.out.println("Listenign:");
                 BufferedReader inp =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 String output = inp.readLine();
                 System.out.println("Output for command " +this.input +" is" + output);
                 socket.close();
                 Listener.close();       
                 
                 
         }
         
    @Override
    public String toString(){
        return this.input;
    }
}
