package circuits;

public class GateAnd extends LogicGate {

	public GateAnd(LogicVariable output, LogicVariable input1, LogicVariable input2) throws ColisionException, CycleException {
		super(output, input1, input2);
	}

	@Override
	public String getSymbol() {
		return "AND";
	}

	@Override
	public boolean getValue() {
		return inputs[0].getValue() && inputs[1].getValue();
	}

}
