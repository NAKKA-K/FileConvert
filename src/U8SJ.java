import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

class U8SJ {
  public static void main(String[] args) {
    // String str = "aあ無良";
    // byte[] u8bytes = str.getBytes(StandardCharsets.UTF_8);
    byte[] u8bytes = {
      (byte)0x61, // a
      (byte)0xE3, (byte)0x81, (byte)0x82, // あ
      (byte)0xE3, (byte)0x81, (byte)0x84, // ①
    };
    final String enc = "";
    String str = new String(u8bytes, StandardCharsets.UTF_8);

    byte[] sjbytes = str.getBytes(Charset.forName("Shift_JIS"));
    byte[] isoToJIS = str.getBytes(Charset.forName("utf8"));
    byte[] utfToJIS = str.getBytes(Charset.forName("utf16"));
    System.out.print("String: ");
    System.out.println(str);

    System.out.print("utf8d");
    print(u8bytes);
    System.out.print("shift");
    print(sjbytes);
    System.out.print("utf8 ");
    print(isoToJIS);
    System.out.print("utf16");
    print(utfToJIS);


    try {
		System.out.println("sjbytes ：" + new String(sjbytes, "Shift-JIS"));
		System.out.println("isoToJIS ：" + new String(isoToJIS, "utf8"));
		System.out.println("utfToJIS ：" + new String(utfToJIS, "utf16"));
	} catch (UnsupportedEncodingException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}


  }

  public static void print(byte[] bytes){
	    for(byte b : bytes){
	    	System.out.print(b + " ");
	    }
	    System.out.println();
  }
}
