/*
	public class Test {
		private char data;
		private Node nextRow;
		private Node sideNode;

		public Test(char letter) {
			data = letter;
			nextRow = null;
			sideNode = null;
		}

		public Test() {
			data = '\0';
			nextRow = null;
			sideNode = null;
		}
	}

	public static void main(String[] args) {

		String test = "hello";
		char[] chars = test.toCharArray();
		Node firstNode = new Node();
		Node currentNode = firstNode;
		int end = 1;
		for (int i = 0; i < chars.length; i++) {
			while (end > 0) {
				if (firstNode.data == '\0') {
					firstNode.data = chars[i];
					firstNode.nextRow = new Node();
					currentNode = firstNode;

				} else {
					if (currentNode.data != chars[i]
							&& currentNode.sideNode != null) { // if i can shift
																// to another
																// node
						currentNode = currentNode.sideNode;
					} else if (currentNode.data != chars[i]) { // if i cant
																// shift to
																// another
						end = 0;
					} else if (currentNode.data == chars[i]) {
						currentNode = currentNode.nextRow; // found same char
						break;
					} else {
						System.out.println("Something Wrong");
					}
					if (end == 0) {
						currentNode.sideNode = new Node(chars[i]);
					}
				}
			}
		}

	}
}
*/