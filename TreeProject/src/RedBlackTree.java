import java.util.Random;

enum Color {
    RED,
    BLACK
}

class RBNode {
    int key;
    RBNode parent;
    RBNode left;
    RBNode right;
    Color color;

    public RBNode(int item) {
        key = item;
        parent = null;
        left = null;
        right = null;
        color = Color.RED;
    }
}

public class RedBlackTree {
    RBNode root;
    int rotationCount;

    public RedBlackTree() {
        root = null;
        rotationCount = 0;
    }

    void rotateLeft(RBNode x) {
        RBNode y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;

        rotationCount++;
    }

    void rotateRight(RBNode x) {
        RBNode y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;

        rotationCount++;
    }

    void fixInsert(RBNode z) {
        while (z != root && z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) {
                RBNode y = z.parent.parent.right;
                if (y != null && y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        rotateLeft(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rotateRight(z.parent.parent);
                }
            } else {
                RBNode y = z.parent.parent.left;
                if (y != null && y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rotateLeft(z.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }

    void insert(int key) {
        RBNode z = new RBNode(key);
        RBNode y = null;
        RBNode x = root;

        while (x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.parent = y;

        if (y == null) {
            root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }

        fixInsert(z);
    }
//
    void inOrder(RBNode node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    int height(RBNode node) {
        if (node == null)
            return 0;
        else {
            int leftHeight = height(node.left);
            int rightHeight = height(node.right);

            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        int[] numbers = generateRandomNumbers(1500000); // Altere o tamanho do array conforme necessário

        for (int number : numbers) {
            tree.insert(number);
        }

        System.out.println("Árvore Red-Black:");
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
