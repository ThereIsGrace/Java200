package kr.co.infopub.chapter.s189.dto;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DepCountConvert {
	
	public static DepCount toPro(DepCountDto b) {
		DepCount bp = new DepCount();
		bp.setCount(b.getCount());
		bp.setDepartment_id(b.getDepartment_id());
		bp.setDepartment_name(b.getDepartment_name());
		return bp;
	}
	public static List<DepCount> toPro(List<DepCountDto> blist){
		List<DepCount> bplists = new ArrayList<>();
		for(DepCountDto b:blist) {
			bplists.add(toPro(b));
		}
		return bplists;
	}
	public static ObservableList<DepCount> toObservPro(List<DepCount> alists){
		ObservableList<DepCount> bList = FXCollections.observableArrayList(alists);
		return bList;
	}
	public static ObservableList<DepCount> toObservProFromDto(List<DepCountDto> blist){
		return toObservPro(toPro(blist));
	}
}
