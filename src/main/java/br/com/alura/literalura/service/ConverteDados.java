package br.com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverteDados implements  IConverteDados {

    private ObjectMapper mapper = new ObjectMapper();

    public String extraiObjetoJson(String json, String objeto){
        try {
            JsonNode jsonCompleto = mapper.readTree(json);
            JsonNode jsonLivro = jsonCompleto.path("results");
            return jsonLivro.toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class, classe);

        try {
            return mapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
