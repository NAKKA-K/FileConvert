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
 * ./workspace�f�B���N�g�����̃t�@�C�����A�ύX�������R�[�h����ύX�敶���R�[�h�ɕϊ�����v���O����
 * �ύX�������R�[�h��������ꍇ�A�����������N�������ߒ���
 */
public class ChangeCode {
	static CodeData codeData = new CodeData();
	static ArrayList<String> listToFile = new ArrayList<String>();

	public static void main(String[] args) {
		System.out.println("���̃v���O�����́Aworkspace�f�B���N�g�����̃t�@�C�����A�ϊ����̕����R�[�h����ϊ���̕����R�[�h�֕ϊ����镨�ł��B");
		setAppMode(args);
		codeData.printStatus();

		System.out.println("�ϊ��J�n");
		doChange(codeData.getFileList());
		System.out.println("�ϊ�����");

		System.out.println("-------------------------------------����-----------------------------------");
	}

	//�����t���Ŏ��s���ꂽ�ꍇ�ƁA���s�t�@�C�������Ŏ��s���ꂽ�ꍇ�̏���
	public static void setAppMode(String[] args){
		//�����t�����s�ŁA��񂪐���ɓo�^����Ă���ꍇ
		if(args.length == 2){
			codeData.setFromCode(args[0]);
			codeData.setToCode(args[1]);
			return;
		}

		Scanner stdin = new Scanner(System.in);

		codeData.printStringCode(); //�����R�[�h�ꗗ��\��
		System.out.print("�ϊ����̕����R�[�h����͂��Ă�������:");
		codeData.setFromCode(stdin.nextLine());
		System.out.print("�ϊ���̕����R�[�h����͂��Ă�������:");
		codeData.setToCode(stdin.nextLine());
	}


	//�t�@�C���̓Ǐo���A�ϊ��A�����o�����ċA�I�ɂ��Ă����
	public static void doChange(File[] fileList){
		for(File file : fileList){
			//�f�B���N�g���Ȃ�ċA�I�ɌĂяo��
			if(file.isDirectory()){
				doChange(file.listFiles());
			}
			readFile(file);
			writeFile(file);
			listToFile.clear();
		}
	}

	//�t�@�C����ǂݏo���ϊ����āAlist�Ɉꎞ�ۑ�
	public static void readFile(File file){
		BufferedReader filein = null;
		System.out.println(file.getName());
		try{
			filein = new BufferedReader(new InputStreamReader(new FileInputStream(file), codeData.getFromCode())); //�ǂݍ��ރt�@�C���̕����R�[�h���w��

			String tmp;
			while((tmp = filein.readLine()) != null){
				listToFile.add(tmp); //�ۑ�
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

	//�ꎞ�ۑ����ꂽ��������t�@�C���ɏ����o��
	public static void writeFile(File file){
		PrintWriter fileout = null;
		try{
			//�w��R�[�h�Ńt�@�C���ɏo��
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
	 * �����R�[�h��t�@�C���Ȃǂ̏��𓝊�����N���X
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
			System.out.println("�����R�[�h:" + fromCode + "����A�����R�[�h:" + toCode + "�֕ϊ����܂��B");
			System.out.println("�Ώۂ̃f�B���N�g����" + dirPath + "�ł��B");
		}

		public void printStringCode(){
			System.out.println("�����R�[�h�ꗗ");
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

