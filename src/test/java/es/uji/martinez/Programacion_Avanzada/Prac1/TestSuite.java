package es.uji.martinez.Programacion_Avanzada.Prac1;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;



@Suite
@SuiteDisplayName("Lanzar todos los tests")
@SelectPackages({"es.uji.martinez.Programacion_Avanzada", "es.uji.martinez.Programacion_Avanzada.csv.CSV", "es.uji.martinez.Programacion_Avanzada.TABLE.Table"})
@IncludeClassNamePatterns(".*Test")
public class TestSuite {
}
