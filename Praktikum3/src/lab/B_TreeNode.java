package lab;

import java.util.ArrayList;

import frame.Entry;

/*
 * Implements a node of a B-Tree
 *
 * Make sure that you have tested all the given test cases
 * given on the homepage before you submit your solution.
 *
 */

public class B_TreeNode {

	private final int t;
	protected ArrayList<Entry> entries;
	protected ArrayList<B_TreeNode> children;
	protected B_TreeNode parent;

	/**
	 * The constructor
	 * 
	 * @param t
	 *            minimum degree of the B-tree
	 */
	public B_TreeNode(int t) {
		this.t = t;
		entries = new ArrayList<Entry>(2 * t - 1);
		children = new ArrayList<B_TreeNode>(2 * t);
	}

	/**
	 * Determines whether a node is a root.
	 * 
	 * @return true if the node is a root
	 */

	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * Getter method for the Entry size
	 * 
	 * @return the entry size
	 */

	public int getEntrySize() {
		return entries.size();
	}

	/**
	 * Getter method for the parent
	 * 
	 * @return the parent
	 */

	public B_TreeNode getParent() {
		return parent;
	}

	/**
	 * 
	 * @return
	 */

	public int getChildrenSize() {
		return children.size();
	}

	/**
	 * 
	 * @return
	 */

