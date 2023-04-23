package kr.co.infopub.chapter.s184.test;

import java.sql.SQLException;
import java.util.List;

import kr.co.infopub.chapter.s184.dto.EmployeeDto;
import kr.co.infopub.chapter.s184.model.EmployeeDAO;

public class EmployeeTest2 {
	public static void main(String[] args) {
		EmployeeDAO ddao = new EmployeeDAO();
		try {
			List<EmployeeDto> lists = ddao.findTreeManagerInEmployee();
			for(EmployeeDto dd: lists) {
				System.out.print(dd.getOrder2());
				System.out.println("\t\t"+dd.getFirstName()+" "+dd.getLastName());
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
	}
}
