package es.uji.EI1017.Programacion_Avanzada;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;



@Suite
@SuiteDisplayName("Lanzar todos los tests")
@SelectPackages({"es.uji.EI1017.Programacion_Avanzada", "es.uji.EI1017.Programacion_Avanzada.LecturaCSV.CSV", "es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Table"})
@IncludeClassNamePatterns(".*Test")
public class TestSuite {
}
