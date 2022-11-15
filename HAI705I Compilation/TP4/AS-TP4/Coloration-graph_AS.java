 
// Coloriage de graph
// SAID Adam
// BENTOLILA Jérémie

import java.util.Scanner;

public class Graph {
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";

    // retourne le noeuds de degre minimum
    public static int minNode(int[] edges) {
        int min = 0;
        for (int i = 0; i < edges.length; i++) {
            if(edges[i] < edges[min]) {
                min = i;
            }
        }
        return min;
    }

    // retourne -1 si le graphe peut être colorié avec k couleurs, tableau des noeuds spillés sinon
    public static int[] graphColoriable(int[][] graph, int k, int[] colors) {
        int[] edgesTab = new int[graph.length];
        int[] nodeOrder = new int[graph.length];
        int[] spilledNodes = new int[graph.length];
        for (int i = 0; i < spilledNodes.length; i++) {
            spilledNodes[i] = 0;
        }

        for (int i = 0; i < graph.length; i++) {
            int edges = 0;
            for (int j = 0; j < graph[i].length; j++) {
                if(graph[i][j] == 1) {
                    edges++;
                }
            }
            edgesTab[i] = edges;
        }

        for (int i = 0; i < graph.length; i++) {
            int index = minNode(edgesTab);
            nodeOrder[i] = index;
            edgesTab[index] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < nodeOrder.length; i++) {
            for (int c = 1; c <= k; c++) {
                if (sommetColoriable(graph, colors, nodeOrder[i], c)) {
                    colors[nodeOrder[i]] = c;
                    break;
                }
            }
            if(colors[nodeOrder[i]] == 0) {
                spilledNodes[nodeOrder[i]] = 1;
            }
        }
        boolean res = true;
        for (int i : spilledNodes) {
            if(i == 1) {
                res = false;
            }
        }
        if(res) {
            return new int[] {-1};
        }
        else {
            return spilledNodes;
        }
    }

    // retourne true si le sommet peut être colorié avec la couleur c, false sinon
    public static boolean sommetColoriable(int[][] graph, int[] couleurs, int sommet, int c) {
        for (int i = 0; i < graph.length; i++) {
            if(graph[sommet][i] == 2 && couleurs[i] != 0) {
                return true;
            }
            if (graph[sommet][i] == 1 && couleurs[i] == c) {
                return false;
            }
        }
        return true;
    }

	public static void main(String[] args) {
        int[][] graphCours = { 
            // b  c  d  e  f  g  h  j  k  m
              {0, 1, 1, 1, 2, 0, 0, 2, 1, 1}, // b
              {1, 0, 2, 0, 0, 0, 0, 0, 0, 1}, // c
              {1, 2, 0, 0, 0, 0, 0, 1, 1, 1}, // d
              {1, 0, 0, 0, 1, 0, 0, 1, 0, 1}, // e
              {2, 0, 0, 1, 0, 0, 0, 1, 0, 1}, // f
              {0, 0, 0, 0, 0, 0, 1, 1, 1, 0}, // g
              {0, 0, 0, 0, 0, 1, 0, 1, 0, 0}, // h
              {2, 0, 1, 1, 1, 1, 1, 0, 1, 0}, // j
              {1, 0, 1, 0, 0, 1, 0, 1, 0, 0}, // k
              {1, 1, 1, 1, 1, 0, 0, 0, 0, 0}, // m
          };

        String[] dicoStrings = {
            "b", "c", "d", "e", "f", "g", "h", "j", "k", "m"
        };

		Scanner sc = new Scanner(System.in);
		int n = -1;

		System.out.println("\tGraphe du cours :");
        System.out.println("\t\tg := j + 12\n\t\th := k - 1\n\t\tf := g * h\n\t\te := j + 8\n\t\tm := j + 16");
		System.out.println("\t\tb := f\n\t\tc := e + 8\n\t\td := c\n\t\tk := m + 4\n\t\tj := b");
        System.out.println("\nNombre de couleurs (0 pour quitter): ");
        n = sc.nextInt();
        while (n != 0) {
            int[] colors = new int[graphCours.length];
            for (int i = 0; i < colors.length; i++) {
                colors[i] = 0;
            }
            int[] res = graphColoriable(graphCours, n, colors);
			if (res[0] == -1) {
                System.out.println(ANSI_GREEN + "Le graphe peut être colorié avec " + n + " couleurs" + ANSI_RESET);
            }
            else {
                System.out.println(ANSI_RED + "Le graphe ne peut pas être colorié avec " + n + " couleurs" + ANSI_RESET);
                System.out.println("Les sommets qui ne peuvent pas être coloriés sont : ");
                for (int i = 0; i < res.length; i++) {
                    if(res[i] == 1) {
                        System.out.print(dicoStrings[i] + " ");
                    }
                }
            }
            System.out.println("\nNombre de couleurs (0 pour quitter): ");
			n = sc.nextInt();
		}
		sc.close();
	}

}
