import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.io.PrintWriter; 
import java.net.ServerSocket; 
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Executor; 
import java.util.concurrent.Executors; 

public class JavaWebServer
{   
	private static final int fNumberOfThreads = 100;	//para que el  servidor pueda tener varios clientes
	private static final Executor fThreadPool = Executors.newFixedThreadPool(fNumberOfThreads);//para ejecutar hebras   
	
	public static void main(String[] args) throws IOException 
	{ 
		ServerSocket socket = new ServerSocket(8082); // se crea socket en puerto designado
		
		while (true) 
		{
			final Socket connection = socket.accept(); //while  hasta que  exista conexion 
			Runnable task = new Runnable() // meto los hilos
			{ 
				@Override 
				public void run() 
				{ 
					HandleRequest(connection);//ejecuto hilos como  manejadores del soket
				} 
			};
			fThreadPool.execute(task);
		}
	}   
	
	private static void HandleRequest(Socket sockete)
	{ 
		BufferedReader in;
		PrintWriter out; 
		String request;   
		try 
		{ 
			
			while(true)
			{
			String DireccionServidor = sockete.getInetAddress().toString();;
			System.out.println("New Connection:" + DireccionServidor);
			in = new BufferedReader(new InputStreamReader(sockete.getInputStream())); 
			
			
			request = in.readLine(); 
			System.out.println("--- Client request: " + request);
			

			out = new PrintWriter(sockete.getOutputStream(), true); 
			out.println("HTTP/1.0 200");
			out.println("Content-type: text/html"); 
			out.println("Server-name: myserver"); 
			
			
	

		        
                File file = new File(System.getProperty("user.dir") + "/src/bootstrap-3.0.0/examples/redes tarea/pagredes.html"  );
                BufferedReader br = new BufferedReader(new FileReader(file));
                
                FileInputStream fis = new FileInputStream(file);
                
                out.println("Content-length: " + file.length());
                out.println("");
                
                
                
                
        		int pesoArchivo = (int) file.length();
                
    			
        		
		        
                if(request.startsWith("POST") ){
                  
                     System.out.println("hola");  
                     
                 }
 			
                
   	         if(request.startsWith("GET") ){

   	            
   	                
   	             System.out.println("HTTP/1.0 200 OK");
   	             System.out.println("Server: Java HTTP Server 1.0");
   	             System.out.println("Date: " + new Date());
   	             System.out.println("Content-length: " + pesoArchivo);
   	             System.out.println("\n");
   	                
   	         }
                
                
                byte[] buffer = new byte[(int)file.length()];
                for(int i=0;i<file.length();i++) {
                    buffer[i] = (byte)fis.read();
                }
                sockete.getOutputStream().write(buffer);
                
            
		        

			
			
			//String response = "<html>n" 
		//			+ "<head>n" 
			//		+ "<title>My Web Server</title></head>n" 
				//	+ "<h1>caca!</h1>n" 
					//+ "</html>n"; 
		//	out.println("Content-length: " + response.length());
			
			out.println("");
			//out.println(response);
			out.flush();
			out.close(); 
			sockete.close();
			}
		} 
		catch (IOException e)
		{ 
			System.out.println("Failed respond to client request: " + e.getMessage());
		} 
		finally 
		{ 
			if (sockete != null) 
			{
				try 
				{
					sockete.close();
				} 
				catch (IOException e)
				{ 
					e.printStackTrace();
				}
			}
		} 
		return; 
	}   
	
}