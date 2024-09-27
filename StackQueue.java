

class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

class Queue<T> {
    Node<T> first;
    Node<T> last;

    public Queue() {
        this.first = null;
        this.last = null;
    }

    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (this.last == null) {
            first = last = newNode;
        }
        else {
            last.next = newNode;
            last = newNode;
        }
    }

    public T dequeue() {
        if (this.first == null) {
            return null; // empty
        }
        T data = this.first.data;
        first = this.first.next;
        if (first == null) {
            this.last = null;
        }
        return data;


    }
    public boolean isEmpty() {
        return this.first == null;
    }
}

class Stack<T> {
    private Node<T> first;

    public Stack() {
        this.first = null;
    }

    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = this.first;
        this.first = newNode;
    }

    public T pop() {
        if (this.first == null) {
            return null;
        }
        T data = this.first.data;
        first = this.first.next;
        return data;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

}

