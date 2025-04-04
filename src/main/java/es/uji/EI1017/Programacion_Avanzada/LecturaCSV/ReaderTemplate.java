package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;


public abstract class ReaderTemplate<T extends Table> {

        // Método plantilla para leer la tabla
        public final T readTableFromSource(String source) {
            openSource(source);
            String headers = getHeaders();
            processHeaders(headers);
            T table = createTable();
            while (hasMoreData()) {
                String data = getNextData();
                processData(data, table);
            }
            closeSource();
            return table;
        }

        protected abstract void openSource(String source);
        protected abstract String getHeaders();
        protected abstract void processHeaders(String headers);
        protected abstract boolean hasMoreData();
        protected abstract String getNextData();
        protected abstract void processData(String data, T table);
        protected abstract void closeSource();
        protected abstract T createTable();
}


