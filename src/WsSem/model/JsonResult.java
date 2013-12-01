package WsSem.model;

import java.util.List;

public class JsonResult {

	private String status;
	private String result;
	private String aa;
	private List<?> binding;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<?> getBinding() {
		return binding;
	}
	public void setBinding(List<?> binding) {
		this.binding = binding;
	}
	
	
}