	public ArrayList<Entry> getEntries() {
		return entries;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */

	public Entry getEntry(int index) {
		if (entries.isEmpty()) {
			return null;
		} else {
			return entries.get(index);
		}
	}

	/**
	 * 
	 * @param entryKey
	 * @return
	 */

	public Entry getEntry(String entryKey) {
		int i = 0;
		while (entries.get(i) != null && i < entries.size()) {
			if (entries.get(i).getKey().equals(entryKey)) {
				return entries.get(i);
			}
			i++;
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */

	public ArrayList<B_TreeNode> getChildren() {
		return children;
	}

	/**
	 * 
	 * @param findKey
	 * @return
	 */

	public int getIndex(String key) {
		int i = entries.size();
		while (i > 0 && key.compareTo(getEntry(i - 1).getKey()) <= 0) {
			i--;
		}
		return i;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */

	public B_TreeNode getChild(int index) {
		if (children.isEmpty()) {
			return null;
		} else {
			return children.get(index);
		}
	}

	/**
	 * 
	 * @return
	 */

	public boolean isLeaf() {
		for (B_TreeNode leafTree : children) {
			if (leafTree != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param insertEntry
	 */

	public void insert(Entry insertEntry) {
		int i = 0;
		while (entries.get(i) != null && i < entries.size()) {
			if (insertEntry.compareTo(entries.get(i)) < 0) {
				Entry temp = entries.get(i);
				entries.set(i, insertEntry);
				insertEntry = temp;
			}
			i++;
		}
	}

	/**
	 * 
	 * @param deleteKey
	 * @return
	 */

	public void mergeNode(int index, int direction) {
		if (direction == -1) {
			getChild(index).entries.addAll(getChild(index - direction).entries);
			getChild(index).children.addAll(getChild(index - direction).children);
		} else {
			getChild(index).entries.addAll(0, getChild(index - direction).entries);
			getChild(index).children.addAll(0, getChild(index - direction).children);
		}
		getChild(index).entries.add(t - 1, entries.remove(index - 1));
		children.remove(index - 1);
	}

	/**
	 * 
	 * @param deleteKey
	 * @return
	 */

	public Entry delete(String deleteKey) {
		int index = getIndex(deleteKey);
		int direction = 1;
		Entry temp = null;
		if (isLeaf())
			index %= entries.size();
		if (index < entries.size() && entries.get(index).getKey().equals(deleteKey)) {
			if (isLeaf()) {
				return entries.remove(index);
			} else {
				temp = entries.get(index);
				entries.set(index, getChild(index + 1).delete(substituteNode(index)));
				return temp;
			}
		} else if (!isLeaf() && getChild(index).getEntries().size() <= t - 1) {
			if (index <= 0)
				direction *= -1;
			if (getChild(index - 1 * direction).getEntries().size() > t - 1) {
				rotateNode(index, direction);
			} else {
				mergeNode(index, direction);
				index--;
			}
		}
		if (!isLeaf())
			return getChild(index).delete(deleteKey);
		return null;
	}

	/**
	 * substitutes a node at given index
	 * 
	 * @param index
	 *            is the given position
	 * @return
	 */

	public String substituteNode(int index) {
		B_TreeNode a = this.getChild(index + 1);
		while (!a.isLeaf()) {
			a = a.getChild(0);
		}
		return a.getEntry(0).getKey();
	}

	/**
	 * rotates a node at given index and direction
	 * 
	 * @param index
	 *            is the given position
	 * @param direction
	 *            is the given direction to rotate
	 */

	public void rotateNode(int index, int direction) {
		if (direction == 1) {
			children.get(index).getEntries().add(0, entries.get(index - 1));
			if (!getChild(index - 1).isLeaf())
				children.get(index).getChildren().add(0,
						getChild(index - 1).getChildren().remove(getChild(index - 1).getChildren().size() - 1));
			entries.set(index - 1,
					getChild(index - 1).getEntries().remove(getChild(index - 1).getEntries().size() - 1));
		} else {
			children.get(index).getEntries().add(entries.get(index));
			if (!getChild(index + 1).isLeaf())
				children.get(index).getChildren().add(getChild(index + 1).getChildren().remove(0));
			entries.set(index, getChild(index + 1).getEntries().remove(0));
		}
	}

	/**
	 * deletes an Entry at given index
	 * 
	 * @param index
	 *            is the given position
	 * @return the deleted Entry
	 */

	public Entry delete(int index) {
		int i = index;
		Entry temp = entries.get(index);
		while (i < entries.size() - 1 && entries.get(i) != null) {
			entries.set(i, entries.get(i + 1));
			i++;
		}
		entries.set(entries.size() - 1, null);
		return temp;
	}

	/**
	 * 
	 * @return
	 */

	public int getSize() {
		return entries.size();
	}

	/**
	 * 
	 * @return
	 */

	public boolean isFull() {
		return entries.size() >= 2 * t - 1;
	}

	/**
	 * splits a node
	 */

	public void split() {
		int middleindex = (this.entries.size() - 1) / 2;
		Entry e = this.entries.get(middleindex);
		B_TreeNode leftNode = new B_TreeNode(t);
		B_TreeNode rightNode = new B_TreeNode(t);
		leftNode.parent = this.parent;
		rightNode.parent = this.parent;
		ArrayList<Entry> leftEntries = new ArrayList<Entry>();
		for (int i = 0; i < middleindex; i++) {
			leftEntries.add(this.entries.get(i));
		}
		ArrayList<Entry> rightEntries = new ArrayList<Entry>();
		for (int i = middleindex + 1; i < 2 * t - 1; i++) {
			rightEntries.add(this.entries.get(i));
		}
		leftNode.entries = leftEntries;
		rightNode.entries = rightEntries;
		if (!this.isLeaf()) {
			ArrayList<B_TreeNode> leftChildren = new ArrayList<B_TreeNode>();
			ArrayList<B_TreeNode> rightChildren = new ArrayList<B_TreeNode>();
			for (int i = 0; i <= middleindex; i++) {
				this.children.get(i).parent = leftNode;
				leftChildren.add(this.children.get(i));
			}
			for (int i = middleindex + 1; i < 2 * t; i++) {
				this.children.get(i).parent = rightNode;
				rightChildren.add(this.children.get(i));
			}
			leftNode.children = leftChildren;
			rightNode.children = rightChildren;
		}
		int orignalindex = this.parent.children.indexOf(this);
		this.parent.children.set(orignalindex, leftNode);
		this.parent.children.add(orignalindex + 1, rightNode);
		this.parent.entries.add(orignalindex, e);
	}

	public boolean isMinimal() {
		return entries.size() <= t - 1;
	}

	/**
	 * 
	 * @return
	 */

	public boolean isEmpty() {
		for (Entry eachEntry : entries) {
			if (eachEntry != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param leftNode
	 * @param rightNode
	 * @return
	 */

	public B_TreeNode merge(B_TreeNode leftNode, B_TreeNode rightNode) {
		int i = 0;
		int rightSize = rightNode.getSize();
		int leftSize = leftNode.getSize();
		if ((leftSize + rightSize) > 2 * t - 1)
			return null;
		while (i < rightSize) {
			leftNode.entries.set(i + leftSize, rightNode.entries.get(i));
			leftNode.children.set(i + leftSize + 1, rightNode.children.get(i + 1));
		}
		return leftNode;
	}

	/**
	 * 
	 * @return
	 */

	public ArrayList<Entry> getInorderTraversalNode() {
		ArrayList<Entry> entriesInOrder = new ArrayList<Entry>();
		int i = 0;
		while (i < getEntries().size()) {
			if (!children.isEmpty()) {
				entriesInOrder.addAll(getChild(i).getInorderTraversalNode());
			}
			entriesInOrder.add(getEntry(i));
			i++;
		}
		if (!children.isEmpty()) {
			entriesInOrder.addAll(getChild(i).getInorderTraversalNode());
		}
		return entriesInOrder;
	}
}
