package ua.od.zakhariya.patterns.factory_method;

import ua.od.zakhariya.patterns.factory_method.impl.ModuleSuperClass;

public class MainClass {
	

	public static void main(String... args) {
		
		ModuleInterface inter = new ModuleSuperClass();
		
		Object o1 = inter.getEntity("some");
		
		Object o2 = inter.getEntity("another");
		
		System.out.println(o1.toString());
		System.out.println(o2.toString());
		
	}
}
