package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
	public static void main(String[] args) {
		
Connection con = null;
Statement statement = null;
	
try {
	
	con = DriverManager.getConnection(
			"jdbc:mysql://localhost/challenge_java",
			"root",
			"0608Yuuka"
			);
	
	System.out.println("データベース接続成功：" + con );
	
	//ステートメント生成
	statement = con.createStatement();
	
	
	//テーブル作成
	String sql = """
			CREATE TABLE posts (
			post_id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
			user_id INT(11) NOT NULL,
			posted_at DATE NOT NULL,
			post_content INT(255) NOT NULL,
			likes INT(11) DEFAULT 0
			""";
	
	
    // レコード追加

    String insertSQL = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?)";
    PreparedStatement preparedStatement = con.prepareStatement(insertSQL);

	String[][] postsList = {
			{"1003","2023-02-08","昨日の夜は徹夜でした・・","13"},
			{"1002","2023-02-08","お疲れ様です！","12"},
			{"1003","2023-02-09","今日も頑張ります！","18"},
			{"1001","2023-02-09","無理は禁物ですよ！","17"},
			{"1002","2023-02-10","明日から連休ですね！","20"}
	};
	

    for (String[] post : postsList) {
        preparedStatement.setInt(1, Integer.parseInt(post[0]));
        preparedStatement.setString(2, post[1]);
        preparedStatement.setString(3, post[2]);
        preparedStatement.setInt(4, Integer.parseInt(post[3]));
        preparedStatement.executeUpdate();

    }
				
    System.out.println("レコード追加を実行します");
	System.out.println(postsList.length + "件のレコードが追加されました");
		
		
		
		//検索
    statement = con.createStatement();

    String sql1 = "SELECT post_id, posted_at, post_content, likes FROM posts WHERE user_id = 1002";

    ResultSet result = statement.executeQuery(sql1);
	System.out.println("ユーザーIDが1002のレコードを検索しました");
		
//	int recordCount =1;
	while(result.next()) {
		String posted_at = result.getString("posted_at");
		String post_content = result.getString("post_content") ;
		int likes = result.getInt("likes");
			
		System.out.println(result.getRow() + "件目:投稿日時="+posted_at 
			+"/投稿内容=" + post_content +"いいね数=" + likes );
	}
	
	} catch(SQLException e) {
        System.out.println("エラー発生：" + e.getMessage());
    } finally {
            // 使用したオブジェクトを解放
        if( statement != null ) {
        try { statement.close(); } catch(SQLException ignore) {}
            }
        if( con != null ) {
        	try { con.close(); } catch(SQLException ignore) {}
            }
			}
	}
}