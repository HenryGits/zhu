package vrbaidu.top.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NIOClient {
	
	SocketChannel client;
	InetSocketAddress serverAdrress = new InetSocketAddress("localhost", 8080);
	
	Selector selector;
	
	
	ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
	ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
	
	public NIOClient() throws IOException{
		//�ȿ�·
		client = SocketChannel.open();
		client.configureBlocking(false);
		
		client.connect(serverAdrress);
		
		
		selector = Selector.open();
		
		client.register(selector, SelectionKey.OP_CONNECT);
		
		
	}
	
	
	public void session() throws IOException{
		//��Ҫ�ж��Ƿ��Ѿ���������
		if(client.isConnectionPending()){
			client.finishConnect();
			System.out.println("���ڿ���̨�Ǽ�����");
			client.register(selector, SelectionKey.OP_WRITE);
		}
		
		Scanner scan = new Scanner(System.in);
		while(scan.hasNextLine()){
			String name = scan.nextLine();
			if("".equals(name)){continue;}
			process(name);
		}
		
	}
	
	public void process(String name) throws IOException{
		boolean unFinish = true;
		while(unFinish){
			//�ж�һ�£���ǰ��û�пͻ���ע�ᣬ��û���Ŷӵģ���û��ȡ�ŵ�
			int i = selector.select();
			if(i == 0){ continue;}
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = keys.iterator();
			
			while (iterator.hasNext()) {
				
				SelectionKey key = iterator.next();
				if(key.isWritable()){
					sendBuffer.clear();
					sendBuffer.put(name.getBytes());
					sendBuffer.flip();
					
					client.write(sendBuffer);
					
					client.register(selector, SelectionKey.OP_READ);
					
				}else if(key.isReadable()){
					receiveBuffer.clear();
					int len = client.read(receiveBuffer);
					if(len > 0){
						receiveBuffer.flip();
						System.out.println("��ȡ������˷�������Ϣ��" + new String(receiveBuffer.array(),0,len));
						client.register(selector, SelectionKey.OP_WRITE);
						unFinish = false;
					}
					
					
				}
				
			}
			
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		new NIOClient().session();
	}
	
	
}
