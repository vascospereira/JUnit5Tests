package circuits;

public abstract class LogicGate {
	
	protected LogicVariable output;
	protected LogicVariable [] inputs;
	
	public LogicGate(LogicVariable output, LogicVariable ... inputs) throws ColisionException, CycleException {
		if (output.getCalculatedBy() != null)
			throw new ColisionException();
		this.output = output;
		this.inputs = inputs;
		for (LogicVariable input : inputs) {
			if(input.equals(output) || input.dependsOn(output))
				throw new CycleException();
		}
		output.setCalculatedBy(this);
	}

	public LogicVariable getOutput() {
		return output;
	}

	public LogicVariable [] getInputs() {
		return inputs;
	}

	public abstract String getSymbol();
	
	public abstract boolean getValue();

	public String getFormula() {
		StringBuilder sb = new StringBuilder();
		sb.append(getSymbol()).append("(");
		for (int i = 0; i < inputs.length; i++) {
			if(i > 0)
				sb.append(",");
			sb.append(inputs[i].getFormula());
		}
		sb.append(")");
		return sb.toString();
	}
	
	public boolean dependsOn(LogicVariable lv) {

		for (LogicVariable input : inputs) {
			if(input.equals(lv) || input.dependsOn(lv))
				return true;
		}
		return false;
		
	}

}
