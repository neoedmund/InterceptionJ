package neoe.kbdev;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

import neoe.kbdev.api.Interception;
import neoe.kbdev.api.Interception.InterceptionKeyStroke;
import neoe.kbdev.api.InterceptionFilterKeyState;
import neoe.kbdev.api.U;

public class X2Y {

	public static void main(String[] args) {

		U.raise_process_priority();

		int SCANCODE_X = 0x2D;
		int SCANCODE_Y = 0x15;
		int SCANCODE_ESC = 0x01;

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
		lib.interception_set_filter(context, cb1, (short) (InterceptionFilterKeyState.INTERCEPTION_FILTER_KEY_DOWN
				| InterceptionFilterKeyState.INTERCEPTION_FILTER_KEY_UP));
		int device;
		while (lib.interception_receive(context, device = lib.interception_wait(context), stroke, 1) > 0) {
			System.out.printf("device:%d code:%d(0x%h)\n", device, stroke.code, stroke.code);
			if (stroke.code == SCANCODE_X) {
				stroke.code = (short) SCANCODE_Y;
			}
			if (stroke.code == SCANCODE_ESC) {
				break;
			}
			lib.interception_send(context, device, stroke, 1);
		}

		lib.interception_destroy_context(context);

		System.out.println("ok");

	}

}
