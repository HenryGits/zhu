package vrbaidu.top.netty.chat.pojo;


import org.msgpack.annotation.Message;

/**
 * 自定义协议消息体
 * @author Tom
 *
 */
@Message
public class IMMessage{
	
	private String addr;		//IP地址及端口
	private String cmd;			//命令类型，如：[LOGIN]、[SYSTEM]等
	private long time;			//命令发送时间
	private String sender;		//命令发送人
	private String receiver;	//命令接收人
	private String content;		//消息内容
	private int online;			//当前在线人数
	
	public IMMessage(){}
	
	public IMMessage(String cmd,long time,int online,String content){
		this.cmd = cmd;
		this.time = time;
		this.online = online;
		this.content = content;
	}
	
	public IMMessage(String cmd,long time,String sender){
		this.cmd = cmd;
		this.time = time;
		this.sender = sender;
	}
	
	public IMMessage(String cmd,long time,String sender,String content){
		this.cmd = cmd;
		this.time = time;
		this.sender = sender;
		this.content = content;
	}
	
	
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}

	@Override
	public String toString() {
		return "IMMessage{" +
				"addr='" + addr + '\'' +
				", cmd='" + cmd + '\'' +
				", time=" + time +
				", sender='" + sender + '\'' +
				", receiver='" + receiver + '\'' +
				", content='" + content + '\'' +
				", online=" + online +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		IMMessage imMessage = (IMMessage) o;

		if (time != imMessage.time) return false;
		if (online != imMessage.online) return false;
		if (addr != null ? !addr.equals(imMessage.addr) : imMessage.addr != null) return false;
		if (cmd != null ? !cmd.equals(imMessage.cmd) : imMessage.cmd != null) return false;
		if (sender != null ? !sender.equals(imMessage.sender) : imMessage.sender != null) return false;
		if (receiver != null ? !receiver.equals(imMessage.receiver) : imMessage.receiver != null) return false;
		return content != null ? content.equals(imMessage.content) : imMessage.content == null;

	}

	@Override
	public int hashCode() {
		int result = addr != null ? addr.hashCode() : 0;
		result = 31 * result + (cmd != null ? cmd.hashCode() : 0);
		result = 31 * result + (int) (time ^ (time >>> 32));
		result = 31 * result + (sender != null ? sender.hashCode() : 0);
		result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
		result = 31 * result + (content != null ? content.hashCode() : 0);
		result = 31 * result + online;
		return result;
	}
}
