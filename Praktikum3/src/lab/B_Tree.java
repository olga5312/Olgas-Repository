package lab;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import frame.*;

/*
 * Implements a B-Tree structure as introduced in the 
 * lecture to store the information read by the RFID 
 * readers in the library.
 *
 * Make sure that you have tested all the given test cases
 * given on the homepage before you submit your solution.
 *
 */

public class B_Tree {

	private final int t;
	private B_TreeNode root;
	private int insertedEntries;

	/**
	 * The constructor
	 * 
	 * @param t
	 *            minimum degree of the B-tree. t can not be changed once a new B
	 *            Tree object is created.
	 *
	 */

	public B_Tree(int t) {
		if (t < 2) {
			t = 2;
		}
		this.t = t;
		root = new B_TreeNode(t);
		insertedEntries = 0;
	}

	/**
	 * This method takes as input the name of a file containing a sequence of
	 * entries that should be inserted to the B-Tree in the order they appear in the
	 * file. You cannot make any assumptions on the order of the entries nor is it
	 * allowed to change the order given in the file. You can assume that the file
	 * is located in the same directory as the executable program. The input file is
	 * similar to the input file for lab 1. The return value is the number of
	 * entries successfully inserted in the B-Tree.
	 * 
	 * @param filename
	 *            name of the file containing the entries
	 * @return returns the number of entries successfully inserted in the B-Tree.
	 */

	public int constructB_TreeFromFile(String filename) {
		int counter = 0;
		try {
			FileReader fileRead = new FileReader(filename);
			BufferedReader buffRead = new BufferedReader(fileRead);
			String line;
			while ((line = buffRead.readLine()) != null) {
				String parts[] = line.split(";");
				if (this.insert(new Entry(parts[0], parts[1], parts[2]))) {
					counter++;
				}
			}
			buffRead.close();
			return counter;
		} catch (FileNotFoundException message) {
			message.printStackTrace();
			return counter;
		} catch (IOException message) {
			message.printStackTrace();
			return counter;
		}
	}

	/**
	 * This method inserts the entry insertEntry into the B-Tree. Note that you have
	 * to deal with overflows if you want to insert an entry into a leaf which
	 * already contains 2t - 1 entries. This method returns true if the insertion of
	 * the entry insertEntry is successful and false if the key of this entry
	 * already exists in the B-Tree.
	 *
	 * @param insertEntry
	 *            entry to insert into the B-Tree
	 * @return returns true if the entry insertEntry is successfully inserted false
	 *         if the entry already exists in the B-Tree
	 */
	public boolean insert(Entry insertEntry) {
		if (find(insertEntry.getKey()) != null) {
			return false;
		}
		if (root.isEmpty()) {
			root.entries.add(insertEntry);
			insertedEntries++;
			return true;
		} else {
			insertRec(insertEntry, root);
			insertedEntries++;
			return true;
		}
	}

	/**
	 * The recursive methode for insert()
	 * 
	 * @param insertEntry
	 *            is the Entry to insert
	 * @param insertNode
	 *            is the node, where the Entry should be inserted
	 */

	public void insertRec(Entry insertEntry, B_TreeNode insertNode) {
		if (insertNode.isFull()) {
			if (insertNode.isRoot()) {
				splitRoot();
				insertRec(insertEntry, root);
			} else {
				insertNode.split();
				int index = insertNode.getParent().getIndex(insertEntry.getKey());
				insertRec(insertEntry, insertNode.getParent().getChild(index));
			}
		} else {
			if (!insertNode.isLeaf()) {
				int index = insertNode.getIndex(insertEntry.getKey());
				B_TreeNode child = insertNode.getChild(index);
				insertRec(insertEntry, child);
			} else {
				int index = insertNode.getIndex(insertEntry.getKey());
				if (index == insertNode.getEntries().size()) {
					insertNode.getEntries().add(insertEntry);
				} else {
					insertNode.getEntries().add(index, insertEntry);
				}
			}
		}
	}

	/**
	 * This method splits the root
	 */

