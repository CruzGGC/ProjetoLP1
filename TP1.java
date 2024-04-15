import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class TP1 {
    public static void main(String[] args) {

        File folder = new File("Distritos");
        File[] listOfFiles = folder.listFiles();

        Map<String, Integer> totalVotos = new HashMap<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    ObjectInputStream iS = new ObjectInputStream(new FileInputStream(file));
                    VotosCirculoEleitoral leitura = (VotosCirculoEleitoral) iS.readObject();
                    
                    System.out.println("-------------------------------------------------");
                    System.out.println("Distrito: " + leitura.getNomeCirculo());

                    for (String conselho : leitura.getVotosPorConcelho().keySet()) {

                        System.out.println("  Concelho: " + conselho);
                        VotosConcelho votosPorPartido = leitura.getVotosPorConcelho().get(conselho);
                        System.out.println("  Partidos do " + conselho);
                        Map<String, Integer> votos = votosPorPartido.getVotosPorPartido();
                        for (Map.Entry<String, Integer> entry : votos.entrySet()) {
                            System.out.println("    Partido: " + entry.getKey() + ", Votos: " + entry.getValue());
                        }
                    }

                    for (String conselho : leitura.getVotosPorConcelho().keySet()) {
                        VotosConcelho votosPorPartido = leitura.getVotosPorConcelho().get(conselho);
                        Map<String, Integer> votos = votosPorPartido.getVotosPorPartido();
                        for (Map.Entry<String, Integer> entry : votos.entrySet()) {
                            totalVotos.put(entry.getKey(),
                                    totalVotos.getOrDefault(entry.getKey(), 0) + entry.getValue());
                        }
                    }
                    iS.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Erro!");
                }
            }

            int totalGeralVotos = 0;
            int votosBrancos = totalVotos.getOrDefault("Brancos", 0);
            int votosNulos = totalVotos.getOrDefault("Nulos", 0);
            for (Integer votos : totalVotos.values()) {
                totalGeralVotos += votos;
            }
            int totalVotantes = totalGeralVotos + votosBrancos + votosNulos;
            DecimalFormat df = new DecimalFormat("#.##");

            totalVotos.remove("Brancos");
            totalVotos.remove("Nulos");

            List<Map.Entry<String, Integer>> list = new ArrayList<>(totalVotos.entrySet());
            list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            try (PrintWriter writer = new PrintWriter("Resultado/TotalNacional.txt", "UTF-8")) {
                writer.println("--------------------Total Nacional-------------------");
                writer.printf("Total de votantes: %d\n", totalVotantes);
                writer.printf("Total de votos validos: %d Percentagem: %s%%\n", totalGeralVotos,
                        df.format((float) totalGeralVotos / totalVotantes * 100));
                writer.printf("Votos Brancos: %d Percentagem: %s%%\n", votosBrancos,
                        df.format((float) votosBrancos / totalVotantes * 100));
                writer.printf("Votos Nulos: %d Percentagem: %s%%\n", votosNulos,
                        df.format((float) votosNulos / totalVotantes * 100));
                writer.println("--------------------Votos por Partido-------------------");

                for (Map.Entry<String, Integer> entry : list) {
                    double percentagem = (double) entry.getValue() / totalVotantes * 100;
                    writer.printf("Partido: %s, Votos: %d, Percentagem: %.2f%%\n", entry.getKey(), entry.getValue(),
                            percentagem);
                }
            

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Erro!");
            }
        }
        System.out.println("--------------------------------------------");
        System.out.println("Total nacional no ficheiro TotalNacional.txt");
    }
}