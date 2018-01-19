package neoe.kbdev.tool;

import java.io.FileInputStream;
import java.util.Map;

import neoe.util.FileUtil;
import neoe.util.PyData;

public class MapGen {

	public static void main(String[] args) throws  Exception {
		ScanCodeMap scm = new ScanCodeMap();
		Map m1  =(Map) PyData.parseAll(FileUtil.readString(new FileInputStream("dvorak_to_qwerty"), null));
		String s1 = (String) m1.get("q");
		String s2 = (String) m1.get("d");
		scm.gen(s1,s2);

	}

}
