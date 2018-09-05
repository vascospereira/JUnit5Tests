package circuits;

public class GateOr extends LogicGate {

	public GateOr(LogicVariable output, LogicVariable input1, LogicVariable input2) throws ColisionException, CycleException {
		super(output, input1, input2);
	}

	@Override
	public String getSymbol() {
		return "OR";
	}

	@Override
	public boolean getValue() {
		return inputs[0].getValue() || inputs[1].getValue();
	}

}
