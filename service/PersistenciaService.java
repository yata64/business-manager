package service;

public class PersistenciaService {
    private static final String DIR = "dados/";

    public static void salvarDados(String nomeArquivo, String conteudo) {
        try (java.io.FileWriter fw = new java.io.FileWriter(DIR + nomeArquivo, true);
             java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
            bw.write(conteudo);
            bw.newLine();
        } catch (java.io.IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public static java.util.List<String> carregarDados(String nomeArquivo) {
        java.util.List<String> linhas = new java.util.ArrayList<>();
        java.io.File arquivo = new java.io.File(DIR + nomeArquivo);
        
        if (!arquivo.exists()) return linhas;

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (java.io.IOException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
        return linhas;
    }

}