package frame;
/**
 * B_TreeTestCase
 *
 * Version:	1.0
 *
 *
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import lab.B_Tree;

/**
 * TestCases for the forth lab.
 *
 */
public class AllTests {

	private Duration timeout = Duration.ofMillis(1500);

	ArrayList<TestNode> nodes = new ArrayList<TestNode>();
	ArrayList<String> node_names = new ArrayList<String>();
	ArrayList<String> pointers = new ArrayList<String>();

	@Test
	public void testInsertFile1_NumberOfNodes() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			ArrayList<String> out = b.getB_Tree();
			ArrayList<TestNode> test_tree = this.constractB_Tree(out);
			assertEquals(14, test_tree.size(), "Number of nodes not correct!");
		});
	}

	@Test
	public void testInsertFile1_HeightOfTree() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
			assertEquals(2, b.getB_TreeHeight(), "Height of the tree not correct!");
		});
	}

	@Test
	public void testInsertFile1_NumberOfEntries() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
			assertEquals(22, b.getB_TreeSize(), "Number of the entries is not correct!");
		});
	}

	@Test
	public void testInsertFile1_NodesOfTree() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
		});
		testNode("root", nodes.get(0), 3, 4, "FMF1QTZ0QKC83OJVA8SSG24YXB1");
		testNode("node" + 1, nodes.get(1), 2, 3, "4OVZBRS9FDP56R7OTD");
		testNode("node" + 2, nodes.get(2), 2, 0, "14ST01GLP319BCWVH9");
		testNode("node" + 3, nodes.get(3), 2, 0, "8ENSQEGV99BTCH8AHW");
		testNode("node" + 4, nodes.get(4), 1, 0, "E465LZPOE");
		testNode("node" + 5, nodes.get(5), 1, 2, "IUS3K395W");
		testNode("node" + 6, nodes.get(6), 2, 0, "GFV8X4TT0HRR27B6JY");
		testNode("node" + 7, nodes.get(7), 1, 0, "JZXHZFCB6");
		testNode("node" + 8, nodes.get(8), 1, 2, "NXM6F6UWJ");
		testNode("node" + 9, nodes.get(9), 1, 0, "L2Z7499YH");
		testNode("node" + 10, nodes.get(10), 1, 0, "OTYAH43JX");
		testNode("node" + 11, nodes.get(11), 1, 2, "W0KBB1RE7");
		testNode("node" + 12, nodes.get(12), 3, 0, "TY4L8P0D3U36TUQ5NOVM22T8ESM");
		testNode("node" + 13, nodes.get(13), 1, 0, "YMUEM9LVC");
	}

	@Test
	public void testInsertFile1_InOrderTraversal() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
			assertTrue(testOrderOfEntries(b.getInorderTraversal()),
					"getInorderTraversal() doesn't deliver entries in inorder traversal!");
		});
	}

	@Test
	public void testDeleteFile1_ReturnedEntries() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			Entry e1 = b.delete("L2Z7499YH");
			Entry e2 = b.delete("FMF1QTZ0Q");
			Entry e3 = b.delete("L2Z74TZ0Q");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
			assertEquals("L2Z74;99YH;Error", e1.toString(), "delete(L2Z7499YH) output not correct!");
			assertEquals("FMF1Q;TZ0Q;Error", e2.toString(), "delete(FMF1QTZ0Q) output not correct!");
			assertTrue(null == e3, "delete(L2Z74TZ0Q) output not correct!");
		});
	}

	@Test
	public void testDeleteFile1_NumberOfNodes() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			b.delete("L2Z7499YH");
			b.delete("FMF1QTZ0Q");
			b.delete("L2Z74TZ0Q");
			ArrayList<String> out = b.getB_Tree();
			ArrayList<TestNode> test_tree = this.constractB_Tree(out);

			assertEquals(12, test_tree.size(), "Number of nodes not correct!");
		});
	}

	@Test
	public void testDeleteFile1_HeightOfTree() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			b.delete("L2Z7499YH");
			b.delete("FMF1QTZ0Q");
			b.delete("L2Z74TZ0Q");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
			assertEquals(2, b.getB_TreeHeight(), "Height of the tree not correct!");
		});
	}

	@Test
	public void testDeleteFile1_NumberOfEntries() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			b.delete("L2Z7499YH");
			b.delete("FMF1QTZ0Q");
			b.delete("L2Z74TZ0Q");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
			assertEquals(20, b.getB_TreeSize(), "Number of the entries is not correct!");
		});
	}

	@Test
	public void testDeleteFile1_NodesOfTree() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			b.delete("L2Z7499YH");
			b.delete("FMF1QTZ0Q");
			b.delete("L2Z74TZ0Q");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
		});
		testNode("root", nodes.get(0), 2, 3, "E465LZPOESSG24YXB1", 2, 3, "GFV8X4TT0SSG24YXB1");
		testNode("node" + 1, nodes.get(1), 2, 3, "4OVZBRS9F9BTCH8AHW", 2, 3, "4OVZBRS9FDP56R7OTD");
		testNode("node" + 2, nodes.get(2), 2, 0, "14ST01GLP319BCWVH9");
		testNode("node" + 3, nodes.get(3), 1, 0, "8ENSQEGV9", 2, 0, "8ENSQEGV99BTCH8AHW");
		testNode("node" + 4, nodes.get(4), 1, 0, "DP56R7OTD", 1, 0, "E465LZPOE");
		testNode("node" + 5, nodes.get(5), 2, 3, "IUS3K395WNXM6F6UWJ");
		testNode("node" + 6, nodes.get(6), 2, 0, "GFV8X4TT0HRR27B6JY", 1, 0, "HRR27B6JY");
		testNode("node" + 7, nodes.get(7), 2, 0, "JZXHZFCB6KC83OJVA8");
		testNode("node" + 8, nodes.get(8), 1, 0, "OTYAH43JX");
		testNode("node" + 9, nodes.get(9), 1, 2, "W0KBB1RE7");
		testNode("node" + 10, nodes.get(10), 3, 0, "TY4L8P0D3U36TUQ5NOVM22T8ESM");
		testNode("node" + 11, nodes.get(11), 1, 0, "YMUEM9LVC");
	}

	@Test
	public void testDeleteFile1_InOrderTraversal() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			b.delete("L2Z7499YH");
			b.delete("FMF1QTZ0Q");
			b.delete("L2Z74TZ0Q");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);

			assertTrue(testOrderOfEntries(b.getInorderTraversal()),
					"getInorderTraversal() doesn't deliver entries in inorder traversal!");
		});
	}

	@Test
	public void testfindFile1_ReturnedEntries() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			Entry e1 = b.find("SSG24YXB1");
			Entry e2 = b.find("SSG27YXB1");
			assertEquals("SSG24;YXB1;Error", e1.toString(), "find(SSG24YXB1) output not correct!");
			assertTrue(null == e2, "find(SSG27YXB1) output not correct!");
		});
	}

	@Test
	public void testInsertFile1_Balanced() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			ArrayList<String> out = b.getB_Tree();
			ArrayList<TestNode> test_tree = this.constractB_Tree(out);
			assertTrue(isBalanced(test_tree.get(0)), "The Tree is not balanced!");
		});
	}

	@Test
	public void testDeleteFile1_Balanced() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(2);
			b.constructB_TreeFromFile("TestFile1.txt");
			b.delete("L2Z7499YH");
			b.delete("FMF1QTZ0Q");
			b.delete("L2Z74TZ0Q");
			ArrayList<String> out = b.getB_Tree();
			ArrayList<TestNode> test_tree = this.constractB_Tree(out);
			assertTrue(isBalanced(test_tree.get(0)), "The Tree is not balanced!");
		});
	}

	///////// testFile2

	@Test
	public void testInsertFile2_NumberOfNodes() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			ArrayList<String> out = b.getB_Tree();
			ArrayList<TestNode> test_tree = this.constractB_Tree(out);
			assertEquals(15, test_tree.size(), "Number of nodes not correct!");
		});
	}

	@Test
	public void testInsertFile2_HeightOfTree() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
			assertEquals(2, b.getB_TreeHeight(), "Height of the tree not correct!");
		});
	}

	@Test
	public void testInsertFile2_NumberOfEntries() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			assertEquals(50, b.getB_TreeSize(), "Number of inserted entries into the B-Tree is not correst!");
		});
	}

	@Test
	public void testInsertFile2_NodesOfTree() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
		});
		testNode("root", nodes.get(0), 2, 3, "CI7ZU5K8FPJCFNGYX9");
		testNode("node" + 1, nodes.get(1), 3, 4, "1IWU9ULEG41ZCD124S638ACPQ1X");
		testNode("node" + 2, nodes.get(2), 2, 0, "0HMXO0XC80SNSF5K8F");
		testNode("node" + 3, nodes.get(3), 5, 0, "22Z9ZKQWI26N34QZPD2FKCH124S2K50J4C9V2SGESULEG");
		testNode("node" + 4, nodes.get(4), 3, 0, "4G474SHUF51FUI5OET589JTJU3K");
		testNode("node" + 5, nodes.get(5), 4, 0, "9KYMFSM7F9S49VV70L9TUBXGYX9AANKPLZIC");
		testNode("node" + 6, nodes.get(6), 2, 3, "CWLJ32OK9I7A9OPPJ7");
		testNode("node" + 7, nodes.get(7), 2, 0, "CPM1OUOCHCRQT0ZUMW");
		testNode("node" + 8, nodes.get(8), 4, 0, "FBDEH4KVIGBP1ETP0HHDO2P2AIGHGK6XKQWI");
		testNode("node" + 9, nodes.get(9), 5, 0, "JHV3YL2LAJVVRA0171KFVZX4I3IO6MZHCXLUOY2P3GYX9");
		testNode("node" + 10, nodes.get(10), 3, 4, "RH4YB3WRJUYT5AGH1DYRURX5GBA");
		testNode("node" + 11, nodes.get(11), 4, 0, "PPITJ5GBAPZWHYGYX9PZX87SM7FQEU8NQU7V");
		testNode("node" + 12, nodes.get(12), 5, 0, "SELHF81GMSOOXUH5KLTHMBTNHOFU79ZGYU5PUNYN3KGGL");
		testNode("node" + 13, nodes.get(13), 3, 0, "WW5ZWI9XUXK6VLKGGLYGQEMD5WF");
		testNode("node" + 14, nodes.get(14), 3, 0, "YSRN00GMUZRDIYZOSXZWQHPQY17");

	}

	@Test
	public void testInsertFile2_InOrderTraversal() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
			assertTrue(testOrderOfEntries(b.getInorderTraversal()),
					"getInorderTraversal() doesn't deliver entries in inorder traversal!");
		});
	}

	@Test
	public void testDeleteFile2_ReturnedEntries() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			Entry e1 = b.delete("U79ZGYU5P");
			Entry e2 = b.delete("L2Z74TZ0Q");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
			assertEquals("U79ZG;YU5P;OK", e1.toString(), "delete(U79ZGYU5P) output not correct!");
			assertTrue(null == e2, "delete(L2Z74TZ0Q) output not correct!");
		});
	}

	@Test
	public void testDeleteFile2_NumberOfNodes() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			b.delete("U79ZGYU5P");
			ArrayList<String> out = b.getB_Tree();
			ArrayList<TestNode> test_tree = this.constractB_Tree(out);
			assertEquals(15, test_tree.size(), "Number of nodes not correct!");
		});
	}

	@Test
	public void testDeleteFile2_HeightOfTree() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			b.delete("U79ZGYU5P");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
			assertEquals(2, b.getB_TreeHeight(), "Height of the tree not correct!");
		});
	}

	@Test
	public void testDeleteFile2_NumberOfEntries() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			b.delete("U79ZGYU5P");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
			assertEquals(49, b.getB_TreeSize(), "Number of the entries is not correct!");
		});
	}

	@Test
	public void testDeleteFile2_NodesOfTree() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			b.delete("U79ZGYU5P");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);
		});
		testNode("root", nodes.get(0), 2, 3, "CI7ZU5K8FPJCFNGYX9");
		testNode("node" + 1, nodes.get(1), 3, 4, "1IWU9ULEG41ZCD124S638ACPQ1X");
		testNode("node" + 2, nodes.get(2), 2, 0, "0HMXO0XC80SNSF5K8F");
		testNode("node" + 3, nodes.get(3), 5, 0, "22Z9ZKQWI26N34QZPD2FKCH124S2K50J4C9V2SGESULEG");
		testNode("node" + 4, nodes.get(4), 3, 0, "4G474SHUF51FUI5OET589JTJU3K");
		testNode("node" + 5, nodes.get(5), 4, 0, "9KYMFSM7F9S49VV70L9TUBXGYX9AANKPLZIC");
		testNode("node" + 6, nodes.get(6), 2, 3, "CWLJ32OK9I7A9OPPJ7");
		testNode("node" + 7, nodes.get(7), 2, 0, "CPM1OUOCHCRQT0ZUMW");
		testNode("node" + 8, nodes.get(8), 4, 0, "FBDEH4KVIGBP1ETP0HHDO2P2AIGHGK6XKQWI");
		testNode("node" + 9, nodes.get(9), 5, 0, "JHV3YL2LAJVVRA0171KFVZX4I3IO6MZHCXLUOY2P3GYX9");
		testNode("node" + 10, nodes.get(10), 3, 4, "RH4YB3WRJUYT5AGH1DYRURX5GBA");
		testNode("node" + 11, nodes.get(11), 4, 0, "PPITJ5GBAPZWHYGYX9PZX87SM7FQEU8NQU7V");
		testNode("node" + 12, nodes.get(12), 4, 0, "SELHF81GMSOOXUH5KLTHMBTNHOFUNYN3KGGL");
		testNode("node" + 13, nodes.get(13), 3, 0, "WW5ZWI9XUXK6VLKGGLYGQEMD5WF");
		testNode("node" + 14, nodes.get(14), 3, 0, "YSRN00GMUZRDIYZOSXZWQHPQY17");
	}

	@Test
	public void testDeleteFile2_InOrderTraversal() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			b.delete("U79ZGYU5P");
			b.delete("CPM1OUOCH");
			b.delete("L2Z74TZ0Q");
			ArrayList<String> out = b.getB_Tree();
			this.constractB_Tree(out);

			assertTrue(testOrderOfEntries(b.getInorderTraversal()),
					"getInorderTraversal() doesn't deliver entries in inorder traversal!");
		});
	}

	@Test
	public void testfindFile2_ReturnedEntries() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			Entry e1 = b.find("SOOXUH5KL");
			Entry e2 = b.find("SOOXIH5KL");
			assertEquals("SOOXU;H5KL;OK", e1.toString(), "find(SOOXUH5KL) output not correct!");
			assertTrue(null == e2, "find(SOOXIH5KL) output not correct!");
		});
	}

	@Test
	public void testInsertFile2_Balanced() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			ArrayList<String> out = b.getB_Tree();
			ArrayList<TestNode> test_tree = this.constractB_Tree(out);
			assertTrue(isBalanced(test_tree.get(0)), "The Tree is not balanced!");
		});
	}

	@Test
	public void testDeleteFile2_Balanced() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			b.constructB_TreeFromFile("TestFile2.txt");
			b.delete("U79ZGYU5P");
			b.delete("CWLJ32OK9");
			b.delete("CPM1OUOCH");
			ArrayList<String> out = b.getB_Tree();
			ArrayList<TestNode> test_tree = this.constractB_Tree(out);
			assertTrue(isBalanced(test_tree.get(0)), "The Tree is not balanced!");
		});
	}

	@Test
	public void testConstructB_TreeFromFile2() {
		assertTimeoutPreemptively(timeout, () -> {
			B_Tree b = new B_Tree(3);
			assertEquals(50, b.constructB_TreeFromFile("TestFile2.txt"),
					"Number of inserted entries into the B-Tree is not correst!");
		});
	}

	//////////////////////////
	protected void testNode(String name, TestNode t, int n_e, int n_ch, String ent) {
		assertEquals(n_e, t.getEntries().size(), "Number of entries of " + name + " not correct!");
		assertEquals(n_ch, t.getChildren().size(), "Number of childen of " + name + " not correct!");
		assertEquals(ent, t.entriesToString(), "Entries of " + name + " not correct!");
	}

	protected void testNode(String name, TestNode t, int n_e, int n_ch, String ent, int n_e2, int n_ch2, String ent2) {
		boolean t1 = (t.getEntries().size() == n_e || t.getEntries().size() == n_e2);
		boolean t2 = (t.getChildren().size() == n_ch || t.getChildren().size() == n_ch2);
		boolean t3 = (t.entriesToString().equalsIgnoreCase(ent) || t.entriesToString().equalsIgnoreCase(ent2));

		assertTrue(t1, "Number of entries of " + name + " not correct!");
		assertTrue(t2, "Number of childen of " + name + " not correct!");
		assertTrue(t3, "Entries of " + name + " not correct!");

	}

	protected boolean testOrderOfEntries(ArrayList<Entry> e) {
		for (int i = 0; i < e.size() - 1; i++) {
			if (e.get(i).compareTo(e.get(i + 1)) >= 0) {
				return false;
			}
		}
		return true;
	}

	protected void parseB_Tree(ArrayList<String> output, int k, String name) {

		int i = 0;
		int noChildren = 0;
		String line;
		while (i < output.size()) {
			line = output.get(i);
			/**
			 * only progress if the word "Digraph" and "}" is not contained in the line.
			 * "Digraph" is only contained in the first and "}" only in the last line.
			 */
			if (!line.contains(DotFileConstants.DOT_FILE_DIGRAPH)
					&& !line.contains(DotFileConstants.DOT_FILE_CLOSE_BRACKET)
					&& !line.contains(DotFileConstants.DOT_FILE_SOURCE)) {

				// progress a node
				if (line.contains(DotFileConstants.DOT_FILE_LABEL_START) && line.startsWith(name + "[")) {
					progressNode(line, k);
				}
				// progress a pointer
				else if (line.contains(DotFileConstants.DOT_FILE_EDGE) && line.startsWith(name + ":")) {
					noChildren++;
					progressPointer(line);
				}
			}
			i++;
		}
		if (noChildren > 0) {
			String[] temp = new String[noChildren];
			int lastIndex = pointers.size() - 1;
			for (int j = 0; j < noChildren; j++) {
				String[] p = pointers.get(lastIndex - j).split("->");
				String number = ((p[0].split(":"))[1]).split("f")[1];
				int no = Integer.valueOf(number);
				temp[no / 2] = p[1];
			}
			for (int j = 0; j < noChildren; j++) {
				parseB_Tree(output, k, temp[j]);
			}
		}
	}

	protected void progressNode(String line, int k) {
		TestNode result = new TestNode(k);
		String[] names = line.split("\\[");
		String[] entries = (line.split("\""))[1].split("\\|?+<f[0-9]+>\\*?+\\|?+");
		for (int i = 0; i < entries.length; i++) {
			if (!entries[i].equals("")) {
				Entry e = new Entry();
				e.setKey(entries[i]);
				result.addEntry(e);
			}
		}
		node_names.add(names[0].trim());
		nodes.add(result);
	}

	protected void progressPointer(String line) {
		String pointer = (line.split(";"))[0];
		pointers.add(pointer.trim());
	}

	protected ArrayList<TestNode> constractB_Tree(ArrayList<String> output) {
		ArrayList<TestNode> r = new ArrayList<TestNode>();
		parseB_Tree(output, 2, "root");
		Collections.sort(pointers);
		TestNode root = nodes.get(node_names.indexOf("root"));
		ArrayList<String> pts = new ArrayList<String>();
		for (int i = 0; i < pointers.size(); i++) {
			String[] p = pointers.get(i).split("->");
			String parent = (p[0].split(":"))[0];
			pts.add(parent.trim());
			pts.add(p[1].trim());
		}
		for (int i = 0; i < pts.size(); i = i + 2) {
			nodes.get(node_names.indexOf(pts.get(i))).addChild(nodes.get(node_names.indexOf(pts.get(i + 1))));
		}

		r.add(root);
		int i = 0;
		while (i < r.size()) {
			r.addAll(r.get(i).getChildren());
			i++;
		}
		return r;
	}

	private void printArrayList(ArrayList<String> dot) {
		try {
			FileWriter fw = new FileWriter("test.txt");
			BufferedWriter bw = new BufferedWriter(fw);

			for (String string : dot) {
				bw.write(string + System.getProperty("line.separator"));
				System.out.println(string);
			}

			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected boolean isBalanced(TestNode t) {
		return getBalancedHeight(t) > -1;
	}

	/**
	 * @param t
	 *            the root of the checked subtree
	 * @return -1 if the subtree isnt balanced, and the height of the subtree, if it
	 *         is.
	 */
	protected int getBalancedHeight(TestNode t) {
		ArrayList<TestNode> children = t.getChildren();
		if (children.isEmpty())
			return 0;
		int height = getBalancedHeight(children.get(0));
		for (TestNode child : children) {
			if (height != getBalancedHeight(child))
				return -1;
		}
		return (height == -1) ? -1 : height + 1;
	}

}
