package ua.od.zakhariya.patterns.factory_method.impl;

import ua.od.zakhariya.patterns.factory_method.ModuleInterface;
import ua.od.zakhariya.patterns.factory_method.entity.AnotherEntity;
import ua.od.zakhariya.patterns.factory_method.entity.SomeEntity;

public class ModuleSuperClass implements ModuleInterface {

	@Override
	public Object getEntity(String type) {
		
		if(type.equalsIgnoreCase("some")) 
			return new SomeEntity();
		else if (type.equalsIgnoreCase("another"))
			return new AnotherEntity();
		
		return null;	
	}

}
