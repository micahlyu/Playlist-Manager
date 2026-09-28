/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author micah
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Doubly linked list used by the playlist assignment.
 *
 * @param <E> element type
 */
public class MyDoubleLinkedList<E> implements List<E> {

    private class Node {
        private E data;
        private Node next;
        private Node prev;

        private Node(E data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        private Node(E data, Node next) {
            this.data = data;
            this.next = next;
            this.prev = null;

            if (next != null) {
                next.prev = this;
            }
        }

        @Override
        public String toString() {
            return "Node(" + String.valueOf(data) + ")";
        }
    }

    private int size;
    private Node head;
    private Node tail;

    public MyDoubleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(E element) {
        Node newNode = new Node(element);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size) {
            add(element);
            return;
        }

        Node newNode = new Node(element);

        if (index == 0) {
            newNode.next = head;

            if (head != null) {
                head.prev = newNode;
            }

            head = newNode;

            if (tail == null) {
                tail = newNode;
            }
        } else {
            Node node = getNode(index - 1);

            newNode.next = node.next;
            newNode.prev = node;

            if (node.next != null) {
                node.next.prev = newNode;
            }

            node.next = newNode;
        }

        size++;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean changed = false;

        for (E element : collection) {
            add(element);
            changed = true;
        }

        return changed;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        boolean changed = false;

        for (E element : collection) {
            add(index, element);
            index++;
            changed = true;
        }

        return changed;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object obj : collection) {
            if (!contains(obj)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public E get(int index) {
        return getNode(index).data;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node node;

        if (index < size / 2) {
            node = head;

            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;

            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }

        return node;
    }

    @Override
    public int indexOf(Object target) {
        Node node = head;

        for (int i = 0; i < size; i++) {
            if (equals(target, node.data)) {
                return i;
            }

            node = node.next;
        }

        return -1;
    }

    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public int lastIndexOf(Object target) {
        Node node = tail;

        for (int i = size - 1; i >= 0; i--) {
            if (equals(target, node.data)) {
                return i;
            }

            node = node.prev;
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ArrayList<E>(this).listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ArrayList<E>(this).listIterator(index);
    }

    @Override
    public boolean remove(Object obj) {
        Node node = head;

        while (node != null && !equals(obj, node.data)) {
            node = node.next;
        }

        if (node == null) {
            return false;
        }

        unlink(node);
        size--;

        return true;
    }

    @Override
    public E remove(int index) {
        Node node = getNode(index);
        E element = node.data;

        unlink(node);
        size--;

        return element;
    }

    private void unlink(Node node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        node.next = null;
        node.prev = null;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean changed = false;

        Iterator<E> it = new ArrayList<E>(this).iterator();

        while (it.hasNext()) {
            E item = it.next();

            if (collection.contains(item)) {
                remove(item);
                changed = true;
            }
        }

        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean changed = false;
        Node current = head;

        while (current != null) {
            Node next = current.next;

            if (!collection.contains(current.data)) {
                unlink(current);
                size--;
                changed = true;
            }

            current = next;
        }

        return changed;
    }

    @Override
    public E set(int index, E element) {
        Node node = getNode(index);

        E old = node.data;
        node.data = element;

        return old;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Counts the nodes in the list.
     */
    public int count() {
        int count = 0;
        Node current = head;

        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }

    /**
     * Physically reverses the linked list.
     */
    public void reverse() {
        Node current = head;

        while (current != null) {
            Node next = current.next;

            current.next = current.prev;
            current.prev = next;

            current = next;
        }

        Node oldHead = head;
        head = tail;
        tail = oldHead;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }

        List<E> list = new ArrayList<E>();

        for (int i = fromIndex; i < toIndex; i++) {
            list.add(get(i));
        }

        return list;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;

        for (Node node = head; node != null; node = node.next) {
            array[i] = node.data;
            i++;
        }

        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) Arrays.copyOf(a, size, a.getClass());
        }

        int i = 0;

        for (Node node = head; node != null; node = node.next) {
            a[i] = (T) node.data;
            i++;
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }
}
