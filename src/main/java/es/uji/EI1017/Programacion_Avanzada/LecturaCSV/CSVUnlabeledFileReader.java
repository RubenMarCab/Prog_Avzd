package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import java.util.ArrayList;
import java.util.List;

public class CSVUnlabeledFileReader extends FileReader<Table> {

    @Override
    protected void processHeaders(String headers) {
        String[] headerColumns = headers.split(",");

        for (String column : headerColumns) {
            tableBeingBuilt.addColumn(column.trim());
        }
    }

    @Override
    protected void processData(String data, Table table) {
        String[] tokens = data.split(",");
        List<Double> rowData = new ArrayList<>();
        for (String token : tokens) {
            rowData.add(Double.parseDouble(token.trim()));
        }
        Row row = new Row(rowData);
        table.addRow(row);

    }

    @Override
    protected Table createTable() {
        return new Table();
    }
}

