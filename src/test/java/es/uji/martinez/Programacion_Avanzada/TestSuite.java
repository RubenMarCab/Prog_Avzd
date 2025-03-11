package es.uji.martinez.Programacion_Avanzada;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;



@Suite
@SuiteDisplayName("Lanzar todos los tests")
@SelectPackages({"es.uji.martinez.Programacion_Avanzada", "es.uji.martinez.Programacion_Avanzada.CSV.csv", "es.uji.martinez.Programacion_Avanzada.TABLE.table"})
@IncludeClassNamePatterns(".*Test")
public class TestSuite {
}
