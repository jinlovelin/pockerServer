import java.io.IOException;
import java.util.HashMap;

public class Script {
	public Script(){
		
	}
	/**
	 * 登录
	 * type=login&name=xxx
	 */
	public static void login(HashMap<String,String> hashScript,SampleSocket socket){
		String name = hashScript.get("name");
		if(name == null || name.length() == 0){
			socket.sendToSelf("result=error&error=姓名不能为空");
			return;
		}
		while(SampleSocket.socketList.get(name) != null){
			name = hashScript.get("name")+(int)(Math.random()*10000);
		}
		
		SampleSocket.socketList.put(name,socket);
		User user = new User();
		user.setName(name);
		socket.setUser(user);
		String ulist = socket.getUserList();
		socket.sendToSelf("result=loginok&name="+name);
		socket.sendToAll("result=setuserlist&list="+ulist);
		socket.sendToAll("result=talk&msg=用户【"+name+"】进入");
	}
	/**
	 * 发言
	 * type=talk&target=xxx&msg=xxx
	 */
	public static void talk(HashMap<String,String> hashScript,SampleSocket socket){
		String target = hashScript.get("target");
		String msg = hashScript.get("msg");
		if(target == null)return;
		if(target.equals("all")){
			socket.sendToAll("result=talk&msg=用户【"+socket.getUser().getName()+"】:"+msg);
		}else{
			socket.sendToName(target, "result=talk&msg=用户【"+socket.getUser().getName()+"】:"+msg);
		}
	}
	
	public static void abandon(HashMap<String,String> hashScript,SampleSocket socket){
		
			socket.sendToAll("result=talk&msg=用户【"+socket.getUser().getName()+"】这个二货已经把牌扔掉了！");
		
	}
	public static void follow(HashMap<String,String> hashScript,SampleSocket socket){
		
		socket.sendToAll("result=talk&msg=用户【"+socket.getUser().getName()+"】这个二货已经把分加到了20！");
	
}
	
	/**
	 * 看牌
	 * type=look
	 */
	public static void look(HashMap<String,String> hashScript,SampleSocket socket){
//		String target = hashScript.get("target");
//		String msg = hashScript.get("msg");
//		if(target == null)return;
//		if(target.equals("all")){
//			socket.sendToAll("result=talk&msg=你是王炸");
	/*	Player[] players = {new Player(socket.getUserList())
		};
				
		//2.创建一个新的扑克游戏（利用这四个准备好的玩家）
		PokeGame game = new PokeGame(players);

		//3.游戏自动给每个玩家发三张纸牌
		game.autoDispatchCard();

		//4.游戏判断哪个玩家赢
		Player winner = game.getWinner();

		//5.输出每个玩家的信息和胜利者的信息
		System.out.println("======扑克游戏========");
		game.showAllPlayersInfo(); 
		System.out.println(winner.getName()+"赢啦"); 
		game.showAllPlayersMoney();
		System.out.println("======扑克游戏========");	*/
		
		
		socket.sendToSelf("result=talk&msg=你是同花顺");
//		}else{
//			socket.sendToName(target, "result=talk&msg=用户【"+socket.getUser().getName()+"】:"+msg);
//			socket.sendToSelf("result=talK&msg=你的牌是王炸");
//		}
	
	}
	/**
	 * 加入坦克
	 * type=addTank&x=*&y=*&direction=**&color=**
	 */
	public static void addTank(HashMap<String,String> hashScript,SampleSocket socket){

    	for (SampleSocket socket1 : SampleSocket.connections_set) {
    		if(socket1.getUser().getName().equals(socket.getUser().getName()))continue;
			socket.sendToSelf("result=addTank&name="+socket1.getUser().getName()+
					"&x="+socket1.getUser().getX()+"&y="+socket1.getUser().getY()+
					"&direction=up&color="+socket1.getUser().getColor());
		}
    	socket.getUser().setX(Integer.parseInt(hashScript.get("x")));
    	socket.getUser().setY(Integer.parseInt(hashScript.get("y")));
    	socket.getUser().setColor(hashScript.get("color"));
		socket.sendToAll("result=addTank&name="+socket.getUser().getName()+
				"&x="+socket.getUser().getX()+"&y="+socket.getUser().getY()+
				"&direction="+hashScript.get("direction")+
				"&color="+socket.getUser().getColor());
	}
	public static void move(HashMap<String,String> hashScript,SampleSocket socket){
    	socket.getUser().setX(Integer.parseInt(hashScript.get("x")));
    	socket.getUser().setY(Integer.parseInt(hashScript.get("y")));
		socket.sendToAll("result=move&name="+socket.getUser().getName()+
				"&x="+socket.getUser().getX()+"&y="+socket.getUser().getY());
	}
	public static void shoot(HashMap<String,String> hashScript,SampleSocket socket){
		socket.sendToAll("result=shoot&name="+socket.getUser().getName()+
				"&direction="+hashScript.get("direction"));
	}
	public static void kill(HashMap<String,String> hashScript,SampleSocket socket){
		socket.sendToAll("result=kill&name="+socket.getUser().getName());
	}
}
