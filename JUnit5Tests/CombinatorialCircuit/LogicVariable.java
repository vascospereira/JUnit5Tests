package circuits;

public class LogicVariable {

	private String name;
	private Boolean value;
	private LogicGate calculatedBy;
	
	public LogicVariable(String name, boolean value) {
		this.name = name;
		this.value = value;
	}

	public LogicVariable(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean getValue() {
		return (calculatedBy == null) ? value : calculatedBy.getValue();
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (value ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogicVariable other = (LogicVariable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	public LogicGate getCalculatedBy() {
		return calculatedBy;
	}
	
	public void setCalculatedBy(LogicGate gate) {
		calculatedBy = gate;
	}

	public String getFormula() {
		return (calculatedBy == null) ? name : calculatedBy.getFormula();
	}

	public boolean dependsOn(LogicVariable lv) {
		return (calculatedBy == null) ? false : calculatedBy.dependsOn(lv);
	}

}
