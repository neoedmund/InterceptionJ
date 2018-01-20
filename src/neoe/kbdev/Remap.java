package neoe.kbdev;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

import neoe.kbdev.api.Interception;
import neoe.kbdev.api.Interception.InterceptionKeyStroke;
import neoe.kbdev.api.InterceptionFilterKeyState;
import neoe.kbdev.api.U;
import neoe.util.FileUtil;
import neoe.util.PyData;

public class Remap {

	public static void main(String[] args) throws Exception {

		Map keyMap = loadKeyMap(args[0]);
		System.out.println("load config:" + keyMap);
		U.raise_process_priority();

		Interception lib = Interception.interception;
		Callback cb1 = new Callback() {
			public int callback(int device) {
				int v = lib.interception_is_keyboard(device);
				System.out.println(device + ":" + v);
				return v;
			}
		};
		Pointer context = lib.interception_create_context();
		InterceptionKeyStroke stroke = new InterceptionKeyStroke();
		lib.interception_set_filter(context, cb1, (short) (InterceptionFilterKeyState.INTERCEPTION_FILTER_KEY_ALL));
		int device;
		while (lib.interception_receive(context, device = lib.interception_wait(context), stroke, 1) > 0) {
			System.out.printf("device:%d code:%d(0x%h)\n", device, stroke.code, stroke.code);
			Map deviceKeyMap = (Map) keyMap.get("" + device);
			if (deviceKeyMap != null) {
				Short re = (Short) deviceKeyMap.get("" + stroke.code);
				if (re != null) {
					System.out.printf("[dev %d] %d(0x%h) -> %d(0x%h)\n", device, stroke.code, stroke.code, re, re);
					stroke.code = re;
				}
			}
			lib.interception_send(context, device, stroke, 1);
		}
		// just close the program will be okay
		lib.interception_destroy_context(context);

		System.out.println("ok");

	}

	private static short toShort(Object v) {
		if (v instanceof Number) {
			return ((Number) v).shortValue();
		}
		return Short.parseShort(v.toString());
	}

	private static Map loadKeyMap(String fn) throws Exception {
		// key are all string type
		Map ret = new HashMap();
		Map m = (Map) PyData.parseAll(FileUtil.readString(new FileInputStream(fn), null));
		Map m2 = (Map) m.get("device");
		for (Object key : m2.keySet()) {
			ret.put(toStr2(key), normalizeMap((Map) m2.get(key)));
		}
		return ret;
	}

	private static Map normalizeMap(Map m) {
		Map ret = new HashMap();
		for (Object key : m.keySet()) {
			ret.put(toStr2(key), toShort(m.get(key)));
		}
		return ret;
	}

	private static String toStr2(Object v) {
		if (v instanceof String && v.toString().startsWith("0x")) {
			return "" + Integer.parseInt(v.toString().substring(2), 16);
		}
		return v.toString();
	}

}
