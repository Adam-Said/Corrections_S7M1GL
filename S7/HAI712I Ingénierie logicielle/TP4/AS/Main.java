// Par Adam SAID
// TP7 Design Pattern : Command
// Seulement la partie 1 (j'avais la flemme de finir)

package V2;
import V2.Partie;

public class Main {
    public static void main(String[] args) {
        int[] capacitesBidons = {200, 100, 50};
        Partie p = new Partie(3, capacitesBidons, 150); 
        p.jouer();
    }
}
