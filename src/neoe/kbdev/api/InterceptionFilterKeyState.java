package neoe.kbdev.api;

public interface InterceptionFilterKeyState {
	int INTERCEPTION_KEY_DOWN = 0x00;
	int INTERCEPTION_KEY_UP = 0x01;
	int INTERCEPTION_KEY_E0 = 0x02;
	int INTERCEPTION_KEY_E1 = 0x04;
	int INTERCEPTION_KEY_TERMSRV_SET_LED = 0x08;
	int INTERCEPTION_KEY_TERMSRV_SHADOW = 0x10;
	int INTERCEPTION_KEY_TERMSRV_VKPACKET = 0x20;
	int INTERCEPTION_FILTER_KEY_NONE = 0x0000;
	int INTERCEPTION_FILTER_KEY_ALL = 0xFFFF;
	int INTERCEPTION_FILTER_KEY_DOWN = INTERCEPTION_KEY_UP;
	int INTERCEPTION_FILTER_KEY_UP = INTERCEPTION_KEY_UP << 1;
	int INTERCEPTION_FILTER_KEY_E0 = INTERCEPTION_KEY_E0 << 1;
	int INTERCEPTION_FILTER_KEY_E1 = INTERCEPTION_KEY_E1 << 1;
	int INTERCEPTION_FILTER_KEY_TERMSRV_SET_LED = INTERCEPTION_KEY_TERMSRV_SET_LED << 1;
	int INTERCEPTION_FILTER_KEY_TERMSRV_SHADOW = INTERCEPTION_KEY_TERMSRV_SHADOW << 1;
	int INTERCEPTION_FILTER_KEY_TERMSRV_VKPACKET = INTERCEPTION_KEY_TERMSRV_VKPACKET << 1;
}
