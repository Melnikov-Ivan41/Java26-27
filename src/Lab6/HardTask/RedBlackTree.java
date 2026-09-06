package Lab6.HardTask;

public class RedBlackTree {

    // Внутрішній клас вузла
    static class Node {
        int data;
        Node parent;
        Node left;
        Node right;
        int color; // 1 - червоний, 0 - чорний
    }

    private Node root;
    private final Node TNULL; // Sentinel (порожній вузол-обмежувач)

    public RedBlackTree() {
        TNULL = new Node();
        TNULL.color = 0; // TNULL завжди чорний
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    // --- БАЗОВІ ПОВОРОТИ ---

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // --- ВСТАВКА ТА БАЛАНСУВАННЯ ---

    public void insert(int key) {
        Node node = new Node();
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = 1; // Новий вузол завжди червоний

        Node y = null;
        Node x = this.root;

        // Звичайний пошук місця для вставки (як у бінарному дереві)
        while (x != TNULL) {
            y = x;
            if (node.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data < y.data) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = 0; // Корінь завжди чорний
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        // Викликаємо метод балансування
        fixInsert(node);
    }

    private void fixInsert(Node k) {
        Node u;
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left; // Дядько
                if (u.color == 1) {
                    // Випадок 1: Дядько червоний
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        // Випадок 2: Дядько чорний, вузол - лівий нащадок
                        k = k.parent;
                        rightRotate(k);
                    }
                    // Випадок 3: Дядько чорний, вузол - правий нащадок
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right; // Дядько

                if (u.color == 1) {
                    // Випадок 1: Дядько червоний
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        // Випадок 2: Дядько чорний, вузол - правий нащадок
                        k = k.parent;
                        leftRotate(k);
                    }
                    // Випадок 3: Дядько чорний, вузол - лівий нащадок
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0; // Корінь завжди залишається чорним
    }

    // --- ОБХІД ТА ВІДОБРАЖЕННЯ ---

    // In-Order обхід (відсортований вивід)
    public void inOrder() {
        inOrderHelper(this.root);
        System.out.println();
    }

    private void inOrderHelper(Node node) {
        if (node != TNULL) {
            inOrderHelper(node.left);
            System.out.print(node.data + " ");
            inOrderHelper(node.right);
        }
    }

    // Красиве виведення дерева в консоль
    // --- ОБХІД ТА ВІДОБРАЖЕННЯ ---

    // Красиве виведення дерева в консоль (адаптовано для від'ємних чисел)
    public void printTree() {
        if (this.root == TNULL) {
            System.out.println("Дерево порожнє.");
            return;
        }
        printHelper(this.root, "", true, true);
    }

    private void printHelper(Node root, String indent, boolean last, boolean isRoot) {
        if (root != TNULL) {
            System.out.print(indent);

            // Форматуємо гілки
            if (isRoot) {
                System.out.print("Root: ");
            } else if (last) {
                System.out.print("└─ R: ");
                indent += "      "; // Відступ для правих нащадків
            } else {
                System.out.print("├─ L: ");
                indent += "│     "; // Вертикальна лінія для лівих нащадків
            }

            // Виводимо значення і колір з пробілом перед числом
            String sColor = root.color == 1 ? "RED" : "BLACK";
            System.out.println(root.data + " (" + sColor + ")");

            // Рекурсивно викликаємо для нащадків
            printHelper(root.left, indent, false, false);
            printHelper(root.right, indent, true, false);
        }
    }
}
