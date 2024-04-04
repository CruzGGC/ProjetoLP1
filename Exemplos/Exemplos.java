package Exemplos;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Exemplos {
    public static void main(String[] args) {
        VotosCirculoEleitoral Distrito = new VotosCirculoEleitoral("Aveiro"); // Criação de um objeto do tipo VotosEleitoral
        try {

            // Criar um ficheiro de objetos e escrever um objeto do tipo VotosEleitoral no ficheiro criado

            FileOutputStream os = new FileOutputStream("teste.dat"); // Criação de um ficheiro
            ObjectOutputStream oS; // Criação de um objeto do tipo ObjectOutputStream
            oS = new ObjectOutputStream(new FileOutputStream("teste.dat")); // Criação de um objeto do tipo ObjectOutputStream
            oS.writeObject(Distrito); // Criado ficheiro com um objeto do tipo VotosEleitoral (linha 15)
            oS.close(); // Fechar o ObjectOutputStream
            os.close();

            // Ler um ficheiro de objetos e ler um objeto do tipo VotosEleitoral do ficheiro lido e fazer cast para o tipo de objeto correto e imprimir o nome do círculo eleitoral do objeto lido

            ObjectInputStream iS; // Criação de um objeto do tipo ObjectInputStream
            iS = new ObjectInputStream(new FileInputStream("Distritos/circulo_coimbra.dat")); // Criação de um objeto do tipo ObjectInputStream
            VotosCirculoEleitoral leitura = (VotosCirculoEleitoral) iS.readObject(); // Ler o objeto do ficheiro e fazer cast para o tipo de objeto correto
            System.out.println(leitura.getNomeCirculo()); // Ler o objeto do ficheiro e fazer cast para o tipo de objeto correto
            iS.close(); // Fechar o ObjectInputStream
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Erro!");
        }
    }
}
