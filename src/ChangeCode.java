import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author NAKKA-K
 * ./workspaceディレクトリ内のファイルを、変更元文字コードから変更先文字コードに変換するプログラム
 * 変更元文字コードが違った場合、文字化けを起こすため注意
 */
public class ChangeCode {
	static CodeData codeData = new CodeData();
	static ArrayList<String> listToFile = new ArrayList<String>();

	public static void main(String[] args) {
		System.out.println("このプログラムは、workspaceディレクトリ内のファイルを、変換元の文字コードから変換先の文字コードへ変換する物です。");
		setAppMode(args);
		codeData.printStatus();

		System.out.println("変換開始");
		doChange(codeData.getFileList());
		System.out.println("変換完了");

		System.out.println("-------------------------------------完了-----------------------------------");
	}

	//引数付きで実行された場合と、実行ファイルだけで実行された場合の処理
	public static void setAppMode(String[] args){
		//引数付き実行で、情報が正常に登録されている場合
		if(args.length == 2){
			codeData.setFromCode(args[0]);
			codeData.setToCode(args[1]);
			return;
		}

		Scanner stdin = new Scanner(System.in);

		codeData.printStringCode(); //文字コード一覧を表示
		System.out.print("変換元の文字コードを入力してください:");
		codeData.setFromCode(stdin.nextLine());
		System.out.print("変換先の文字コードを入力してください:");
		codeData.setToCode(stdin.nextLine());
	}


	//ファイルの読出し、変換、書き出しを再帰的にしてくれる
	public static void doChange(File[] fileList){
		for(File file : fileList){
			//ディレクトリなら再帰的に呼び出し
			if(file.isDirectory()){
				doChange(file.listFiles());
			}
			readFile(file);
			writeFile(file);
			listToFile.clear();
		}
	}

	//ファイルを読み出し変換して、listに一時保存
	public static void readFile(File file){
		BufferedReader filein = null;
		System.out.println(file.getName());
		try{
			filein = new BufferedReader(new InputStreamReader(new FileInputStream(file), codeData.getFromCode())); //読み込むファイルの文字コードを指定

			String tmp;
			while((tmp = filein.readLine()) != null){
				listToFile.add(tmp); //保存
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if(filein != null) filein.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	//一時保存された文字列をファイルに書き出し
	public static void writeFile(File file){
		PrintWriter fileout = null;
		try{
			//指定コードでファイルに出力
			fileout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), codeData.getToCode())));
			for(String str : listToFile){
				System.out.println(str);
				fileout.println(str);
			}
			fileout.flush();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(fileout != null) fileout.close();
		}
	}


	/**
	 * @author NAKKA-K
	 * 文字コードやファイルなどの情報を統括するクラス
	 */
	static class CodeData{
		static final String[] stringCode = {"UTF-8", "UTF-16", "SJIS"};
		String fromCode = "";
		String toCode = "";
		String dirPath = "./workspace/";
		File[] fileList;

		public File[] getFileList(){
			if(fileList == null){
				fileList = new File(dirPath).listFiles();
			}
			return fileList;
		}

		public void printStatus(){
			System.out.println("文字コード:" + fromCode + "から、文字コード:" + toCode + "へ変換します。");
			System.out.println("対象のディレクトリは" + dirPath + "です。");
		}

		public void printStringCode(){
			System.out.println("文字コード一覧");
			for(String code : stringCode){
				System.out.print("[" + code+ "] ");
			}
			System.out.println();
		}

		public String getFromCode(){
			return fromCode;
		}
		public void setFromCode(String from){
			fromCode = from;
		}

		public String getToCode(){
			return toCode;
		}
		public void setToCode(String to){
			toCode = to;
		}

		public String getDirPath(){
			return dirPath;
		}
	}
}

