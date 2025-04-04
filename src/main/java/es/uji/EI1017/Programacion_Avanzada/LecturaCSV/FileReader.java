package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class FileReader<T extends Table> extends ReaderTemplate<T> {
    protected Scanner scanner;

    @Override
    protected void openSource(String source) {
        try {
            scanner = new Scanner(new File(source));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo no encontrado: " + source);
        }
    }

    @Override
    protected String getHeaders() {
        return scanner.hasNextLine() ? scanner.nextLine() : "";
    }

    @Override
    protected boolean hasMoreData() {
        return scanner.hasNextLine();
    }

    @Override
    protected String getNextData() {
        return scanner.nextLine();
    }

    @Override
    protected void closeSource() {
        if (scanner != null) {
            scanner.close();
        }
    }
}


