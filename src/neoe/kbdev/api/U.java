package neoe.kbdev.api;

public class U {
 

	public static void raise_process_priority() {
		Kernel32 k32 = Kernel32.k32;
		boolean ok = k32.SetPriorityClass(k32.GetCurrentProcess(), k32.HIGH_PRIORITY_CLASS);
		System.out.println("raise_process_priority:" + ok);
	}
}
