import java.io.*; // Import the File class
import java.util.Map;

public class TP1 {

    public static void main(String[] args) {
        VotosCirculoEleitoral Distrito = new VotosCirculoEleitoral("Aveiro");
        try {
            /*
             * FileOutputStream os = new FileOutputStream("teste.dat");
             * 
             * ObjectOutputStream oS;
             * oS = new ObjectOutputStream(new FileOutputStream("teste.dat"));
             * oS.writeObject(Distrito); // Criado ficheiro com um objeto do tipo
             * VotosEleitoral (linha 15)
             */
            ObjectInputStream iS;
            iS = new ObjectInputStream(new FileInputStream("Distritos/circulo_coimbra.dat"));
            VotosCirculoEleitoral leitura = (VotosCirculoEleitoral) iS.readObject();
            System.out.println(leitura.getNomeCirculo()); // Ler o objeto do ficheiro e fazer cast para o tipo de objeto
                                                          // correto
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