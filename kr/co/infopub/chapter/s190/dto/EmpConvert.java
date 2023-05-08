package kr.co.infopub.chapter.s190.dto;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmpConvert {
	
	public static EmployeeDto toDto(Employee b) {
		EmployeeDto bp = new EmployeeDto();
		bp.setCommission_pct(b.getCommissionPct());
		bp.setDepartment_id(b.getDepartmentId());
		bp.setEmail(b.getEmail());
		bp.setEmployee_id(b.getEmployeeId());
		bp.setFirst_name(b.getFirstName());
		bp.setHire_date((java.sql.Date)b.getHireDate());
		bp.setJob_id(b.getJobId());
		bp.setLast_name(b.getLastName());
		bp.setManager_id(b.getManagerId());
		bp.setOrder2(b.getOrder2());
		bp.setPhone_number(b.getPhoneNumber());
		bp.setSalary(b.getSalary());
		return bp;
	}
	public static Employee toPro(EmployeeDto b) {
		Employee bp = new Employee();
		bp.setCommissionPct(b.getCommission_pct());
		bp.setDepartmentId(b.getDepartment_id());
		bp.setEmail(b.getEmail());
		bp.setEmployeeId(b.getEmployee_id());
		bp.setFirstName(b.getFirst_name());
		bp.setHireDate(b.getHire_date());
		bp.setJobId(b.getJob_id());
		bp.setLastName(b.getLast_name());
		bp.setManagerId(b.getManager_id());
		bp.setOrder2(b.getOrder2());
		bp.setPhoneNumber(b.getPhone_number());
		bp.setSalary(b.getSalary());
		return bp;
	}
	
	public static List<Employee> toPro(List<EmployeeDto> blist){
		List<Employee> bplists = new ArrayList<>();
		for(EmployeeDto b: blist) {
			bplists.add(toPro(b));
		}
		return bplists;
	}
	public static ObservableList<Employee> toObservPro(List<Employee> alists){
		ObservableList<Employee> bList = FXCollections.observableArrayList(alists);
		return bList;
	}
	
	public static ObservableList<Employee> toObservProFromDto(List<EmployeeDto> alists){
		return toObservPro(toPro(alists));
	}
	
	public static ObservableList<String> strList(List<String> alists){
		ObservableList<String> bList = FXCollections.observableArrayList(alists);
		return bList;
	}
}