	public void splitRoot() {
		B_TreeNode splittedRoot = new B_TreeNode(t);
		splittedRoot.parent = null;
		int middleindex = (root.getEntries().size() - 1) / 2;
		Entry e = root.getEntries().get(middleindex);

		splittedRoot.getEntries().add(e);

		B_TreeNode leftNode = new B_TreeNode(t);
		B_TreeNode rightNode = new B_TreeNode(t);
		leftNode.parent = splittedRoot;
		rightNode.parent = splittedRoot;

		ArrayList<Entry> leftEntries = new ArrayList<Entry>();
		for (int i = 0; i < middleindex; i++) {
			leftEntries.add(root.getEntry(i));
		}
		ArrayList<Entry> rightEntries = new ArrayList<Entry>();
		for (int j = middleindex + 1; j < 2 * t - 1; j++) {
			rightEntries.add(root.getEntry(j));
		}
		leftNode.entries = leftEntries;
		rightNode.entries = rightEntries;
		if (!root.isLeaf()) {
			ArrayList<B_TreeNode> leftChildren = new ArrayList<B_TreeNode>();
			ArrayList<B_TreeNode> rightChildren = new ArrayList<B_TreeNode>();
			for (int k = 0; k <= middleindex; k++) {
				root.getChild(k).parent = leftNode;
				leftChildren.add(root.getChild(k));
			}
			for (int l = middleindex + 1; l < 2 * t; l++) {
				root.getChild(l).parent = rightNode;
				rightChildren.add(root.getChild(l));
			}

			leftNode.children = leftChildren;
			rightNode.children = rightChildren;
		}
		splittedRoot.getChildren().add(0, leftNode);
		splittedRoot.getChildren().add(1, rightNode);
		this.root = splittedRoot;
	}

	/**
	 * This method deletes the entry from the B-Tree structure, having deleteKey as
	 * key. In this method you have to distinguish between two cases: 1. The entry,
	 * having deleteKey as key, is located in an internal node. 2. The entry, having
	 * deleteKey as key, is located in a leaf. This method returns the entry, having
	 * deleteKey as key if the deletion is successful and null if the key deleteKey
	 * is not found in any entry of the B-Tree.
	 *
	 * @param deleteKey
	 *            key of the entry to delete from the B-Tree
	 * @return returns the deleted entry if the deletion ends successfully null if
	 *         the entry is not found in the B-Tree
	 */

	public Entry delete(String deleteKey) {        
    	Entry temp = find(deleteKey);
    	if(temp == null) {        	
    		return temp;	    		
        } else {  
        	root.delete(deleteKey);
        	insertedEntries--;
        	return temp;
        }
    }
	
	/**
	 * This method searches in the B-Tree for the entry with key searchKey. It
	 * returns the entry, having searchKey as key if such an entry is found, null
	 * otherwise.
	 *
	 * @param searchKey
	 *            key of the entry to find in the B-Tree
	 * @return returns the entry having searchKey as key if such an entry exists
	 *         null if the entry is not found in the B-Tree
	 */

	public Entry find(String searchKey) {
		Entry foundEntry = findRec(root, searchKey);
		return foundEntry;
	}

	/**
	 * The recursive method of find
	 *
	 * @param searchKey
	 *            key of the entry to find in the B-Tree
	 * @param treeNode
	 *            the node where to find the key
	 * @return the entry having searchKey as key if such an entry exists null if the
	 *         entry is not found in the B-Tree
	 */

	private Entry findRec(B_TreeNode treeNode, String searchKey) {
		if (treeNode == null) {
			return null;
		}
		for (int i = 0; i < treeNode.getSize(); i++) {
			if (searchKey.compareTo(treeNode.getEntry(i).getKey()) < 0) {
				return findRec(treeNode.getChild(i), searchKey);
			}
			if (searchKey.equals(treeNode.getEntry(i).getKey())) {
				return treeNode.getEntry(i);
			}
		}
		return findRec(treeNode.getChild(treeNode.getEntrySize()), searchKey);
	}

	/**
	 * This method returns a ArrayList<String> containing the output B-Tree. The
	 * output should be directly interpretable dot code. Each item in the ArrayList
	 * corresponds to one line of the output tree. The nodes of the output tree
	 * should only contain the keys of the entries and not the data.
	 *
	 * @return returns the output B-Tree in directly interpretable dot code
	 */

