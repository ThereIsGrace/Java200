package kr.co.infopub.chapter.s184.test;

import java.sql.SQLException;
import kr.co.infopub.chapter.s184.model.EmployeeDAO;
// getTreeMaxLevel 계층형 쿼리의 가장 큰 레벨
public class EmployeeTest3 {
	
	public static void main(String[] args) {
		EmployeeDAO employeeDAO = new EmployeeDAO();
		int max = -1;
		try {
			max = employeeDAO.getTreeMaxLevel();
			System.out.println(max);
		} catch(SQLException e) {
			System.out.println(e);
		}
	}
}
