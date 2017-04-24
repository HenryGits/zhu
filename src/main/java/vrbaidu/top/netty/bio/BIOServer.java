package vrbaidu.top.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {
	
	ServerSocket server;
	//����һ�������
	public BIOServer(int port){
		try {
			server = new ServerSocket(port);
			System.out.println("BIO�����������������˿��ǣ�" + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ͻ��˹���������
	 * @throws IOException 
	 */
	public void listener() throws IOException{
		//��ѭ�������������
		while(true){
			Socket client = server.accept();//��������,�����������ִ��
			
			//�൱�ڿ���ʱ���µ���幫·,�������ˣ��ǲ��ǻ�ȡ�ͻ��˷��������ݣ�
			//�ǲ����൱�ڿͻ���Ҫ���������˵��ڴ���д����
			//��ô�����ڷ������˵��ڴ���˵������������ȡ���ݣ�����
			InputStream is = client.getInputStream();
			//α�첽����ֻ���ö��߳�
			byte [] buff = new byte[1024];
			int len = is.read(buff);
			if(len > 0){
				String msg = new String(buff,0,len);
				System.out.println("���յ��ͻ��˷�������Ϣ" + msg);
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		new BIOServer(8080).listener();
	}
	
}
