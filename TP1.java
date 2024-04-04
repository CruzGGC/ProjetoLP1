import java.io.*; // Import the File class
import java.util.Map;

public class TP1 {

    public static void main(String[] args) {
        VotosCirculoEleitoral Distrito = new VotosCirculoEleitoral("Aveiro");
        try {
            ObjectInputStream iS;
            iS = new ObjectInputStream(new FileInputStream("Distritos/circulo_coimbra.dat"));
            VotosCirculoEleitoral leitura = (VotosCirculoEleitoral) iS.readObject();
            System.out.println(leitura.getNomeCirculo());
            
            for (String conselho : leitura.getVotosPorConcelho().keySet()) {
                System.out.println(conselho);
            }
            for (VotosConcelho votosPorPartido : leitura.getVotosPorConcelho().values()) {
                Map<String, Integer> votos = votosPorPartido.getVotosPorPartido();
                for (String partido : votos.keySet()) {
                    System.out.println(partido);
                }
            }
            iS.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Erro!");
            System.out.println("Ola");
        }
    }
}