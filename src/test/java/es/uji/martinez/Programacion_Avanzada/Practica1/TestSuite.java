package es.uji.martinez.Programacion_Avanzada.Practica1;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;



@Suite
@SuiteDisplayName("Lanzar todos los tests")
@SelectPackages({"es.uji.martinez.Programacion_Avanzada.Practica1", "es.uji.martinez.Programacion_Avanzada.Practica1.csv", "es.uji.martinez.Programacion_Avanzada.Practica1.table"})
@IncludeClassNamePatterns(".*Test")
public class TestSuite {
}