	public ArrayList<String> getB_Tree() {
		ArrayList<String> bTreeList = new ArrayList<String>();
		bTreeList.add(DotFileConstants.DOT_FILE_START);
		bTreeList.add("node" + DotFileConstants.DOT_FILE_SOURCE);
		StringBuilder rootBuilder = new StringBuilder("root[label=\"<f0>*");
		for (int i = 0; i < root.getEntrySize(); i++) {
			rootBuilder.append("|<f");
			rootBuilder.append(2 * i + 1);
			rootBuilder.append(">");
			rootBuilder.append(root.getEntry(i).getKey());
			rootBuilder.append("|<f");
			rootBuilder.append(2 * (i + 1));
			rootBuilder.append(">*");
		}
		rootBuilder.append(DotFileConstants.DOT_FILE_LABEL_END);
		bTreeList.add(rootBuilder.toString());
		if (!root.isLeaf()) {
			ArrayList<B_TreeNode> allNodes = getAllNodes(root);
			for (int j = 1; j < allNodes.size(); j++) {
				B_TreeNode takeNode = allNodes.get(j);
				StringBuilder nodeBuilder = new StringBuilder();
				nodeBuilder.append("node");
				nodeBuilder.append(j + 1);
				nodeBuilder.append("[label=\"<f0>*");
				for (int k = 0; k < takeNode.getEntrySize(); k++) {
					nodeBuilder.append("|<f");
					nodeBuilder.append(2 * k + 1);
					nodeBuilder.append(">");
					nodeBuilder.append(takeNode.getEntry(k).getKey());
					nodeBuilder.append("|<f");
					nodeBuilder.append(2 * (k + 1));
					nodeBuilder.append(">*");
				}
				nodeBuilder.append(DotFileConstants.DOT_FILE_LABEL_END);
				bTreeList.add(String.valueOf(nodeBuilder));
			}
			for (int l = 0; l < root.getChildrenSize(); l++) {
				StringBuilder secRootBuilder = new StringBuilder();
				secRootBuilder.append("root:f");
				secRootBuilder.append(2 * l);
				secRootBuilder.append("->node");
				int index = allNodes.indexOf(root.getChild(l));
				secRootBuilder.append(index + 1);
				secRootBuilder.append(DotFileConstants.DOT_FILE_LINE_END);
				bTreeList.add(String.valueOf(secRootBuilder));
			}
			for (int m = 1; m < allNodes.size(); m++) {
				B_TreeNode takeSecNode = allNodes.get(m);
				String nodeName = "node" + (m + 1) + ":f";
				if (!takeSecNode.isLeaf()) {
					for (int n = 0; n < takeSecNode.getChildrenSize(); n++) {
						StringBuilder secNodeBuilder = new StringBuilder();
						secNodeBuilder.append(nodeName);
						secNodeBuilder.append(2 * n);
						secNodeBuilder.append("->node");
						int index = allNodes.indexOf(takeSecNode.getChild(n));
						secNodeBuilder.append(index + 1);
						secNodeBuilder.append(DotFileConstants.DOT_FILE_LINE_END);
						bTreeList.add(secNodeBuilder.toString());
					}
				}
			}
		}
		bTreeList.add(DotFileConstants.DOT_FILE_END);
		return bTreeList;
	}

	/**
	 * Converts the treenodes into an ArrayList
	 * @param node is the node to convert
	 * @return the ArrayList of B_TreeNodes
	 */

	public ArrayList<B_TreeNode> getAllNodes(B_TreeNode node) {
		ArrayList<B_TreeNode> allNodes = new ArrayList<B_TreeNode>();
		if (node != null) {
			allNodes.add(node);
		}
		if (!node.isLeaf()) {
			for (B_TreeNode partNode : node.getChildren()) {
				allNodes.addAll(getAllNodes(partNode));
			}
		}
		return allNodes;
	}

	/**
	 * This method returns the height of the B-Tree If the B-Tree is empty this
	 * method should return 0.
	 *
	 * @return returns the height of the B-Tree
	 */

	public int getB_TreeHeight() {
		int height = 0;
		B_TreeNode node = root;
		while (!node.isLeaf()) {
			height++;
			node = node.getChild(0);
		}
		return height;
	}

	/**
	 * This method traverses the B-Tree in inorder and adds each entry to a
	 * ArrayList<Entry>. The returned ArrayList contains the entries of the B-Tree
	 * in ascending order.
	 *
	 * @return returns the entries stored in the B-Tree in ascending order
	 */

	public ArrayList<Entry> getInorderTraversal() {
		return root.getInorderTraversalNode();
	}

	/**
	 * This method returns the number of entries in the B-Tree (not the number of
	 * nodes).
	 *
	 *
	 * @return returns the size of the B-Tree, i.e., the number of entries stored in
	 *         the B-Tree
	 */

	public int getB_TreeSize() {
		return insertedEntries;
	}
}