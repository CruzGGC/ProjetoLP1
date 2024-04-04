import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class TP1 {
    public static void main(String[] args) {

        // Criação um objeto File para o diretório "Distritos" e obtém uma lista de todos os ficheiros nesse diretório.

        File folder = new File("Distritos");
        File[] listOfFiles = folder.listFiles();

        // Criação um HashMap chamado totalVotos para armazenar o total de votos de cada partido em todos os círculos eleitorais.

        Map<String, Integer> totalVotos = new HashMap<>();
        
        for (File file : listOfFiles) {
            if (file.isFile()) { // Verifica se o ficheiro é realmente um ficheiro (e não um diretório).
                try {
                    ObjectInputStream iS = new ObjectInputStream(new FileInputStream(file));
                    VotosCirculoEleitoral leitura = (VotosCirculoEleitoral) iS.readObject(); // Lê um objeto do ficheiro e faz cast para VotosCirculoEleitoral.
        
                    System.out.println("Distrito: " + leitura.getNomeCirculo()); // Imprime o nome do círculo eleitoral.
        
                    for (String conselho : leitura.getVotosPorConcelho().keySet()) { // Para cada concelho no círculo eleitoral, imprime o nome do concelho e os votos de cada partido nesse concelho.
                        System.out.println("  Concelho: " + conselho);
                        VotosConcelho votosPorPartido = leitura.getVotosPorConcelho().get(conselho);
                        System.out.println("  Partidos do " + conselho);
                        Map<String, Integer> votos = votosPorPartido.getVotosPorPartido();
                        for (Map.Entry<String, Integer> entry : votos.entrySet()) { 
                            System.out.println("    Partido: " + entry.getKey() + ", Votos: " + entry.getValue());
                        }
                    }
                    
                    for (String conselho : leitura.getVotosPorConcelho().keySet()) { // Para cada concelho no círculo eleitoral, adiciona os votos de cada partido ao total de votos.
                        VotosConcelho votosPorPartido = leitura.getVotosPorConcelho().get(conselho);
                        Map<String, Integer> votos = votosPorPartido.getVotosPorPartido();
                        for (Map.Entry<String, Integer> entry : votos.entrySet()) {
                            totalVotos.put(entry.getKey(), totalVotos.getOrDefault(entry.getKey(), 0) + entry.getValue());
                        }
                    }

                    iS.close(); // Fecha o ObjectInputStream.
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Erro!");
                }
            }
        }
        System.out.println("--------------------Total Nacional-------------------");
        // Imprime o total de votos de cada partido em todos os círculos eleitorais.
        int totalGeralVotos = 0;
        for (Integer votos : totalVotos.values()) {
            totalGeralVotos += votos;
        }
        System.out.println("Total de votos: " + totalGeralVotos);
        
        try {
            PrintWriter writer = new PrintWriter("Resultado/TotalNacional.txt", "UTF-8");
            writer.println("Total de votos: " + totalGeralVotos);
            
            for (Map.Entry<String, Integer> entry : totalVotos.entrySet()) {
                double percentagem = (double) entry.getValue() / totalGeralVotos * 100;
                System.out.printf("Partido: %s, Votos: %d, Percentagem: %.2f%%\n", entry.getKey(), entry.getValue(), percentagem);
                writer.printf("Partido: %s, Votos: %d, Percentagem: %.2f%%\n", entry.getKey(), entry.getValue(), percentagem);
            }
            
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro!");
            e.printStackTrace();
        }
    }
}