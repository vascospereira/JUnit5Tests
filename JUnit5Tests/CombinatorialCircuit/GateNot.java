package circuits;

public class GateNot extends LogicGate {

	public GateNot(LogicVariable output, LogicVariable input) throws ColisionException, CycleException {
		super(output, input);
	}

	@Override
	public String getSymbol() {
		return "NOT";
	}

	@Override
	public boolean getValue() {
		return !inputs[0].getValue();
	}

}
