package vrbaidu.top.netty.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class BIOClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket client = new Socket("localhost", 8080);
		
		//�ͻ�����߾���д����
		OutputStream os = client.getOutputStream();
		
		os.write("������".getBytes());
		os.close();
		client.close();
		
	}
	
}
