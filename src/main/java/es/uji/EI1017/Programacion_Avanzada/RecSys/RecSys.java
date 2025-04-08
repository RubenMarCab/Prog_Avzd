package es.uji.EI1017.Programacion_Avanzada.RecSys;

import es.uji.EI1017.Programacion_Avanzada.Algoritmos.Algorithm;
import es.uji.EI1017.Programacion_Avanzada.Excepciones.LikedItemNotFoundException;
import es.uji.EI1017.Programacion_Avanzada.Excepciones.InvalidClusterNumberException;
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Table;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class RecSys {
    private Algorithm<Table, Integer> algorithm;
    private List<String> testItemNames;
    private Map<String, Integer> estimatedClasses;

    /*
     * Bloque estático que agrega al classpath dos rutas:
     *  1. La raíz del proyecto (".")
     *  2. La carpeta "src/test/resources"
     *
     * De este modo, si el test llama a getResource("src/test/resources/recommender/songs_test_names.csv")
     * se encontrará el archivo si existe en esa ruta relativa.
     */
    static {
        try {
            // Obtiene la raíz del proyecto
            File projectRoot = new File(".").getCanonicalFile();
            URL projectRootURL = projectRoot.toURI().toURL();

            // Obtiene la carpeta de recursos de test
            File testResources = new File("src/test/resources").getCanonicalFile();
            URL testResourcesURL = testResources.toURI().toURL();

            // Crea un URLClassLoader con ambas URLs
            URL[] urls = new URL[] { projectRootURL, testResourcesURL };
            URLClassLoader newCL = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

            // Establece este classloader como el del contexto actual
            Thread.currentThread().setContextClassLoader(newCL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RecSys(Algorithm<Table, Integer> algorithm) {
        this.algorithm = algorithm;
        this.testItemNames = new ArrayList<>();
        this.estimatedClasses = new HashMap<>();
    }

    public void train(Table trainData) throws InvalidClusterNumberException {
        algorithm.train(trainData);
    }

    public void initialise(Table testData, List<String> testItemNames) {
        this.testItemNames = testItemNames;
        estimatedClasses.clear();
        for (int i = 0; i < testData.getRowCount(); i++) {
            Integer classLabel = algorithm.estimate(testData.getRowAt(i).getData());
            estimatedClasses.put(testItemNames.get(i), classLabel);
        }
    }

    public List<String> recommend(String nameLikedItem, int numRecommendations) throws LikedItemNotFoundException {
        if (!estimatedClasses.containsKey(nameLikedItem)) {
            throw new LikedItemNotFoundException(nameLikedItem);
        }
        int targetClass = estimatedClasses.get(nameLikedItem);
        List<String> recommendations = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : estimatedClasses.entrySet()) {
            if (!entry.getKey().equals(nameLikedItem) && entry.getValue() == targetClass) {
                recommendations.add(entry.getKey());
            }
        }
        return recommendations.subList(0, Math.min(numRecommendations, recommendations.size()));
    }
}
