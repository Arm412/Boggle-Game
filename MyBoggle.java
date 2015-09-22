import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class MyBoggle {

	public static void main(String[] args) {
		Scanner dicScan = null;
		ArrayList<String>boggleWords=new ArrayList<String>();
		try {
			dicScan = new Scanner(new FileInputStream("dictionary.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("You did something wrong. Get the dictionary file.");
			e.printStackTrace();
			System.exit(1);
		}
		String holdWord=null;
		DictionaryInterface dlbDiction = new DLB_Dictionary();
		DictionaryInterface simpleDiction = new SimpleDictionary();
		while (dicScan.hasNext())
		{
			holdWord = dicScan.nextLine();
			holdWord=holdWord.toUpperCase();
			simpleDiction.add(holdWord);
			dlbDiction.add(holdWord);
		}
		File board=null;
		DictionaryInterface boggle;
		String firstArg=args[1].substring(0,1);
		String secondArg=args[3].substring(0,1);
		if(firstArg.equals("b")){
			board=new File(args[1]);
			System.out.println("This is Boggle");
			makeBoard(board);
		} else if(secondArg.equals("b")){
			board=new File(args[3]);
			System.out.println("This is Boggle");
			makeBoard(board);
		} 
		if(secondArg.equals("s")){
			boggle=searchBoggle(board, simpleDiction,boggleWords);
			playBoggle(boggle,boggleWords);
		} else if(firstArg.equals("s")){
			boggle=searchBoggle(board, simpleDiction,boggleWords);
			playBoggle(boggle,boggleWords);
		} else if(secondArg.equals("d")){
			boggle=searchBoggle(board, dlbDiction,boggleWords);
			playBoggle(boggle,boggleWords);
		} else if(firstArg.equals("d")){
			boggle=searchBoggle(board, dlbDiction,boggleWords);
			playBoggle(boggle,boggleWords);
		}
	}
	private static void makeBoard(File board) {//TODO
		try {
			System.out.println("| | | | |\n|---|---|---|---|");
			Scanner in= new Scanner(board);
			char[] boardChars=null;
			while(in.hasNext()){
				boardChars=in.nextLine().toCharArray();
			}
			if(boardChars==null){
				System.out.println("This board is empty.");
			}
			int i=0;
			while(i<boardChars.length){
				System.out.println("|"+boardChars[i]+"|"+boardChars[i+1]+"|"+boardChars[i+2]+"|"+boardChars[i+3]+"|");
				i=i+4;
			}
		} catch (FileNotFoundException e) {
			System.out.println("You messed up");
			
			e.printStackTrace();
			System.exit(1);
		}
	}
	private static DictionaryInterface searchBoggle(File board,DictionaryInterface diction, ArrayList<String>boggleWords){ //TODO
		Scanner in = null;
		try {
			in = new Scanner(board);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		char[] boardChars=null;
		while(in.hasNext()){
			boardChars=in.nextLine().toCharArray();
		}
		//boardChars="abcdefghijklmnop".toCharArray();
		for(int i=0;i<16;i++){
			String madeWord="";
			boolean[] checked= new boolean[16];
			if(boardChars[i]=='*'){
				dealWithAsterisk(boardChars,checked,i,madeWord,diction,boggleWords);
			}
			boggleWords=backtracking(boardChars,checked,i,madeWord, diction,boggleWords);
			
		}
		DictionaryInterface bogDLB=new DLB_Dictionary();
		for(int i=0;i<boggleWords.size();i++){
			bogDLB.add(boggleWords.get(i));
		}
		Collections.sort(boggleWords);
		return bogDLB;
	}
	
	private static ArrayList<String> preBacktracking(char[] boardChars, boolean[] checked, int i, String word,DictionaryInterface diction,ArrayList<String>boggleWords){
		if(boardChars[i]=='*'){
			dealWithAsterisk(boardChars,checked,i,word,diction,boggleWords);
			return boggleWords;
		}
		backtracking(boardChars,checked,i,word,diction,boggleWords);
		return boggleWords;
	}

	
	private static ArrayList<String> backtracking(char[] boardChars, boolean[] checked, int i, String word,DictionaryInterface diction,ArrayList<String>boggleWords){
		String beforeChange=word;
		word=word+boardChars[i];
		StringBuilder sb= new StringBuilder(word);
		checked[i]=true;
		if(word.length()!=0){
			int found=0;
			boolean dup=false;
			found=diction.search(sb);
			if(found==2 || found==3){
				for(int j=0;j<boggleWords.size();j++){
					if(boggleWords.get(j).equals(word)){
						dup=true;
					}
				}
				if(dup==false){
					boggleWords.add(word);
				}
			} else if(found==0){
				word=beforeChange;
				return boggleWords;
			}
		}
		if(i%4==1 || i%4==2){
			if((i-4)>=0 && checked[i-4]!=true){
				preBacktracking(boardChars,checked,i-4,word,diction,boggleWords);
				checked[i-4]=false;
			}
			if((i-3)>=0 && checked[i-3]!=true){
				preBacktracking(boardChars,checked,i-3,word,diction,boggleWords);
				checked[i-3]=false;
			}
			if((i+1)<16 && checked[i+1]!=true){
				preBacktracking(boardChars,checked,i+1,word,diction,boggleWords);
				checked[i+1]=false;
			}
			if((i+5)<16 && checked[i+5]!=true){
				preBacktracking(boardChars,checked,i+5,word,diction,boggleWords);
				checked[i+5]=false;
			}
			if((i+4)<16 && checked[i+4]!=true){
				preBacktracking(boardChars,checked,i+4,word,diction,boggleWords);
				checked[i+4]=false;
			}
			if((i+3)<16 && checked[i+3]!=true){
				preBacktracking(boardChars,checked,i+3,word,diction,boggleWords);
				checked[i+3]=false;
			}
			if((i-1)>=0 && checked[i-1]!=true){
				preBacktracking(boardChars,checked,i-1,word,diction,boggleWords);
				checked[i-1]=false;
			}
			if((i-5)>=0 && checked[i-5]!=true){
				preBacktracking(boardChars,checked,i-5,word,diction,boggleWords);
				checked[i-5]=false;
			}
		}else if(i%4==0){
			if((i-4)>=0 && checked[i-4]!=true){
				preBacktracking(boardChars,checked,i-4,word,diction,boggleWords);
				checked[i-4]=false;
			}
			if((i-3)>=0 && checked[i-3]!=true){
				preBacktracking(boardChars,checked,i-3,word,diction,boggleWords);
				checked[i-3]=false;
			}
			if((i+1)<16 && checked[i+1]!=true){
				preBacktracking(boardChars,checked,i+1,word,diction,boggleWords);
				checked[i+1]=false;
			}
			if((i+5)<16 && checked[i+5]!=true){
				preBacktracking(boardChars,checked,i+5,word,diction,boggleWords);
				checked[i+5]=false;
			}
			if((i+4)<16 && checked[i+4]!=true){
				preBacktracking(boardChars,checked,i+4,word,diction,boggleWords);
				checked[i+4]=false;
			}
		} else if(i%4==3){
			if((i-4)>=0 && checked[i-4]!=true){
				preBacktracking(boardChars,checked,i-4,word,diction,boggleWords);
				checked[i-4]=false;
			}
			if((i-5)>=0 && checked[i-5]!=true){
				preBacktracking(boardChars,checked,i-5,word,diction,boggleWords);
				checked[i-5]=false;
			}
			if((i-1)>=0 && checked[i-1]!=true){
				preBacktracking(boardChars,checked,i-1,word,diction,boggleWords);
				checked[i-1]=false;
			}
			if((i+3)<16 && checked[i+3]!=true){
				preBacktracking(boardChars,checked,i+3,word,diction,boggleWords);
				checked[i+3]=false;
			}
			if((i+4)<16 && checked[i+4]!=true){
				preBacktracking(boardChars,checked,i+4,word,diction,boggleWords);
				checked[i+4]=false;
			}
		}
		
		return boggleWords;
	}
	
	private static ArrayList<String> dealWithAsterisk(char[] boardChars, boolean[] checked, int i, String word,DictionaryInterface diction,ArrayList<String>boggleWords){
		char[] alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		for(int j=0;j<alphabet.length;j++){
			boardChars[i]=alphabet[j];
			backtracking(boardChars,checked,i,word,diction,boggleWords);
		}
		boardChars[i]='*';
		return boggleWords;
	}
	
	private static void playBoggle(DictionaryInterface dlbBoggle,ArrayList<String>boggleWords) { 
		Scanner in = new Scanner(System.in);
		boolean exit=false;
		boolean repeat=false;
		int wordsCorrect=0;
		ArrayList<String>guesses=new ArrayList<String>();
		while(exit==false){
			System.out.print("Type a word that's on the board. Enter '\\0' to exit: ");
			String input=in.nextLine();
			if(input.equals("\\0")){
				break;
			}
			input=input.toUpperCase();
			for(int i=0;i<guesses.size();i++){
				if(guesses.get(i).equals(input)){
					repeat=true;
				}
			}
			int wordPrefix=0;
			StringBuilder word=new StringBuilder(input);
			wordPrefix=dlbBoggle.search(word);
			if(wordPrefix==3 && repeat==false){
				System.out.println(word + " is a Word and a Prefix.");
				wordsCorrect++;
				guesses.add(input);
			} else if(wordPrefix==2 && repeat==false){
				System.out.println(word + " is a Word.");
				wordsCorrect++;
				guesses.add(input);
			} else if(wordPrefix==1){
				System.out.println(word + " is a Prefix.");
			} else if(wordPrefix==0){
				System.out.println(word + " is not a Word.");
			} else if(repeat==true){
				System.out.println("You guessed this word already.");
			}
		}
		System.out.println("");
		System.out.println("All possible words that could have be made:");
		for(int i=0;i<boggleWords.size();i++){
			System.out.println(boggleWords.get(i));
		}
		System.out.println("You guessed these words correctly:");
		for(int i=0;i<guesses.size();i++){
			System.out.println(guesses.get(i));
		}
		System.out.println("You guessed "+wordsCorrect+" words correctly.");
		System.out.println("There were "+boggleWords.size()+" possible words.");
		double percentage=(double)wordsCorrect/(double)boggleWords.size()*100;
		System.out.println("You found %"+percentage+" of the words.");
		
	}
}



