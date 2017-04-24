package vrbaidu.top.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NIOServer {
	int port = 8080;
	ServerSocketChannel server;
	
	Selector selector;
	
	
	ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
	ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
	
	
	Map<SelectionKey,String> sessionMsg = new HashMap<SelectionKey,String>();
	
	public NIOServer(int port) throws IOException{
		
		this.port = port;
		//���൱���Ǹ��ٹ�·��ͬʱ�����ų�,��������Խ�󣬳�������Խ��
		server = ServerSocketChannel.open();
		
		//�ؿ�Ҳ���ˣ����Զ�·������
		server.socket().bind(new InetSocketAddress(this.port));
		//Ĭ���������ģ��ֶ�����Ϊ�������������Ƿ�����
		server.configureBlocking(false);
		
		//�к�ϵͳ��ʼ����
		selector = Selector.open();
		
		//���ٹܼң�BOSS�Ѿ�׼���������Ȼ��п������ˣ�Ҫ֪ͨ��һ��
		//�Ҹ���Ȥ���¼�
		server.register(selector, SelectionKey.OP_ACCEPT);
		
		System.out.println("NIO�����Ѿ������������˿��ǣ�" + this.port);
	}
	
	
	public void listener() throws IOException{
		//��ѯ
		while(true){
			//�ж�һ�£���ǰ��û�пͻ���ע�ᣬ��û���Ŷӵģ���û��ȡ�ŵ�
			//û�������Ȥ���¼�������ʱ�����������������λ��
			//ֻҪ�������Ȥ���¼�������ʱ�򣬳���Ż�����ִ��
			int i = selector.select();//���λ�ã�����һ��������
			
			if(i == 0){ continue;}
			
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = keys.iterator();
			
			while (iterator.hasNext()) {

				//��һ������һ��
				process(iterator.next());
				
				//������֮�����
				iterator.remove();
				
			}
			
		}
		
		
	}
	
	
	private void process(SelectionKey key) throws IOException{
		
		//�жϿͻ���û�и�����BOSS����������
		if(key.isAcceptable()){
			SocketChannel client = server.accept();
			client.configureBlocking(false);
			//�������߽к�ϵͳ����һ����Ҫ��ʼ�������ˣ��ǵ�֪ͨ��
			client.register(selector, SelectionKey.OP_READ);
		}
		//�ж��Ƿ���Զ�������
		else if(key.isReadable()){
			receiveBuffer.clear();
			
			//�����ܵ��ǽ�������NIO API�ڲ�ȥ�����
			SocketChannel client = (SocketChannel)key.channel();
			int len = client.read(receiveBuffer);
			if(len > 0){
				String msg = new String(receiveBuffer.array(), 0, len);
				sessionMsg.put(key, msg);
				System.out.println("��ȡ�ͻ��˷���������Ϣ��" + msg);
			}
			//���߽к�ϵͳ����һ���Ҹ���Ȥ���¼�����Ҫд������
			client.register(selector, SelectionKey.OP_WRITE);
		}
		//�Ƿ����д����
		else if(key.isWritable()){
			
			
			if(!sessionMsg.containsKey(key)){ return;}
			
			SocketChannel client = (SocketChannel)key.channel();
			
			sendBuffer.clear();
			
			sendBuffer.put(new String(sessionMsg.get(key) + ",��ã����������Ѵ������").getBytes());
			
			sendBuffer.flip();//
			client.write(sendBuffer);
			//�ٸ������ǽк�ϵͳ����һ����Ҫ���ĵĶ��������Ƕ���
			//��˷��������ǵĳ�������뵽��һ������ȡ���ͬ����������ȥ�˵�
			client.register(selector,SelectionKey.OP_READ);
			//SelectionKey.OP_ACCEPT//����ˣ�ֻҪ�ͻ������ӣ��ʹ���
			//SelectionKey.OP_CONNECT//�ͻ��ˣ�ֻҪ�����˷���ˣ��ʹ���
			//SelectionKey.OP_READ//ֻҪ�������������ʹ���
			//SelectionKey.OP_WRITE//ֻҪ����д�������ʹ���
			
		}
		
	}
	
	
	public static void main(String[] args) throws IOException {
		new NIOServer(8080).listener();
	}
}
