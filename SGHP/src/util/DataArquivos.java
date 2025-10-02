package util;

import java.nio.file.Files;
import java.nio.file.Path;

public final class DataArquivos {
    private static final Path DATA_DIR = init();

    private DataArquivos() {
    }

    private static Path init() {
        Path base = Path.of(System.getProperty("user.dir"));
        Path data = base.resolve("data");
        try {
            Files.createDirectories(data);
        } catch (Exception e) {
            System.out.println("Aviso: não foi possível criar a pasta data: " + e.getMessage());
        }
        return data;
    }

    public static Path arquivo(String nome) {
        return DATA_DIR.resolve(nome);
    }

    public static Path dir() {
        return DATA_DIR;
    }
}
