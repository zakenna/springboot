package ac.sw.mvcTest.repository;

import ac.sw.mvcTest.model.Member;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component //
public class MemberDAO {
     final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
     final String URL = "jdbc:mysql://localhost:3306/mydb";
     PreparedStatement psmt;
     Connection con = null;
     ResultSet rs;

     public void dbOpen() {
         try {
             Class.forName(JDBC_Driver);
             con = DriverManager.getConnection(URL, "root", "lcs395711@");
         } catch (Exception e) {
             System.out.println("DB 연결 실패");
             e.getStackTrace();
         }
     }

     public void addMember(String name, String email, String pwd, int age) {
         dbOpen();
         String Sql = "insert into member (name, email, pwd, age) values (?,?,?,?)";
         try {
             psmt = con.prepareStatement(Sql);
             psmt.setString(1, name);
             psmt.setString(2, email);
             psmt.setString(3, pwd);
             psmt.setInt(4, age);

             psmt.executeUpdate();
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }

     }

    public Member findMember(String email, String pwd) {
        dbOpen();
        String Sql = "select * from member where email=? and pwd=?";
        try {
            psmt = con.prepareStatement(Sql);
            psmt.setString(1, email);
            psmt.setString(2, pwd);

            rs = psmt.executeQuery();
            if(rs.next()) {
                System.out.println("환영합니다.");
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPwd(rs.getString("pwd"));
                member.setAge(rs.getInt("age"));
                return member;
            } else {
                System.out.println(" 다시 로긴하세요...");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Member findMemberById(int id) {
        dbOpen();
        String Sql = "select * from member where id = ?";
        try {
            psmt = con.prepareStatement(Sql);
            psmt.setInt(1, id);

            rs = psmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPwd(rs.getString("pwd"));
                member.setAge(rs.getInt("age"));
                return member;
            } else {
                System.out.println(" 다시 로긴하세요...");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateMember(Member member) {
        dbOpen();
        String Sql = "UPDATE member SET name=?, email=?, pwd=?, age=? where id = ?";
        try {
            psmt = con.prepareStatement(Sql);
            psmt.setString(1, member.getName());
            psmt.setString(2, member.getEmail());
            psmt.setString(3, member.getPwd());
            psmt.setInt(4, member.getAge());
            psmt.setInt(5, member.getId());
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  List<Member> getAllMember() {
        dbOpen();
        String Sql = "select * from member";
        List<Member> list = new ArrayList<>();

        try {
            psmt = con.prepareStatement(Sql);
            rs = psmt.executeQuery();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPwd(rs.getString("pwd"));
                member.setAge(rs.getInt("age"));
                list.add(member);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void deleteMember(int id) {
        dbOpen();
        String Sql = "delete from member where id=?";
        try {
            psmt = con.prepareStatement(Sql);
            psmt.setInt(1, id);
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
