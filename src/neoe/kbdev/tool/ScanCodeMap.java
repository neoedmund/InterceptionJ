package neoe.kbdev.tool;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import neoe.util.FileUtil;

public class ScanCodeMap {

	public ScanCodeMap() throws IOException {
		init();
	}

	Map m;

	private void init() throws IOException {
		m = new HashMap();
		String s = FileUtil.readString(new FileInputStream("scancode_map"), null);
		String[] lines = s.split("\\n");
		for (String line : lines) {
			String[] ss = line.split("\\t");
			for (int i = 0; i < ss.length / 2; i++) {
				add(ss[i * 2], ss[i * 2 + 1]);
			}
		}

	}

	private void add(String a, String b) {
		a = a.trim();
		b = b.trim();
		char c = 0;
		if (b.length() == 1) {
			c = b.charAt(0);
		} else {
			if (b.length() > 2 && b.charAt(1) == ' ') {
				c = b.charAt(0);
			}
		}
		if (c != 0) {
			char c1 = Character.toLowerCase(c);
			int v = Integer.parseInt(a, 16);
			if (m.containsKey(c1)) {
				System.out.printf("dup %s (%h with %h)\n", c1, v, m.get(c1));
			} else {
				System.out.printf("add %s:%s\n", c1, v);
				m.put(c1, v);
			}
		}
	}

	public void gen(String s1, String s2) {
		int len = s1.length();
		for (int i = 0; i < len; i++) {
			char c1 = s1.charAt(i);
			char c2 = s2.charAt(i);
			Integer v1 = (Integer) m.get(c1);
			Integer v2 = (Integer) m.get(c2);
			if (v1 == null || v2 == null) {
				System.out.printf("[unknow]%s->%s\n", c1, c2);
			} else {
				if (v1 - v2 == 0) {
					System.out.printf("/* %s:%s  %s -> %s */\n", v1, v2, c1, c2);
				} else {
					System.out.printf("%s:%s /* %s -> %s */\n", v1, v2, c1, c2);
				}
			}
		}

	}

}
