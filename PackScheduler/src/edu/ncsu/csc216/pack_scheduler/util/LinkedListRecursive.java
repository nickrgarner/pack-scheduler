package edu.ncsu.csc216.pack_scheduler.util;

public class LinkedListRecursive<E> {
	
	private ListNode front;
	private int size;
	
	public LinkedListRecursive() {
		
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public int size() {
		return 0;
	}
	
	public boolean add (E data) {
		return false;
	}
	
	public void add (int index, E data) {
		
	}
	
	public E get (int index) {
		return null;
	}
	
	public boolean remove (E data) {
		return false;
	}
	
	public E remove (int index) {
		return null;
	}
	
	public E set (int index, E data) {
		return null;
	}
	
	public boolean contains (E data) {
		return false;
	}
	
	private class ListNode {
		
		public E data;
		public ListNode next;
		
		public ListNode (E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
		
		public void add (int index , E data) {
			
		}
		
		public E get (int index) {
			return null;
		}
		
		public E remove (int index) {
			return null;
		}
		
		public E set (int index, E data) {
			return null;
		}
		
		public boolean contains (E data) {
			return false;
		}
	}
}
