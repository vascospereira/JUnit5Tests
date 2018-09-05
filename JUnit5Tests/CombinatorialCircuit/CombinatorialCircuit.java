package circuits;

import java.util.HashMap;

public class CombinatorialCircuit {

	HashMap<String, LogicVariable> lv = new HashMap<>();
	public boolean addVariable(LogicVariable newLv) {
		if(lv.containsKey(newLv.getName()))
			return false;
		lv.put(newLv.getName(), newLv);
		return true;
	}

	public LogicVariable getVariableByName(String lvName) {
		return lv.get(lvName);
	}
	
}
