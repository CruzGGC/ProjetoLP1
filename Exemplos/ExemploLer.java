import java.io.*;
import java.util.*;

public class ExemploLer {
    public static void main(String[] args) {
        try (ObjectInputStream iS = new ObjectInputStream(new FileInputStream("Distritos/circulo_coimbra.dat"))) {
            
            VotosCirculoEleitoral leitura = (VotosCirculoEleitoral) iS.readObject();
            System.out.println(leitura.getNomeCirculo());

            // Imprimir os conselhos
            
            Map<String, VotosConcelho> votosPorConcelho = leitura.getVotosPorConcelho();
            Iterator<String> iterator = votosPorConcelho.keySet().iterator();
            String primeiroConcelho = iterator.next(); // Obtém o primeiro conselho
            System.out.println("Primeiro conselho: " + primeiroConcelho);
            String segundoConcelho = iterator.next(); // Obtém o segundo conselho
            System.out.println("Segundo conselho: " + segundoConcelho);
            
            // Imprimir os partidos do primeiro conselho

            VotosConcelho votosprimeiroConcelhos = votosPorConcelho.get(primeiroConcelho);
            Iterator<String> partidoIterator = votosprimeiroConcelhos.getVotosPorPartido().keySet().iterator();
            String primeiroPartido = partidoIterator.next(); // Obtém o primeiro partido
            System.out.println("Primeiro partido: " + primeiroPartido);
            String segundoPartido = partidoIterator.next(); // Obtém o segundo partido
            System.out.println("Segundo partido: " + segundoPartido);

            // Imprimir a quantidade de votos do primeiro partido do primeiro conselho

            Integer votosPrimeiroPartido = votosprimeiroConcelhos.getVotosPorPartido().get(primeiroPartido);
            System.out.println("Votos do primeiro partido: " + votosPrimeiroPartido);
            Integer votosSegundoPartido = votosprimeiroConcelhos.getVotosPorPartido().get(segundoPartido);
            System.out.println("Votos do segundo partido: " + votosSegundoPartido);
            
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}