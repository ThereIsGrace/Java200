package kr.co.infopub.chapter.s189.dto;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmpConvert {

	public static Employee toPro(EmployeeDto b) {
		Employee bp = new Employee();
		bp.setCommissionPct(b.getCommission_pct());
		bp.setDepartmentId(b.getDepartment_id());
		bp.setEmail(b.getEmail());
		bp.setEmployee_id(b.getEmployee_id());
		bp.setFirstName(b.getFirst_name());
		bp.setLastName(b.getLast_name());
		bp.setHireDate(b.getHire_date());
		bp.setJobId(b.getJob_id());
		bp.setManagerId(b.getManager_id());
		bp.setOrder2(b.getOrder2());
		bp.setPhoneNumber(b.getPhone_number());
		bp.setSalary(b.getSalary());
		return bp;
	}
	
	public static List<Employee> toPro(List<EmployeeDto> blist){
		List<Employee> bplists = new ArrayList<>();
		for(EmployeeDto b:blist) {
			bplists.add(toPro(b));
		}
		return bplists;
	}
	
	public static ObservableList<Employee> toObservPro(List<Employee> alists){
		ObservableList<Employee> bList = FXCollections.observableArrayList(alists);
		return bList;
	}
	
	public static ObservableList<Employee> toObservProFromDto(List<EmployeeDto> blist){
		return toObservPro(toPro(blist));
	}

}
