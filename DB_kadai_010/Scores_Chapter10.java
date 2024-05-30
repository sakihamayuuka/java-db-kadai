package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Scores_Chapter10 {
    public static void main(String[] args) {

        Connection con = null;
        Statement statement = null;
        Scanner scanner = null;

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/challenge_java", 
                    "root",
                    "0608Yuuka"
            );

            System.out.println("データベース接続成功: " + con);

            statement = con.createStatement();
            String updateSql = "UPDATE scores SET score_english = 80, score_math = 95 WHERE id = 5;";
            System.out.println("レコード更新を実行します");
            int rowCnt = statement.executeUpdate(updateSql);
            System.out.println(rowCnt + "件のレコードが更新されました");

            String selectSql = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC;";
            System.out.println("数学・英語の点数が高い順に並べ替えました");
            ResultSet result = statement.executeQuery(selectSql);

            // SQLクエリの実行結果を抽出
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int score_math = result.getInt("score_math");
                int score_english = result.getInt("score_english");
                System.out.println(result.getRow() + "件目:生徒id=" + id + "/name=" + name + "/数学=" + score_math + "/英語=" + score_english);
            }
        } catch (SQLException e) {
            System.out.println("エラー発生: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("入力が正しくありません");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }
}
