import java.util.Random;

class AVLNode {
    int key;
    int height;
    AVLNode left, right;

    public AVLNode(int item) {
        key = item;
        height = 1;
    }
}

public class AVLTree {
    AVLNode root;
    int rotationCount;

    public AVLTree() {
        root = null;
        rotationCount = 0;
    }

    int height(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        rotationCount++;
        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        rotationCount++;
        return y;
    }

    int getBalance(AVLNode node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    AVLNode insert(AVLNode node, int key) {
        if (node == null)
            return (new AVLNode(key));

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else // Duplicate keys not allowed
            return node;

        node.height = 1 + max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }

        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }

        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void inOrder(AVLNode node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        int size = 500000; // Tamanho do array
        int[] numbers = generateRandomNumbers(size);

        for (int number : numbers) {
            tree.root = tree.insert(tree.root, number);
        }

        System.out.println("Árvore AVL:");
        System.out.println("Quantidade de rotações: " + tree.rotationCount);
        System.out.println("Altura final da árvore: " + tree.height(tree.root));
    }

    private static int[] generateRandomNumbers(int size) {
        int[] numbers = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            numbers[i] = random.nextInt();
        }

        return numbers;
    }
}
