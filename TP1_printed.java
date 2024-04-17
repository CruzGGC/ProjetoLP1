import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

public class TP1_printed {
    public static void main(String[] args) {

        // Cria um objeto File para o diretório "Distritos"
        File folder = new File("Distritos");
        // Lista todos os ficheiros no diretório
        File[] listOfFiles = folder.listFiles();

        // Cria um mapa para armazenar o total de votos
        Map<String, Integer> totalVotos = new HashMap<>();

        // Percorre todos os ficheiros no diretório
        for (File file : listOfFiles) {
            // Verifica se o item é um ficheiro
            if (file.isFile()) {
                try {
                    
                    // Cria um mapa para armazenar o total de votos por círculo eleitoral
                    Map<String, Integer> totalVotosCirculo = new HashMap<>();
                    // Cria um ObjectInputStream para ler o ficheiro
                    ObjectInputStream iS = new ObjectInputStream(new FileInputStream(file));
                    // Lê o objeto do ficheiro
                    VotosCirculoEleitoral leitura = (VotosCirculoEleitoral) iS.readObject();
                    
                     // Imprime informações sobre o distrito
                    System.out.println("-------------------------------------------------");
                    System.out.println("Distrito: " + leitura.getNomeCirculo());

                    // Percorre todos os conselhos e imprime informações sobre os votos
                    for (String conselho : leitura.getVotosPorConcelho().keySet()) {

                        System.out.println("  Concelho: " + conselho);
                        VotosConcelho votosPorPartido = leitura.getVotosPorConcelho().get(conselho);
                        // Imprime os partidos e os votos correspondentes de cada conselho
                        System.out.println("  Partidos do " + conselho);
                        Map<String, Integer> votos = votosPorPartido.getVotosPorPartido();
                        for (Map.Entry<String, Integer> entry : votos.entrySet()) {
                            System.out.println("    Partido: " + entry.getKey() + ", Votos: " + entry.getValue());
                        }
                    }

                    // Percorre todos os conselhos e atualiza o total de votos
                    for (String conselho : leitura.getVotosPorConcelho().keySet()) {
                        VotosConcelho votosPorPartido = leitura.getVotosPorConcelho().get(conselho);
                        Map<String, Integer> votos = votosPorPartido.getVotosPorPartido();
                        for (Map.Entry<String, Integer> entry : votos.entrySet()) {
                            // Atualiza o total de votos para cada partido
                            totalVotos.put(entry.getKey(), totalVotos.getOrDefault(entry.getKey(), 0) + entry.getValue());
                            totalVotosCirculo.put(entry.getKey(), totalVotosCirculo.getOrDefault(entry.getKey(), 0) + entry.getValue());
                        }
                    }

                    try (PrintWriter writer = new PrintWriter("Resultado/"+leitura.getNomeCirculo()+".txt", "UTF-8")) {

                        int totalGeralVotos = 0;
                        int votosBrancos = totalVotosCirculo.getOrDefault("Brancos", 0);
                        int votosNulos = totalVotosCirculo.getOrDefault("Nulos", 0);
                        for (Integer votos : totalVotosCirculo.values()) {
                            totalGeralVotos += votos;
                        }
                        int totalVotantes = totalGeralVotos + votosBrancos + votosNulos;
                        DecimalFormat df = new DecimalFormat("#.##");
                    
                        // Create a list from the entries of totalVotosCirculo excluding "Brancos" and "Nulos"
                        List<Map.Entry<String, Integer>> list = totalVotosCirculo.entrySet().stream()
                            .filter(entry -> !entry.getKey().equals("Brancos") && !entry.getKey().equals("Nulos"))
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .collect(Collectors.toList());
                    
                        writer.println("--------------------Total Distrital-------------------");
                        writer.printf("Distrito: %s\n", leitura.getNomeCirculo());
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
                    // Fecha o ObjectInputStream
                    iS.close();
                    // Imprime a mensagem de erro e "Erro!" se ocorrer um erro
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Erro!");
                }
            }

            // Calcula o total geral de votos, votos brancos e votos nulos
            int totalGeralVotos = 0;
            int votosBrancos = totalVotos.getOrDefault("Brancos", 0);
            int votosNulos = totalVotos.getOrDefault("Nulos", 0);
            for (Integer votos : totalVotos.values()) {
                totalGeralVotos += votos;
            }
            // Calcula o total de votantes
            int totalVotantes = totalGeralVotos + votosBrancos + votosNulos;
            // Formata os números para duas casas decimais
            DecimalFormat df = new DecimalFormat("#.##");

            // Remove os votos brancos e nulos do total de votos
            totalVotos.remove("Brancos");
            totalVotos.remove("Nulos");

            // Ordena a lista de votos em ordem decrescente
            List<Map.Entry<String, Integer>> list = new ArrayList<>(totalVotos.entrySet());
            list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            // Escreve os resultados no ficheiro "Resultado/TotalNacional.txt"
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

                // Para cada entrada na lista, calcula a percentagem de votos e escreve no ficheiro
                for (Map.Entry<String, Integer> entry : list) {
                    double percentagem = (double) entry.getValue() / totalVotantes * 100;
                    writer.printf("Partido: %s, Votos: %d, Percentagem: %.2f%%\n", entry.getKey(), entry.getValue(),
                            percentagem);
                }
            
            // Captura e imprime qualquer exceção que possa ocorrer durante a escrita no ficheiro
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Erro!");
            }
        }
        // Imprime uma mensagem final indicando que o total nacional foi escrito no ficheiro "TotalNacional.txt"
        System.out.println("--------------------------------------------");
        System.out.println("Total nacional no ficheiro TotalNacional.txt");
    }
}