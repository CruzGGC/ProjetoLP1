import java.io.*;

public class ExemploCriar {
    public static void main(String[] args) {
        VotosCirculoEleitoral Distrito = new VotosCirculoEleitoral("Aveiro");
        try (FileOutputStream os = new FileOutputStream("teste.dat");
                ObjectOutputStream oS = new ObjectOutputStream(os)) {
            oS.writeObject(Distrito);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Erro!");
        }
    }
}