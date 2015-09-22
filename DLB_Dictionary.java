import java.io.File;
import java.util.Scanner;

public class DLB_Dictionary implements DictionaryInterface {

	private class Node {
		private char data;
		private Node nextRow;
		private Node sideNode;

		private Node(char letter) {
			data = letter;
			nextRow = null;
			sideNode = null;
		}

		private Node() {
			data = '\0';
			nextRow = null;
			sideNode = null;
		}
	}

	Node firstNode = new Node();
	Node currentNode = firstNode;

	public boolean add(String s) {
		s.toUpperCase();
		char[] chars = s.toCharArray();
		if (firstNode.data == '\0') {
			for (int i = 0; i < chars.length; i++) {
				currentNode.data = chars[i];
				currentNode.nextRow = new Node();
				currentNode = currentNode.nextRow;
			}
		}
		placeWord(chars, firstNode);
		return true;
	}

	private void placeWord(char[] chars, Node currentNode) {
		int end = 1;
		boolean stop = false;
		for (int i = 0; i < chars.length; i++) {
			while (stop == false) {

				if (currentNode.data != chars[i]
						&& currentNode.sideNode != null) { // if i can shift
															// to another
															// node
					currentNode = currentNode.sideNode;
				} else if (currentNode.data != chars[i]) { // if i cant
															// shift to
															// another
					end = 0;
				} else if (currentNode.data == chars[i]
						&& i == chars.length - 1
						&& currentNode.nextRow.data != '\0') {
					currentNode = currentNode.nextRow;
					currentNode.sideNode = new Node();
				} else if (currentNode.data == chars[i]) {
					currentNode = currentNode.nextRow; // found same char
					//System.out.println("Hit");
					break;
				} else {
					System.out.println("Something Wrong");
				}
				if (end == 0) {
					currentNode.sideNode = new Node(chars[i]);
					currentNode = currentNode.sideNode;
					stop = finishWord(currentNode, i, chars);
					//System.out.println("New Word");
				}
			}
		}
	}

	private boolean finishWord(Node currentNode, int i, char[] chars) {
		for (; i < chars.length; i++) {
			currentNode.data = chars[i];
			currentNode.nextRow = new Node();
			currentNode = currentNode.nextRow;

		}
		//System.out.println("Finished");
		return true;
	}

	public int search(StringBuilder s) {
		currentNode = firstNode;
		for(int i=0;i<s.length();i++){
			while(currentNode.data!=s.charAt(i) && currentNode.sideNode!=null){
				currentNode=currentNode.sideNode;
			}
			if(currentNode.data!=s.charAt(i)){
				return 0;
			}
			currentNode=currentNode.nextRow;
		}
		if(currentNode.data=='\0' && currentNode.sideNode!=null){
			return 3; // Both prefix and Word
		} else if(currentNode.data!='\0'){
			return 1; // Prefix
		} else if(currentNode.data=='\0'){
			return 2; // Word
		}

		return 0;
	}
}
